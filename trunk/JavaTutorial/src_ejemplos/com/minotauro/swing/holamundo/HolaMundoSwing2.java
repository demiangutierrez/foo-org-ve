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
public class HolaMundoSwing2 extends JFrame
{
	/**
	 *
	 */
	public HolaMundoSwing2()
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
	private void initGUI()
	{
		JLabel lbl = new JLabel("Hola Mundo");
		getContentPane().add(lbl);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new HolaMundoSwing2();
	}
}
