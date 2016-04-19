package br.com.fatec.netty.chat.example.view;

import br.com.fatec.netty.chat.example.domain.UserChannel;
import br.com.fatec.netty.chat.example.network.NetworkConsumerCallback;
import br.com.fatec.netty.chat.example.network.client.ClientContainer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
public class ClientView extends Stage {

	private TextArea textAreaMessage = new TextArea();

	private ClientContainer container = createClient();

	public ClientView() {
		setTitle("Gateway:::Transmissor");
		setScene(new Scene(createContent()));
		setOnCloseRequest(new EventHandler<WindowEvent>() {	
			@Override
			public void handle(WindowEvent event) {
				container.stop();
			}
		});
	}

	private ClientContainer createClient() {

		return new ClientContainer("localhost", 5252, new NetworkConsumerCallback<UserChannel>() {

			@Override
			public void registerCallback(UserChannel t) {
				Platform.runLater(() -> {
					textAreaMessage.appendText("Conexao realizada com sucesso\n");
				});
			}

			@Override
			public void unregisterCallback(String channelId) {
				Platform.runLater(() -> {
					textAreaMessage.appendText("Conexao finalizada");
				});
			}

			@Override
			public void processCallback(UserChannel data, String message) {
				Platform.runLater(() -> {
					textAreaMessage.appendText("[" + data.getName() + "]:::=> " + message + "\n");
				});
			}

		});
	}

	private Parent createContent() {

		textAreaMessage.setPrefHeight(370);
		TextArea input = new TextArea();
		input.setPrefSize(280, 72);

		Button buttonSend = new Button("Enviar");
		buttonSend.setPrefSize(100, 72);
		buttonSend.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(() -> {
					container.sendAndFlushMensage(input.getText());
				});
			}
		});

		HBox footPanel = new HBox(10, input, buttonSend);
		footPanel.setAlignment(Pos.CENTER);

		VBox root = new VBox(10, textAreaMessage, footPanel);
		root.setPrefSize(450, 450);

		return root;
	}

	public void init() {
		container.run();
		show();
	}

}
