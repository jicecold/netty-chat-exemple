package br.com.fatec.netty.chat.example;

import br.com.fatec.netty.chat.example.view.MainView;
import javafx.application.Application;

/**
 * Classe principal que inicia os canais de transmissão e recepção de dados Este
 * é um projeto academico de demostração de camada fisica do modelo de
 * referencia OSI, que simula a troca de mensegens e por um canal de transmissão
 * e recepção de dados e exibe a mensagem em bits.
 * 
 * Exemplo de uso da biblioteca NETTY => io.netty.
 * 
 * Livre para USO e ABUSO, passe os créditos.
 * 
 * https://github.com/jicecold/physical_layer_netty_example.git
 * 
 * @author Fabio S. da Silveira
 * @author Jair Jr Batista
 * @author Warnner A. F. Sinotti
 *
 */
public class App {

	// ---------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
 
		Application.launch(MainView.class, args);
	}
	
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------------
}
