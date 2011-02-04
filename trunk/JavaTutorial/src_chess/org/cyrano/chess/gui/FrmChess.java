/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Oct 24, 2003
 */
package org.cyrano.chess.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import org.cyrano.chess.pieces.AbstractPiece;
import org.cyrano.chess.pieces.BishopPiece;
import org.cyrano.chess.pieces.CastlePiece;
import org.cyrano.chess.pieces.KingPiece;
import org.cyrano.chess.pieces.KnightPiece;
import org.cyrano.chess.pieces.PawnPiece;
import org.cyrano.chess.pieces.QueenPiece;

/**
 * @author DMI: Demian Gutierrez
 */
public class FrmChess extends JFrame
{
	private static int WHITE_UP = 0;

	private static int WHITE_DN = 1;

	private AbstractPiece[][] board;

	/**
	 * 
	 */
	public FrmChess()
	{
		initGUI();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		setResizable(false);

		repaint();
	}

	/**
	 * 
	 */
	private void initGUI()
	{
		getContentPane().setLayout(new GridBagLayout());

		LblChess lblChess = new LblChess();
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;

		lblChess.setPreferredSize(new Dimension(480, 480));

		getContentPane().add(lblChess, gbc);
		initBoard(WHITE_UP);
		lblChess.setBoard(board);
	}

	/**
	 * 
	 */
	private void initBoard(int whiteFlag)
	{
		int white_pawns_j = whiteFlag == WHITE_DN ? 6 : 1;
		int black_pawns_j = whiteFlag == WHITE_DN ? 1 : 6;

		int white_piece_j = whiteFlag == WHITE_DN ? 7 : 0;
		int black_piece_j = whiteFlag == WHITE_DN ? 0 : 7;

		int white_king_i = whiteFlag == WHITE_DN ? 4 : 3;
		int black_king_i = whiteFlag == WHITE_DN ? 3 : 4;

		int white_queen_i = whiteFlag == WHITE_DN ? 3 : 4;
		int black_queen_i = whiteFlag == WHITE_DN ? 4 : 3;

		int white_from = whiteFlag == WHITE_DN ? AbstractPiece.FROM_SOUTH : AbstractPiece.FROM_NORTH;
		int black_from = whiteFlag == WHITE_DN ? AbstractPiece.FROM_NORTH : AbstractPiece.FROM_SOUTH;

		board = new AbstractPiece[8][8];

		for (int i = 0; i < 8; i++)
		{
			AbstractPiece.setDefaultColor(AbstractPiece.COLOR_WHITE);
			AbstractPiece.setDefaultFrom(white_from);
			board[i][white_pawns_j] = new PawnPiece(i, white_pawns_j);

			AbstractPiece.setDefaultColor(AbstractPiece.COLOR_BLACK);
			AbstractPiece.setDefaultFrom(black_from);
			board[i][black_pawns_j] = new PawnPiece(i, black_pawns_j);
		}

		AbstractPiece.setDefaultColor(AbstractPiece.COLOR_WHITE);
		AbstractPiece.setDefaultFrom(white_from);
		board[0][white_piece_j] = new CastlePiece(0, white_piece_j);
		board[1][white_piece_j] = new KnightPiece(1, white_piece_j);
		board[2][white_piece_j] = new BishopPiece(2, white_piece_j);
		board[white_queen_i][white_piece_j] = new QueenPiece(white_queen_i, white_piece_j);
		board[white_king_i][white_piece_j] = new KingPiece(white_king_i, white_piece_j);
		board[5][white_piece_j] = new BishopPiece(5, white_piece_j);
		board[6][white_piece_j] = new KnightPiece(6, white_piece_j);
		board[7][white_piece_j] = new CastlePiece(7, white_piece_j);

		AbstractPiece.setDefaultColor(AbstractPiece.COLOR_BLACK);
		AbstractPiece.setDefaultFrom(black_from);
		board[0][black_piece_j] = new CastlePiece(0, black_piece_j);
		board[1][black_piece_j] = new KnightPiece(1, black_piece_j);
		board[2][black_piece_j] = new BishopPiece(2, black_piece_j);
		board[black_king_i][black_piece_j] = new KingPiece(black_king_i, black_piece_j);
		board[black_queen_i][black_piece_j] = new QueenPiece(black_queen_i, black_piece_j);
		board[5][black_piece_j] = new BishopPiece(5, black_piece_j);
		board[6][black_piece_j] = new KnightPiece(6, black_piece_j);
		board[7][black_piece_j] = new CastlePiece(7, black_piece_j);
	}

	/**
	 * 
	 */
	public static void main(String[] args)
	{
		new FrmChess();
	}
}
