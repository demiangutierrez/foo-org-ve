/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.eventos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploEventos3 extends JFrame
{
	private static final int W = 300;
	private static final int H = 100;
	private JTextField txtSrc;
	private JTextField txtDst;

	/**
	 *
	 */
	public EjemploEventos3()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setBounds((d.width - W) / 2, (d.height - W) / 2, W, H);

		setVisible(true);
	}

	/**
	 *
	 */
	public void initGUI()
	{
		getContentPane().setLayout(new BorderLayout());

		txtSrc = new JTextField("Origen");
		getContentPane().add(txtSrc, BorderLayout.NORTH);

		JButton btnCopiar = new JButton("Copiar");
		btnCopiar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnCopiarClicked();
			}
		});
		getContentPane().add(btnCopiar, BorderLayout.CENTER);

		txtDst = new JTextField("Destino");
		getContentPane().add(txtDst, BorderLayout.SOUTH);
	}

	/**
	 *
	 */
	private void btnCopiarClicked()
	{
		txtDst.setText(txtSrc.getText());
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploEventos3();
	}
}
