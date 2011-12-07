package chat.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel para mostrar la conversacion y pedir al usuario el texto que quiere
 * enviar.
 */

public class ClientPanel {
	/* Scroll */
	private JScrollPane scroll;

	/* Area para mostrar la conversacion */
	private JTextArea textArea;

	/* Para pedir el texto al usuario */
	private JTextField textField;

	/* Boton para enviar el texto */
	private JButton boton;

	/* Crea el panel con todos sus componentes */
	public ClientPanel(Container contenedor) {
		contenedor.setLayout(new BorderLayout());
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);

		JPanel panel = new JPanel(new FlowLayout());
		textField = new JTextField(50);
		boton = new JButton("Enviar");
		panel.add(textField);
		panel.add(boton);

		contenedor.add(scroll, BorderLayout.CENTER);
		contenedor.add(panel, BorderLayout.SOUTH);
	}

	/*
	 * Añade el actionListener que se le pasa tanto a pulsar <intro> en el
	 * textField como al botón
	 */
	public void addActionListener(ActionListener accion) {
		textField.addActionListener(accion);
		boton.addActionListener(accion);
	}

	/* Añade el texto que se le pasa al textArea */
	public void addText(String texto) {
		textArea.append(texto);
	}

	public String getText() {
		String text = textField.getText();
		textField.setText("");
		return text;
	}
}
