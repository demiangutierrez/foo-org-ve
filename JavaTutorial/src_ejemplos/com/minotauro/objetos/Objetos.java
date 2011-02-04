/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 27, 2004
 */
package com.minotauro.objetos;

/**
 * @author DMI: Demian Gutierrez
 */
public class Objetos
{
	/**
	 *
	 */
	public static void main(String[] args)
	{
		// Una persona
		Persona p1 = new Persona();
		System.out.println(p1);

		// Otra persona
		Persona p2 = new Persona(10234999, "Pedro");
		System.out.println(p2);

		// Un empleado
		Empleado e1 = new Empleado();
		e1.setCedula(12334555);
		e1.setNombre("Juan");
		e1.setSalario(200000);
		e1.setAjuste(0.1);

		System.out.println(e1);
		System.out.println(e1.getSalarioConAjuste());

		// Valido y natural
		Persona p3 = e1;

		// Otro empleado, asignado a una persona
		Persona p4 = new Empleado();
		p4.setCedula(11990223);
		p4.setNombre("Jose");

		// Valido, pero es necesario un molde
		Empleado e2 = (Empleado) p4;

		System.out.println("Datos de e2 (Persona)");
		System.out.println(e2.getNombre() + ";" + e2.getCedula());

		// Aumento de salario
		e2.setSalario(e2.getSalario() + 100000);
		int salario = e2.getSalario();

		System.out.println("Datos de e2 (Empleado)");
		System.out.println(salario);
	}
}
