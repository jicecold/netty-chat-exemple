package br.com.fatec.netty.chat.example.network.client;

import br.com.fatec.netty.chat.example.domain.UserChannel;
import br.com.fatec.netty.chat.example.network.ChannelListener;
import br.com.fatec.netty.chat.example.network.ChannelWrite;
import br.com.fatec.netty.chat.example.network.CommandAction;
import br.com.fatec.netty.chat.example.network.DefaultCommandAction;
import br.com.fatec.netty.chat.example.network.NetworkConsumerCallback;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ClientContainer implements ChannelListener, ChannelWrite {

	private UserChannel userChannel;
	private String server;
	private int port;
	private EventLoopGroup group;
	private CommandAction commandAction = new DefaultCommandAction();
	private NetworkConsumerCallback<UserChannel> callback;

	// ---------------------------------------------------------------------------------------------------------------
	public ClientContainer(String server, int port, NetworkConsumerCallback<UserChannel> callback) {
		this.server = server;
		this.port = port;
		this.callback = callback;
	}

	// ---------------------------------------------------------------------------------------------------------------
	public void run() {
		group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
					.handler(new ClientAdapterInitializer(this));
			bootstrap.connect(server, port).sync().channel();

		} catch (Exception e) {
			e.printStackTrace();
			group.shutdownGracefully();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------
	public void stop() {
		Channel channel = userChannel.getChannel();
		channel.disconnect();
		group.shutdownGracefully();
	}

	private UserChannel createUserChannel(Channel ctx) {

		String channelId = ctx.id().asShortText();
		UserChannel userChannel = new UserChannel();
		userChannel.setId(channelId);
		userChannel.setName("Server");
		userChannel.setChannel(ctx);
		// userChannel.setAddress(ctx.localAddress().toString());
		return userChannel;
	}

	@Override
	public void fireOpenChannel(Channel ctx, String message) {
		String channelId = ctx.id().asShortText();
		userChannel = createUserChannel(ctx);
		callback.registerCallback(userChannel);

		System.out.println(message + ":" + channelId);

	}

	@Override
	public void fireCloseChannel(Channel ctx, String message) {
		String channelId = ctx.id().asShortText();
		callback.unregisterCallback(channelId);
	}

	@Override
	public void fireReceivedMessage(Channel ctx, String message) {
		String channelId = ctx.id().asShortText();

		if (!message.trim().equals("")) {
			callback.processCallback(userChannel, message);
			
			if( message.startsWith("/") ){
				commandAction.processAction(this, channelId, message);
			}
			
			System.out.println(channelId + ":::=>" + message);
		}

	}

	/**
	 * Envia a mensagem pelo canal
	 * 
	 * @param message
	 */
	@Override
	public void sendAndFlushMensage(Channel channel, String message) {
		if (channel.isRegistered()) {
			channel.write(message + "\n");
			channel.flush();
		} else {
			System.out.println("Canal fechado");
		}
	}

	@Override
	public void sendAndFlushMensage(String channelId, String message) {
		Channel channel = userChannel.getChannel();
		sendAndFlushMensage(channel, message);
	}

	public void sendAndFlushMensage(String message) {
		Channel channel = userChannel.getChannel();
		sendAndFlushMensage(channel, message);
	}

	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
}
