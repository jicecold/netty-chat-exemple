package br.com.fatec.netty.chat.example.network;

import io.netty.channel.Channel;

public interface ChannelListener {

	void fireOpenChannel(Channel ctx, String message);

	void fireCloseChannel(Channel ctx, String message);

	void fireReceivedMessage(Channel ctx, String message);

}
