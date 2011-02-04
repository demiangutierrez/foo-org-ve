package sample.java.proxy;
public class Main {

  public static void main(String[] args) {

    IDemoClass bar = (IDemoClass) DynProxyDemo.newInstance(new DemoClass());
    bar.sayHi();

  }
}
