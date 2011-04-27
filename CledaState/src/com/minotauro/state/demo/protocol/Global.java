// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package com.minotauro.state.demo.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Global {

  protected Logger log = Logger.getLogger(getClass().getName());

  // --------------------------------------------------------------------------------

  public enum EventType {
    creg, //
    abeg, //
    pass, //
    turn, //
    time, //
    cbid, //
    next, //
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    INIT, //
    RUN0, //
    RUN1, //
    CHK1, //
    RUN2, //
    CHK0, //
    NOSA, //
    CHK2, //
    SALE, //
  }

  // --------------------------------------------------------------------------------

  protected StateType state = StateType.INIT;

  // --------------------------------------------------------------------------------

  protected List<EventType> eventQueue = new ArrayList<EventType>();

  protected boolean firing;

  // --------------------------------------------------------------------------------

  protected void internalFireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (firing) {
      eventQueue.add(event); // TODO: add par to queue
      return;
    }

    firing = true;
    
    log.debug("firing " + //
        event + " event, in state " + state);

    switch (state) {
      case INIT :
        stateINIT(event, ctx, par);
        break;
      case RUN0 :
        stateRUN0(event, ctx, par);
        break;
      case RUN1 :
        stateRUN1(event, ctx, par);
        break;
      case CHK1 :
        stateCHK1(event, ctx, par);
        break;
      case RUN2 :
        stateRUN2(event, ctx, par);
        break;
      case CHK0 :
        stateCHK0(event, ctx, par);
        break;
      case NOSA :
        stateNOSA(event, ctx, par);
        break;
      case CHK2 :
        stateCHK2(event, ctx, par);
        break;
      case SALE :
        stateSALE(event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }

    firing = false;

    if (!eventQueue.isEmpty()) {
      EventType eventType = eventQueue.remove(0);
      fireEvent(eventType, ctx, null);
    }
  }

  // --------------------------------------------------------------------------------

  public synchronized void fireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (checkPreFireEvent(event, ctx, par)) {
      internalFireEvent(event, ctx, par);
    }
  }

  // --------------------------------------------------------------------------------

  protected boolean checkPreFireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    return true;
  }

  // --------------------------------------------------------------------------------

  private void stateINIT(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        stateChangeMsg(StateType.INIT, event, ctx, par);

        extStateChange(this.state, StateType.INIT, event, ctx, par);
        evtStateChange(this.state, StateType.INIT, event, ctx, par);

        INIT_creg_INIT(ctx, par);

        entStateChange(this.state, StateType.INIT, event, ctx, par);

        stateChangeSet(StateType.INIT, event, ctx, par);
        break;
      case abeg :
        stateChangeMsg(StateType.RUN0, event, ctx, par);

        extStateChange(this.state, StateType.RUN0, event, ctx, par);
        evtStateChange(this.state, StateType.RUN0, event, ctx, par);

        INIT_abeg_RUN0(ctx, par);

        entStateChange(this.state, StateType.RUN0, event, ctx, par);

        stateChangeSet(StateType.RUN0, event, ctx, par);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRUN0(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        stateChangeMsg(StateType.CHK0, event, ctx, par);

        extStateChange(this.state, StateType.CHK0, event, ctx, par);
        evtStateChange(this.state, StateType.CHK0, event, ctx, par);

        RUN0_pass_CHK0(ctx, par);

        entStateChange(this.state, StateType.CHK0, event, ctx, par);

        stateChangeSet(StateType.CHK0, event, ctx, par);
        break;
      case turn :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        stateChangeMsg(StateType.RUN1, event, ctx, par);

        extStateChange(this.state, StateType.RUN1, event, ctx, par);
        evtStateChange(this.state, StateType.RUN1, event, ctx, par);

        RUN0_cbid_RUN1(ctx, par);

        entStateChange(this.state, StateType.RUN1, event, ctx, par);

        stateChangeSet(StateType.RUN1, event, ctx, par);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRUN1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        stateChangeMsg(StateType.CHK1, event, ctx, par);

        extStateChange(this.state, StateType.CHK1, event, ctx, par);
        evtStateChange(this.state, StateType.CHK1, event, ctx, par);

        RUN1_pass_CHK1(ctx, par);

        entStateChange(this.state, StateType.CHK1, event, ctx, par);

        stateChangeSet(StateType.CHK1, event, ctx, par);
        break;
      case turn :
        stateChangeMsg(StateType.RUN2, event, ctx, par);

        extStateChange(this.state, StateType.RUN2, event, ctx, par);
        evtStateChange(this.state, StateType.RUN2, event, ctx, par);

        RUN1_turn_RUN2(ctx, par);

        entStateChange(this.state, StateType.RUN2, event, ctx, par);

        stateChangeSet(StateType.RUN2, event, ctx, par);
        break;
      case time :
        stateChangeMsg(StateType.RUN1, event, ctx, par);

        extStateChange(this.state, StateType.RUN1, event, ctx, par);
        evtStateChange(this.state, StateType.RUN1, event, ctx, par);

        RUN1_time_RUN1(ctx, par);

        entStateChange(this.state, StateType.RUN1, event, ctx, par);

        stateChangeSet(StateType.RUN1, event, ctx, par);
        break;
      case cbid :
        stateChangeMsg(StateType.RUN1, event, ctx, par);

        extStateChange(this.state, StateType.RUN1, event, ctx, par);
        evtStateChange(this.state, StateType.RUN1, event, ctx, par);

        RUN1_cbid_RUN1(ctx, par);

        entStateChange(this.state, StateType.RUN1, event, ctx, par);

        stateChangeSet(StateType.RUN1, event, ctx, par);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateCHK1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        stateChangeMsg(StateType.RUN2, event, ctx, par);

        extStateChange(this.state, StateType.RUN2, event, ctx, par);
        evtStateChange(this.state, StateType.RUN2, event, ctx, par);

        CHK1_turn_RUN2(ctx, par);

        entStateChange(this.state, StateType.RUN2, event, ctx, par);

        stateChangeSet(StateType.RUN2, event, ctx, par);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        stateChangeMsg(StateType.RUN1, event, ctx, par);

        extStateChange(this.state, StateType.RUN1, event, ctx, par);
        evtStateChange(this.state, StateType.RUN1, event, ctx, par);

        CHK1_next_RUN1(ctx, par);

        entStateChange(this.state, StateType.RUN1, event, ctx, par);

        stateChangeSet(StateType.RUN1, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRUN2(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        stateChangeMsg(StateType.CHK2, event, ctx, par);

        extStateChange(this.state, StateType.CHK2, event, ctx, par);
        evtStateChange(this.state, StateType.CHK2, event, ctx, par);

        RUN2_pass_CHK2(ctx, par);

        entStateChange(this.state, StateType.CHK2, event, ctx, par);

        stateChangeSet(StateType.CHK2, event, ctx, par);
        break;
      case turn :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        stateChangeMsg(StateType.RUN1, event, ctx, par);

        extStateChange(this.state, StateType.RUN1, event, ctx, par);
        evtStateChange(this.state, StateType.RUN1, event, ctx, par);

        RUN2_cbid_RUN1(ctx, par);

        entStateChange(this.state, StateType.RUN1, event, ctx, par);

        stateChangeSet(StateType.RUN1, event, ctx, par);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateCHK0(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        stateChangeMsg(StateType.NOSA, event, ctx, par);

        extStateChange(this.state, StateType.NOSA, event, ctx, par);
        evtStateChange(this.state, StateType.NOSA, event, ctx, par);

        CHK0_turn_NOSA(ctx, par);

        entStateChange(this.state, StateType.NOSA, event, ctx, par);

        stateChangeSet(StateType.NOSA, event, ctx, par);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        stateChangeMsg(StateType.RUN0, event, ctx, par);

        extStateChange(this.state, StateType.RUN0, event, ctx, par);
        evtStateChange(this.state, StateType.RUN0, event, ctx, par);

        CHK0_next_RUN0(ctx, par);

        entStateChange(this.state, StateType.RUN0, event, ctx, par);

        stateChangeSet(StateType.RUN0, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateNOSA(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateCHK2(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        stateChangeMsg(StateType.SALE, event, ctx, par);

        extStateChange(this.state, StateType.SALE, event, ctx, par);
        evtStateChange(this.state, StateType.SALE, event, ctx, par);

        CHK2_turn_SALE(ctx, par);

        entStateChange(this.state, StateType.SALE, event, ctx, par);

        stateChangeSet(StateType.SALE, event, ctx, par);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        stateChangeMsg(StateType.RUN2, event, ctx, par);

        extStateChange(this.state, StateType.RUN2, event, ctx, par);
        evtStateChange(this.state, StateType.RUN2, event, ctx, par);

        CHK2_next_RUN2(ctx, par);

        entStateChange(this.state, StateType.RUN2, event, ctx, par);

        stateChangeSet(StateType.RUN2, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateSALE(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case creg :
        throwIllegalState(state, event);
        break;
      case abeg :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case turn :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case next :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void ent_INIT(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_INIT(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_RUN0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_RUN0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_RUN1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_RUN1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_CHK1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_CHK1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_RUN2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_RUN2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_CHK0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_CHK0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_NOSA(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_NOSA(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_CHK2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_CHK2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_SALE(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_SALE(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_creg(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_abeg(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_pass(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_turn(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_time(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_cbid(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_next(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void INIT_creg_INIT(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void INIT_abeg_RUN0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN0_pass_CHK0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN0_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN1_pass_CHK1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN1_time_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN1_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN1_turn_RUN2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK1_next_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK1_turn_RUN2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN2_pass_CHK2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void RUN2_cbid_RUN1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK0_next_RUN0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK0_turn_NOSA(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK2_next_RUN2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void CHK2_turn_SALE(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      case INIT :
        ext_INIT(currState, nextState, event, ctx, par);
        break;
      case RUN0 :
        ext_RUN0(currState, nextState, event, ctx, par);
        break;
      case RUN1 :
        ext_RUN1(currState, nextState, event, ctx, par);
        break;
      case CHK1 :
        ext_CHK1(currState, nextState, event, ctx, par);
        break;
      case RUN2 :
        ext_RUN2(currState, nextState, event, ctx, par);
        break;
      case CHK0 :
        ext_CHK0(currState, nextState, event, ctx, par);
        break;
      case NOSA :
        ext_NOSA(currState, nextState, event, ctx, par);
        break;
      case CHK2 :
        ext_CHK2(currState, nextState, event, ctx, par);
        break;
      case SALE :
        ext_SALE(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void entStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (nextState) {
      case INIT :
        ent_INIT(currState, nextState, event, ctx, par);
        break;
      case RUN0 :
        ent_RUN0(currState, nextState, event, ctx, par);
        break;
      case RUN1 :
        ent_RUN1(currState, nextState, event, ctx, par);
        break;
      case CHK1 :
        ent_CHK1(currState, nextState, event, ctx, par);
        break;
      case RUN2 :
        ent_RUN2(currState, nextState, event, ctx, par);
        break;
      case CHK0 :
        ent_CHK0(currState, nextState, event, ctx, par);
        break;
      case NOSA :
        ent_NOSA(currState, nextState, event, ctx, par);
        break;
      case CHK2 :
        ent_CHK2(currState, nextState, event, ctx, par);
        break;
      case SALE :
        ent_SALE(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void evtStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (event) {
      case creg :
        evt_creg(currState, nextState, event, ctx, par);
        break;
      case abeg :
        evt_abeg(currState, nextState, event, ctx, par);
        break;
      case pass :
        evt_pass(currState, nextState, event, ctx, par);
        break;
      case turn :
        evt_turn(currState, nextState, event, ctx, par);
        break;
      case time :
        evt_time(currState, nextState, event, ctx, par);
        break;
      case cbid :
        evt_cbid(currState, nextState, event, ctx, par);
        break;
      case next :
        evt_next(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateChangeMsg(StateType state, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    log.debug( //
        "Curr state is: " + this.state + //
            "; event is: " + event + "; next state is: " + state);
  }

  private void stateChangeSet(StateType state, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    this.state = state;
  }

  // --------------------------------------------------------------------------------

  protected void throwIllegalState(StateType state, EventType event) {
    log.debug( //
        "IllegalStateException: " + state + " for event " + event);

    throw new IllegalStateException( //
        /**/state + " for event " + event);
  }

  protected void throwDefaultState(StateType state, EventType event) {
    String msg = "default : this is for sure a bug in the state machine for state " + //
        state + " and event " + event;

    log.debug(msg);

    throw new RuntimeException(msg);
  }
}
