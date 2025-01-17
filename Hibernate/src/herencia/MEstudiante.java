package herencia;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
@Entity
@Table(name = "t_estudiante")
@Proxy(lazy = false)
public class MEstudiante extends MPersona {

  private float promedio;
  private int numMaterias;

  public float getPromedio() {
    return promedio;
  }

  public void setPromedio(float promedio) {
    this.promedio = promedio;
  }

  public int getNumMaterias() {
    return numMaterias;
  }

  public void setNumMaterias(int numMaterias) {
    this.numMaterias = numMaterias;
  }
}
