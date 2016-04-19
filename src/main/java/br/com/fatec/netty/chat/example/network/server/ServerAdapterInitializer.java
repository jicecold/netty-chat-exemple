package br.com.fatec.netty.chat.example.network.server;

import br.com.fatec.netty.chat.example.network.ChannelListener;
import br.com.fatec.netty.chat.example.network.NetworkChannelCallback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Configura o canal de transmissão e recepção de dados
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ServerAdapterInitializer extends ChannelInitializer<SocketChannel> implements NetworkChannelCallback {

	private ChannelListener listener;

	public ServerAdapterInitializer(ChannelListener listener) {
		this.listener = listener;
	}

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		// Obtem a pipeline atraves do socket
		ChannelPipeline pipeline = channel.pipeline();

		// Decodifica os bytes recebidos em string
		pipeline.addLast("decoder", new StringDecoder());
		// Codifica a mendagem em bytes
		pipeline.addLast("encoder", new StringEncoder());
		// Tratamento do canal lado receptor
		pipeline.addLast("handler", new ServerAdapterHandler(this));
	}

	@Override
	public void OnOpenChannel(ChannelHandlerContext ctx, String message) {
		listener.fireOpenChannel(ctx.channel(), message);
	}

	@Override
	public void OnCloseChannel(ChannelHandlerContext ctx, String message) {
		listener.fireCloseChannel(ctx.channel(), message);
	}

	@Override
	public void OnProcess(ChannelHandlerContext ctx, String message) {
		listener.fireReceivedMessage(ctx.channel(), message);
	}

}
