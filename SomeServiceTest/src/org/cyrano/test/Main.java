package org.cyrano.test;

import java.util.Iterator;

import org.cyrano.someservice.ISomeService;

public class Main {

  public static void main(String[] args) {

    Iterator<ISomeService> itt = ISomeService.someServiceLoader.iterator();

    while (itt.hasNext()) {
      ISomeService someService = itt.next();

      System.err.println(someService.getClass());
      System.err.println(someService.sayHelloTo("me!"));
    }
  }
}
