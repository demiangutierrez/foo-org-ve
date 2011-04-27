// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package com.minotauro.state.demo.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Client {

  protected Logger log = Logger.getLogger(getClass().getName());

  // --------------------------------------------------------------------------------

  public enum EventType {
    play, //
    obid, //
    pass, //
    time, //
    cbid, //
    done, //
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    INIT, //
    TURN, //
    HEAD, //
    BACK, //
    SALE, //
    LOSE, //
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
      case TURN :
        stateTURN(event, ctx, par);
        break;
      case HEAD :
        stateHEAD(event, ctx, par);
        break;
      case BACK :
        stateBACK(event, ctx, par);
        break;
      case SALE :
        stateSALE(event, ctx, par);
        break;
      case LOSE :
        stateLOSE(event, ctx, par);
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
      case play :
        stateChangeMsg(StateType.TURN, event, ctx, par);

        extStateChange(this.state, StateType.TURN, event, ctx, par);
        evtStateChange(this.state, StateType.TURN, event, ctx, par);

        INIT_play_TURN(ctx, par);

        entStateChange(this.state, StateType.TURN, event, ctx, par);

        stateChangeSet(StateType.TURN, event, ctx, par);
        break;
      case obid :
        stateChangeMsg(StateType.BACK, event, ctx, par);

        extStateChange(this.state, StateType.BACK, event, ctx, par);
        evtStateChange(this.state, StateType.BACK, event, ctx, par);

        INIT_obid_BACK(ctx, par);

        entStateChange(this.state, StateType.BACK, event, ctx, par);

        stateChangeSet(StateType.BACK, event, ctx, par);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case done :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateTURN(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case play :
        throwIllegalState(state, event);
        break;
      case obid :
        throwIllegalState(state, event);
        break;
      case pass :
        stateChangeMsg(StateType.BACK, event, ctx, par);

        extStateChange(this.state, StateType.BACK, event, ctx, par);
        evtStateChange(this.state, StateType.BACK, event, ctx, par);

        TURN_pass_BACK(ctx, par);

        entStateChange(this.state, StateType.BACK, event, ctx, par);

        stateChangeSet(StateType.BACK, event, ctx, par);
        break;
      case time :
        stateChangeMsg(StateType.BACK, event, ctx, par);

        extStateChange(this.state, StateType.BACK, event, ctx, par);
        evtStateChange(this.state, StateType.BACK, event, ctx, par);

        TURN_time_BACK(ctx, par);

        entStateChange(this.state, StateType.BACK, event, ctx, par);

        stateChangeSet(StateType.BACK, event, ctx, par);
        break;
      case cbid :
        stateChangeMsg(StateType.HEAD, event, ctx, par);

        extStateChange(this.state, StateType.HEAD, event, ctx, par);
        evtStateChange(this.state, StateType.HEAD, event, ctx, par);

        TURN_cbid_HEAD(ctx, par);

        entStateChange(this.state, StateType.HEAD, event, ctx, par);

        stateChangeSet(StateType.HEAD, event, ctx, par);
        break;
      case done :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateHEAD(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case play :
        stateChangeMsg(StateType.TURN, event, ctx, par);

        extStateChange(this.state, StateType.TURN, event, ctx, par);
        evtStateChange(this.state, StateType.TURN, event, ctx, par);

        HEAD_play_TURN(ctx, par);

        entStateChange(this.state, StateType.TURN, event, ctx, par);

        stateChangeSet(StateType.TURN, event, ctx, par);
        break;
      case obid :
        stateChangeMsg(StateType.BACK, event, ctx, par);

        extStateChange(this.state, StateType.BACK, event, ctx, par);
        evtStateChange(this.state, StateType.BACK, event, ctx, par);

        HEAD_obid_BACK(ctx, par);

        entStateChange(this.state, StateType.BACK, event, ctx, par);

        stateChangeSet(StateType.BACK, event, ctx, par);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case done :
        stateChangeMsg(StateType.SALE, event, ctx, par);

        extStateChange(this.state, StateType.SALE, event, ctx, par);
        evtStateChange(this.state, StateType.SALE, event, ctx, par);

        HEAD_done_SALE(ctx, par);

        entStateChange(this.state, StateType.SALE, event, ctx, par);

        stateChangeSet(StateType.SALE, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateBACK(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case play :
        stateChangeMsg(StateType.TURN, event, ctx, par);

        extStateChange(this.state, StateType.TURN, event, ctx, par);
        evtStateChange(this.state, StateType.TURN, event, ctx, par);

        BACK_play_TURN(ctx, par);

        entStateChange(this.state, StateType.TURN, event, ctx, par);

        stateChangeSet(StateType.TURN, event, ctx, par);
        break;
      case obid :
        stateChangeMsg(StateType.BACK, event, ctx, par);

        extStateChange(this.state, StateType.BACK, event, ctx, par);
        evtStateChange(this.state, StateType.BACK, event, ctx, par);

        BACK_obid_BACK(ctx, par);

        entStateChange(this.state, StateType.BACK, event, ctx, par);

        stateChangeSet(StateType.BACK, event, ctx, par);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case done :
        stateChangeMsg(StateType.LOSE, event, ctx, par);

        extStateChange(this.state, StateType.LOSE, event, ctx, par);
        evtStateChange(this.state, StateType.LOSE, event, ctx, par);

        BACK_done_LOSE(ctx, par);

        entStateChange(this.state, StateType.LOSE, event, ctx, par);

        stateChangeSet(StateType.LOSE, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateSALE(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case play :
        throwIllegalState(state, event);
        break;
      case obid :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case done :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateLOSE(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case play :
        throwIllegalState(state, event);
        break;
      case obid :
        throwIllegalState(state, event);
        break;
      case pass :
        throwIllegalState(state, event);
        break;
      case time :
        throwIllegalState(state, event);
        break;
      case cbid :
        throwIllegalState(state, event);
        break;
      case done :
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

  protected void ent_TURN(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_TURN(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_HEAD(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_HEAD(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_BACK(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_BACK(StateType currState, StateType nextState, //
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

  protected void ent_LOSE(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_LOSE(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_play(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_obid(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_pass(StateType currState, StateType nextState, //
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

  protected void evt_done(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void INIT_play_TURN(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void INIT_obid_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void TURN_cbid_HEAD(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void TURN_pass_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void TURN_time_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void HEAD_play_TURN(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void HEAD_obid_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void HEAD_done_SALE(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void BACK_play_TURN(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void BACK_obid_BACK(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void BACK_done_LOSE(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      case INIT :
        ext_INIT(currState, nextState, event, ctx, par);
        break;
      case TURN :
        ext_TURN(currState, nextState, event, ctx, par);
        break;
      case HEAD :
        ext_HEAD(currState, nextState, event, ctx, par);
        break;
      case BACK :
        ext_BACK(currState, nextState, event, ctx, par);
        break;
      case SALE :
        ext_SALE(currState, nextState, event, ctx, par);
        break;
      case LOSE :
        ext_LOSE(currState, nextState, event, ctx, par);
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
      case TURN :
        ent_TURN(currState, nextState, event, ctx, par);
        break;
      case HEAD :
        ent_HEAD(currState, nextState, event, ctx, par);
        break;
      case BACK :
        ent_BACK(currState, nextState, event, ctx, par);
        break;
      case SALE :
        ent_SALE(currState, nextState, event, ctx, par);
        break;
      case LOSE :
        ent_LOSE(currState, nextState, event, ctx, par);
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
      case play :
        evt_play(currState, nextState, event, ctx, par);
        break;
      case obid :
        evt_obid(currState, nextState, event, ctx, par);
        break;
      case pass :
        evt_pass(currState, nextState, event, ctx, par);
        break;
      case time :
        evt_time(currState, nextState, event, ctx, par);
        break;
      case cbid :
        evt_cbid(currState, nextState, event, ctx, par);
        break;
      case done :
        evt_done(currState, nextState, event, ctx, par);
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
