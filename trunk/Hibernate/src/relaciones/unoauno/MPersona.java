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
public class MPersona {

  private int id;
  private MConyugue conyugue;

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
  public MConyugue getConyugue() {
    return conyugue;
  }

  public void setConyugue(MConyugue conyugue) {
    this.conyugue = conyugue;
  }
}