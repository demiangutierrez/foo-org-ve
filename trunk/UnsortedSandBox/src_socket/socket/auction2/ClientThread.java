package socket.auction2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientThread extends Thread {

  private GlobalThread globalThread;
  private Socket/*   */clientSocket;

  private PrintWriter/**/pw;
  private BufferedReader br;

  private int playerId;

  private boolean running = true;

  private ClientProtocol clientProtocol = new ClientProtocol();
  private String soTimeoutEventType; // TODO: Don't like this as string...

  // --------------------------------------------------------------------------------

  public ClientThread(GlobalThread globalThread, Socket clientSocket, int playerId) throws IOException {
    this.globalThread = globalThread;
    this.clientSocket = clientSocket;

    this.playerId = playerId;

    pw = new PrintWriter/*   */(clientSocket.getOutputStream(), true);
    br = new BufferedReader/**/(new InputStreamReader(clientSocket.getInputStream()));

    doBeg();

    pw.println("OK " + playerId);

    globalThread.addClientThread(this);
  }

  // --------------------------------------------------------------------------------

  public void setSoTimeout(int timeout, String soTimeoutEventType) throws SocketException {
    this.soTimeoutEventType = soTimeoutEventType;
    clientSocket.setSoTimeout(timeout);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    try {
      failsafeRun();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // -------------------------------------------------
    // TODO: Improve exception handling and finalization
    // -------------------------------------------------
  }

  // --------------------------------------------------------------------------------

  private void failsafeRun() throws Exception {
    while (running) {
      String fromUsr;
      try {
        fromUsr = br.readLine(); // XXX

        while (fromUsr != null) {
          System.out.println("Client: " + fromUsr);

          try {
            globalThread.addMsg(this, fromUsr);
          } catch (IllegalStateException e) {
            // TODO: DMI: NOW HERE IS A POSSIBLE DOS ATTACK!!!, if the guy keeps
            // sending illegal events we can restart here for ever...
            // WOOT::: Hacking!!!
            e.printStackTrace();
            write("WTF!!! STOP MESSING WITH THE SERVER!!!");
          }

          // Probably wont be needed... (see write below)
          //          System.err.println("");
          //          pw.println(ret);

          fromUsr = br.readLine();
        }
      } catch (SocketTimeoutException e) {

        // DMI: Don't like the way the catch is being handled...
        String msg = soTimeoutEventType;
        setSoTimeout(0, null);

        globalThread.addMsg(this, msg);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void doBeg() {
    running = true;
    start();
  }

  public void doEnd() {
    running = false;
  }

  public int getPlayerId() {
    return playerId;
  }

  public ClientProtocol getClientProtocol() {
    return clientProtocol;
  }

  // --------------------------------------------------------------------------------

  // DMI: Interesting of using this method is that the fact of writing to socket
  // is abstracted with this, in other words, I can either write directly to the socket
  // here or can just add the message to a queue to be written later by the client thread
  // in the main cycle...
  public void write(String string) {
    System.err.println("Server to Client: " + string);
    pw.println(string);
  }
}
