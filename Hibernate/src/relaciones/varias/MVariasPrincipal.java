package relaciones.varias;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

/**
 * @author Demián Gutierrez
 */
@Entity
@Table(name = "t_varias_principal")
@Proxy(lazy = false)
public class MVariasPrincipal {

  private int id;
  private MVariasUno variasUnoRef;

  private String att;

  private List<MVariasMuchos> variasMuchosList = new ArrayList<MVariasMuchos>();

  // --------------------------------------------------------------------------------

  public MVariasPrincipal() {
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

  public String getAtt() {
    return att;
  }

  public void setAtt(String att) {
    this.att = att;
  }

  // --------------------------------------------------------------------------------

  @OneToOne(orphanRemoval = true)
  @PrimaryKeyJoinColumn
  @Cascade({CascadeType.ALL})
  public MVariasUno getVariasUnoRef() {
    return variasUnoRef;
  }

  public void setVariasUnoRef(MVariasUno variasUnoRef) {
    this.variasUnoRef = variasUnoRef;
  }

  // --------------------------------------------------------------------------------

  @OneToMany(mappedBy = "variasPrincipalRef", orphanRemoval = true)
  @LazyCollection(LazyCollectionOption.TRUE)
  @Cascade({CascadeType.ALL})
  // Revisar fetch
  public List<MVariasMuchos> getVariasMuchosList() {
    return variasMuchosList;
  }

  public void setVariasMuchosList(List<MVariasMuchos> variasMuchosList) {
    this.variasMuchosList = variasMuchosList;
  }
}
