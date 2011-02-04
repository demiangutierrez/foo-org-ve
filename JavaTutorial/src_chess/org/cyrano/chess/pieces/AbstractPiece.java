/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Oct 24, 2003
 */
package org.cyrano.chess.pieces;

import java.awt.Image;

/**
 * @author DMI: Demian Gutierrez
 */
public abstract class AbstractPiece
{
	public static int COLOR_WHITE = 0;

	public static int COLOR_BLACK = 1;

	public static int FROM_NORTH = -1;

	public static int FROM_SOUTH = 1;

	protected static int defaultColor;

	protected static int defaultFrom;

	protected boolean firstMove = true;

	protected boolean pawnMove = false;

	protected int color;

	protected int from;

	protected int currI = -1;

	protected int currJ = -1;

	/**
	 * 
	 */
	public AbstractPiece(int currI, int currJ)
	{
		this.currI = currI;
		this.currJ = currJ;

		color = defaultColor;
		from = defaultFrom;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public static int getDefaultColor()
	{
		return defaultColor;
	}

	/**
	 * 
	 */
	public static void setDefaultColor(int defaultColor)
	{
		AbstractPiece.defaultColor = defaultColor;
	}

	/**
	 * 
	 */
	public static int getDefaultFrom()
	{
		return defaultFrom;
	}

	/**
	 * 
	 */
	public static void setDefaultFrom(int defaultFrom)
	{
		AbstractPiece.defaultFrom = defaultFrom;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	protected boolean castleMove(int i, int j, AbstractPiece[][] board, boolean king)
	{
		if (board[i][j] != null && board[i][j].getColor() == color)
		{
			return false;
		}

		int di = i > currI ? 1 : -1;
		int dj = j > currJ ? 1 : -1;

		int si = currI + di;
		int sj = currJ;

		while (si > -1 && si < 8 && sj > -1 && sj < 8)
		{
			if (si == i && sj == j)
			{
				return true;
			}

			if (board[si][sj] != null || king)
			{
				break;
			}

			si += di;
		}

		si = currI;
		sj = currJ + dj;

		while (si > -1 && si < 8 && sj > -1 && sj < 8)
		{
			if (si == i && sj == j)
			{
				return true;
			}

			if (board[si][sj] != null || king)
			{
				break;
			}

			sj += dj;
		}

		return false;
	}

	/**
	 * 
	 */
	protected boolean bishopMove(int i, int j, AbstractPiece[][] board, boolean king)
	{
		if (board[i][j] != null && board[i][j].getColor() == color)
		{
			return false;
		}

		int di = i > currI ? 1 : -1;
		int dj = j > currJ ? 1 : -1;

		int si = currI + di;
		int sj = currJ + dj;

		while (si > -1 && si < 8 && sj > -1 && sj < 8)
		{
			if (si == i && sj == j)
			{
				return true;
			}

			if (board[si][sj] != null || king)
			{
				break;
			}

			si += di;
			sj += dj;
		}

		return false;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public boolean getFirstMove()
	{
		return firstMove;
	}

	/**
	 * 
	 */
	public void setFirstMove(boolean firstMove)
	{
		this.firstMove = firstMove;
	}

	/**
	 * 
	 */
	public boolean getPawnMove()
	{
		return pawnMove;
	}

	/**
	 * 
	 */
	public void setPawnMove(boolean pawnMove)
	{
		this.pawnMove = pawnMove;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public int getColor()
	{
		return color;
	}

	/**
	 * 
	 */
	public void setColor(int color)
	{
		this.color = color;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public int getFrom()
	{
		return from;
	}

	/**
	 * 
	 */
	public void setFrom(int from)
	{
		this.from = from;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public void moveTo(int i, int j, AbstractPiece[][] board)
	{
		firstMove = false;

		if (currI != -1 && currJ != -1)
		{
			board[currI][currJ] = null;
		}

		currI = i;
		currJ = j;

		board[i][j] = this;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public abstract Image getImage();

	/**
	 * 
	 */
	public abstract boolean canMoveTo(int i, int j, AbstractPiece[][] board);

	/**
	 * 
	 */
	public abstract AbstractPiece captureTo(int i, int j, AbstractPiece[][] board);

	/**
	 * @return
	 */
	public int getCurrI()
	{
		return currI;
	}

	/**
	 * @return
	 */
	public int getCurrJ()
	{
		return currJ;
	}

	/**
	 * @param i
	 */
	public void setCurrI(int i)
	{
		currI = i;
	}

	/**
	 * @param i
	 */
	public void setCurrJ(int i)
	{
		currJ = i;
	}

}
