package org.cyrano.quadtree3;

public abstract class Visitor {

  public void visit(QuadNode qn) {
    boolean visit = internalVisit(qn);

    if (visit && qn.hasChildren()) {
      visit(qn.getNw());
      visit(qn.getNe());
      visit(qn.getSw());
      visit(qn.getSe());
    }
  }

  public abstract boolean internalVisit(QuadNode qn);
}
