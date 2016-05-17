package br.com.fatec.netty.chat.example.network.actions;

import br.com.fatec.netty.chat.example.network.ChannelWrite;
import br.com.fatec.netty.chat.example.network.CommandAction;

public class Pong implements Action {

	private CommandAction command;

	public Pong(CommandAction command) {
		this.command = command;
	}
	
	@Override
	public String type() {
		return "/pong";
	}

	@Override
	public void execute(ChannelWrite write, String channelId) {
		try {
			Thread.sleep(1000);
			Ping ping = new Ping(command);
			write.sendAndFlushMensage(channelId, ping.type());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
