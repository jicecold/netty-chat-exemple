package br.com.fatec.netty.chat.example.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends WindowAdapter implements WindowListener {

	private JFrame frame;
	private JTextArea textArea;

	public Server() {
		frame = new JFrame("Server");
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=new Dimension((int)(screenSize.width/2),(int)(screenSize.height/2));
		int x=(int)(frameSize.width/2);
		int y=(int)(frameSize.height/2);
		frame.setBounds(x, y, frameSize.width, frameSize.height);

		textArea = new JTextArea();
		textArea.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(new Label("Servidor Ativo:"));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(frameSize.width, (int)frameSize.height/2));

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);
		frame.setVisible(true);
		
		frame.addWindowListener(this);		
	}

	public void windowClosed(WindowEvent evt) {
		this.notifyAll(); // stop all threads
		System.exit(0);
	}

	public static void main(String[] args) {

		new Server();

	}

}
