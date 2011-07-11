import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloWorldServer extends UnicastRemoteObject implements HelloWorld {
  public HelloWorldServer() throws RemoteException {
    super();
  }

  public String helloWorld() {
    System.out.println("HelloWorld!");

    return "HelloWorld from server!";
  }

  public static void main(String args[]) {
    try {
      HelloWorldServer obj = new HelloWorldServer();

      Naming.rebind("HelloWorld", obj);

      System.out.println("HelloWorld bound in registry");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}