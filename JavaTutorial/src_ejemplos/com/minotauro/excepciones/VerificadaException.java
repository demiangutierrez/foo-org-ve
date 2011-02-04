/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.excepciones;

/**
 * @author DMI: Demian Gutierrez
 */
public class VerificadaException extends Exception
{
	/**
	 *
	 */
	public VerificadaException()
	{
		super();
	}

	/**
	 *
	 */
	public VerificadaException(String message)
	{
		super(message);
	}

	/**
	 *
	 */
	public VerificadaException(Throwable cause)
	{
		super(cause);
	}

	/**
	 *
	 */
	public VerificadaException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
