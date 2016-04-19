package br.com.fatec.netty.chat.example.network.client;

import br.com.fatec.netty.chat.example.network.ChannelListener;
import br.com.fatec.netty.chat.example.network.NetworkChannelCallback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ClientAdapterInitializer extends ChannelInitializer<SocketChannel> implements NetworkChannelCallback {

	private ChannelListener listener;

	// ---------------------------------------------------------------------------------------------------------------
	public ClientAdapterInitializer(ChannelListener listener) {
		this.listener = listener;
	}

	// ---------------------------------------------------------------------------------------------------------------
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();

		// Tranforma os bytes recebido em uma string
		pipeline.addLast("decoder", new StringDecoder());
		// Tranforma os bytes transmitidos em uma string
		pipeline.addLast("encoder", new StringEncoder());
		// trata a recepção e transmissão com o servidor, deve estar depois da
		// codificação
		pipeline.addLast("handler", new ClientAdapterHandler(this));
	}
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------

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
