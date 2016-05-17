package br.com.fatec.netty.chat.example.view;


import br.com.fatec.netty.chat.example.domain.UserChannel;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CommandView extends Stage {

	private UserChannel userChannel;
	
	public CommandView(UserChannel userChannel) {
		this.userChannel = userChannel;
		
		setTitle("Gateway:::Enviar Mensagem");
		setScene(new Scene(createContent(), 200, 150));
	}

	private Parent createContent() {
		
		VBox root = new VBox();
		
		TextArea input = new TextArea();
		
		Button button = new Button("Enviar");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(()->{
					//FIXME: Deve ser usada a implementação de ChannelWrite
					
					Channel channel = userChannel.getChannel();
					channel.writeAndFlush( input.getText() + "\n" );
				});
			}
		});
		
		root.getChildren().addAll(input, button);
		
		return root;
	}

}
