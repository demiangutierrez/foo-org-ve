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
public class AB {

  private int id;
  private EntityA entityARef;
  private EntityB entityBRef;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @ManyToOne
  public EntityA getEntityARef() {
    return entityARef;
  }

  public void setEntityARef(EntityA entityARef) {
    this.entityARef = entityARef;
  }

  @ManyToOne
  public EntityB getEntityBRef() {
    return entityBRef;
  }

  public void setEntityBRef(EntityB entityBRef) {
    this.entityBRef = entityBRef;
  }
}