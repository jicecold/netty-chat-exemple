package br.com.fatec.netty.chat.example.network.client;

import br.com.fatec.netty.chat.example.network.NetworkChannelCallback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Canal de transmissão e recepção de dados
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ClientAdapterHandler extends SimpleChannelInboundHandler<String> {

	private NetworkChannelCallback callback;

	// ---------------------------------------------------------------------------------------------------------------
	public ClientAdapterHandler(NetworkChannelCallback callback) {
		this.callback = callback;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		callback.OnOpenChannel(ctx, "Canal registrado");
	}

	// ---------------------------------------------------------------------------------------------------------------
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		msg = msg.replace("\n", "").replace("\r", "");
		callback.OnProcess(ctx, msg);

		// Channel currentChannel = ctx.channel();
		// String text = chatjTextArea.getText();
		// chatjTextArea.setText(text + "[INFO] - " +
		// currentChannel.remoteAddress() + " - " + message);
		//
		// // brincadeira do ping/pong
		// if ("pong\n".equals(message)) {
		// Thread.sleep(1000);
		// currentChannel.writeAndFlush("ping\n");
		// }
	}
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		callback.OnCloseChannel(ctx, "Canal removido");
	}

}
