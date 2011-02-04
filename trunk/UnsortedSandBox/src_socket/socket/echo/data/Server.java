package socket.echo.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    Socket/*   */clientSocket = null;

    try {
      serverSocket = new ServerSocket(8080);
      clientSocket = serverSocket.accept();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // -----------------------------------------------------------------------
    // DMI: Play with the flush if set to false it may block because an output
    // instruction may block waiting for the buffer to fill up to flush
    // -----------------------------------------------------------------------
    DataOutputStream/**/dos = new DataOutputStream/**/(clientSocket.getOutputStream());
    DataInputStream/* */dis = new DataInputStream/* */(clientSocket.getInputStream());

    String fromUsr;

    while ((fromUsr = dis.readUTF()) != null) {
      System.out.println("Client: " + fromUsr);
      dos.writeUTF(fromUsr.toUpperCase());

      if (fromUsr.equals("bye")) {
        break;
      }
    }

    dos.close();
    dis.close();

    clientSocket.close();
    serverSocket.close();
  }
}
