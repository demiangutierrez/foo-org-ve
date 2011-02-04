package socket.auction2;

import java.util.ArrayList;
import java.util.List;

import socket.auction2.ClientProtocol.EventType;
import socket.auction2.GlobalEvent.EventABEG;
import socket.auction2.GlobalEvent.EventTURN;
import socket.auction2.GlobalEvent.EventUREG;
import socket.auction2.GlobalEvent.StateType;

public class GlobalProtocol {

  private List<ClientThread> clientThreadList = new ArrayList<ClientThread>();

  private ClientThread clientThreadHead;
  private int/*      */clientThreadMBid;

  public ClientThread clientThreadCurr;
  private int/*     */clientThreadIndx;

  // --------------------------------------------------------------------------------

  private StateType state = StateType.INIT;

  // --------------------------------------------------------------------------------

  public GlobalProtocol() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private boolean checkFiringClientThread(GlobalEvent event, ClientThread firingClientThread) {
    switch (event.eventType) {
      case ABEG :
      case TURN :
      case UREG :
        return true; // Does not matter if the same in turn
      case PASS :
      case UBID :
      case TIME :
        if (firingClientThread != clientThreadCurr) {
          firingClientThread.write("WTF!!!");
          return false;
        }
        return true;
      default :
        throwDefaultState(state, event);
        break;
    }
    return false; // Should never get here
  }

  // --------------------------------------------------------------------------------

  // XXX: Blocks output??? Is blocked for client delay when sending them stuff???
  // XXX: If so, output should be sent to client thread (well, in fact we are on client thread
  // XXX: May be we can queue it and after exiting the state machine we can send them stuff?
  // XXX: Don't know if it aplies with the OTHER clients (not the running one) because it's
  // XXX: blocked waiting for input... SoTimeout may be???

