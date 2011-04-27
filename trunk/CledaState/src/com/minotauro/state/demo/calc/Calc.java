// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package com.minotauro.state.demo.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Calc {

  protected Logger log = Logger.getLogger(getClass().getName());

  // --------------------------------------------------------------------------------

  public enum EventType {
    numb, //
    decp, //
    oper, //
    equa, //
    clea, //
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    OPI0, //
    OPE1, //
    OPD1, //
    OPI1, //
    OPE2, //
    OPD2, //
  }

  // --------------------------------------------------------------------------------

  protected StateType state = StateType.OPE1;

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
      case OPI0 :
        stateOPI0(event, ctx, par);
        break;
      case OPE1 :
        stateOPE1(event, ctx, par);
        break;
      case OPD1 :
        stateOPD1(event, ctx, par);
        break;
      case OPI1 :
        stateOPI1(event, ctx, par);
        break;
      case OPE2 :
        stateOPE2(event, ctx, par);
        break;
      case OPD2 :
        stateOPD2(event, ctx, par);
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

  private void stateOPI0(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPE1, event, ctx, par);

        extStateChange(this.state, StateType.OPE1, event, ctx, par);
        evtStateChange(this.state, StateType.OPE1, event, ctx, par);

        OPI0_numb_OPE1(ctx, par);

        entStateChange(this.state, StateType.OPE1, event, ctx, par);

        stateChangeSet(StateType.OPE1, event, ctx, par);
        break;
      case decp :
        stateChangeMsg(StateType.OPD1, event, ctx, par);

        extStateChange(this.state, StateType.OPD1, event, ctx, par);
        evtStateChange(this.state, StateType.OPD1, event, ctx, par);

        OPI0_decp_OPD1(ctx, par);

        entStateChange(this.state, StateType.OPD1, event, ctx, par);

        stateChangeSet(StateType.OPD1, event, ctx, par);
        break;
      case oper :
        throwIllegalState(state, event);
        break;
      case equa :
        throwIllegalState(state, event);
        break;
      case clea :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateOPE1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPE1, event, ctx, par);

        extStateChange(this.state, StateType.OPE1, event, ctx, par);
        evtStateChange(this.state, StateType.OPE1, event, ctx, par);

        OPE1_numb_OPE1(ctx, par);

        entStateChange(this.state, StateType.OPE1, event, ctx, par);

        stateChangeSet(StateType.OPE1, event, ctx, par);
        break;
      case decp :
        stateChangeMsg(StateType.OPD1, event, ctx, par);

        extStateChange(this.state, StateType.OPD1, event, ctx, par);
        evtStateChange(this.state, StateType.OPD1, event, ctx, par);

        OPE1_decp_OPD1(ctx, par);

        entStateChange(this.state, StateType.OPD1, event, ctx, par);

        stateChangeSet(StateType.OPD1, event, ctx, par);
        break;
      case oper :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPE1_oper_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      case equa :
        throwIllegalState(state, event);
        break;
      case clea :
        stateChangeMsg(StateType.OPI0, event, ctx, par);

        extStateChange(this.state, StateType.OPI0, event, ctx, par);
        evtStateChange(this.state, StateType.OPI0, event, ctx, par);

        OPE1_clea_OPI0(ctx, par);

        entStateChange(this.state, StateType.OPI0, event, ctx, par);

        stateChangeSet(StateType.OPI0, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateOPD1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPD1, event, ctx, par);

        extStateChange(this.state, StateType.OPD1, event, ctx, par);
        evtStateChange(this.state, StateType.OPD1, event, ctx, par);

        OPD1_numb_OPD1(ctx, par);

        entStateChange(this.state, StateType.OPD1, event, ctx, par);

        stateChangeSet(StateType.OPD1, event, ctx, par);
        break;
      case decp :
        throwIllegalState(state, event);
        break;
      case oper :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPD1_oper_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      case equa :
        throwIllegalState(state, event);
        break;
      case clea :
        stateChangeMsg(StateType.OPI0, event, ctx, par);

        extStateChange(this.state, StateType.OPI0, event, ctx, par);
        evtStateChange(this.state, StateType.OPI0, event, ctx, par);

        OPD1_clea_OPI0(ctx, par);

        entStateChange(this.state, StateType.OPI0, event, ctx, par);

        stateChangeSet(StateType.OPI0, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateOPI1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPE2, event, ctx, par);

        extStateChange(this.state, StateType.OPE2, event, ctx, par);
        evtStateChange(this.state, StateType.OPE2, event, ctx, par);

        OPI1_numb_OPE2(ctx, par);

        entStateChange(this.state, StateType.OPE2, event, ctx, par);

        stateChangeSet(StateType.OPE2, event, ctx, par);
        break;
      case decp :
        stateChangeMsg(StateType.OPD2, event, ctx, par);

        extStateChange(this.state, StateType.OPD2, event, ctx, par);
        evtStateChange(this.state, StateType.OPD2, event, ctx, par);

        OPI1_decp_OPD2(ctx, par);

        entStateChange(this.state, StateType.OPD2, event, ctx, par);

        stateChangeSet(StateType.OPD2, event, ctx, par);
        break;
      case oper :
        throwIllegalState(state, event);
        break;
      case equa :
        throwIllegalState(state, event);
        break;
      case clea :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateOPE2(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPE2, event, ctx, par);

        extStateChange(this.state, StateType.OPE2, event, ctx, par);
        evtStateChange(this.state, StateType.OPE2, event, ctx, par);

        OPE2_numb_OPE2(ctx, par);

        entStateChange(this.state, StateType.OPE2, event, ctx, par);

        stateChangeSet(StateType.OPE2, event, ctx, par);
        break;
      case decp :
        stateChangeMsg(StateType.OPD2, event, ctx, par);

        extStateChange(this.state, StateType.OPD2, event, ctx, par);
        evtStateChange(this.state, StateType.OPD2, event, ctx, par);

        OPE2_decp_OPD2(ctx, par);

        entStateChange(this.state, StateType.OPD2, event, ctx, par);

        stateChangeSet(StateType.OPD2, event, ctx, par);
        break;
      case oper :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPE2_oper_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      case equa :
        stateChangeMsg(StateType.OPI0, event, ctx, par);

        extStateChange(this.state, StateType.OPI0, event, ctx, par);
        evtStateChange(this.state, StateType.OPI0, event, ctx, par);

        OPE2_equa_OPI0(ctx, par);

        entStateChange(this.state, StateType.OPI0, event, ctx, par);

        stateChangeSet(StateType.OPI0, event, ctx, par);
        break;
      case clea :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPE2_clea_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateOPD2(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case numb :
        stateChangeMsg(StateType.OPD2, event, ctx, par);

        extStateChange(this.state, StateType.OPD2, event, ctx, par);
        evtStateChange(this.state, StateType.OPD2, event, ctx, par);

        OPD2_numb_OPD2(ctx, par);

        entStateChange(this.state, StateType.OPD2, event, ctx, par);

        stateChangeSet(StateType.OPD2, event, ctx, par);
        break;
      case decp :
        throwIllegalState(state, event);
        break;
      case oper :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPD2_oper_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      case equa :
        stateChangeMsg(StateType.OPI0, event, ctx, par);

        extStateChange(this.state, StateType.OPI0, event, ctx, par);
        evtStateChange(this.state, StateType.OPI0, event, ctx, par);

        OPD2_equa_OPI0(ctx, par);

        entStateChange(this.state, StateType.OPI0, event, ctx, par);

        stateChangeSet(StateType.OPI0, event, ctx, par);
        break;
      case clea :
        stateChangeMsg(StateType.OPI1, event, ctx, par);

        extStateChange(this.state, StateType.OPI1, event, ctx, par);
        evtStateChange(this.state, StateType.OPI1, event, ctx, par);

        OPD2_clea_OPI1(ctx, par);

        entStateChange(this.state, StateType.OPI1, event, ctx, par);

        stateChangeSet(StateType.OPI1, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPI0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPI0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPE1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPE1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPD1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPD1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPI1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPI1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPE2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPE2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_OPD2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_OPD2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_numb(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_decp(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_oper(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_equa(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_clea(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPI0_numb_OPE1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPI0_decp_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE1_numb_OPE1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE1_decp_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE1_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE1_clea_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD1_numb_OPD1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD1_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD1_clea_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPI1_numb_OPE2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPI1_decp_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE2_numb_OPE2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE2_decp_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE2_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE2_clea_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPE2_equa_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD2_numb_OPD2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD2_oper_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD2_clea_OPI1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void OPD2_equa_OPI0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      case OPI0 :
        ext_OPI0(currState, nextState, event, ctx, par);
        break;
      case OPE1 :
        ext_OPE1(currState, nextState, event, ctx, par);
        break;
      case OPD1 :
        ext_OPD1(currState, nextState, event, ctx, par);
        break;
      case OPI1 :
        ext_OPI1(currState, nextState, event, ctx, par);
        break;
      case OPE2 :
        ext_OPE2(currState, nextState, event, ctx, par);
        break;
      case OPD2 :
        ext_OPD2(currState, nextState, event, ctx, par);
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
      case OPI0 :
        ent_OPI0(currState, nextState, event, ctx, par);
        break;
      case OPE1 :
        ent_OPE1(currState, nextState, event, ctx, par);
        break;
      case OPD1 :
        ent_OPD1(currState, nextState, event, ctx, par);
        break;
      case OPI1 :
        ent_OPI1(currState, nextState, event, ctx, par);
        break;
      case OPE2 :
        ent_OPE2(currState, nextState, event, ctx, par);
        break;
      case OPD2 :
        ent_OPD2(currState, nextState, event, ctx, par);
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
      case numb :
        evt_numb(currState, nextState, event, ctx, par);
        break;
      case decp :
        evt_decp(currState, nextState, event, ctx, par);
        break;
      case oper :
        evt_oper(currState, nextState, event, ctx, par);
        break;
      case equa :
        evt_equa(currState, nextState, event, ctx, par);
        break;
      case clea :
        evt_clea(currState, nextState, event, ctx, par);
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
