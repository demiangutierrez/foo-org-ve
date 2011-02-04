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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploList extends JFrame
{
	private JList lstEjemplo;
	private JTextField txtSalida;
	private JTextField txtEntrada;

	/**
	 *
	 */
	public EjemploList()
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

		String[] nombres =
			new String[] {
				"Pedro Gutierrez",
				"Juan Moreno",
				"Jose Zambrano",
				"Maria Rojas",
				"Laura Medina",
				"Mariela Olivera" };
		DefaultListModel lstModel = new DefaultListModel();
		lstModel.addListDataListener(new ListDataListener()
		{
			public void contentsChanged(ListDataEvent e)
			{
			}

			public void intervalAdded(ListDataEvent e)
			{
				DefaultListModel lm = (DefaultListModel) e.getSource();
				System.err.println("intervalAdded: " + lm.getElementAt(e.getIndex0()));
			}

			public void intervalRemoved(ListDataEvent e)
			{
			}
		});

		for (int i = 0; i < nombres.length; i++)
		{
			lstModel.addElement(nombres[i]);
		}

		lstEjemplo = new JList(lstModel);
		lstEjemplo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel lstSelModel = lstEjemplo.getSelectionModel();
		lstSelModel.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				DefaultListModel lm = (DefaultListModel) lstEjemplo.getModel();

				int selIndex = lstEjemplo.getSelectedIndex();

				if (selIndex > -1)
				{
					String salida = (String) lm.getElementAt(lstEjemplo.getSelectedIndex());
					txtSalida.setText(salida);
				}
			}
		});

		lstEjemplo.setToolTipText("Este es la lista");
		JScrollPane scrEjemplo = new JScrollPane(lstEjemplo);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 5, 10);
		pnl.add(scrEjemplo, gbc);

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

		txtSalida = new JTextField("Pedro Gutierrez");
		txtSalida.setToolTipText("Aqui sale lo que esta seleccionado en la lista");
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
		txtEntrada.setToolTipText("Escribe aqui lo que quieres agregar a la lista");
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
		btnEntrada.setToolTipText("Aprieta para agregar un elemento a la lista");
		btnEntrada.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DefaultListModel lstModel = (DefaultListModel) lstEjemplo.getModel();

				String entrada = txtEntrada.getText();
				lstModel.insertElementAt(entrada, 0);
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		pnl.add(btnEntrada, gbc);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setToolTipText("Aprieta para eliminar el elemento seleccionado de la lista");
		btnEliminar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				DefaultListModel lstModel = (DefaultListModel) lstEjemplo.getModel();

				int selIndex = lstEjemplo.getSelectedIndex();

				if (selIndex > -1)
				{
					System.err.println(selIndex);
					lstModel.remove(selIndex);
				}
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 10, 10);
		pnl.add(btnEliminar, gbc);

		pack();
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploList();
	}
}
