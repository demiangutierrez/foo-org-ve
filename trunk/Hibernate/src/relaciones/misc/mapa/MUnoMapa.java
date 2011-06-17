package relaciones.misc.mapa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
@Entity
@Table(name = "t_uno_mapa")
@Proxy(lazy = false)
public class MUnoMapa {

  private int id;
  private Map<Integer, MMuchosMapa> muchosMap = new HashMap<Integer, MMuchosMapa>();

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @OneToMany(mappedBy = "unoMapaRef", orphanRemoval = true)
  @Cascade({CascadeType.ALL})
  @MapKey(name = "codigo")
  public Map<Integer, MMuchosMapa> getMuchosMap() {
    return muchosMap;
  }

  public void setMuchosMap(Map<Integer, MMuchosMapa> muchosMap) {
    this.muchosMap = muchosMap;
  }
}