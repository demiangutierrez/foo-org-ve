/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.componentes;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploRadio extends JFrame
{
	private static final int W = 640;
	private static final int H = 480;
	private JTextField txtRadio;
	private JTextField txtCheck;

	/**
	 *
	 */
	public EjemploRadio()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();

		pack();

		setBounds((d.width - getWidth()) / 2, (d.height - getHeight()) / 2, getWidth(), getHeight());
		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 5, 10);
		getContentPane().add(initRadioPanel(), gbc);

		JCheckBox chkSimple = new JCheckBox("Simple");
		chkSimple.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				AbstractButton btn = (AbstractButton) e.getSource();
				txtCheck.setText(Boolean.toString(btn.isSelected()));
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		getContentPane().add(chkSimple, gbc);

		JLabel lblRadio = new JLabel("Radio");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		getContentPane().add(lblRadio, gbc);

		Dimension d;

		txtRadio = new JTextField("Uno");
		txtRadio.setEditable(false);
		d = txtRadio.getPreferredSize();
		txtRadio.setPreferredSize(new Dimension(50, d.height));
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 5, 10);
		getContentPane().add(txtRadio, gbc);

		JLabel lblCheck = new JLabel("Check");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 10, 5);
		getContentPane().add(lblCheck, gbc);

		txtCheck = new JTextField("falso");
		txtCheck.setEditable(false);
		d = txtCheck.getPreferredSize();
		txtCheck.setPreferredSize(new Dimension(50, d.height));
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 10, 10);
		getContentPane().add(txtCheck, gbc);
	}

	/**
	 *
	 */
	public JPanel initRadioPanel()
	{
		JPanel ret = new JPanel(new GridBagLayout());
		ret.setBorder(BorderFactory.createTitledBorder("Radio..."));

		GridBagConstraints gbc = new GridBagConstraints();

		ButtonGroup buttonGroup = new ButtonGroup();

		JRadioButton rbtUno = new JRadioButton("Uno");
		rbtUno.setSelected(true);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(rbtUno, gbc);
		buttonGroup.add(rbtUno);

		JRadioButton rbtDos = new JRadioButton("Dos");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(rbtDos, gbc);
		buttonGroup.add(rbtDos);

		JRadioButton rbtTres = new JRadioButton("Tres");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(rbtTres, gbc);
		buttonGroup.add(rbtTres);

		JCheckBox rbtCuatro = new JCheckBox("Cuatro");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0, 0, 0, 0);
		ret.add(rbtCuatro, gbc);
		buttonGroup.add(rbtCuatro);

		Enumeration enu = buttonGroup.getElements();

		while (enu.hasMoreElements())
		{
			AbstractButton btn = (AbstractButton) enu.nextElement();

			btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					AbstractButton btn = (AbstractButton) e.getSource();
					System.err.println("Seleccionado: " + btn.getText());
					txtRadio.setText(btn.getText());
				}
			});
		}

		return ret;
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploRadio();
	}
}
