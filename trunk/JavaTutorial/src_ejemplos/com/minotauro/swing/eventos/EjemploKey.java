/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.swing.eventos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploKey extends JFrame
{
	private static final int W = 300;
	private static final int H = 100;
	private JTextField txtSrc;
	private JTextField txtDst;

	/**
	 *
	 */
	public EjemploKey()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setBounds((d.width - W) / 2, (d.height - W) / 2, W, H);

		setVisible(true);
	}

	/**
	 *
	 */
	public void initGUI()
	{
		getContentPane().setLayout(new BorderLayout());

		txtSrc = new JTextField("Origen");
		txtSrc.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				txtDst.setText(txtSrc.getText());
				txtDst.setCaretPosition(txtSrc.getCaretPosition());
			}
		});
		getContentPane().add(txtSrc, BorderLayout.NORTH);

		txtDst = new JTextField("Destino");
		txtDst.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				System.err.println(
					"txtSrc, keyPressed: "
						+ e.getKeyChar()
						+ ":"
						+ e.getKeyCode()
						+ ":"
						+ KeyEvent.getKeyModifiersText(e.getModifiers())
						+ ":"
						+ KeyEvent.getModifiersExText(e.getModifiersEx())
						+ ":"
						+ txtSrc.getText());
			}

			public void keyReleased(KeyEvent e)
			{
				System.err.println(
					"txtSrc, keyReleased: "
						+ e.getKeyChar()
						+ ":"
						+ e.getKeyCode()
						+ ":"
						+ KeyEvent.getKeyModifiersText(e.getModifiers())
						+ ":"
						+ KeyEvent.getModifiersExText(e.getModifiersEx())
						+ ":"
						+ txtSrc.getText());
			}

			public void keyTyped(KeyEvent e)
			{
				System.err.println(
					"txtSrc, keyTyped: "
						+ e.getKeyChar()
						+ ":"
						+ e.getKeyCode()
						+ ":"
						+ KeyEvent.getKeyModifiersText(e.getModifiers())
						+ ":"
						+ KeyEvent.getModifiersExText(e.getModifiersEx())
						+ ":"
						+ txtDst.getText());
			}
		});
		getContentPane().add(txtDst, BorderLayout.SOUTH);
	}

	/**
	 *
	 */
	private void btnCopiarClicked()
	{
		txtDst.setText(txtSrc.getText());
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		new EjemploKey();
	}
}
