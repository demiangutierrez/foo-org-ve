package relaciones.unoauno;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
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
  // @GeneratedValue(strategy = GenerationType.AUTO) // We can't do this
  @GeneratedValue(generator = "take-from-foreign")

  // Generates the ID based in persona's id
  @GenericGenerator( //
  /*      */name = "take-from-foreign", //
  /*  */strategy = "foreign", //
  /**/parameters = {@Parameter(name = "property", value = "persona")})
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

  // Conyugue depends on Persona, the place of
  // @PrimaryKeyJoinColumn marks foreign key
  @OneToOne
  @PrimaryKeyJoinColumn
  public MPersona getPersona() {
    return persona;
  }

  public void setPersona(MPersona persona) {
    this.persona = persona;
  }
}
