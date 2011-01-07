package dao.reflection;

import dao.annotation.ManyToOne;
import dao.annotation.OneToMany;
import dao.annotation.PK;
import dao.annotation.SQLType;
import dao.annotation.Transient;

/**
 * @author Demián Gutierrez
 */
public class ColumnDescriptor {

  protected Class<?>/* */type;

  protected String/*   */name;

  protected ManyToOne/**/manyToOne;
  protected OneToMany/**/oneToMany;
  protected PK/*       */pk;
  protected Transient/**/tranzient;
  protected SQLType/*  */sqlType;

  // --------------------------------------------------------------------------------

  public ColumnDescriptor(Class<?> type, String name) {
    this.name = name;
    this.type = type;
  }

  // --------------------------------------------------------------------------------

  public Class<?>/**/getType() {
    return type;
  }

  public String/*  */getName() {
    return name;
  }

  // --------------------------------------------------------------------------------

  public ManyToOne/**/getManyToOne() {
    return manyToOne;
  }

  public void/*     */setManyToOne(ManyToOne manyToOne) {
    this.manyToOne = manyToOne;
  }

  // --------------------------------------------------------------------------------

  public OneToMany/**/getOneToMany() {
    return oneToMany;
  }

  public void/*     */setOneToMany(OneToMany oneToMany) {
    this.oneToMany = oneToMany;
  }

  // --------------------------------------------------------------------------------

  public PK/*  */getPK() {
    return pk;
  }

  public void/**/setPK(PK pk) {
    this.pk = pk;
  }

  // --------------------------------------------------------------------------------

  public Transient/**/getTransient() {
    return tranzient;
  }

  public void/*     */setTransient(Transient tranzient) {
    this.tranzient = tranzient;
  }

  // --------------------------------------------------------------------------------

  public SQLType/**/getSQLType() {
    return sqlType;
  }

  public void/*   */setSQLType(SQLType sqlType) {
    this.sqlType = sqlType;
  }
}
