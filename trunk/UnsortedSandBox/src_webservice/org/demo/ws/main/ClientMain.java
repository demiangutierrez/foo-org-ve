package org.demo.ws.main;

import org.demo.ws.client.HelloWorld;
import org.demo.ws.client.HelloWorldService;

public class ClientMain {
  public static void main(String[] args) {
    HelloWorldService service = new HelloWorldService();

    HelloWorld helloWorld = service.getHelloWorldPort();

    System.out.println("--------------------->  Call Beg");
    System.out.println(helloWorld.sayHelloWorld("DEMIAN"));
    System.out.println("--------------------->  Call End");
  }
}
