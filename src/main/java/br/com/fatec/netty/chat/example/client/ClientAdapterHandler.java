package br.com.fatec.netty.chat.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientAdapterHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String message) throws Exception {
		
		System.out.println(message);
        if (message.equals("quit"))
            throw new RuntimeException("Server is closed");
		
	}



}
