/*
 *
 */
package com.minotauro.swing.componentes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 *
 */
public class EjemploToolBar extends JFrame
{
	private JTextArea txtSalida;

	/**
	 *
	 */
	public EjemploToolBar()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");
		setSize(600, 260);
		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		JToolBar toolBar = new JToolBar();

		JButton button = new JButton(new ImageIcon(getClass().getResource("left.gif")));
		button.setToolTipText("Boton de la izquierda");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtSalida.append("Primer Boton\n");
			}
		});
		toolBar.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("middle.gif")));
		button.setToolTipText("Boton del medio");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtSalida.append("Segundo Boton\n");
			}
		});
		toolBar.add(button);

		button = new JButton(new ImageIcon(getClass().getResource("right.gif")));
		button.setToolTipText("Boton de la derecha");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtSalida.append("Tercer Boton\n");
			}
		});
		toolBar.add(button);

		toolBar.addSeparator();

		button = new JButton("Otro boton");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtSalida.append("Cuarto Boton\n");
			}
		});
		toolBar.add(button);

		JTextField textField = new JTextField("Un Text Field");
		toolBar.add(textField);

		txtSalida = new JTextArea(5, 30);
		JScrollPane scrollPane = new JScrollPane(txtSalida);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(400, 100));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		EjemploToolBar frame = new EjemploToolBar();
	}
}
