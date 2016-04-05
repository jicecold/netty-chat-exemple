package br.com.fatec.netty.chat.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerContainer extends Thread {

	private int port;

	public ServerContainer() {
		this(5252);
	}

	public ServerContainer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		EventLoopGroup producer = new NioEventLoopGroup();
		EventLoopGroup consumer = new NioEventLoopGroup();

		try {

			ServerBootstrap bootstrap = new ServerBootstrap().group(producer, consumer)
					.channel(NioServerSocketChannel.class).childHandler(new ServerAdapterInitializer());
			System.out.println("Server started");
			bootstrap.bind(port).sync().channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdownGracefully();
			consumer.shutdownGracefully();
		}

	}

}