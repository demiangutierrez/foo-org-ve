package dao.base.impl;

import java.util.ArrayList;

/**
 * @author Demián Gutierrez
 */
public class LazyList<E> extends ArrayList<E> {

  
  
  
  private boolean loaded;

  // --------------------------------------------------------------------------------

  public LazyList() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public boolean getLoaded() {
    return loaded;
  }

  public void/**/setLoaded(boolean loaded) {
    this.loaded = loaded;
  }
}
