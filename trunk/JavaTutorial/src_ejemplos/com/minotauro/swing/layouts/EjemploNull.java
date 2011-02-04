/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.layouts;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploNull extends JFrame
{
	/**
	 *
	 */
	public EjemploNull()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");
		setSize(400, 300);
		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		getContentPane().setLayout(null);

		JLabel lblHola = new JLabel("Hola");
		lblHola.setOpaque(true);
		lblHola.setBackground(Color.YELLOW);
		lblHola.setBounds(30, 20, 50, 20);
		getContentPane().add(lblHola);

		JLabel lblMundo = new JLabel("Mundo");
		lblMundo.setOpaque(true);
		lblMundo.setBackground(Color.RED);
		lblMundo.setBounds(70, 30, 90, 30);
		getContentPane().add(lblMundo);

		JButton btnEstoEsUnBoton = new JButton("Esto Es Un Boton");
		btnEstoEsUnBoton.setBounds(new Rectangle(150, 150, 20, 50));
		getContentPane().add(btnEstoEsUnBoton);

		JTextField txtEjemplo = new JTextField("Hola Mundo");
		txtEjemplo.setBounds(new Rectangle(0, 0, 50, 30));
		getContentPane().add(txtEjemplo);

		JCheckBox chkEjemplo = new JCheckBox("Un Check Box");
		chkEjemplo.setBounds(new Rectangle(400, 300, 80, 80));
		getContentPane().add(chkEjemplo);

		JComboBox cboCombo = new JComboBox(new Object[] { "Uno", "Dos", "Tres", "etc..." });
		cboCombo.setBounds(new Rectangle(250, 60, 200, 40));
		getContentPane().add(cboCombo);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploNull();
	}
}
