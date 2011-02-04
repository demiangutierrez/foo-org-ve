/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.layouts;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploFlow extends JFrame
{
	/**
	 *
	 */
	public EjemploFlow()
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
		getContentPane().setLayout(new FlowLayout());

		JLabel lblHola = new JLabel("Hola");
		getContentPane().add(lblHola);

		JLabel lblMundo = new JLabel("Mundo");
		getContentPane().add(lblMundo);

		JButton btnEstoEsUnBoton = new JButton("Esto Es Un Boton");
		getContentPane().add(btnEstoEsUnBoton);

		JTextField txtEjemplo1 = new JTextField();
		getContentPane().add(txtEjemplo1);

		JTextField txtEjemplo2 = new JTextField(10);
		getContentPane().add(txtEjemplo2);

		JTextField txtEjemplo3 = new JTextField("Hola Mundo");
		getContentPane().add(txtEjemplo3);

		JCheckBox chkEjemplo = new JCheckBox("Un Check Box");
		getContentPane().add(chkEjemplo);

		JComboBox cboCombo = new JComboBox(new Object[] { "Uno", "Dos", "Tres", "etc..." });
		getContentPane().add(cboCombo);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploFlow();
	}
}
