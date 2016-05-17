package br.com.fatec.netty.chat.example.network;

public interface CommandAction {

	void processAction(ChannelWrite write, String channelId, String action);

	void disableAction(String type);

}
