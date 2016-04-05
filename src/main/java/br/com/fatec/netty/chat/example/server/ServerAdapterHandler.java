package br.com.fatec.netty.chat.example.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerAdapterHandler extends SimpleChannelInboundHandler<String> {

	private boolean isPing = false;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel currentChannel = ctx.channel();
		System.out.println("[INFO] - " + currentChannel.remoteAddress() + " - " + msg);

		if ("ping\n".equals(msg) && isPing) {
			Thread.sleep(1000);
			currentChannel.writeAndFlush("pong\n");
		} else {
			isPing = false;

			if ("start ping\n".equals(msg)) {
				isPing = true;
				currentChannel.writeAndFlush("pong\n");
			} else {
				currentChannel.writeAndFlush("[Receptor] - Mensagem Recebida com sucesso: " + msg + "\n");
			}
		}

	}

}