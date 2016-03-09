package br.com.fatec.netty.chat.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.fatec.netty.chat.example.client.ContainerClient;
import br.com.fatec.netty.chat.example.server.ContainerServer;

/**
 * Hello world!
 *
 */
public class App extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JLabel label1;
	private JButton botao1;
	private JButton botao2;

	private String server = "localhost";
	private int port = 5252;

	public App() {

		this.setTitle("Teste Camada Fisica");
		this.setBounds(0, 0, 280, 280);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		label1 = new JLabel();
		label1.setText("Iniciar:");
		label1.setBounds(40, 10, 200, 50);
		this.add(label1);

		botao1 = new JButton();
		botao1.setText("Servidor");
		botao1.setBounds(40, 60, 200, 50);
		botao1.addActionListener(this);
		this.add(botao1);

		botao2 = new JButton();
		botao2.setText("Cliente");
		botao2.setBounds(40, 120, 200, 50);
		botao2.addActionListener(this);
		this.add(botao2);

	}

	public static void main(String[] args) {

		App exemplo = new App();
		exemplo.setVisible(true);

	}

	public void startServer() {
		ContainerServer containerServer = new ContainerServer(port);
		containerServer.start();
	}

	public void startClient() {
		ContainerClient containerClient = new ContainerClient(server, port);
		containerClient.start();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(botao1)) {
			this.startServer();
			
		} else if (e.getSource().equals(botao2)) {
			this.startClient();
			
		}
	}
}
