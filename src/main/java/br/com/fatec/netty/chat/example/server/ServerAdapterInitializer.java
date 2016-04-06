package br.com.fatec.netty.chat.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Configura o canal de transmissão e recepção de dados  
 * @author Jair Jr Batista
 *
 */
public class ServerAdapterInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
    	//Obtem a pipeline atraves do socket
        ChannelPipeline pipeline = channel.pipeline();

        //Decodifica os bytes recebidos em string
        pipeline.addLast("decoder", new StringDecoder());
        //Codifica a mendagem em bytes
        pipeline.addLast("encoder", new StringEncoder());
        //Tratamento do canal lado receptor
        pipeline.addLast("handler", new ServerAdapterHandler());
    }

}
