package test.tracer;

public class TraceLogger {

  public static void begTrace(String clazz, int line) {
    System.err.println("beg: " + clazz + " / " + line);
  }

  public static void endTrace(String clazz, int line) {
    System.err.println("end: " + clazz + " / " + line);
  }
}
