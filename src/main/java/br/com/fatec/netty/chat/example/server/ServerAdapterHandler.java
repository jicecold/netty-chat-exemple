package br.com.fatec.netty.chat.example.server;

import br.com.fatec.netty.chat.example.view.Console;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerAdapterHandler extends SimpleChannelInboundHandler<String> {
	
	private final Console console = Console.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel currentChannel = ctx.channel();
        System.out.println("[INFO] - " + currentChannel.remoteAddress() + " - " + msg);
        
      
        
        currentChannel.write("[Server] - Success");
    }
    
    

}