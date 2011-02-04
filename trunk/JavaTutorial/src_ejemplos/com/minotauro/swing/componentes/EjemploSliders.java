/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 20, 2004
 */
package com.minotauro.swing.componentes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploSliders extends JFrame
{
	private JProgressBar pgbEjemploH;
	private JSlider sldEjemploH;
	private JProgressBar pgbEjemploV;
	private JSlider sldEjemploV;

	/**
	 *
	 */
	public EjemploSliders()
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

		pgbEjemploH = new JProgressBar(0, 100);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(10, 10, 5, 10);
		pnl.add(pgbEjemploH, gbc);

		sldEjemploH = new JSlider(0, 100, 0);
		sldEjemploH.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				pgbEjemploH.setValue(sldEjemploH.getValue());
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 10);
		pnl.add(sldEjemploH, gbc);

		pgbEjemploV = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 5, 5);
		pnl.add(pgbEjemploV, gbc);

		sldEjemploV = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		sldEjemploV.setSnapToTicks(true);
		sldEjemploV.setMajorTickSpacing(25);
		sldEjemploV.setPaintTicks(true);
		sldEjemploV.setPaintLabels(true);
		sldEjemploV.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				pgbEjemploV.setValue(sldEjemploV.getValue());
			}
		});
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 5, 10);
		pnl.add(sldEjemploV, gbc);

		JProgressBar pgbEjemploI1 = new JProgressBar(JProgressBar.VERTICAL);
		pgbEjemploI1.setIndeterminate(true);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		pnl.add(pgbEjemploI1, gbc);

		JProgressBar pgbEjemploI2 = new JProgressBar(JProgressBar.HORIZONTAL);
		pgbEjemploI2.setIndeterminate(true);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(5, 10, 10, 10);
		pnl.add(pgbEjemploI2, gbc);

		pack();
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploSliders();
	}
}
