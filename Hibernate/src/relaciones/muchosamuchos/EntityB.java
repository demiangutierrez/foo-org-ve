package relaciones.muchosamuchos;

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
@Table(name = "t_entity_b")
@Proxy(lazy = false)
public class EntityB {

  private int id;
  private List<AB> abList = new ArrayList<AB>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @OneToMany(mappedBy = "entityBRef")
  @LazyCollection(LazyCollectionOption.TRUE)
  @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
  public List<AB> getAbList() {
    return abList;
  }

  public void setAbList(List<AB> abList) {
    this.abList = abList;
  }
}