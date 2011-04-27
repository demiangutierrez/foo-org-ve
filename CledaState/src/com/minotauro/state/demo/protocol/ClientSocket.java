package com.minotauro.state.demo.protocol;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

public class ClientSocket implements Runnable {

  private ActionListenerProxy actionListenerProxy = new ActionListenerProxy(new EventListenerList());

  private PrintWriter/**/pw = null;
  private BufferedReader br = null;

  private List<String> outMsgQueue = new ArrayList<String>();

  public ClientSocket() {
    try {
      Socket socket = null;
      socket = new Socket("127.0.0.1", 8080);

      socket.setSoTimeout(100);

      pw = new PrintWriter/*   */(socket.getOutputStream(), true);
      br = new BufferedReader/**/(new InputStreamReader(socket.getInputStream()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        try {
          String fromSvr = br.readLine();
          System.out.println("Server: " + fromSvr);
          actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, fromSvr));
        } catch (SocketTimeoutException e) {
          synchronized (this) {
            while (!outMsgQueue.isEmpty()) {
              String msg = outMsgQueue.remove(0);
              System.out.println("Client: " + msg);
              pw.println(msg);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // --------------------------------------------------------------------------------

  public synchronized void sendMessage(String message) {
    outMsgQueue.add(message);
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  public void setActionListenerProxy(ActionListenerProxy actionListenerProxy) {
    this.actionListenerProxy = actionListenerProxy;
  }
}
