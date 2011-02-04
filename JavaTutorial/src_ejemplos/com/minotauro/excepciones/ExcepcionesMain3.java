/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.excepciones;

/**
 * @author DMI: Demian Gutierrez
 */
public class ExcepcionesMain3
{
	/**
	 *
	 */
	public ExcepcionesMain3()
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo1() throws NoVerificadaException
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo2() throws VerificadaHijaException1
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo3() throws VerificadaHijaException1
	{
		// Empty
	}

	/**
	 *
	 */
	public void metodo4() throws VerificadaException
	{
		// Empty
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		ExcepcionesMain3 em = new ExcepcionesMain3();

		try
		{
			em.metodo1();
			em.metodo2();
			em.metodo3();
			em.metodo4();
		}
		catch (NoVerificadaException e)
		{
			// Atrapa NoVerificadaException
		}
		catch (VerificadaHijaException1 e)
		{
			// Atrapa VerificadaHijaException1
		}
		catch (VerificadaHijaException2 e)
		{
			// Atrapa VerificadaHijaException2
		}
		catch (VerificadaException e)
		{
			// Atrapa VerificadaException
			// Este bloque no puede colocarse antes de los dos anteriores
		}
		finally
		{
			// Siempre se ejecuta pase lo que pase
		}

		try
		{
			em.metodo1();
			em.metodo2();
			em.metodo3();
			em.metodo4();
		}
		catch (NoVerificadaException e)
		{
			// Atrapa NoVerificadaException
		}
		catch (VerificadaException e)
		{
			// Atrapa VerificadaException y todas sus hijas
			// Es decir, VerificadaHijaException1 y VerificadaHijaException2
		}
		finally
		{
			// Siempre se ejecuta pase lo que pase
		}
	}
}
