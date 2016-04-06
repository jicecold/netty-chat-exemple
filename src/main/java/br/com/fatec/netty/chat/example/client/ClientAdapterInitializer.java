package br.com.fatec.netty.chat.example.client;

import javax.swing.JTextArea;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 
 * @author Jair Jr Batista
 *
 */
public class ClientAdapterInitializer extends ChannelInitializer<SocketChannel> {

	//FIXME: Tratar com evento ou observer
	private JTextArea chatjTextArea;

	//---------------------------------------------------------------------------------------------------------------
	public ClientAdapterInitializer(JTextArea chatjTextArea) {
		this.chatjTextArea = chatjTextArea;
	}

	//---------------------------------------------------------------------------------------------------------------
	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();

		//Tranforma os bytes recebido em uma string
		pipeline.addLast("decoder", new StringDecoder());
		//Tranforma os bytes transmitidos em uma string
		pipeline.addLast("encoder", new StringEncoder());
		//trata a recepção e transmissão com o servidor, deve estar depois da codificação
		pipeline.addLast("handler", new ClientAdapterHandler(chatjTextArea));
	}
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------

}
