/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.interfaces;

/**
 * @author DMI: Demian Gutierrez
 */
public class PrincipalFiguras
{
	/**
	 *
	 */
	public static void main(String[] args)
	{
		ObjetoBase ob1 = new Rectangulo();
		ObjetoBase ob2 = new Circulo();

		FiguraEstatica fe1 = new Rectangulo();
		FiguraEstatica fe2 = new Circulo();

		FiguraDinamica fd1 = new Rectangulo();
		FiguraDinamica fd2 = new Circulo();

		Dibujable d1 = new Rectangulo();
		Dibujable d2 = new Circulo();

		// Inicialmente son todos null
		// Por eso, en realidad, este ejemplo no corre
		// Esta pensado simplemente para mostrar la magia del polimorfismo!!!
		Dibujable[] d = new Dibujable[100];

		for (int i = 0; i < d.length; i++)
		{
			// Uso los metodos de dibujable
			// Si d[i] resulta ser un Rectangulo, se dibujara un rectangulo
			// Si resulta ser un Circulo, se dibujara un circulo
			d[i].pintar();

			// Este no es un molde seguro porque no sabemos si d[i] es un Circulo
			// o un Rectangulo. Si resultara ser un rectangulo, el molde generaria
			// una excepcion.
			Circulo cobj = (Circulo) d[i];
			Rectangulo robj = (Rectangulo) d[i]; // Igual que el anterior

			// Ejemplo de instanceof. Los siguientes moldes si son
			// seguros porque se realizan con la tranquilidad de que
			// d[i] es del tipo correcto.
			if (d[i] instanceof Circulo)
			{
				cobj = (Circulo) d[i];
			}
			else if (d[i] instanceof Rectangulo)
			{
				robj = (Rectangulo) d[i];
			}
		}
	}
}
