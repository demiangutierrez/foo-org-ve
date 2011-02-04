/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.abstracto;

import com.minotauro.interfaces.FiguraDinamica;

/**
 * @author DMI: Demian Gutierrez
 */
public abstract class ObjetoBase implements FiguraDinamica
{
	private int id;

	/**
	 *
	 */
	public ObjetoBase()
	{
		// Empty
	}

	/**
	 *
	 */
	public int getId()
	{
		return id;
	}

	/**
	 *
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 *
	 */
	public abstract void pintar();
}
