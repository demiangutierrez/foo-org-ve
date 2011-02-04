/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.abstracto;

/**
 * @author DMI: Demian Gutierrez
 */
public class Circulo extends ObjetoBase
{
	public int xc;
	public int yc;
	public int r;

	/**
	 * 
	 */
	public Circulo()
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
		return Math.PI * r * r;
	}

	/**
	 *
	 */
	public boolean contiene(int x, int y)
	{
		double d = Math.sqrt((x - xc) * (x - xc) + (y - yc) * (y - yc));

		return d <= r;
	}

	// --------------------------------------------------------------------------------
	// FiguraDinamica
	// --------------------------------------------------------------------------------

	/**
	 *
	 */
	public void mover(int dx, int dy)
	{
		xc += dx;
		yc += dy;
	}

	// --------------------------------------------------------------------------------
	// ObjetoBase
	// --------------------------------------------------------------------------------

	/**
	 *
	 */
	public void pintar()
	{
		// Aqui va el codigo que pinta un Circulo
	}
}
