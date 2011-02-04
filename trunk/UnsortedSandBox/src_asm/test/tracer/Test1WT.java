package test.tracer;

public class Test1WT {

  public static void main(String[] args) {
    int a = 0;

    if (a == 0) {
      TraceLogger.begTrace("test/tracer/Test1WT", 2);
      System.err.println("txxx");
      TraceLogger.begTrace("test/tracer/Test1WT", 3);
    } else {
      TraceLogger.begTrace("test/tracer/Test1WT", 5);
      System.err.println("fyyy");
      TraceLogger.begTrace("test/tracer/Test1WT", 6);
    }
  }
}
