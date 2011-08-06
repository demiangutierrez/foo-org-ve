package relaciones.unoauno;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
@Entity
@Table(name = "t_persona")
@Proxy(lazy = false)
public class MPersona {

  private int id;
  private String nombre;
  private MConyugue conyugue;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
  public MConyugue getConyugue() {
    return conyugue;
  }

  public void setConyugue(MConyugue conyugue) {
    this.conyugue = conyugue;
  }
}
