package sample.java.proxy;
public class DemoClass implements IDemoClass {

  public String sayHi() {
    System.err.println("Hi...");

    return "hi";
  }
}
