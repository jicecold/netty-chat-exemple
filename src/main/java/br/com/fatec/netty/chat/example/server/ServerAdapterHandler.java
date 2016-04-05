package br.com.fatec.netty.chat.example.server;

import java.math.BigInteger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerAdapterHandler extends SimpleChannelInboundHandler<String> {

	private boolean isPing = false;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel currentChannel = ctx.channel();
		System.out.print("[INFO] - " + currentChannel.remoteAddress() + " - " + msg);
		String binary = new BigInteger(msg.replace("\n", "").getBytes()).toString(2);
		System.out.println("[INFO] - Em binario: " + binary + "\n");

		if ("ping\n".equals(msg) && isPing) {
			Thread.sleep(1000);
			currentChannel.writeAndFlush("pong\n");
		} else {
			isPing = false;

			if ("start ping\n".equals(msg)) {
				isPing = true;
				currentChannel.writeAndFlush("pong\n");
			} else {
				currentChannel.writeAndFlush("[Receptor] - Mensagem Recebida: " + msg + "\n");
			}
		}

	}

}