package socket.echo.readtimeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    Socket/*   */clientSocket = null;

    try {
      serverSocket = new ServerSocket(8080);
      clientSocket = serverSocket.accept();

      // -----------------------------------------------------------------------
      // DMI: Any blocking operation (like a readline) will wait until timeout
      // is consumed, then will throw a java.net.SocketTimeoutException
      // -----------------------------------------------------------------------
      clientSocket.setSoTimeout(10000);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // -----------------------------------------------------------------------
    // DMI: Play with the flush if set to false it may block because an output
    // instruction may block waiting for the buffer to fill up to flush
    // -----------------------------------------------------------------------
    PrintWriter/**/pw = new PrintWriter/*   */(clientSocket.getOutputStream(), true);
    BufferedReader br = new BufferedReader/**/(new InputStreamReader(clientSocket.getInputStream()));

    String fromUsr;

    System.out.println("En espera de mensaje");
    
    try {
      while ((fromUsr = br.readLine()) != null) {
        System.out.println("Client: " + fromUsr);
        pw.println(fromUsr.toUpperCase());

        if (fromUsr.equals("bye")) {
          break;
        }
      }
    } catch (SocketTimeoutException e) {
      System.err.println("Se te fueron los tiempos...");
    }
    

    pw.close();
    br.close();

    clientSocket.close();
    serverSocket.close();
  }
}
