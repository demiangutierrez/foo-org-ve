package socket.echo.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

  public static void main(String[] args) throws IOException {

    DataOutputStream/**/dos = null;
    DataInputStream/* */dis = null;

    Socket socket = null;

    try {
      socket = new Socket("127.0.0.1", 8080);

      // -----------------------------------------------------------------------
      // DMI: Play with the flush if set to false it may block because an output
      // instruction may block waiting for the buffer to fill up to flush
      // -----------------------------------------------------------------------
      dos = new DataOutputStream/**/(socket.getOutputStream());
      dis = new DataInputStream/* */(socket.getInputStream());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    BufferedReader stdinr = new BufferedReader(new InputStreamReader(System.in));

    String fromSvr;
    String fromUsr;

    for (int i = 0; i < 100000; i++) {
      System.out.println("Client: " + i);
      dos.writeUTF(Integer.toString(i));

      fromSvr = dis.readUTF();
      System.out.println("Server: " + fromSvr);
    }

    while ((fromUsr = stdinr.readLine()) != null) {
      System.out.println("Client: " + fromUsr);
      dos.writeUTF(fromUsr);

      fromSvr = dis.readUTF();
      System.out.println("Server: " + fromSvr);

      if (fromUsr.equals("bye")) {
        break;
      }
    }

    dos.close();
    dis.close();

    stdinr.close();
    socket.close();
  }
}
