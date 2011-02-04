package holamundo.java;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Principal2 {

  public static void main(String[] args) throws Exception {

    BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

    Persona persona = new Persona();

    List<String> errorMsgList = null;

    String line;

    do {
      System.out.print("Por favor entre el cédula: ");
      line = rd.readLine();
      persona.setCedula(line);

      System.out.print("Por favor entre el nombre: ");
      line = rd.readLine();
      persona.setNombre(line);

      errorMsgList = persona.validar();

      if (!errorMsgList.isEmpty()) {
        System.out.println("ERRORES -------------------------------->");

        for (String errorMsg : errorMsgList) {
          System.out.println(errorMsg);
        }

        System.out.println("---------------------------------------->");
      }
    } while (!errorMsgList.isEmpty());

    System.out.println("---------------------------------------->");
    persona.imprimir();
  }
}
