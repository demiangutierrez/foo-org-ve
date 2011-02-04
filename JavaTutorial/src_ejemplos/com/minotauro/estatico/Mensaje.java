/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.estatico;

/**
 * @author DMI: Demian Gutierrez
 */
public class Mensaje
{
	// Variable de clase
	public static String mensajeEstatico;

	// Variable de instancia
	public String mensajeNoEstatico;

	/**
	 * 
	 */
	public Mensaje()
	{
		// Empty
	}

	/**
	 * 
	 */
	public void imprimeMensajes()
	{
		System.out.println(mensajeEstatico);
		System.out.println(mensajeNoEstatico);
	}

	/**
	 * 
	 */
	public static void imprimeMensajeEstatico()
	{
		System.out.println(mensajeEstatico);

		// Esto es un error, porque las variables de instancia
		// no se pueden acceder desde metodos estaticos.

		// System.out.println(mensajeNoEstatico);
	}
}
