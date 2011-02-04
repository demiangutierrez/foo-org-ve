package socket.echo.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {

  public static void main(String[] args) throws IOException {

    // --------------------------------------------------------------------------------
    // Taken from: http://exampledepot.com/egs/javax.net.ssl/client.html
    //    try {
    //      int port = 443;
    //      String hostname = "hostname";
    //      SocketFactory socketFactory = SSLSocketFactory.getDefault();
    //      Socket socket = socketFactory.createSocket(hostname, port);
    //      // Create streams to securely send and receive data to the server
    //      InputStream in = socket.getInputStream();
    //      OutputStream out = socket.getOutputStream();
    //      // Read from in and write to out...
    //      // Close the socket
    //      in.close();
    //      out.close();
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    // --------------------------------------------------------------------------------

    PrintWriter/**/pw = null;
    BufferedReader br = null;

    SSLSocket socket = null;

    try {
      // socket = new Socket("127.0.0.1", 8080);
      SocketFactory socketFactory = SSLSocketFactory.getDefault();
      socket = (SSLSocket) socketFactory.createSocket("127.0.0.1", 8080);

      // This was not in the original tutorial
      final String[] enabledCipherSuites = {"SSL_DH_anon_WITH_RC4_128_MD5"};
      socket.setEnabledCipherSuites(enabledCipherSuites);

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
