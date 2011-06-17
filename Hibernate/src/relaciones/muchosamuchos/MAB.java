package relaciones.muchosamuchos;

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
@Table(name = "t_ab")
@Proxy(lazy = false)
public class MAB {

  private int id;
  private MEntityA entityARef;
  private MEntityB entityBRef;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ManyToOne
  public MEntityA getEntityARef() {
    return entityARef;
  }

  public void setEntityARef(MEntityA entityARef) {
    this.entityARef = entityARef;
  }

  @ManyToOne
  public MEntityB getEntityBRef() {
    return entityBRef;
  }

  public void setEntityBRef(MEntityB entityBRef) {
    this.entityBRef = entityBRef;
  }
}