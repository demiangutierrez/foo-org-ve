package com.somename.number2;

import org.cyrano.someservice.ISomeService;

public class SomeServiceNumber2Impl implements ISomeService {

  @Override
  public String sayHelloTo(String someone) {
    return "This is number 2 saying hello to " + someone;
  }
}
