import java.io.FileNotFoundException;


public class HelloScheme {

  public static void main(String[] args) throws FileNotFoundException {
//    JS.load(new java.io.FileReader("app.init"));
    String query = "(+ (expt (Math.sin 2.0) 2) (expt (Math.cos 2.0) 2))";
    System.out.println(query + " = " + JS.eval(query));
  }
}
