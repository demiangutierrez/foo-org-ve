/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 20, 2004
 */
package com.minotauro.swing.componentes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploCombo extends JFrame
{
	private JComboBox cboEjemplo;
	private JTextField txtSalida;
	private JTextField txtEntrada;

	/**
	 *
	 */
	public EjemploCombo()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");
		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		JPanel pnl = (JPanel) getContentPane();

		pnl.setLayout(new GridBagLayout());
		pnl.setBorder(BorderFactory.createTitledBorder("Ejemplo Combo"));

		GridBagConstraints gbc = new GridBagConstraints();

		cboEjemplo = new JComboBox(new String[] { "Pedro", "Juan", "Jose" });
		cboEjemplo.setEditable(true);
		cboEjemplo.setToolTipText("Este es el combo");
		cboEjemplo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				txtSalida.setText(cboEjemplo.getSelectedItem().toString());
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 5, 10);
		pnl.add(cboEjemplo, gbc);

		JLabel lblSalida = new JLabel("Salida");
		lblSalida.setToolTipText("Un label");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		pnl.add(lblSalida, gbc);

		txtSalida = new JTextField("Pedro");
		txtSalida.setToolTipText("Aqui sale lo que esta seleccionado en el combo");
		txtSalida.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 5, 10);
		pnl.add(txtSalida, gbc);

		JLabel lblEntrada = new JLabel("Entrada");
		lblEntrada.setToolTipText("Otro label");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		pnl.add(lblEntrada, gbc);

		txtEntrada = new JTextField(10);
		txtEntrada.setToolTipText("Escribe aqui lo que quieres agregar al combo");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 5, 10);
		pnl.add(txtEntrada, gbc);

		JButton btnEntrada = new JButton("Entrada");
		btnEntrada.setToolTipText("Aprieta para agregar un elemento al combo");
		btnEntrada.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// cboEjemplo.addItem(txtEntrada.getText());
				DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboEjemplo.getModel();

				String entrada = txtEntrada.getText();

				cboModel.insertElementAt(entrada, 0);
				cboModel.setSelectedItem(entrada);
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 10, 10);
		pnl.add(btnEntrada, gbc);

		pack();
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploCombo();
	}
}
