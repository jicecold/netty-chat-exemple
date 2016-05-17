package br.com.fatec.netty.chat.example.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.setTitle("Gateway Simulador de Recepção/Transmissão de dados");

		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

	private Parent createContent() {

		Button buttonServer = new Button("Iniciar Receptor");
		buttonServer.setPrefSize(200, 50);
		buttonServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ServerView server = new ServerView();
				server.init();
			}
		});

		Button buttonClient = new Button("Iniciar Transmissor");
		buttonClient.setPrefSize(200, 50);
		buttonClient.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ClientView client = new ClientView();
				client.init();
			}
		});

		VBox root = new VBox(20, buttonServer, buttonClient);
		root.setAlignment(Pos.CENTER);
		root.setPrefSize(280, 280);

		return root;
	}

}
