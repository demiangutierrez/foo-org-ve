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
public class KnightPiece extends AbstractPiece
{
	protected static Image whiteImage;

	protected static Image blackImage;

	/**
	 * 
	 */
	static {

		try
		{
			whiteImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/WhiteKnight.png"));
			blackImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("org/cyrano/chess/images/BlackKnight.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public KnightPiece(int currI, int currJ)
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

		int di = Math.abs(i - currI);
		int dj = Math.abs(j - currJ);

		if (di == 2 && dj == 1)
		{
			return true;
		}

		if (di == 1 && dj == 2)
		{
			return true;
		}

		return false;
	}

	/**
	 * 
	 */
	public AbstractPiece captureTo(int i, int j, AbstractPiece[][] board)
	{
		return board[i][j];
	}
}
