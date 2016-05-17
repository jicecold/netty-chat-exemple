package br.com.fatec.netty.chat.example.network.actions;

import br.com.fatec.netty.chat.example.network.ChannelWrite;

public interface Action {

	String type();

	void execute(ChannelWrite write, String channelId);

}
