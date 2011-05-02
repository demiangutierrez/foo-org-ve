package herencia;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 30, 2008
 */
@Entity
@Table(name = "t_empleado")
@Proxy(lazy = false)
public class Empleado extends Persona {

  private String cargo;
  private int antiguedad;

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public int getAntiguedad() {
    return antiguedad;
  }

  public void setAntiguedad(int antiguedad) {
    this.antiguedad = antiguedad;
  }
}