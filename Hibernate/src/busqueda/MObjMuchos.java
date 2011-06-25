package busqueda;

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
@Table(name = "t_obj_muchos")
@Proxy(lazy = false)
public class MObjMuchos {

  private int id;

  private String other;

  // ...
  private MObjUno objUnoRef;

  // --------------------------------------------------------------------------------

  public MObjMuchos() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  // --------------------------------------------------------------------------------

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  // --------------------------------------------------------------------------------

  @ManyToOne
  public MObjUno getObjUnoRef() {
    return objUnoRef;
  }

  public void setObjUnoRef(MObjUno objUnoRef) {
    this.objUnoRef = objUnoRef;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return "id: " + id + " ; codigo: " + other + " ; objUnoRef.id: " + objUnoRef.getId();
  }
}
