/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.layouts;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploFlow2 extends JFrame
{
	/**
	 *
	 */
	public EjemploFlow2()
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
		getContentPane().setLayout(new SpringLayout());
		

		JPanel panel = new JPanel(new FlowLayout());
		
		JLabel lblHola = new JLabel("Hola");
		panel.add(lblHola);

		JLabel lblMundo = new JLabel("Mundo");
		panel.add(lblMundo);

		JButton btnEstoEsUnBoton = new JButton("Esto Es Un Boton");
		panel.add(btnEstoEsUnBoton);

		JTextField txtEjemplo1 = new JTextField();
		panel.add(txtEjemplo1);

		JTextField txtEjemplo2 = new JTextField(10);
		panel.add(txtEjemplo2);

		JTextField txtEjemplo3 = new JTextField("Hola Mundo");
		panel.add(txtEjemplo3);

		JCheckBox chkEjemplo = new JCheckBox("Un Check Box");
		panel.add(chkEjemplo);

		JComboBox cboCombo = new JComboBox(new Object[] { "Uno", "Dos", "Tres", "etc..." });
		panel.add(cboCombo);
//		panel.setMaximumSize(new Dimension(600, 300));
//		panel.setPreferredSize(new Dimension(600, 300));
		getContentPane().add(panel);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploFlow2();
	}
}
