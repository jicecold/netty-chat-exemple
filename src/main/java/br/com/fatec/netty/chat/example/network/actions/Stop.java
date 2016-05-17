package br.com.fatec.netty.chat.example.network.actions;

import br.com.fatec.netty.chat.example.network.ChannelWrite;
import br.com.fatec.netty.chat.example.network.CommandAction;

public class Stop implements Action {

	private CommandAction command;

	public Stop(CommandAction command) {
		this.command = command;
	}

	@Override
	public String type() {
		return "/stop";
	}

	@Override
	public void execute(ChannelWrite write, String channelId) {
		Pong pong = new Pong(command);
		command.disableAction(pong.type());
	}

}
