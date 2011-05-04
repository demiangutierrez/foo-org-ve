package com.tutorial.listener;

import java.util.EventObject;

public class CustomABCEvent extends EventObject {

  private String someData;

  public CustomABCEvent(Object source, String someData) {
    super(source);

    this.someData = someData;
  }

  public String getSomeData() {
    return someData;
  }
}
