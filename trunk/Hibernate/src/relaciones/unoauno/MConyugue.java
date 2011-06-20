package relaciones.unoauno;

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
@Table(name = "t_conyugue")
@Proxy(lazy = false)
public class MConyugue {

  private int id;
  private String nombre;
  private MPersona persona;

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

  @OneToOne(mappedBy = "conyugue")
  public MPersona getPersona() {
    return persona;
  }

  public void setPersona(MPersona persona) {
    this.persona = persona;
  }
}
