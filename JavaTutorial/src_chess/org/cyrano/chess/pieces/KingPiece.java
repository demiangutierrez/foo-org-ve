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
public class KingPiece extends AbstractPiece
{
	protected static Image whiteImage;

	protected static Image blackImage;

	/**
	 * 
	 */
	static {

		try
		{
			whiteImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/WhiteKing.png"));
			blackImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/BlackKing.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public KingPiece(int currI, int currJ)
	{
		super(currI, currJ);
	}

	// --------------------------------------------------------------------------------
	// AbstractPiece
	// --------------------------------------------------------------------------------

	/**
	 * 
	 */
	public void moveTo(int i, int j, AbstractPiece[][] board)
	{
		int n3 = from == FROM_SOUTH ? -3 : 3;
		int n2 = from == FROM_SOUTH ? 2 : -2;
		int m3 = from == FROM_SOUTH ? -4 : 4;
		int m2 = from == FROM_SOUTH ? 3 : -3;
		int g3 = from == FROM_SOUTH ? 2 : 5;
		int g2 = from == FROM_SOUTH ? 5 : 2;

		int s3 = from == FROM_SOUTH ? -1 : 1;
		int s2 = from == FROM_SOUTH ? 1 : -1;

		if (firstMove)
		{
			if (i == currI + n3)
			{
				AbstractPiece piece = board[currI + 4 * s3][currJ];

				if (piece != null
					&& piece.getFirstMove()
					&& board[currI + 1 * s3][currJ] == null
					&& board[currI + 2 * s3][currJ] == null
					&& board[currI + 3 * s3][currJ] == null)
				{
					board[currI + m3][currJ].moveTo(g3, currJ, board);
				}
			}

			if (i == currI + n2)
			{
				AbstractPiece piece = board[currI + 3 * s2][currJ];

				if (piece != null
					&& piece.getFirstMove()
					&& board[currI + 1 * s2][currJ] == null
					&& board[currI + 2 * s2][currJ] == null)
				{
					board[currI + m2][currJ].moveTo(g2, currJ, board);
				}
			}
		}

		super.moveTo(i, j, board);
	}

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
		int n3 = from == FROM_SOUTH ? -3 : 3;
		int n2 = from == FROM_SOUTH ? 2 : -2;
		int m3 = from == FROM_SOUTH ? -4 : 3;
		int m2 = from == FROM_SOUTH ? 3 : -4;

		int s3 = from == FROM_SOUTH ? -1 : 1;
		int s2 = from == FROM_SOUTH ? 1 : -1;

		if (firstMove)
		{
			if (i == currI + n3)
			{
				AbstractPiece piece = board[currI + 4 * s3][currJ];

				if (piece != null
					&& piece.getFirstMove()
					&& board[currI + 1 * s3][currJ] == null
					&& board[currI + 2 * s3][currJ] == null
					&& board[currI + 3 * s3][currJ] == null)
				{
					return true;
				}
			}

			if (i == currI + n2)
			{
				AbstractPiece piece = board[currI + 3 * s2][currJ];

				if (piece != null
					&& piece.getFirstMove()
					&& board[currI + 1 * s2][currJ] == null
					&& board[currI + 2 * s2][currJ] == null)
				{
					return true;
				}
			}
		}

		return bishopMove(i, j, board, true) || castleMove(i, j, board, true);
	}

	/**
	 * 
	 */
	public AbstractPiece captureTo(int i, int j, AbstractPiece[][] board)
	{
		return board[i][j];
	}
}
