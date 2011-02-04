/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.layouts;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploGridBag extends JFrame
{
	/**
	 *
	 */
	public EjemploGridBag()
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
		getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lblHola = new JLabel("Hola");
		lblHola.setOpaque(true);
		lblHola.setBackground(Color.RED);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(lblHola, gbc);

		JLabel lblMundo = new JLabel("Mundo");
		lblMundo.setOpaque(true);
		lblMundo.setBackground(Color.BLUE);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(lblMundo, gbc);

		JButton btnEstoEsUnBoton = new JButton("Esto Es Un Boton");
		btnEstoEsUnBoton.setBackground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(btnEstoEsUnBoton, gbc);

		JTextField txtEjemplo = new JTextField("Hola Mundo");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(15, 15, 15, 15);
		getContentPane().add(txtEjemplo, gbc);

		JCheckBox chkEjemplo = new JCheckBox("Un Check Box");
		chkEjemplo.setOpaque(true);
		chkEjemplo.setBackground(Color.GREEN);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(chkEjemplo, gbc);

		JComboBox cboCombo = new JComboBox(new Object[] { "Uno", "Dos", "Tres", "etc..." });
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		getContentPane().add(cboCombo, gbc);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploGridBag();
	}
}
