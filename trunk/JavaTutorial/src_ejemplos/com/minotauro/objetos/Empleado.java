/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 27, 2004
 */
package com.minotauro.objetos;

/**
 * @author DMI: Demian Gutierrez
 */
public class Empleado extends Persona
{
	private int salario;
	private double ajuste;

	/**
	 *
	 */
	public Empleado()
	{
		super();

		System.err.println("Estoy en Empleado()");

		salario = 0;
		ajuste = 0;
	}

	/**
	 *
	 */
	public Empleado(int cedula, String nombre, int salario, double ajuste)
	{
		super(cedula, nombre);

		System.err.println("Estoy en Empleado(int cedula, String nombre, int salario, double ajuste)");
		System.err.println("\t" + cedula + ";" + nombre + ";" + salario + ";" + ajuste);

		this.salario = salario;
		this.ajuste = ajuste;
	}

	/**
	 *
	 */
	public int getSalario()
	{
		return salario;
	}

	/**
	 *
	 */
	public void setSalario(int salario)
	{
		this.salario = salario;
	}

	/**
	 *
	 */
	public double getAjuste()
	{
		return ajuste;
	}

	/**
	 *
	 */
	public void setAjuste(double ajuste)
	{
		this.ajuste = ajuste;
	}

	/**
	 *
	 */
	public double getSalarioConAjuste()
	{
		return salario * (1 + ajuste);
	}

	/**
	 *
	 */
	public String toString()
	{
		return super.toString() + "\nSalario: " + salario + "\nAjuste: " + ajuste;
	}
}
