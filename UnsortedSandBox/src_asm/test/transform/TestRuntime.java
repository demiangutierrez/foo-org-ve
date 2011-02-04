package test.transform;

public class TestRuntime {

  public static void main(String[] args) {
    SimpleTest st = new SimpleTest();

    if (st instanceof SomeDummyInterface) {
      System.err.println("Yes!!!");
    } else {
      System.err.println("No  :(");
    }

    st.a = 10;
    st.b = 10;
  }
}
