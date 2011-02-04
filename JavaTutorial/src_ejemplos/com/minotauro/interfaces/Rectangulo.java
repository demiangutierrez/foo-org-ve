/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.interfaces;

/**
 * @author DMI: Demian Gutierrez
 */
public class Rectangulo extends ObjetoBase implements FiguraDinamica, Dibujable
{
	public int x1;
	public int y1;
	public int x2;
	public int y2;

	/**
	 *
	 */
	public Rectangulo()
	{
		// Empty
	}

	// --------------------------------------------------------------------------------
	// FiguraEstatica
	// --------------------------------------------------------------------------------

	/**
	 *
	 */
	public double area()
	{
		return Math.abs(x2 - x1) * Math.abs(y2 - y1);
	}

	/**
	 *
	 */
	public boolean contiene(int x, int y)
	{
		boolean xcon = (x1 < x && x < x2) || (x2 < x && x < x1);
		boolean ycon = (y1 < y && y < y2) || (y2 < y && y < y1);

		return xcon && ycon;
	}

	// --------------------------------------------------------------------------------
	// FiguraDinamica
	// --------------------------------------------------------------------------------

	/**
	 *
	 */
	public void mover(int dx, int dy)
	{
		x1 += dx;
		y1 += dy;
		x2 += dx;
		y2 += dy;
	}

	// --------------------------------------------------------------------------------
	// Dibujable
	// --------------------------------------------------------------------------------

	/**
	 *
	 */
	public void pintar()
	{
		// Aqui va el codigo que pinta un Rectangulo
	}
}
