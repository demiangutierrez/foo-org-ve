package com.minotauro.state.demo.protocol;

import java.util.HashMap;
import java.util.Map;

import com.minotauro.state.demo.protocol.Global.EventType;

//import com.minotauro.state.protocol.GlobalEvent.EventPASS;
//import com.minotauro.state.protocol.GlobalEvent.EventTIME;
//import com.minotauro.state.protocol.GlobalEvent.EventUBID;
//import com.minotauro.state.protocol.GlobalEvent.EventUREG;

// No thread in fact...
public class GlobalThread /*extends Thread*/{

  // --------------------------------------------------------------------------------

  private GlobalImpl protocol = new GlobalImpl();

  // --------------------------------------------------------------------------------
  // Format is: |ID CMDX DATA...\n| not counting the "|"
  // --------------------------------------------------------------------------------
  // TODO: Object oriented message parser 
  // --------------------------------------------------------------------------------

  private String getMsgCmd(String msg) {
    return msg.substring(0); // NO SUB... for now :)
  }

  // --------------------------------------------------------------------------------

  public void addMsg(ClientThread clientThread, String msg) {
    String cmd = getMsgCmd(msg);

    Map<String, Object> par = new HashMap<String, Object>();
    par.put(GlobalImpl.PAR_CLIENT_THREAD, clientThread);

    /*OOO*/if (cmd.equals("CREG")) {
      protocol.fireEvent(EventType.creg, null, par);
      clientThread.write("INFO: OK");

      // XXX: Hack
      if (protocol.getClientThreadList().size() == GlobalImpl.MIN_CLIENTS) {
        protocol.getClientThreadCurr().setSoTimeout(5000, "TIME");
      }
    } else if (cmd.equals("CBID")) {
      protocol.getClientThreadCurr().setSoTimeout(0, null);
      protocol.fireEvent(EventType.cbid, null, par);
      protocol.getClientThreadCurr().setSoTimeout(5000, "TIME");

      clientThread.write("INFO: OK");
    } else if (cmd.equals("PASS")) {
      protocol.getClientThreadCurr().setSoTimeout(0, null);
      protocol.fireEvent(EventType.pass, null, par);
      protocol.getClientThreadCurr().setSoTimeout(5000, "TIME");

      clientThread.write("INFO: OK");
    } else if (cmd.equals("TIME")) {
      protocol.getClientThreadCurr().setSoTimeout(0, null);
      protocol.fireEvent(EventType.time, null, par);

      //clientThread.write("INFO: OK");
    } else {
      clientThread.write("ERROR: COMMAND UNKNOWN" + cmd);
    }
  }
}
