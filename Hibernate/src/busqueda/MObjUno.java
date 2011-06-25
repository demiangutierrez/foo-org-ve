package busqueda;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
@Entity
@Table(name = "t_obj_uno")
@Proxy(lazy = false)
public class MObjUno {

  private int id;
  private int num;
  private String codigo;

  private List<MObjMuchos> objMuchosList = new ArrayList<MObjMuchos>();

  // --------------------------------------------------------------------------------

  public MObjUno() {
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

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  // --------------------------------------------------------------------------------

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  // --------------------------------------------------------------------------------

  @OneToMany(mappedBy = "objUnoRef", orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.TRUE)
  @Cascade({CascadeType.ALL})
  // Revisar fetch
  public List<MObjMuchos> getObjMuchosList() {
    return objMuchosList;
  }

  public void setObjMuchosList(List<MObjMuchos> objMuchosList) {
    this.objMuchosList = objMuchosList;
  }

  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return "id: " + id + " ; codigo: " + codigo + " ; num: " + num;
  }
}
