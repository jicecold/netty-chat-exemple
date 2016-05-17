package br.com.fatec.netty.chat.example.network.actions;

import br.com.fatec.netty.chat.example.network.ChannelWrite;
import br.com.fatec.netty.chat.example.network.CommandAction;

public class Ping implements Action {

	private CommandAction command;

	public Ping(CommandAction command) {
		this.command = command;
	}

	@Override
	public String type() {
		return "/ping";
	}

	@Override
	public void execute(ChannelWrite write, String channelId) {
		try {
			Thread.sleep(1000);
			Pong pong = new Pong(command);
			write.sendAndFlushMensage(channelId, pong.type());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
