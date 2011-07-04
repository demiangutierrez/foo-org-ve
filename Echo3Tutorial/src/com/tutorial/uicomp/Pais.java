package com.tutorial.uicomp;

public class Pais {

  private String nombre;

  // Cualquier otra cosa que se quiera de un elemento en un combo.
  private int id;

  public Pais(String nombre, int id) {
    this.nombre = nombre;
    this.id = id;
  }

  @Override
  public String toString() {
    return nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
