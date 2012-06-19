[#ftl]
// ----------------------------------------
// Generated code, do not edit
// ----------------------------------------

package com.minotauro.factura.freemarker;

public class Salida {

  public static void main(String[] args) {

    System.out.println("numero");
			    System.out.println(${numero});
    
	    System.out.println("cliente, cedula");
    	System.out.println("${cliente.cedula}");

  [#list item as currentItem]
		    System.out.println("item: ${currentItem.descripcion}, ${currentItem.monto}");
  [/#list]

    System.out.println("monto");
    System.out.println("${total.monto}");    
  }
}
