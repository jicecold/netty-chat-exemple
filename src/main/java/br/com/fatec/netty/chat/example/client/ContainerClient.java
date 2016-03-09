package br.com.fatec.netty.chat.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ContainerClient {

	String server;
	int port;

	public ContainerClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public void start() {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
					.handler(new ClientAdapterInitializer());

			Channel channel = bootstrap.connect(server, port).sync().channel();

			channel.write("Hi\n");
			channel.write("Hi\n");
			channel.write("Hi\n");
			channel.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
