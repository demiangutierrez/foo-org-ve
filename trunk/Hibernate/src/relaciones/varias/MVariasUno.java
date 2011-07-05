package relaciones.varias;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 */
@Entity
@Table(name = "t_varias_uno")
@Proxy(lazy = false)
public class MVariasUno {

  private int id;
  private String nombre;

  private MVariasPrincipal variasPrincipalRef;

  // --------------------------------------------------------------------------------

  public MVariasUno() {
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

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  // --------------------------------------------------------------------------------

  @OneToOne(mappedBy = "variasUnoRef")
  public MVariasPrincipal getVariasPrincipalRef() {
    return variasPrincipalRef;
  }

  public void setVariasPrincipalRef(MVariasPrincipal variasPrincipalRef) {
    this.variasPrincipalRef = variasPrincipalRef;
  }
}
