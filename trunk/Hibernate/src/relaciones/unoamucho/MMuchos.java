package relaciones.unoamucho;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
@Entity
@Table(name = "t_muchos")
@Proxy(lazy = false)
public class MMuchos {

  private int id;
  // ...
  private MUno unoRef;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ManyToOne
  public MUno getUnoRef() {
    return unoRef;
  }

  public void setUnoRef(MUno unoRef) {
    this.unoRef = unoRef;
  }
}