package relaciones.unoamucho;

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
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
@Entity
@Table(name = "t_uno")
@Proxy(lazy = false)
public class Uno {

  private int id;
  // ...
  private List<Muchos> muchosList = new ArrayList<Muchos>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @OneToMany(mappedBy = "unoRef")
  @LazyCollection(LazyCollectionOption.TRUE)
  @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
  // Revisar fetch
  public List<Muchos> getMuchosList() {
    return muchosList;
  }

  public void setMuchosList(List<Muchos> muchosList) {
    this.muchosList = muchosList;
  }
}