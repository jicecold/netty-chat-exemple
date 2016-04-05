package br.com.fatec.netty.chat.example.client;

import javax.swing.JTextArea;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientContainer {

	private String server;
	private int port;
	private Channel channel;
	private EventLoopGroup group;
	private JTextArea chatjTextArea;

	public ClientContainer(String server, int port, JTextArea chatjTextArea) {
		this.server = server;
		this.port = port;
		this.chatjTextArea = chatjTextArea;
	}

	public void start() {
		group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
					.handler(new ClientAdapterInitializer(chatjTextArea));

			channel = bootstrap.connect(server, port).sync().channel();

		} catch (Exception e) {
			e.printStackTrace();
			group.shutdownGracefully();
		}
	}

	public void stop() {
		group.shutdownGracefully();
	}

	public void sendAndFlushMessage(String message) {
		if (channel.isOpen()) {
			channel.write(message + "\n");
			channel.flush();
		} else {
			System.out.println("Canal fechado");
		}
	}

	public Boolean isOpen() {
		return channel.isOpen();
	}

}
