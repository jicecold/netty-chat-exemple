package br.com.fatec.netty.chat.example.network;

import io.netty.channel.ChannelHandlerContext;

public interface NetworkChannelCallback {

	void OnOpenChannel(ChannelHandlerContext ctx, String string);
	
	void OnCloseChannel(ChannelHandlerContext ctx, String string);
	
	void OnProcess( ChannelHandlerContext ctx, String string );
	
}
