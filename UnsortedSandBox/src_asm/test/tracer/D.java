package test.tracer;

public class D {

  public static long timer;

  public void m() throws Exception {
    timer -= System.currentTimeMillis();
    Thread.sleep(100);
    timer += System.currentTimeMillis();
  }
}
