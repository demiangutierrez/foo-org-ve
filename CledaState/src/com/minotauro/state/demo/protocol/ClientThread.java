package com.minotauro.state.demo.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ClientThread extends Thread {

  public static final int TURN_TIMEOUT = 10000; // XXX: Send to common constants interface

  // --------------------------------------------------------------------------------

  private Logger log = Logger.getLogger(getClass().getName());

  private GlobalImpl global;
  private ClientImpl client;

  private PrintWriter/**/pw;
  private BufferedReader br;

  private int playerId;

  private boolean running = true;

  // --------------------------------------------------------------------------------

  private long timeout = Long.MAX_VALUE;
  private long timecur = 0;
  private long timebas = 0;

  private String timeoutEventType; // TODO: Don't like this as string...

  // --------------------------------------------------------------------------------

  public ClientThread(/*GlobalThread globalThread*/GlobalImpl global, Socket clientSocket, int playerId) //
      throws IOException {

    client = new ClientImpl(this, global);

    this.global = global;

    clientSocket.setSoTimeout(500);

    this.playerId = playerId;

    pw = new PrintWriter/*   */(clientSocket.getOutputStream(), true);
    br = new BufferedReader/**/(new InputStreamReader(clientSocket.getInputStream()));

    doBeg();

    pw.println("OK " + playerId);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    try {
      failsafeRun();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    // -------------------------------------------------
    // TODO: Improve exception handling and finalization
    // -------------------------------------------------
  }

  // --------------------------------------------------------------------------------

  private void failsafeRun() throws Exception {
    while (running) {
      String fromUsr;

      timebas = System.currentTimeMillis();
      try {

        //        log.debug("PRE BLOCK " + getPlayerId());
        while ((fromUsr = br.readLine()) != null) {
          //          log.debug("POS BLOCK " + getPlayerId());

          log.debug("Client to Server " + getPlayerId() + " : " + fromUsr);

          try {
            handleMessage(fromUsr);
            //            globalThread.addMsg(this, fromUsr);
          } catch (IllegalStateException e) {
            // TODO: DMI: NOW HERE IS A POSSIBLE DOS ATTACK!!!, if the guy keeps
            // sending illegal events we can restart here for ever...
            // WOOT::: Hacking!!!
            e.printStackTrace();
            write("MMM!!! STOP MESSING WITH THE SERVER!!!");
          }

          timebas = System.currentTimeMillis();
        }
      } catch (SocketTimeoutException e) {
        checkTimeout();
      }
    }
  }

  // --------------------------------------------------------------------------------

  private String getMsgCmd(String msg) {
    return msg.substring(0); // NO SUB... for now :)
  }

  // --------------------------------------------------------------------------------

  private void handleMessage(String msg) {
    String cmd = getMsgCmd(msg);

    Map<String, Object> par = new HashMap<String, Object>();
    par.put(GlobalImpl.PAR_CLIENT_THREAD, this);

    try {
      /*OOO*/if (cmd.equals("CREG")) {
        global.fireEvent(com.minotauro.state.demo.protocol.Global.EventType.creg, //
            null, par);
        write("INFO: OK");
      } else if (cmd.equals("CBID")) {
        client.fireEvent(com.minotauro.state.demo.protocol.Client.EventType.cbid, //
            null, par);
        write("INFO: OK");
      } else if (cmd.equals("PASS")) {
        client.fireEvent(com.minotauro.state.demo.protocol.Client.EventType.pass, //
            null, par);
        write("INFO: OK");
      } else if (cmd.equals("TIME")) {
        client.fireEvent(com.minotauro.state.demo.protocol.Client.EventType.time, //
            null, par);
        write("INFO: TIMEOUT");
      } else {
        write("ERROR: COMMAND UNKNOWN: " + cmd);
      }
    } catch (Exception e) {
      // Be sure that illegal states events will not stop the thread
      log.debug(e.getMessage());
    }
  }

  // --------------------------------------------------------------------------------

  public synchronized void setSoTimeout(int timeout, String timeoutEventType) {
    log.debug("Set timeout " + timeout + " and event " + //
        timeoutEventType + " for client " + getPlayerId());

    this.timeoutEventType = timeoutEventType;

    if (timeout == 0) {
      this.timeout = Long.MAX_VALUE;
    } else {
      this.timeout = timeout;
    }

    timecur = 0;
    //    timebas = 0;
  }

  // --------------------------------------------------------------------------------

  private synchronized void checkTimeout() {
    if (timeout == Long.MAX_VALUE) {
      log.debug("No timeout set for client: " + getPlayerId());
      return;
    }

    timecur += (System.currentTimeMillis() - timebas);

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);

    log.debug("check timeout " + //
        nf.format(timecur / 1000.0) + " / " + nf.format(timeout / 1000.0) + //
        " for client: " + getPlayerId());

    if (timecur > timeout) {
      log.debug("timeout for client: " + getPlayerId());
      handleMessage(timeoutEventType);
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

  // --------------------------------------------------------------------------------

  public int getPlayerId() {
    return playerId;
  }

  // --------------------------------------------------------------------------------

  // DMI: Interesting of using this method is that the fact of writing to socket
  // is abstracted with this, in other words, I can either write directly to the socket
  // here or can just add the message to a queue to be written later by the client thread
  // in the main cycle...
  public void write(String string) {
    log.debug("Server to Client " + getPlayerId() + " : " + string);
    pw.println(string);
  }

  public ClientImpl getClient() {
    return client;
  }
}
