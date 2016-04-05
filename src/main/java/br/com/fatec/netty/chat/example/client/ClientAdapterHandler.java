package br.com.fatec.netty.chat.example.client;

import javax.swing.JTextArea;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientAdapterHandler extends SimpleChannelInboundHandler<String> {

	private JTextArea chatjTextArea;

	public ClientAdapterHandler(JTextArea chatjTextArea) {
		this.chatjTextArea = chatjTextArea;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
		Channel currentChannel = ctx.channel();
		String text = chatjTextArea.getText();
		chatjTextArea.setText( text + "[INFO] - " + currentChannel.remoteAddress() + " - " + message);

		if ("pong\n".equals(message)) {
			Thread.sleep(1000);
			currentChannel.writeAndFlush("ping\n");
		}

	}

}
