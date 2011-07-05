package relaciones.varias;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 */
@Entity
@Table(name = "t_varias_muchos")
@Proxy(lazy = false)
public class MVariasMuchos {

  private int id;

  private String other;

  // ...
  private MVariasPrincipal variasPrincipalRef;

  // --------------------------------------------------------------------------------

  public MVariasMuchos() {
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
  public MVariasPrincipal getVariasPrincipalRef() {
    return variasPrincipalRef;
  }

  public void setVariasPrincipalRef(MVariasPrincipal variasPrincipalRef) {
    this.variasPrincipalRef = variasPrincipalRef;
  }
}
