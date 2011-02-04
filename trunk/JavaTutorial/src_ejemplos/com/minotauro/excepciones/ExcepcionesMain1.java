/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.excepciones;

/**
 * @author DMI: Demian Gutierrez
 */
public class ExcepcionesMain1
{
	/**
	 *
	 */
	public ExcepcionesMain1()
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo1() throws VerificadaException
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo2() throws NoVerificadaException
	{
		// Empty
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		ExcepcionesMain1 em = new ExcepcionesMain1();

		// Es necesario capturar o arrojar las excepciones verificadas
		try
		{
			em.metodo1();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// No es necesario capturar o arrojar las excepciones no verificadas
		// Pero si se produce una, el programa se estrella
		em.metodo2();
	}
}
