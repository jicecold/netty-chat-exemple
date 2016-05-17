package br.com.fatec.netty.chat.example.network;

public interface NetworkConsumerCallback<T> {

	void registerCallback(T t);

	void unregisterCallback(String channelId);
	
	void processCallback( T t, String message );

}
