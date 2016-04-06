package br.com.fatec.netty.chat.example.server;

import java.math.BigInteger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author Jair Jr Batista
 *
 */
public class ServerAdapterHandler extends SimpleChannelInboundHandler<String> {

	//A brincadeira do ping esta valendo?
	private boolean isPing = false;

	//---------------------------------------------------------------------------------------------------------------
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		//Obtem o canal do contexto
		Channel currentChannel = ctx.channel();
		//faz o print da mensagem normal na tela
		System.out.print("[Transmissor: ] - " + currentChannel.remoteAddress() + " - " + msg);
		
		
		//Converte em mensagem binario, FIXME Ideal passar para outra camada
		String binary = new BigInteger(msg.replace("\n", "").getBytes()).toString(2);
		System.out.println("[INFO] - Em binario: " + binary + "\n");

		//checa se a mensagem é um ping
		if ("ping\n".equals(msg) && isPing) {
			//Aguarda 1 segundo
			Thread.sleep(1000);
			//Transmite a mensagem "pong" pelo canal
			currentChannel.writeAndFlush("pong\n");
		} else {
			//FIXME: Este bloco pode ser melhorado e esta logica pode ser extraida para outra camada
			//muda a flag da brincadeira do ping para false
			isPing = false;

			//Se o comando "start ping" chegar pelo canal
			if ("start ping\n".equals(msg)) {
				//Muda a flag da brincadeira do ping para true
				isPing = true;
				//envia o primeiro pong
				currentChannel.writeAndFlush("pong\n");
			} else {
				//Qualquer outra mensagem envia a reposta pelo canal
				currentChannel.writeAndFlush("[Receptor] - Mensagem Recebida: " + msg + "\n");
			}
		}
	}
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
}