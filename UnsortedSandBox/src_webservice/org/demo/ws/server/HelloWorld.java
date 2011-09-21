package org.demo.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HelloWorld {

  @WebMethod
  public String sayHelloWorld(String name) {
    return "Hello World, " + name;
  }

  @WebMethod(exclude = true)
  public String ignoreThis(String name) {
    return "Hello World, " + name;
  }

}
