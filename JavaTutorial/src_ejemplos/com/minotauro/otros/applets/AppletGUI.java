/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Mar 5, 2004
 */
package com.minotauro.otros.applets;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class AppletGUI extends JApplet
{
	private JTextField txtA;
	private JTextField txtB;
	private JTextField txtC;

	/**
	 *
	 */
	public AppletGUI()
	{
		initGUI();
	}

	/**
	 *
	 */
	private void initGUI()
	{
		getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		txtA = new JTextField(15);
		txtA.setText("0");
		txtA.setBackground(getContentPane().getBackground());
		txtA.setHorizontalAlignment(JTextField.RIGHT);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 5, 10);
		getContentPane().add(txtA, gbc);

		txtB = new JTextField(15);
		txtB.setText("0");
		txtB.setBackground(getContentPane().getBackground());
		txtB.setHorizontalAlignment(JTextField.RIGHT);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(txtB, gbc);

		JButton btnStar = new JButton("*");
		btnStar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				btnStarPerformed();
			}
		});
		btnStar.setBackground(getContentPane().getBackground());
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(btnStar, gbc);

		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(separator, gbc);

		txtC = new JTextField(15);
		txtC.setText("0");
		txtC.setBackground(getContentPane().getBackground());
		txtC.setHorizontalAlignment(JTextField.RIGHT);
		txtC.setEditable(false);
		txtC.setForeground(Color.RED);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = new Insets(5, 10, 10, 10);
		getContentPane().add(txtC, gbc);
	}

	/**
	 *
	 */
	private void btnStarPerformed()
	{
		double a = 0;
		double b = 0;

		txtA.setBackground(txtA.getParent().getBackground());
		txtB.setBackground(txtA.getParent().getBackground());

		try
		{
			a = Double.parseDouble(txtA.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Las entradas deben ser numeros", "Error", JOptionPane.ERROR_MESSAGE);
			txtA.setBackground(Color.RED);
		}

		try
		{
			b = Double.parseDouble(txtB.getText());
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Las entradas deben ser numeros", "Error", JOptionPane.ERROR_MESSAGE);
			txtB.setBackground(Color.RED);
		}

		double c = a * b;

		txtC.setText(Double.toString(c));
	}

	/**
	 *
	 */
	public void init()
	{
		// Empty

		System.err.println("public void init()");
	}

	/**
	 *
	 */
	public void start()
	{
		System.err.println("public void start()");
	}

	/**
	 *
	 */
	public void stop()
	{
		// Empty

		System.err.println("public void stop()");
	}

	/**
	 *
	 */
	public void destroy()
	{
		// Empty

		System.err.println("public void destroy()");
	}
}
