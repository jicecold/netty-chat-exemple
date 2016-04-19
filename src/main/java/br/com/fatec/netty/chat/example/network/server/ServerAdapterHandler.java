package br.com.fatec.netty.chat.example.network.server;

import br.com.fatec.netty.chat.example.network.ChannelWrite;
import br.com.fatec.netty.chat.example.network.NetworkChannelCallback;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class ServerAdapterHandler extends SimpleChannelInboundHandler<String> implements ChannelWrite {

	private NetworkChannelCallback callback;

	public ServerAdapterHandler(NetworkChannelCallback callback) {
		this.callback = callback;
	}

	// A brincadeira do ping esta valendo?
	private boolean isPing = false;

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

		// // faz o print da mensagem normal na tela
		// System.out.print("[Transmissor: ] - " +
		// currentChannel.remoteAddress() + " - " + msg);
		//
		// // Converte em mensagem binario, FIXME Ideal passar para outra camada
		// String binary = new BigInteger(msg.getBytes()).toString(2);
		// System.out.println("[INFO] - Em binario: " + binary + "\n");
		//
		// // checa se a mensagem é um ping
		// if ("ping".equals(msg) && isPing) {
		// // Aguarda 1 segundo
		// Thread.sleep(1000);
		// // Transmite a mensagem "pong" pelo canal
		// sendAndFlushMensage(currentChannel, "pong");
		// } else {
		// // FIXME: Este bloco pode ser melhorado e esta logica pode ser
		// // extraida para outra camada
		// // muda a flag da brincadeira do ping para false
		// isPing = false;
		//
		// // Se o comando "start ping" chegar pelo canal
		// if ("start ping".equals(msg)) {
		// // Muda a flag da brincadeira do ping para true
		// isPing = true;
		// // envia o primeiro pong
		// sendAndFlushMensage(currentChannel, "pong");
		// } else {
		// // Qualquer outra mensagem envia a reposta pelo canal
		// sendAndFlushMensage(currentChannel, "[Receptor] - Mensagem Recebida:
		// " + msg);
		// }
		// }
	}

	public void sendAndFlushMensage(Channel channel, String msg) {
		channel.writeAndFlush(msg + "\n");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		super.channelUnregistered(ctx);
		callback.OnCloseChannel(ctx, "Canal removido");
	}

	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
}