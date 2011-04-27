package com.minotauro.state.demo.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalImpl extends Global {

  public static final int MIN_CLIENTS = 2;

  public static final String PAR_CLIENT_THREAD = Integer.toString(0);

  // --------------------------------------------------------------------------------

  private List<ClientThread> clientThreadList = new ArrayList<ClientThread>();

  private ClientThread clientThreadHead;
  private int/*      */clientThreadMBid;

  private ClientThread clientThreadCurr;
  private int/*      */clientThreadIndx;

  // --------------------------------------------------------------------------------

  @Override
  protected boolean checkPreFireEvent(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
      case abeg :
      case turn :
      case next :
        return true; // Does not matter if the same in turn
      case pass :
      case time :
      case cbid : {
        ClientThread clientThread = (ClientThread) par.get(PAR_CLIENT_THREAD);
        if (clientThread != clientThreadCurr) {
          clientThread.write("INFO: WTF??? WAIT FOR YOUR TURN!!!");
          return false;
        }

        return true;
      }
      default :
        throwDefaultState(state, event);
        break;
    }

    return false; // Should never get here
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void INIT_creg_INIT(Map<String, Object> ctx, Map<String, Object> par) {
    ClientThread clientThread = (ClientThread) par.get(PAR_CLIENT_THREAD);
    initClientThread(clientThread);

    StringBuffer strbuf;

    strbuf = new StringBuffer();

    strbuf.append("CREG ");
    strbuf.append(clientThread.getPlayerId());

    clientThread.write(strbuf.toString());

    strbuf = new StringBuffer();

    strbuf.append("INFO: CLIENT REGISTERED ");
    strbuf.append(clientThread.getPlayerId());
    strbuf.append(" WE NEED ");
    strbuf.append(MIN_CLIENTS - clientThreadList.size());
    strbuf.append(" TO START");

    sendForEach(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void INIT_abeg_RUN0(Map<String, Object> ctx, Map<String, Object> par) {
    sendForEach("INFO: READY");
    sendForCurr("INFO: START");
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN0_pass_CHK0(Map<String, Object> ctx, Map<String, Object> par) {
    handlePASSEvent(false);
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void CHK0_turn_NOSA(Map<String, Object> ctx, Map<String, Object> par) {
    sendForEach("INFO: END OF THE TURN");
    sendForEach("INFO: END OF THE AUCTION, THERE IS NO WINNER");

    for (ClientThread clientThread : clientThreadList) {
      clientThread.getClient().fireEvent(com.minotauro.state.demo.protocol.Client.EventType.done, //
          null, null);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN0_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    handleCBIDEvent();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN1_pass_CHK1(Map<String, Object> ctx, Map<String, Object> par) {
    handlePASSEvent(false);
  }

  // --------------------------------------------------------------------------------

  //  @Override
  //  protected void CHK1_turn_RUN2(Map<String, Object> ctx, Map<String, Object> par) {
  //    // Empty
  //  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN1_turn_RUN2(Map<String, Object> ctx, Map<String, Object> par) {
    sendForEach("INFO: END OF THE TURN"); // XXX: WTF???
    //    clientThreadCurr.getClient().fireEvent(com.minotauro.state.protocol.Client.EventType.play, //
    //        null, null);
    //    sendForCurr("INFO: YOUR TURN");
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN1_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    handleCBIDEvent();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN2_pass_CHK2(Map<String, Object> ctx, Map<String, Object> par) {
    handlePASSEvent(false);
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void CHK2_turn_SALE(Map<String, Object> ctx, Map<String, Object> par) {
    sendForEach("INFO: END OF THE TURN");

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("INFO: END OF THE AUCTION, WINER IS ");
    strbuf.append(clientThreadHead.getPlayerId());
    strbuf.append(" WITH A BID OF ");
    strbuf.append(clientThreadMBid);

    sendForEach(strbuf.toString());

    for (ClientThread clientThread : clientThreadList) {
      clientThread.getClient().fireEvent(com.minotauro.state.demo.protocol.Client.EventType.done, //
          null, null);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void RUN2_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    handleCBIDEvent();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_RUN0(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    nextClientThread();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_RUN1(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    nextClientThread();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_RUN2(StateType currState, StateType nextState, EventType event, Map<String, Object> ctx,
      Map<String, Object> par) {

    nextClientThread();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_CHK0(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    checkForTurnOrNext();
  }

  @Override
  protected void ent_CHK1(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    checkForTurnOrNext();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_CHK2(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    checkForTurnOrNext();
  }

  // --------------------------------------------------------------------------------

  private void checkForTurnOrNext() {
    if (clientThreadIndx == MIN_CLIENTS - 1) {
      fireEvent(EventType.turn, null, null);
    } else {
      fireEvent(EventType.next, null, null);
    }
  }

  // --------------------------------------------------------------------------------

  private void handleCBIDEvent() {
    if (clientThreadHead != clientThreadCurr) {
      clientThreadHead = clientThreadCurr;
      clientThreadMBid++;

      StringBuffer strbuf = new StringBuffer();

      strbuf.append("INFO: BID FROM ");
      strbuf.append(clientThreadHead.getPlayerId());
      strbuf.append(" CURRENT BID IS ");
      strbuf.append(clientThreadMBid);

      sendForEach(strbuf.toString());
    } else {
      sendForCurr("INFO: YOU ARE AT THE HEAD, YOU CAN'T BID");
    }

    for (ClientThread clientThread : clientThreadList) {
      if (clientThread == clientThreadCurr) {
        continue;
      }

      clientThread.getClient().fireEvent( //
          com.minotauro.state.demo.protocol.Client.EventType.obid, //
          null, null);
    }
  }

  // --------------------------------------------------------------------------------

  private void handlePASSEvent(boolean time) {
    StringBuffer strbuf = new StringBuffer();

    if (time) {
      strbuf.append("INFO: TIME OUT FROM ");
    } else {
      strbuf.append("INFO: PASS FROM ");
    }

    strbuf.append(clientThreadCurr.getPlayerId());

    if (clientThreadHead != null) {
      strbuf.append(" AND CURRENT BID IS ");
      strbuf.append(clientThreadMBid);
      strbuf.append(" FROM ");
      strbuf.append(clientThreadHead.getPlayerId());
    } else {
      strbuf.append(" AND THERE IS NO CURRENT BID");
    }

    sendForEach(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  private void nextClientThread() {
    clientThreadIndx++;

    clientThreadCurr = clientThreadList.get(clientThreadIndx % clientThreadList.size());

    if (clientThreadIndx == clientThreadList.size()) {
      clientThreadIndx = 0;
    }

    clientThreadCurr.getClient().fireEvent( //
        com.minotauro.state.demo.protocol.Client.EventType.play, //
        null, null);

    sendForCurr("INFO: YOUR TURN");
  }

  // --------------------------------------------------------------------------------

  private void initClientThread(ClientThread clientThread) {
    if (clientThreadList.size() == 0) {
      clientThreadCurr = clientThread;
      clientThreadIndx = -1;
    }

    clientThreadList.add(clientThread);

    if (clientThreadList.size() >= MIN_CLIENTS) {
      fireEvent(EventType.abeg, null, null);
    }
  }

  // --------------------------------------------------------------------------------

  private void sendForEach(String string) {
    for (ClientThread clientThread : clientThreadList) {
      clientThread.write(string);
    }
  }

  // --------------------------------------------------------------------------------

  private void sendForCurr(String string) {
    clientThreadCurr.write(string);
  }

  // --------------------------------------------------------------------------------

  public List<ClientThread> getClientThreadList() {
    return clientThreadList;
  }

  public void setClientThreadList(List<ClientThread> clientThreadList) {
    this.clientThreadList = clientThreadList;
  }

  // --------------------------------------------------------------------------------

  public ClientThread getClientThreadHead() {
    return clientThreadHead;
  }

  public void setClientThreadHead(ClientThread clientThreadHead) {
    this.clientThreadHead = clientThreadHead;
  }

  // --------------------------------------------------------------------------------

  public int getClientThreadMBid() {
    return clientThreadMBid;
  }

  public void setClientThreadMBid(int clientThreadMBid) {
    this.clientThreadMBid = clientThreadMBid;
  }

  // --------------------------------------------------------------------------------

  public ClientThread getClientThreadCurr() {
    return clientThreadCurr;
  }

  public void setClientThreadCurr(ClientThread clientThreadCurr) {
    this.clientThreadCurr = clientThreadCurr;
  }

  // --------------------------------------------------------------------------------

  public int getClientThreadIndx() {
    return clientThreadIndx;
  }

  public void setClientThreadIndx(int clientThreadIndx) {
    this.clientThreadIndx = clientThreadIndx;
  }
}
