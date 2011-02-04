/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.excepciones;

/**
 * @author DMI: Demian Gutierrez
 */
public class ExcepcionesMain2
{
	/**
	 *
	 */
	public ExcepcionesMain2()
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo1()
	{
		try
		{
			metodo2();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public void metodo2() throws VerificadaException
	{
		metodo3();
	}

	/**
	 *
	 */
	public void metodo3() throws VerificadaException
	{
		throw new VerificadaException();
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		ExcepcionesMain2 em = new ExcepcionesMain2();

		em.metodo1();
	}
}
