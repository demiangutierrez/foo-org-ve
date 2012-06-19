package com.minotauro.factura.freemarker;

import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.minotauro.factura.xml.Factura;
import com.minotauro.factura.xml.ObjectFactory;

import freemarker.cache.URLTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class Generador extends URLTemplateLoader {

  public void run() throws Exception {
    Configuration configuration = new Configuration();
    configuration.setObjectWrapper(new BeansWrapper());
    configuration.setTemplateLoader(this);

    FileWriter fileWriter = new FileWriter( //
        "src/com/minotauro/factura/freemarker/Salida.java");

    Factura factura = leerXML();

    configuration.getTemplate( //
        "/com/minotauro/factura/freemarker/main.ftl").process( //
        factura, fileWriter);

    fileWriter.close();
  }

  private Factura leerXML() throws Exception {
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

    return xmlFactura;
  }

  // ----------------------------------------
  // URLTemplateLoader
  // ----------------------------------------

  @Override
  protected URL getURL(String name) {
    return getClass().getClassLoader().getResource(name);
  }

  // ----------------------------------------

  public static void main(String[] args) throws Exception {
    new Generador().run();
  }
}
