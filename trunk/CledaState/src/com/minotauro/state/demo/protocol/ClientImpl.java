package com.minotauro.state.demo.protocol;

import java.util.Map;

public class ClientImpl extends Client {

  private ClientThread clientThread;

  private GlobalImpl global;

  // --------------------------------------------------------------------------------

  public ClientImpl(ClientThread clientThread, GlobalImpl global) {
    this.clientThread = clientThread;
    this.global/*   */= global;
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void TURN_cbid_HEAD(Map<String, Object> ctx, Map<String, Object> par) {
    global.fireEvent(com.minotauro.state.demo.protocol.Global.EventType.cbid, ctx, par);
    clientThread.setSoTimeout(0, null);
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void TURN_pass_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    global.fireEvent(com.minotauro.state.demo.protocol.Global.EventType.pass, ctx, par);
    clientThread.setSoTimeout(0, null);
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void TURN_time_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    global.fireEvent(com.minotauro.state.demo.protocol.Global.EventType.pass, ctx, par);
    clientThread.setSoTimeout(0, null);
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void HEAD_obid_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void HEAD_done_SALE(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void BACK_obid_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void BACK_done_LOSE(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void ent_TURN(StateType currState, StateType nextState, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    clientThread.write("TURN");
    clientThread.setSoTimeout(ClientThread.TURN_TIMEOUT, "TIME");
  }
}
