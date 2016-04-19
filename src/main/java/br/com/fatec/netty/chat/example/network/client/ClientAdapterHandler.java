package br.com.fatec.netty.chat.example.network.client;

import javax.swing.JTextArea;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Canal de transmissão e recepção de dados
 * 
 * @author Fabio S. da Silveira 
 * @author Jair Jr Batista 
 * @author Warnner A. F. Sinotti
 *
 */
public class ClientAdapterHandler extends SimpleChannelInboundHandler<String> {

	//FIXME: O ideal é tratar esta referencia atraves de um observer ou evento
	private JTextArea chatjTextArea;

	//---------------------------------------------------------------------------------------------------------------
	public ClientAdapterHandler(JTextArea chatjTextArea) {
		this.chatjTextArea = chatjTextArea;
	}

	//---------------------------------------------------------------------------------------------------------------
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		Channel currentChannel = ctx.channel();
		String text = chatjTextArea.getText();
		chatjTextArea.setText( text + "[INFO] - " + currentChannel.remoteAddress() + " - " + message);

		//brincadeira do ping/pong
		if ("pong\n".equals(message)) {
			Thread.sleep(1000);
			currentChannel.writeAndFlush("ping\n");
		}
	}
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------

}
