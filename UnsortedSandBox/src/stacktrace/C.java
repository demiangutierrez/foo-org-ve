package stacktrace;

public class C extends B {

  public void foo() {
    System.err.println(Util.getDeclaringClazz());
  }
}
