/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 27, 2004
 */
package com.minotauro.objetos;

/**
 * @author DMI: Demian Gutierrez
 */
public class Persona
{
	protected int cedula;
	protected String nombre;

	/**
	 *
	 */
	public Persona()
	{
		System.err.println("Estoy en Persona()");

		cedula = -1;
		nombre = "";
	}

	/**
	 *
	 */
	public Persona(int cedula, String nombre)
	{
		System.err.println("Estoy en Persona(int cedula, String nombre, String apellido)");
		System.err.println("\t" + cedula + ";" + nombre);

		this.cedula = cedula;
		this.nombre = nombre;
	}

	/**
	 *
	 */
	public int getCedula()
	{
		return cedula;
	}

	/**
	 *
	 */
	public void setCedula(int cedula)
	{
		this.cedula = cedula;
	}

	/**
	 *
	 */
	public String getNombre()
	{
		return nombre;
	}

	/**
	 *
	 */
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	/**
	 *
	 */
	public String toString()
	{
		return "Nombre: " + nombre + "\nCedula: " + cedula;
	}
}
