/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.estatico;

/**
 * @author DMI: Demian Gutierrez
 */
public class EjemploEstatico
{
	/**
	 * 
	 */
	public static void main(String[] args)
	{
		Mensaje.mensajeEstatico = "Este es el mensaje estatico. Es comun para la clase y todas las instancias";
		Mensaje.imprimeMensajeEstatico();

		Mensaje m1 = new Mensaje();
		// Cuando se usa un metodo estatico desde una instancia, Java genera un warning.
		// Esto es logico, porque un miembro estatico deberia ser accedido de forma estatica.
		// Sin embargo aqui lo pasamos por alto para poder completar la demostracion
		m1.imprimeMensajeEstatico();

		Mensaje m2 = new Mensaje();
		// Aplica la explicacion anterior del warning...
		m2.imprimeMensajeEstatico();

		m1.mensajeNoEstatico = "Este es el mensaje de la instancia m1...";
		m1.imprimeMensajes();

		m2.mensajeNoEstatico = "Este es el mensaje de la instancia m2...";
		m2.imprimeMensajes();

		// Finalmente, si cambiamos el mensaje estatico en m1...
		// Aplica la explicacion anterior del warning...
		m1.mensajeEstatico = "Peligro... Mensaje estatico alterado desde m1";

		// Podemos ver el cambio desde m2!!!
		// Aplica la explicacion anterior del warning...
		System.out.println(m2.mensajeEstatico);
	}
}
