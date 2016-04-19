package br.com.fatec.netty.chat.example.network.server;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import br.com.fatec.netty.chat.example.domain.UserChannel;
import br.com.fatec.netty.chat.example.network.ChannelListener;
import br.com.fatec.netty.chat.example.network.NetworkConsumerCallback;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * FIXME: Melhorar a thread
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ServerContainer implements ChannelListener {

	public static final Map<String, UserChannel> connections = new ConcurrentHashMap<String, UserChannel>(10);
	private Thread thread;
	private ServerBootstrap bootstrap;
	private NetworkConsumerCallback<UserChannel> onReceiveCallback;
	private int port;

	public ServerContainer(int port, NetworkConsumerCallback<UserChannel> onReceiveCallback) {
		this.onReceiveCallback = onReceiveCallback;
		this.port = port;
	}

	public synchronized void run() {
		EventLoopGroup producer = new NioEventLoopGroup();
		EventLoopGroup consumer = new NioEventLoopGroup();

		thread = new Thread(() -> {
			try {
				System.out.println("Iniciando Servidor...");
				bootstrap = new ServerBootstrap();
				bootstrap.group(producer, consumer);
				bootstrap.channel(NioServerSocketChannel.class);
				bootstrap.childHandler(new ServerAdapterInitializer(this));

				System.out.println("Servidor iniciado");

				bootstrap.bind(port).sync().channel().closeFuture().sync();

				System.out.println("Servidor Parado");

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				producer.shutdownGracefully();
				consumer.shutdownGracefully();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public synchronized void stop() {
		// FIXME: Verificar como parar a thread, evitando o
		// java.lang.InterruptedException
		try {
			thread.interrupt();
			System.out.println("Servidor Encerrado");
		} catch (Exception e) {
			System.out.println("Houve um erro ao interromper a thread: " + e.getMessage());
		}
	}

	private UserChannel createUserChannel(Channel ctx) {

		String channelId = ctx.id().asShortText();
		UserChannel userChannel = new UserChannel();
		userChannel.setId(channelId);
		userChannel.setName(generateChannelName());
		userChannel.setChannel(ctx);
		userChannel.setAddress(ctx.remoteAddress().toString());
		return userChannel;
	}

	private String generateChannelName() {
		return "Canal " + new Random().nextInt(100);
	}

	@Override
	public void fireOpenChannel(Channel ctx, String message) {

		String channelId = ctx.id().asShortText();

		UserChannel userChannel = null;

		if (connections.containsKey(channelId)) {
			userChannel = connections.get(channelId);
		} else {
			userChannel = createUserChannel(ctx);
			connections.put(channelId, userChannel);
		}

		onReceiveCallback.registerCallback(userChannel);

		System.out.println(message + ":" + channelId);
	}

	@Override
	public void fireCloseChannel(Channel ctx, String message) {

		String channelId = ctx.id().asShortText();

		if (connections.containsKey(channelId)) {
			connections.remove(channelId);
		}

		onReceiveCallback.unregisterCallback(channelId);
	}

	@Override
	public void fireReceivedMessage(Channel ctx, String message) {

		String channelId = ctx.id().asShortText();

		if (connections.containsKey(channelId) && !message.trim().equals("")) {
			UserChannel userChannel = connections.get(channelId);
			onReceiveCallback.processCallback(userChannel, message);
			System.out.println(channelId + ":::=>" + message);
		}
	}

}