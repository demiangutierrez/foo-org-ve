package com.minotauro.factura.demo;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.minotauro.factura.xml.Factura;
import com.minotauro.factura.xml.Item;
import com.minotauro.factura.xml.ObjectFactory;

public class MainRead {

  public static void main(String[] args) throws Exception {

    // ----------------------------------------
    // Obtener el InputStream
    // ----------------------------------------

    InputStream is = //
    ClassLoader.getSystemResourceAsStream( //
        "com/minotauro/factura/demo/factura_1.xml");

    // ----------------------------------------
    // Inicializar JAXB y leer el XML
    // ----------------------------------------

    JAXBContext jc = JAXBContext.newInstance( //
        ObjectFactory.class.getPackage().getName());

    Unmarshaller unmarshaller = jc.createUnmarshaller();

    Factura xmlFactura = (Factura) unmarshaller.unmarshal(is);

    // ----------------------------------------
    // Procesar el arbol de objetos generado
    // ----------------------------------------

    System.err.println("factura numero: " + xmlFactura.getNumero());
    System.err.println( //
        "cliente cedula: " + xmlFactura.getCliente().getCedula());

    for (Item xmlItem : xmlFactura.getItem()) {
      System.err.println( //
          xmlItem.getDescripcion() + ";" + xmlItem.getMonto());
    }

    System.err.println(//
        "monto total: " + xmlFactura.getTotal().getMonto());
  }
}
