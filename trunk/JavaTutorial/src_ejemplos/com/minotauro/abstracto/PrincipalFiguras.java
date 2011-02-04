/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on May 28, 2004
 */
package com.minotauro.abstracto;

import com.minotauro.interfaces.FiguraDinamica;
import com.minotauro.interfaces.FiguraEstatica;

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

		// Inicialmente son todos null
		// Por eso, en realidad, este ejemplo no corre
		// Esta pensado simplemente para mostrar la magia del polimorfismo!!!
		ObjetoBase[] ob = new ObjetoBase[100];

		for (int i = 0; i < ob.length; i++)
		{
			// Uso los metodos de ObjetoBase
			// Si d[i] resulta ser un Rectangulo, se dibujara un rectangulo
			// Si resulta ser un Circulo, se dibujara un circulo
			ob[i].pintar();

			// Este no es un molde seguro porque no sabemos si d[i] es un Circulo
			// o un Rectangulo. Si resultara ser un rectangulo, el molde generaria
			// una excepcion.
			Circulo cobj = (Circulo) ob[i];
			Rectangulo robj = (Rectangulo) ob[i]; // Igual que el anterior

			// Ejemplo de instanceof. Los siguientes moldes si son
			// seguros porque se realizan con la tranquilidad de que
			// d[i] es del tipo correcto.
			if (ob[i] instanceof Circulo)
			{
				cobj = (Circulo) ob[i];
			}
			else if (ob[i] instanceof Rectangulo)
			{
				robj = (Rectangulo) ob[i];
			}
		}
	}
}
