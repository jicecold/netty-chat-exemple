package br.com.fatec.netty.chat.example.network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.fatec.netty.chat.example.network.actions.Action;
import br.com.fatec.netty.chat.example.network.actions.Ping;
import br.com.fatec.netty.chat.example.network.actions.Pong;
import br.com.fatec.netty.chat.example.network.actions.Stop;

public class DefaultCommandAction implements CommandAction {

	List<Action> enabledActions = new CopyOnWriteArrayList<Action>(new ArrayList<Action>());

	public DefaultCommandAction() {
		enabledActions.add(new Ping(this));
		enabledActions.add(new Pong(this));
		enabledActions.add(new Stop(this));
	}

	@Override
	public void processAction(ChannelWrite write, String channelId, String type) {

		for (Action action : enabledActions) {

			if (type.equals(action.type())) {
				action.execute(write, channelId);
			}
		}

	}

	@Override
	public void disableAction(String type) {
		for (Action action : enabledActions) {

			if (type.equals(action.type())) {
				enabledActions.remove(action);
			}
		}

	}

}
