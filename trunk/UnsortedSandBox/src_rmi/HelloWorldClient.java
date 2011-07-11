import java.rmi.Naming;

public class HelloWorldClient {

  public static void main(String args[]) {
    try {
      HelloWorld obj = (HelloWorld) Naming.lookup("//localhost/HelloWorld");
      String message = obj.helloWorld();

      System.out.println(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
