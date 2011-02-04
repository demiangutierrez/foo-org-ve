/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Mar 5, 2004
 */
package com.minotauro.otros.applets;

import javax.swing.JApplet;

/**
 * @author DMI: Demian Gutierrez
 */
public class HelloApplet extends JApplet
{
	/**
	 *
	 */
	public HelloApplet()
	{
		// Empty
	}

	/**
	 *
	 */
	public void init()
	{
		// Empty

		System.err.println("public void init()");
	}

	/**
	 *
	 */
	public void start()
	{
		System.err.println("public void start()");
	}

	/**
	 *
	 */
	public void stop()
	{
		// Empty

		System.err.println("public void stop()");
	}

	/**
	 *
	 */
	public void destroy()
	{
		// Empty

		System.err.println("public void destroy()");
	}
}
