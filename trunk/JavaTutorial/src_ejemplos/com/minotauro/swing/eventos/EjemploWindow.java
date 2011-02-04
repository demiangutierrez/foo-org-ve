/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.eventos;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploWindow extends JFrame
{
	private static final int W = 640;
	private static final int H = 480;

	/**
	 *
	 */
	public EjemploWindow()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				JFrame frm = (JFrame) e.getSource();

				int res =
					JOptionPane.showConfirmDialog(
						frm,
						"Esta seguro que desea salir",
						"Dialogo",
						JOptionPane.YES_NO_OPTION);

				if (res == JOptionPane.OK_OPTION)
				{
					System.exit(0);
				}
			}
		});

		setTitle("Ventana");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setBounds((d.width - W) / 2, (d.height - W) / 2, W, H);

		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		JLabel lbl = new JLabel("Hola Mundo");
		lbl.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(lbl);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploWindow();
	}
}
