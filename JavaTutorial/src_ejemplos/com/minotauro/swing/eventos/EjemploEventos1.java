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
public class EjemploEventos1 extends JFrame implements ActionListener
{
	/**
	 *
	 */
	public EjemploEventos1()
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
		btnEste.addActionListener(this);
		getContentPane().add(btnEste, BorderLayout.EAST);

		JButton btnOeste = new JButton("Oeste");
		btnOeste.addActionListener(this);
		getContentPane().add(btnOeste, BorderLayout.WEST);

		JButton btnNorte = new JButton("Norte");
		btnNorte.addActionListener(this);
		getContentPane().add(btnNorte, BorderLayout.NORTH);

		JButton btnSur = new JButton("Sur");
		btnSur.addActionListener(this);
		getContentPane().add(btnSur, BorderLayout.SOUTH);

		JButton btnCentro = new JButton("Centro");
		btnCentro.addActionListener(this);
		getContentPane().add(btnCentro, BorderLayout.CENTER);
	}

	/**
	 *
	 */
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();

		System.err.println("Pulsado el boton: " + btn.getText());

		JOptionPane.showMessageDialog(this, "Pulsado el boton: " + btn.getText());
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploEventos1();
	}
}
