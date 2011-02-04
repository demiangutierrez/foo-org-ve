package socket.echo.plain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

  public static void main(String[] args) throws IOException {

    PrintWriter/**/pw = null;
    BufferedReader br = null;

    Socket socket = null;

    try {
      socket = new Socket("127.0.0.1", 8080);

      // -----------------------------------------------------------------------
      // DMI: Play with the flush if set to false it may block because an output
      // instruction may block waiting for the buffer to fill up to flush
      // -----------------------------------------------------------------------
      pw = new PrintWriter/*   */(socket.getOutputStream(), true);
      br = new BufferedReader/**/(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    BufferedReader stdinr = new BufferedReader(new InputStreamReader(System.in));

    String fromSvr;
    String fromUsr;

    for (int i = 0; i < 1000; i++) {
      System.out.println("Client: " + i);
      pw.println(Integer.toString(i));

      fromSvr = br.readLine();
      System.out.println("Server: " + fromSvr);
    }

    while ((fromUsr = stdinr.readLine()) != null) {
      System.out.println("Client: " + fromUsr);
      pw.println(fromUsr);

      fromSvr = br.readLine();
      System.out.println("Server: " + fromSvr);

      if (fromUsr.equals("bye")) {
        break;
      }
    }

    pw.close();
    br.close();

    stdinr.close();
    socket.close();
  }
}
