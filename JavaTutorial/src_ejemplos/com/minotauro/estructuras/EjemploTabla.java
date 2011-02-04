/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Feb 13, 2004
 */
package com.minotauro.estructuras;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploTabla
{
	/**
	 *
	 */
	public EjemploTabla()
	{
		// Nada
	}

	/**
	 *
	 */
	public static void imprimirClaves(Map m)
	{
		System.err.println("********************* Claves");

		Iterator itt = m.keySet().iterator();

		while (itt.hasNext())
		{
			String s = (String) itt.next();

			System.err.println(s);
		}
	}

	/**
	 *
	 */
	public static void imprimirValores(Map m)
	{
		System.err.println("********************* Valores");

		Iterator itt = m.values().iterator();

		while (itt.hasNext())
		{
			Integer i = (Integer) itt.next();

			System.err.println(i);
		}
	}

	/**
	 *
	 */
	public static void imprimirTodo(Map m)
	{
		System.err.println("********************* Todo");

		Iterator itt = m.entrySet().iterator();

		while (itt.hasNext())
		{
			Map.Entry e = (Map.Entry) itt.next();

			System.err.println(e.getKey() + ";" + e.getValue());
		}
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		// HashMap m = new HashMap();
		// Hashtable m = new Hashtable();
		// Map m = new Hashtable();
		Map m = new HashMap();

		m.put("Pedro", new Integer(25));
		m.put("Mariela", new Integer(23));
		m.put("Miguel", new Integer(19));

		imprimirClaves(m);
		imprimirValores(m);
		imprimirTodo(m);

		System.err.println("*********************");
		System.err.println("Edad de Mariela: " + m.get("Mariela"));

		System.err.println("*********************");
		System.err.println("La tabla tiene " + m.size() + " elementos");

		m.remove("Mariela");
		imprimirTodo(m);

		Integer i = (Integer) m.get("Miguel");
		m.put("Miguel", new Integer(i.intValue() + 1));
		imprimirTodo(m);

		System.err.println("*********************");
		System.err.println("Edad de alguien que no existe: " + m.get("alguien que no existe"));
	}
}
