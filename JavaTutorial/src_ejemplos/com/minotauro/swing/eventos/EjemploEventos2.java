/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.eventos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploEventos2 extends JFrame
{
	private JFrame thisFrame = this;

	/**
	 *
	 */
	public EjemploEventos2()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");
		setSize(300, 200);
		setVisible(true);
	}

	/**
	 *
	 */
	public void initGUI()
	{
		getContentPane().setLayout(new BorderLayout());

		JButton btnEste = new JButton("Este");
		// Clase Anonima
		btnEste.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.err.println("Pulsado el boton del Este");

				JOptionPane.showMessageDialog(thisFrame, "Pulsado el boton del Este");
			}
		});
		getContentPane().add(btnEste, BorderLayout.EAST);

		JButton btnOeste = new JButton("Oeste");
		btnOeste.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.err.println("Vamos al Oeste");

				JOptionPane.showMessageDialog(thisFrame, "Vamos al Oeste");
			}
		});
		getContentPane().add(btnOeste, BorderLayout.WEST);

		JButton btnNorte = new JButton("Norte");
		btnNorte.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.err.println("NORTE!!!");

				JOptionPane.showMessageDialog(thisFrame, "NORTE!!!");
			}
		});
		getContentPane().add(btnNorte, BorderLayout.NORTH);

		JButton btnSur = new JButton("Sur");
		btnSur.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.err.println("123, Sur... 123, Sur... 123, Sur... ... ...");

				JOptionPane.showMessageDialog(thisFrame, "123, Sur... 123, Sur... 123, Sur... ... ...");
			}
		});
		getContentPane().add(btnSur, BorderLayout.SOUTH);

		JButton btnCentro = new JButton("Centro");
		btnCentro.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.err.println("otrneC la somaV");

				JOptionPane.showMessageDialog(thisFrame, "otrneC la somaV");
			}
		});
		getContentPane().add(btnCentro, BorderLayout.CENTER);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploEventos2();
	}
}
