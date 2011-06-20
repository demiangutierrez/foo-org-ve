package relaciones.unoamucho;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
@Entity
@Table(name = "t_muchos")
@Proxy(lazy = false)
public class MMuchos {

  private int id;

  private String other;

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

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  @ManyToOne
  public MUno getUnoRef() {
    return unoRef;
  }

  public void setUnoRef(MUno unoRef) {
    this.unoRef = unoRef;
  }
}
