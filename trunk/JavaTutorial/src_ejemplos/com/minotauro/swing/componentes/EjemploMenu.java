/*
 *
 */
package com.minotauro.swing.componentes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 */
public class EjemploMenu extends JFrame implements ActionListener, ItemListener
{
	private JTextArea txtSalida;

	/**
	 *
	 */
	public EjemploMenu()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ventana");
		setSize(600, 260);
		setVisible(true);
	}

	/**
	 *
	 */
	private void initGUI()
	{
		txtSalida = new JTextArea(5, 30);
		txtSalida.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(txtSalida);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Archivo");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Abrir");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				abrirClicked();
			}
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Guardar");
		menuItem.setEnabled(false);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Salir");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		menu.add(menuItem);

		menu = new JMenu("Un Menu");
		menuBar.add(menu);

		menuItem = new JMenuItem("Solo texto");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Texto e icono", new ImageIcon(getClass().getResource("middle.gif")));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon(getClass().getResource("middle.gif")));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();

		ButtonGroup group = new ButtonGroup();

		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Un JRadioButtonMenuItem");
		rbMenuItem.setSelected(true);
		rbMenuItem.addActionListener(this);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Otro...");
		rbMenuItem.addActionListener(this);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		menu.addSeparator();

		JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Un JCheckBoxMenuItem");
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Otro...");
		cbMenuItem.addItemListener(this);
		menu.add(cbMenuItem);

		menu.addSeparator();

		JMenu submenu = new JMenu("Un submenu");
		menu.add(submenu);

		menuItem = new JMenuItem("Un elemento en un submenu");
		menuItem.addActionListener(this);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Otro...");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
	}

	/**
	 *
	 */
	private void abrirClicked()
	{
		JFileChooser fileChooser = new JFileChooser();
		int res = fileChooser.showOpenDialog(this);

		if (res == JFileChooser.APPROVE_OPTION)
		{
			JOptionPane.showMessageDialog(this, "Usted selecciono: " + fileChooser.getSelectedFile().getAbsolutePath());
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Usted no selecciono ningun archivo");
		}
	}

	/**
	 *
	 */
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem) (e.getSource());
		String s =
			"Click sobre un menu.\n   Origen: "
				+ source.getText()
				+ " (Es una instancia de "
				+ source.getClass()
				+ ")\n";
		txtSalida.append(s);
	}

	/**
	 *
	 */
	public void itemStateChanged(ItemEvent e)
	{
		JMenuItem source = (JMenuItem) (e.getSource());
		String s =
			"Click sobre un menu.\n   Origen: "
				+ source.getText()
				+ " (Es una instancia de "
				+ source.getClass()
				+ ")\n   El nuevo estado: "
				+ ((e.getStateChange() == ItemEvent.SELECTED) ? "encendido\n" : "apagado\n");
		txtSalida.append(s);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		EjemploMenu window = new EjemploMenu();
	}
}
