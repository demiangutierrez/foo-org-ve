/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.estructuras;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploVector
{
	/**
	 *
	 */
	public EjemploVector()
	{
		// Nada
	}

	/**
	 *
	 */
	public static void imprimir(Vector v)
	{
		System.err.println("*********************");

		Iterator itt = v.iterator();

		while (itt.hasNext())
		{
			String s = (String) itt.next();

			System.err.println(s);
		}
	}

	/**
	 *
	 */
	public static void imprimirEnum(Vector v)
	{
		System.err.println("*********************");

		Enumeration enu = v.elements();

		while (enu.hasMoreElements())
		{
			String s = (String) enu.nextElement();

			System.err.println(s);
		}
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		Vector v = new Vector();

		v.add("Pedro");
		v.add("Mariela");
		v.add("Luis");
		imprimir(v);

		System.err.println("*********************");
		System.err.println("El vector tiene " + v.size() + " elementos");

		v.remove(1);
		imprimir(v);

		v.add(0, "Miguel");
		imprimir(v);

		System.err.println("*********************");
		System.err.println(v.get(0) + ";" + v.get(1) + ";" + v.get(2));

		v.set(2, "Estela");
		imprimirEnum(v);
	}
}
