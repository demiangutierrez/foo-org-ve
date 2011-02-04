package test.asmifier;

public class Test2 {

  public void foo() {
    System.err.println("foo");
    int a = 10;
    if (a < 10) {
      System.err.println("XXX");
    } else {
      System.err.println("YYY");
    }
  }
}
