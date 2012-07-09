package com.somename.number1;

import org.cyrano.someservice.ISomeService;

public class SomeServiceNumber1Impl implements ISomeService {

  @Override
  public String sayHelloTo(String someone) {
    return "This is number 1 saying hello to " + someone;
  }
}
