/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.excepciones;

/**
 * @author DMI: Demian Gutierrez
 */
public class NoVerificadaException extends RuntimeException
{
	/**
	 *
	 */
	public NoVerificadaException()
	{
		super();
	}

	/**
	 *
	 */
	public NoVerificadaException(String message)
	{
		super(message);
	}

	/**
	 *
	 */
	public NoVerificadaException(Throwable cause)
	{
		super(cause);
	}

	/**
	 *
	 */
	public NoVerificadaException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
