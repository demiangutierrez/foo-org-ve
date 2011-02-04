package socket.auction2;

import java.net.SocketException;

import socket.auction2.GlobalEvent.EventPASS;
import socket.auction2.GlobalEvent.EventTIME;
import socket.auction2.GlobalEvent.EventUBID;
import socket.auction2.GlobalEvent.EventUREG;

// No thread in fact...
public class GlobalThread /*extends Thread*/{

  // --------------------------------------------------------------------------------

  private GlobalProtocol globalProtocol = new GlobalProtocol();

  // --------------------------------------------------------------------------------
  // Format is: |ID CMDX DATA...\n| not counting the "|"
  // --------------------------------------------------------------------------------
  // TODO: Object oriented message parser 
  // --------------------------------------------------------------------------------

  private String getMsgCmd/* */(String msg) {
    return msg.substring(0); // NO SUB... for now :)
  }

  // --------------------------------------------------------------------------------

  public void addMsg(ClientThread clientThread, String msg) throws SocketException {
    String cmd/* */= getMsgCmd/* */(msg);

    /*   */if (cmd.equals("UBID")) {
      globalProtocol.fireEvent(new EventUBID(), clientThread);
      // TODO!!! It
      //globalProtocol.clientThreadCurr.setSoTimeout(10000, "TIME");
      clientThread.setSoTimeout(10000, "TIME");
    } else if (cmd.equals("PASS")) {
      globalProtocol.fireEvent(new EventPASS(), clientThread);
      // TODO!!!
      //globalProtocol.clientThreadCurr.setSoTimeout(10000, "TIME");
      clientThread.setSoTimeout(10000, "TIME");
    } else if (cmd.equals("TIME")) {
      globalProtocol.fireEvent(new EventTIME(), clientThread);
    } else {
      clientThread.write("ERROR " + cmd);
    }

    clientThread.write("PROTO OK");
  }

  // --------------------------------------------------------------------------------

  public void addClientThread(ClientThread clientThread) {
    globalProtocol.fireEvent(new EventUREG(clientThread), clientThread);
  }
}
