/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Oct 24, 2003
 */
package org.cyrano.chess.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import org.cyrano.chess.pieces.AbstractPiece;

/**
 * @author DMI: Demian Gutierrez
 */
public class LblChess extends JLabel implements MouseListener, MouseMotionListener
{
	private int xPrev;

	private int yPrev;

	private int xCurr;

	private int yCurr;

	private int dIx;

	private int dIy;

	private AbstractPiece[][] board;

	private AbstractPiece drag;

	private int play = AbstractPiece.COLOR_WHITE;

	/**
	 * 
	 */
	public LblChess()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	private int getI(Point p)
	{
		int w = getWidth();
		int h = getHeight();
		int d = w < h ? w / 8 : h / 8;

		return p.x / d;
	}

	/**
	 * 
	 */
	private int getJ(Point p)
	{
		int w = getWidth();
		int h = getHeight();
		int d = w < h ? w / 8 : h / 8;

		return p.y / d;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public AbstractPiece[][] getBoard()
	{
		return board;
	}

	/**
	 * 
	 */
	public void setBoard(AbstractPiece[][] board)
	{
		this.board = board;
	}

	// --------------------------------------------------------------------------------
	// JLabel
	// --------------------------------------------------------------------------------

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
		int w = getWidth();
		int h = getHeight();
		int d = w < h ? w / 8 : h / 8;

		Color curr = Color.GRAY;

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				g.setColor(curr);
				g.fillRect(i * d, j * d, d, d);

				curr = curr.equals(Color.GRAY) ? Color.WHITE : Color.GRAY;

				if (board[i][j] != null && board[i][j] != drag)
				{
					Image image = board[i][j].getImage();

					int x = (i * d) + d / 2 - image.getWidth(null) / 2;
					int y = (j * d) + d / 2 - image.getHeight(null) / 2;

					g.drawImage(image, x, y, null);
				}
			}

			curr = curr.equals(Color.GRAY) ? Color.WHITE : Color.GRAY;

			if (drag != null)
			{
				g.drawImage(drag.getImage(), xCurr + dIx, yCurr + dIy, null);
			}
		}
	}

	// --------------------------------------------------------------------------------
	// MouseListener
	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public void mouseClicked(MouseEvent evt)
	{
		// Empty
	}

	/**
	 * 
	 */
	public void mouseEntered(MouseEvent e)
	{
		// Empty
	}

	/**
	 * 
	 */
	public void mouseExited(MouseEvent e)
	{
		drag = null;
		repaint();
	}

	/**
	 * 
	 */
	public void mousePressed(MouseEvent evt)
	{
		xCurr = evt.getX();
		yCurr = evt.getY();

		int i = getI(evt.getPoint());
		int j = getJ(evt.getPoint());

		if (board[i][j] != null && board[i][j].getColor() == play)
		{
			int w = getWidth();
			int h = getHeight();
			int d = w < h ? w / 8 : h / 8;

			Image image = board[i][j].getImage();

			int x = (i * d) + d / 2 - image.getWidth(null) / 2;
			int y = (j * d) + d / 2 - image.getHeight(null) / 2;

			dIx = x - xCurr;
			dIy = y - yCurr;

			drag = board[i][j];
		}

		repaint();
	}

	/**
	 * 
	 */
	public void mouseReleased(MouseEvent evt)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (board[i][j] != null && board[i][j].getColor() == play)
				{
					board[i][j].setPawnMove(false);
				}
			}
		}

		if (drag != null)
		{
			int i = getI(evt.getPoint());
			int j = getJ(evt.getPoint());

			if (drag.canMoveTo(i, j, board))
			{
				AbstractPiece captured = drag.captureTo(i, j, board);

				if (captured != null)
				{
					board[captured.getCurrI()][captured.getCurrJ()] = null;
				}

				drag.moveTo(i, j, board);

				play = (play == AbstractPiece.COLOR_WHITE ? AbstractPiece.COLOR_BLACK : AbstractPiece.COLOR_WHITE);
			}
		}

		drag = null;
		repaint();
	}

	// --------------------------------------------------------------------------------
	// MouseMotionListener
	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public void mouseDragged(MouseEvent evt)
	{
		int dx = evt.getX() - xPrev;
		int dy = evt.getY() - yPrev;

		if (drag != null)
		{
			xCurr = evt.getX();
			yCurr = evt.getY();
		}

		xPrev = evt.getX();
		yPrev = evt.getY();

		repaint();
	}

	/**
	 * 
	 */
	public void mouseMoved(MouseEvent e)
	{
		// Empty
	}
}
