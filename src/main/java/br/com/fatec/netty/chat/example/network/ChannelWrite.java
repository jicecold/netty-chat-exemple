package br.com.fatec.netty.chat.example.network;

import io.netty.channel.Channel;

public interface ChannelWrite {

	void sendAndFlushMensage(Channel channel, String msg);

	void sendAndFlushMensage(String channelId, String msg);

}
