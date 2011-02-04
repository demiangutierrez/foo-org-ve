/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Oct 24, 2003
 */
package org.cyrano.chess.pieces;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author DMI: Demian Gutierrez
 */
public class PawnPiece extends AbstractPiece
{
	protected static Image whiteImage;

	protected static Image blackImage;

	/**
	 * 
	 */
	static {

		try
		{
			whiteImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/WhitePawn.png"));
			blackImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/BlackPawn.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public PawnPiece(int currI, int currJ)
	{
		super(currI, currJ);
	}

	// --------------------------------------------------------------------------------
	// AbstractPiece
	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public Image getImage()
	{
		return color == COLOR_WHITE ? whiteImage : blackImage;
	}

	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public boolean canMoveTo(int i, int j, AbstractPiece[][] board)
	{
		if (board[i][j] != null && board[i][j].getColor() == color)
		{
			return false;
		}

		if (firstMove && currI == i)
		{
			for (int di = 1; di < 3; di++)
			{
				if (board[i][currJ - from * di] != null)
				{
					break;
				}

				if (j == currJ - from * di)
				{
					if (di == 2)
					{
						pawnMove = true;
					}

					return true;
				}
			}
		}

		if ((currI == i - 1 || currI == i || currI == i + 1) && currJ - from == j)
		{
			if (currI == i)
			{
				if (board[i][j] != null)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
			else
			{
				if (board[i][j] != null)
				{
					return true;
				}
				else
				{
					System.err.println(board[i][j + from]);
					if (board[i][j + from] != null)
					{
						System.err.println(board[i][j + from].getPawnMove() + ";" + board[i][j + from].getColor());
					}
					if (board[i][j + from] != null && board[i][j + from].getPawnMove())
					{
						return true;
					}

					return false;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 */
	public AbstractPiece captureTo(int i, int j, AbstractPiece[][] board)
	{
		if (i == currI - 1 && board[i][j + from] != null && board[i][j + from].getPawnMove())
		{
			return board[i][j + from];
		}

		if (i == currI + 1 && board[i][j + from] != null && board[i][j + from].getPawnMove())
		{
			return board[i][j + from];
		}

		return board[i][j];
	}
}
