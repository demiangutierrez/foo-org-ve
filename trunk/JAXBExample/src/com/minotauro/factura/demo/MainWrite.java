package com.minotauro.factura.demo;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.minotauro.factura.xml.Cliente;
import com.minotauro.factura.xml.Factura;
import com.minotauro.factura.xml.Item;
import com.minotauro.factura.xml.ObjectFactory;
import com.minotauro.factura.xml.Total;

public class MainWrite {

  public static void main(String[] args) throws Exception {

    Factura xmlFactura = new Factura();
    xmlFactura.setNumero(13);

    Cliente xmlCliente = new Cliente();
    xmlCliente.setCedula("14555222");
    xmlFactura.setCliente(xmlCliente);

    Item xmlItem;

    xmlItem = new Item();
    xmlItem.setDescripcion("pizza");
    xmlItem.setMonto(45);
    xmlFactura.getItem().add(xmlItem);

    xmlItem = new Item();
    xmlItem.setDescripcion("refresco");
    xmlItem.setMonto(15);
    xmlFactura.getItem().add(xmlItem);

    Total xmlTotal = new Total();
    xmlTotal.setMonto(60);
    xmlFactura.setTotal(xmlTotal);

    JAXBContext jc = JAXBContext.newInstance( //
        ObjectFactory.class.getPackage().getName());

    Marshaller marshaller = jc.createMarshaller();

    FileOutputStream fos = new FileOutputStream("factura_2.xml");

    marshaller.marshal(xmlFactura, fos);

    fos.close();
  }
}
