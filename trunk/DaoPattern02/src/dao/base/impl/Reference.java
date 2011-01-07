package dao.base.impl;

import dao.base.api.IDTO;

/**
 * @author Demián Gutierrez
 */
public class Reference<Ref extends IDTO> {

  protected int refIdent;

  protected Ref refValue;

  // --------------------------------------------------------------------------------

  public Reference() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public int getRefIdent() {
    return refIdent;
  }

  public void setRefIdent(int refIdent) {
    this.refIdent = refIdent;
  }

  // --------------------------------------------------------------------------------

  public Ref getRefValue() {
    return refValue;
  }

  public void setRefValue(Ref refValue) {
    this.refValue = refValue;
  }

  // --------------------------------------------------------------------------------

  public String getIdAsString() {
    if (refValue != null) {

      // ----------------------------------------
      // If we have a refValue, use it directly
      // ----------------------------------------

      refIdent = refValue.getId();

      return Integer.toString(refValue.getId());
    } else {

      // ----------------------------------------
      // If refIdent != 0 use it, refValue is
      // just not loaded...
      //
      // If not then it's for sure a NULL ref
      // ----------------------------------------

      if (refIdent != 0) {
        return Integer.toString(refIdent);
      } else {
        return "NULL";
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void checkInsert() {

    // ----------------------------------------------------------
    // Can't save this (no cascade support yet or defined so far)
    // ----------------------------------------------------------

    if (getIdAsString().equals("0")) {
      throw new IllegalArgumentException( //
          "getIdAsString().equals(\"0\")");
    }
  }

  // --------------------------------------------------------------------------------

  public void checkUpdate() {

    // ----------------------------------------------------------
    // Can't save this (no cascade support yet or defined so far)
    // ----------------------------------------------------------

    if (getIdAsString().equals("0")) {
      throw new IllegalArgumentException( //
          "getIdAsString().equals(\"0\")");
    }
  }
}
