package br.com.fatec.netty.chat.example.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.util.EventObject;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import br.com.fatec.netty.chat.example.client.ClientContainer;

/**
 * 
 * @author Fabio S. da Silveira 
 * @author Jair Jr Batista 
 * @author Warnner A. F. Sinotti
 *
 */
public class Cliente extends WindowAdapter implements WindowListener {

	// ---------------------------------------------------------------------------------------------------------------
	private JFrame frame;
	private JTextArea chatjTextArea;
	private JButton enviarjButton;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTextArea mensagemjTextArea;
	private ClientContainer containerClient;

	// ---------------------------------------------------------------------------------------------------------------
	public Cliente(String server, int port) {
		initComponents();
		containerClient = new ClientContainer(server, port, chatjTextArea);
		containerClient.start();
	}

	// ---------------------------------------------------------------------------------------------------------------
	/**
	 * Inicia os componente de visualização do console
	 */
	private void initComponents() {

		frame = new JFrame("Transmissor:::Mensageiro");

		jScrollPane1 = new JScrollPane();
		chatjTextArea = new JTextArea();
		jScrollPane2 = new JScrollPane();
		mensagemjTextArea = new JTextArea();
		enviarjButton = new JButton();

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		chatjTextArea.setEditable(false);
		chatjTextArea.setColumns(20);
		chatjTextArea.setRows(5);
		jScrollPane1.setViewportView(chatjTextArea);

		mensagemjTextArea.setColumns(20);
		mensagemjTextArea.setRows(5);
		mensagemjTextArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				mensagemjTextAreaKeyReleased(evt);
			}
		});
		jScrollPane2.setViewportView(mensagemjTextArea);

		enviarjButton.setText("Enviar");
		enviarjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				enviarjButtonActionPerformed(evt);
			}
		});

		GroupLayout layout = new GroupLayout(frame.getContentPane());
		frame.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(enviarjButton, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane2).addComponent(enviarjButton, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(0, 0, Short.MAX_VALUE)));

		frame.pack();
	}

	//---------------------------------------------------------------------------------------------------------------
	/**
	 * FIXME não está funcionando
	 * Evento de pressionamento de tecla
	 * @param evt
	 */
	protected void mensagemjTextAreaKeyReleased(KeyEvent evt) {
		if( evt.getKeyCode() == 13 ){
			enviarjButtonActionPerformed(evt);
		}
	}

	//---------------------------------------------------------------------------------------------------------------
	public void setLocationRelativeTo(Component component) {
		this.frame.setLocationRelativeTo(component);
	}

	//---------------------------------------------------------------------------------------------------------------
	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}

	//---------------------------------------------------------------------------------------------------------------
	/**
	 * Evento de envio de mensagem pelo canal
	 * @param event
	 */
	public void enviarjButtonActionPerformed(EventObject event) {
		containerClient.sendAndFlushMessage(mensagemjTextArea.getText());
		mensagemjTextArea.setText("");
	}
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
}
