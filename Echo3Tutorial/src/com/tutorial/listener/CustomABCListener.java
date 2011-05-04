package com.tutorial.listener;

import java.util.EventListener;

public interface CustomABCListener extends EventListener {

  public enum Method {
    AAA, BBB, CCC
  }

  // --------------------------------------------------------------------------------

  public void actionAAAPerformed(CustomABCEvent evt);

  public void actionBBBPerformed(CustomABCEvent evt);

  public void actionCCCPerformed(CustomABCEvent evt);
}
