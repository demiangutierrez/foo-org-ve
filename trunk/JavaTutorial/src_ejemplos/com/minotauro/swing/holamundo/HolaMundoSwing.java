/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.holamundo;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author DMI: Demian Gutierrez
 */
public class HolaMundoSwing
{
	/**
	 *
	 */
	public HolaMundoSwing()
	{
		// Nada
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		JLabel lbl = new JLabel("Hola Mundo");
		frame.getContentPane().add(lbl);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Ventana");
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}