  // DMI: States first, events then is in my opinion the best way to do it, it's much faster
  // than otherwise to implement it from the state diagram
  public synchronized void fireEvent(GlobalEvent event, ClientThread firingClientThread) {
    if (!checkFiringClientThread(event, firingClientThread)) {
      return;
    }

    switch (state) {
      case INIT :
        stateINIT(event);
        break;
      case NOBD :
        stateNOBD(event);
        break;
      case RUNI :
        stateRUNI(event);
        break;
      case RUNC :
        stateRUNC(event);
        break;
      case SALE :
        stateSALE(event);
        break;
      case NOSA :
        stateNOSA(event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateINIT(GlobalEvent event) {
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
        stateChange(event, state);
        String msg = "A NEW USER HAS BEEN REGISTERED!: " + ((EventUREG) event).clientThread.getPlayerId() + "WE NEED: "
            + (4 - clientThreadList.size()) + "TO GO!"; // Keep apart for debug
        sendForEach(msg);

        initClientThread((EventUREG) event);

        break;
      // ----------------------------------------
      // ----------------------------------------
      case ABEG :
        stateChange(event, StateType.NOBD);
        sendForEach("ABEG READY!");
        sendForCurr("ABEG START!");
        break;
      // ----------------------------------------
      // ----------------------------------------
      case PASS :
      case TURN :
      case UBID :
      case TIME :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateNOBD(GlobalEvent event) {
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case PASS :
      case TIME :
        stateChange(event, state);

        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.PASS);

        if (clientThreadHead != null) {
          sendForEach("PASS by " + clientThreadCurr.getPlayerId() + " Current bid is: " + clientThreadMBid + " by :"
              + clientThreadHead.getPlayerId());
        } else {
          sendForEach("PASS by " + clientThreadCurr.getPlayerId() + " Current bid is: " + clientThreadMBid
              + " by NONE!!! You cheap people!");
        }

        nextClientThread();

        sendForCurr("GO GO GO... YOUR TURN!");
        break;
      // ----------------------------------------
      // ----------------------------------------
      case TURN :
        stateChange(event, StateType.NOSA);

        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.DONE);

        sendForEach("AND THE F**KING TURN IS GONE TO HELL!!! AND NOOOOO WINNER, you cheap bastards!!!");

        break;
      // ----------------------------------------
      // ----------------------------------------
      case UBID :
        // TODO: Send to a method with all the UBIDs...
        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.UBID);

        for (ClientThread clientThread : clientThreadList) {
          if (clientThreadCurr != clientThread) {
            clientThread.getClientProtocol().fireEvent(EventType.OBID);
          }
        }

        clientThreadHead = clientThreadCurr;
        clientThreadMBid++;
        stateChange(event, StateType.RUNI);
        nextClientThread();

        sendForEach("UBID Current bid is: " + clientThreadMBid + " On the head is..." + clientThreadHead.getPlayerId());
        sendForCurr("GO GO GO... YOUR TURN!");

        break;
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
      case ABEG :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRUNI(GlobalEvent event) {
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case PASS :
      case TIME :
        stateChange(event, state);

        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.PASS);

        sendForEach("PASS by " + clientThreadCurr.getPlayerId() + " Current bid is: " + clientThreadMBid + " by :"
            + clientThreadHead.getPlayerId());

        nextClientThread();

        sendForCurr("GO GO GO... YOUR TURN!");
        break;
      // ----------------------------------------
      // ----------------------------------------
      case TURN :
        // Woot: Firing client state machine from here!!!
        // Ignore...
        // clientThreadCurr.getClientProtocol().fireEvent(EventType.UBID);

        stateChange(event, StateType.RUNC);

        sendForEach("AND THE F**KING TURN IS GONE TO HELL!!!");
        break;
      // ----------------------------------------
      // ----------------------------------------
      case UBID :
        // TODO: Send to a method with all the UBIDs...
        // DMI: Not sure of the if...
        // In theory is an illegal event for this particulary client...
        if (clientThreadCurr != clientThreadHead) {
          // Woot: Firing client state machine from here!!!
          clientThreadCurr.getClientProtocol().fireEvent(EventType.UBID);

          for (ClientThread clientThread : clientThreadList) {
            if (clientThreadCurr != clientThread) {
              clientThread.getClientProtocol().fireEvent(EventType.OBID);
            }
          }

          clientThreadHead = clientThreadCurr;
          clientThreadMBid++;

          stateChange(event, state);
        }

        nextClientThread();

        sendForEach("UBID Current bid is: " + clientThreadMBid + " On the head is..." + clientThreadHead.getPlayerId());
        sendForCurr("GO GO GO... YOUR TURN!");

        break;
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
      case ABEG :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRUNC(GlobalEvent event) {
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case PASS :
      case TIME :
        stateChange(event, state);

        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.PASS);

        sendForEach("PASS by " + clientThreadCurr.getPlayerId() + " Current bid is: " + clientThreadMBid + " by :"
            + clientThreadHead.getPlayerId());

        nextClientThread();

        sendForCurr("GO GO GO... YOUR TURN!");
        break;
      // ----------------------------------------
      // ----------------------------------------
      case TURN :
        stateChange(event, StateType.SALE);

        // Woot: Firing client state machine from here!!!
        clientThreadCurr.getClientProtocol().fireEvent(EventType.DONE);

        sendForEach("AND THE F**KING TURN IS GONE TO HELL!!! The WINNER is: " + clientThreadHead.getPlayerId()
            + " with a bid of " + clientThreadMBid);

        break;
      // ----------------------------------------
      // ----------------------------------------
      case UBID :
        // TODO: Send to a method with all the UBIDs...
        // DMI: Not sure of the if...
        // In theory is an illegal event for this particulary client...
        if (clientThreadCurr != clientThreadHead) {
          // Woot: Firing client state machine from here!!!
          clientThreadCurr.getClientProtocol().fireEvent(EventType.UBID);

          for (ClientThread clientThread : clientThreadList) {
            if (clientThreadCurr != clientThread) {
              clientThread.getClientProtocol().fireEvent(EventType.OBID);
            }
          }

          clientThreadHead = clientThreadCurr;
          clientThreadMBid++;

          stateChange(event, StateType.RUNI);
        }

        nextClientThread();

        sendForEach("UBID Current bid is: " + clientThreadMBid + " On the head is..." + clientThreadHead.getPlayerId());
        sendForCurr("GO GO GO... YOUR TURN!");

        break;
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
      case ABEG :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateSALE(GlobalEvent event) {
    // Final state
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
      case ABEG :
      case PASS :
      case TURN :
      case UBID :
      case TIME :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateNOSA(GlobalEvent event) {
    // Final state
    switch (event.eventType) {
      // ----------------------------------------
      // ----------------------------------------
      case UREG :
      case ABEG :
      case PASS :
      case TURN :
      case UBID :
      case TIME :
        throwIllegalState(state, event);
        break;
      // ----------------------------------------
      // ----------------------------------------
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void initClientThread(EventUREG event) {
    ClientThread clientThread = event.clientThread;

    if (clientThreadList.size() == 0) {
      clientThreadCurr = clientThread;
      clientThreadIndx = 0;
    }

    clientThreadList.add(clientThread);

    if (clientThreadList.size() >= 4) {
      fireEvent(new EventABEG(), null);
    }
  }

  // --------------------------------------------------------------------------------

  private void nextClientThread() {
    clientThreadIndx++;

    if (clientThreadIndx == clientThreadList.size()) {
      clientThreadIndx = 0;

      fireEvent(new EventTURN(), null);
    }

    clientThreadCurr = clientThreadList.get(clientThreadIndx);
  }

  // --------------------------------------------------------------------------------

  private void sendForEach(String string) {
    for (ClientThread clientThread : clientThreadList) {
      clientThread.write(string);
    }
  }

  private void sendForCurr(String string) {
    clientThreadCurr.write(string);
  }

  // --------------------------------------------------------------------------------
  // XXX: Duplicated, does it worth it to send them to a super class?
  // --------------------------------------------------------------------------------

  private void throwIllegalState(StateType state, GlobalEvent event) {
    throw new IllegalStateException( //
        /**/state + " for event " + event.eventType);
  }

  private void throwDefaultState(StateType state, GlobalEvent event) {
    throw new RuntimeException( //
        "default : this is for sure a bug in the state machine for state " + //
            state + " and event " + event.eventType);
  }

  // --------------------------------------------------------------------------------

  private void stateChange(GlobalEvent event, StateType state) {
    System.err.println( //
        "Curr state is: " + this.state + //
            "; event is: " + event.eventType + "; next state is: " + state);

    this.state = state;
  }

  // --------------------------------------------------------------------------------

  public StateType getState() {
    return state;
  }

  // --------------------------------------------------------------------------------

  public ClientThread getClientThreadHead() {
    return clientThreadHead;
  }

  public int getClientThreadMBid() {
    return clientThreadMBid;
  }

  public ClientThread getClientThreadFromId(int playerId) {
    synchronized (clientThreadList) {
      for (ClientThread clientThread : clientThreadList) {

        if (playerId == clientThread.getPlayerId()) { // XXX: StringUtils
          return clientThread;
        }
      }

      return null;
    }
  }

}
