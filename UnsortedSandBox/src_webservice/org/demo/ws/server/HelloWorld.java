package org.demo.ws.server;

import javax.jws.WebService;

@WebService
public class HelloWorld {

  public String sayHelloWorld(String name) {
    return "Hello World, " + name;
  }
}
