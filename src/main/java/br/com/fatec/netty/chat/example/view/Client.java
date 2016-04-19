package br.com.fatec.netty.chat.example.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class Client extends Stage {

	private TextArea message = new TextArea();

	public Client() {
		setTitle("Canal:::Transmissor");
		setScene(new Scene(createContent()));
	}

	private Parent createContent() {

		message.setPrefHeight(370);
		TextArea input = new TextArea();
		input.setPrefSize(280,72);

		Button buttonSend = new Button("Enviar");
		buttonSend.setPrefSize(100, 72);

		HBox footPanel = new HBox(10, input, buttonSend);
		footPanel.setAlignment(Pos.CENTER);

		VBox root = new VBox(10, message, footPanel);
		root.setPrefSize(450, 450);

		return root;
	}

}
