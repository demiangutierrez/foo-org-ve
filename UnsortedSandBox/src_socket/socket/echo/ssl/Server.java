package socket.echo.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server {

  public static void main(String[] args) throws IOException {

    // --------------------------------------------------------------------------------
    // Taken from: http://www.exampledepot.com/egs/javax.net.ssl/server.html
    //    try {
    //      int port = 443;
    //      ServerSocketFactory ssocketFactory = SSLServerSocketFactory.getDefault();
    //      ServerSocket ssocket = ssocketFactory.createServerSocket(port);
    //      // Listen for connections
    //      Socket socket = ssocket.accept();
    //      // Create streams to securely send and receive data to the client
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

    // --------------------------------------------------------------------------------
    // Test key store created this way:
    //    [dmi@cyrano ~]$ keytool -keystore mySrvKeystore -keypasswd -genkey -keyalg RSA -alias mycert 
    //    Enter keystore password:  
    //    Re-enter new password: 
    //    What is your first and last name?
    //      [Unknown]:  Demian Gutierrez
    //    What is the name of your organizational unit?
    //      [Unknown]:  Cyrano
    //    What is the name of your organization?
    //      [Unknown]:  Cyrano
    //    What is the name of your City or Locality?
    //      [Unknown]:  Merida
    //    What is the name of your State or Province?
    //      [Unknown]:  Merida
    //    What is the two-letter country code for this unit?
    //      [Unknown]:  VE
    //    Is CN=Demian Gutierrez, OU=Cyrano, O=Cyrano, L=Merida, ST=Merida, C=VE correct?
    //      [no]:  yes
    //
    //    Enter key password for <mycert>
    //            (RETURN if same as keystore password):  
    //    [dmi@cyrano ~]$ 
    // The generated file (mySrvKeystore) has to be in the working directory
    // --------------------------------------------------------------------------------

    // --------------------------------------------------------------------------------
    // Then, the server is runned with:
    // -Djavax.net.ssl.keyStore=mySrvKeystore
    // -Djavax.net.ssl.keyStorePassword=123456
    // --------------------------------------------------------------------------------

    SSLServerSocket serverSocket = null;
    Socket/**/clientSocket = null;

    try {
      // serverSocket = new ServerSocket(8080); // Non SSL
      ServerSocketFactory socketFactory = SSLServerSocketFactory.getDefault();
      serverSocket = (SSLServerSocket) socketFactory.createServerSocket(8080);

      // This was not in the original tutorial
      final String[] enabledCipherSuites = {"SSL_DH_anon_WITH_RC4_128_MD5"};
      serverSocket.setEnabledCipherSuites(enabledCipherSuites);

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

    while ((fromUsr = br.readLine()) != null) {
      System.out.println("Client: " + fromUsr);
      pw.println(fromUsr.toUpperCase());

      if (fromUsr.equals("bye")) {
        break;
      }
    }

    pw.close();
    br.close();

    clientSocket.close();
    serverSocket.close();
  }
}
