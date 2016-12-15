package br.com.fatec.netty.chat.example.view;

import java.math.BigInteger;
import java.util.ArrayList;

import br.com.fatec.netty.chat.example.domain.UserChannel;
import br.com.fatec.netty.chat.example.network.NetworkConsumerCallback;
import br.com.fatec.netty.chat.example.network.server.ServerContainer;
import br.com.fatec.netty.chat.example.view.utils.ContextMenuListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ServerView extends Stage {

	private TextArea console;
	private ServerContainer container = createServer();
	private ObservableList<UserChannel> userList;

	public ServerView() {
		ObservableList<UserChannel> observableList = FXCollections.observableList(new ArrayList<UserChannel>());
		this.userList = FXCollections.synchronizedObservableList(observableList);

		setTitle("Gateway:::Receptor");
		setScene(new Scene(createContent(), 800, 600));
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				container.stop();
			}
		});
	}

	private ServerContainer createServer() {
		return new ServerContainer(5252, new NetworkConsumerCallback<UserChannel>() {

			@Override
			public void unregisterCallback(String channelId) {
				Platform.runLater(() -> {
					for (UserChannel userChannel : userList) {

						if (channelId.equals(userChannel.getId())) {
							userList.remove(userChannel);
							System.out.println(userChannel.getId() + ":::=> Canal removido");
						}

					}
				});
			}

			@Override
			public void registerCallback(UserChannel data) {
				Platform.runLater(() -> {
					UserChannel userChannel = (UserChannel) data;
					userList.add(userChannel);
				});
			}

			@Override
			public void processCallback(UserChannel data, String message) {
				Platform.runLater(() -> {

					console.appendText("[" + data.getName() + data.getAddress() + "]:::=> " + message + "\n");

					String messages[] = message.split(" ");

					for (String msg : messages) {

						// TODO: Fazer uma interface de output
						String binary = new BigInteger(msg.getBytes()).toString(2);
						console.appendText("[Palavra]:::>" + msg + "\t[Binario]:::=>" + binary + "\n");
					}
				});
			}
		});
	}

	private Parent createContent() {

		SplitPane root = new SplitPane();

		// Left Panel
		VBox leftPane = new VBox(20);
		leftPane.setAlignment(Pos.CENTER);

		Label leftLabel = new Label("Canais Abertos:");
		leftLabel.setPrefHeight(40);
		leftPane.getChildren().add(leftLabel);

		ListView<UserChannel> listView = new ListView<UserChannel>(userList);

		MenuItem menu = new MenuItem("Enviar Comando");
		menu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				UserChannel userChannel = listView.getSelectionModel().getSelectedItem();
				CommandView view = new CommandView(userChannel);
				view.show();
			}
		});
		ContextMenu contextMenu = new ContextMenu(menu);

		// listView.setPrefHeight(560);
		// listView.setPrefWidth(320);
		listView.setEditable(false);

		listView.setCellFactory(ContextMenuListCell.forListView(contextMenu));

		ScrollPane scrollPaneLeft = new ScrollPane(listView);
		listView.prefHeightProperty().bind(scrollPaneLeft.heightProperty());
		listView.prefWidthProperty().bind(scrollPaneLeft.widthProperty());

		leftPane.getChildren().add(scrollPaneLeft);
		scrollPaneLeft.prefHeightProperty().bind(leftPane.heightProperty());
		scrollPaneLeft.prefWidthProperty().bind(leftPane.widthProperty());
		// .Left Panel

		// Rigth Panel
		VBox rightPanel = new VBox(10);
		// rightPanel.setPrefSize(500, 600);
		rightPanel.setAlignment(Pos.CENTER);

		Label rightLabel = new Label("Console ::: Logs");
		rightLabel.setPrefHeight(40);

		Button rigthButton = new Button("Limpar Console");
		rigthButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				console.clear();
			}
		});

		HBox rightHeader = new HBox(rightLabel, rigthButton);
		rightHeader.setAlignment(Pos.CENTER);
		rightLabel.prefWidthProperty().bind(rightHeader.widthProperty().subtract(150));

		rightPanel.getChildren().add(rightHeader);
		rightHeader.prefWidthProperty().bind(rightPanel.widthProperty());

		console = new TextArea();

		ScrollPane scrollPaneRight = new ScrollPane(console);
		console.prefHeightProperty().bind(scrollPaneRight.heightProperty());
		console.prefWidthProperty().bind(scrollPaneRight.widthProperty());

		rightPanel.getChildren().add(scrollPaneRight);
		scrollPaneRight.prefHeightProperty().bind(rightPanel.heightProperty());
		scrollPaneRight.prefWidthProperty().bind(rightPanel.widthProperty());
		// .Rigth Panel

		root.getItems().addAll(leftPane, rightPanel);
		root.setDividerPositions(0.30);

		leftPane.maxWidthProperty().bind(root.widthProperty().multiply(0.50));
		leftPane.minWidthProperty().bind(root.widthProperty().multiply(0.30));

		return new BorderPane(root);
	}

	public void init() {
		container.run();
		show();
	}

}
