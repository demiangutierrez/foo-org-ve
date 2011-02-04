/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Mar 5, 2004
 */
package com.minotauro.otros.applets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JApplet;

/**
 * @author DMI: Demian Gutierrez
 */
public class AppletMouse extends JApplet implements MouseListener, MouseMotionListener
{
	private int x;
	private int y;
	private boolean btn1;
	private boolean btn2;
	private boolean btn3;
	private BufferedImage bimg;

	/**
	 *
	 */
	public AppletMouse()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
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

	/**
	 *
	 */
	public void paint(Graphics g)
	{
		update(g);
	}

	/**
	 *
	 */
	public void update(Graphics g)
	{
		if (bimg == null)
		{
			bimg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

			System.err.println(getWidth() + ";" + getHeight());
		}

		// Comentar
		Graphics2D g2d = (Graphics2D) bimg.getGraphics();

		g2d.setBackground(Color.WHITE);
		g2d.clearRect(0, 0, getWidth(), getHeight());

		g2d.setFont(new Font("SansSerif", Font.PLAIN, 20));
		g2d.setColor(Color.BLACK);
		g2d.drawString("(" + x + "," + y + "), (" + btn1 + "," + btn2 + "," + btn3 + ")", x, y);
		g.drawImage(bimg, 0, 0, null);
	}

	// --------------------------------------------------------------------------------

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		System.err.print("public void mouseClicked(MouseEvent e) - ");

		switch (e.getButton())
		{
			case MouseEvent.BUTTON1 :
				System.err.println("MouseEvent.BUTTON1");
				break;

			case MouseEvent.BUTTON2 :
				System.err.println("MouseEvent.BUTTON2");
				break;

			case MouseEvent.BUTTON3 :
				System.err.println("MouseEvent.BUTTON3");
				break;
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
	 */
	public void mouseEntered(MouseEvent e)
	{
		System.err.print("public void mouseEntered(MouseEvent e)");
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
	 */
	public void mouseExited(MouseEvent e)
	{
		System.err.println("public void mouseExited(MouseEvent e)");
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	public void mousePressed(MouseEvent e)
	{
		switch (e.getButton())
		{
			case MouseEvent.BUTTON1 :
				btn1 = true;
				break;

			case MouseEvent.BUTTON2 :
				btn2 = true;
				break;

			case MouseEvent.BUTTON3 :
				btn3 = true;
				break;
		}

		repaint();
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
	 */
	public void mouseReleased(MouseEvent e)
	{
		switch (e.getButton())
		{
			case MouseEvent.BUTTON1 :
				btn1 = false;
				break;

			case MouseEvent.BUTTON2 :
				btn2 = false;
				break;

			case MouseEvent.BUTTON3 :
				btn3 = false;
				break;
		}

		repaint();
	}

	// --------------------------------------------------------------------------------

	/**
	 * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
	 */
	public void mouseDragged(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();

		repaint();
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
	 */
	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();

		repaint();
	}
}
