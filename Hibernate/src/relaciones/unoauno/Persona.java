package relaciones.unoauno;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 30, 2008
 */
@Entity
@Table(name = "t_persona")
@Proxy(lazy = false)
public class Persona {

  private int id;
  private Conyugue conyugue;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public Conyugue getConyugue() {
    return conyugue;
  }

  public void setConyugue(Conyugue conyugue) {
    this.conyugue = conyugue;
  }
}