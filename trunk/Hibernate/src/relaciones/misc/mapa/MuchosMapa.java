package relaciones.misc.mapa;

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
@Table(name = "t_muchos_mapa")
@Proxy(lazy = false)
public class MuchosMapa {

  private int id;
  private int codigo;
  private UnoMapa unoMapaRef;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  @ManyToOne
  public UnoMapa getUnoMapaRef() {
    return unoMapaRef;
  }

  public void setUnoMapaRef(UnoMapa unoRef) {
    this.unoMapaRef = unoRef;
  }
}