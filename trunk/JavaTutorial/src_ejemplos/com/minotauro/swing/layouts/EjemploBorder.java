/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.layouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
public class EjemploBorder extends JFrame
{
	/**
	 *
	 */
	public EjemploBorder()
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
		getContentPane().setLayout(new BorderLayout());

		JLabel lblNorth = new JLabel("Norte");
		lblNorth.setOpaque(true);
		lblNorth.setBackground(Color.RED);
		getContentPane().add(lblNorth, BorderLayout.NORTH);

		JLabel lblWest = new JLabel("Oeste");
		lblWest.setOpaque(true);
		lblWest.setBackground(Color.CYAN);
		getContentPane().add(lblWest, BorderLayout.WEST);

		JLabel lblSouth = new JLabel("Sur");
		lblSouth.setOpaque(true);
		lblSouth.setBackground(Color.YELLOW);
		getContentPane().add(lblSouth, BorderLayout.SOUTH);

		JLabel lblEast = new JLabel("Este");
		lblEast.setOpaque(true);
		lblEast.setBackground(Color.GREEN);
		getContentPane().add(lblEast, BorderLayout.EAST);

		getContentPane().add(initPanelCenter(), BorderLayout.CENTER);
	}

	/**
	 *
	 */
	public JPanel initPanelCenter()
	{
		JPanel ret = new JPanel();
		// RGB
		ret.setBackground(new Color(100, 200, 210));

		ret.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		JLabel lblHola = new JLabel("Hola");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(lblHola, gbc);

		JLabel lblMundo = new JLabel("Mundo");
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(lblMundo, gbc);

		JButton btnEstoEsUnBoton = new JButton("Esto Es Un Boton");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(btnEstoEsUnBoton, gbc);

		JTextField txtEjemplo = new JTextField("Hola Mundo");
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(15, 15, 15, 15);
		ret.add(txtEjemplo, gbc);

		JCheckBox chkEjemplo = new JCheckBox("Un Check Box");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(chkEjemplo, gbc);

		JComboBox cboCombo = new JComboBox(new Object[] { "Uno", "Dos", "Tres", "etc..." });
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(cboCombo, gbc);

		return ret;
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploBorder();
	}
}
