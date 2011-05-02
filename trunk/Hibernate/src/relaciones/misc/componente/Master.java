package relaciones.misc.componente;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 2, 2008
 */
@Entity
@Table(name = "t_master")
@Proxy(lazy = false)
public class Master {

  private int id;
  private Componente componente;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Componente getComponente() {
    return componente;
  }

  public void setComponente(Componente componente) {
    this.componente = componente;
  }
}