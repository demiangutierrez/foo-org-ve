package holamundo.java;
import java.util.ArrayList;
import java.util.List;

public class Persona {

  private String cedula;
  private String nombre;

  // ----------------------------------------

  public Persona() {
    // Empty
  }

  public Persona(String cedula, String nombre) {
    this.cedula = cedula;
    this.nombre = nombre;
  }

  // ----------------------------------------

  public String getCedula() {
    return cedula;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  // ----------------------------------------

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  // ----------------------------------------

  public void imprimir() {
    System.out.println("Cedula: " + cedula);
    System.out.println("Nombre: " + nombre);
  }

  // ----------------------------------------

  public List<String> validar() {
    List<String> ret = new ArrayList<String>();

    if (cedula == null || cedula.trim().equals("")) {
      ret.add("Cedula no puede ser nulo");
    }

    if (nombre == null || nombre.trim().equals("")) {
      ret.add("Nombre no puede ser nulo");
    }

    return ret;
  }
}
