// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package com.minotauro.state.demo.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import stateMachine.EFieldState;

public class Workflow {

  protected Logger log = Logger.getLogger(getClass().getName());

  // --------------------------------------------------------------------------------

  public enum EventType {
    creg, //
    apro, //
    rech, //
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    INIT, //
    VALI, //
    APRO, //
    RECH, //
  }

  // --------------------------------------------------------------------------------

  public enum DocumentField {
    codDep, //
    monto, //
    fecha, //
    comentario, //
  }

  // --------------------------------------------------------------------------------

  public enum Role { // TODO: Rename to RoleType
    cliente, //
    operador, //
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
      case VALI :
        stateVALI(event, ctx, par);
        break;
      case APRO :
        stateAPRO(event, ctx, par);
        break;
      case RECH :
        stateRECH(event, ctx, par);
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
        stateChangeMsg(StateType.VALI, event, ctx, par);

        extStateChange(this.state, StateType.VALI, event, ctx, par);
        evtStateChange(this.state, StateType.VALI, event, ctx, par);

        INIT_creg_VALI(ctx, par);

        entStateChange(this.state, StateType.VALI, event, ctx, par);

        stateChangeSet(StateType.VALI, event, ctx, par);
        break;

      case apro :
        throwIllegalState(state, event);
        break;

      case rech :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateVALI(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {


      case creg :
        throwIllegalState(state, event);
        break;

      case apro :
        stateChangeMsg(StateType.APRO, event, ctx, par);

        extStateChange(this.state, StateType.APRO, event, ctx, par);
        evtStateChange(this.state, StateType.APRO, event, ctx, par);

        VALI_apro_APRO(ctx, par);

        entStateChange(this.state, StateType.APRO, event, ctx, par);

        stateChangeSet(StateType.APRO, event, ctx, par);
        break;



      case rech :
        stateChangeMsg(StateType.RECH, event, ctx, par);

        extStateChange(this.state, StateType.RECH, event, ctx, par);
        evtStateChange(this.state, StateType.RECH, event, ctx, par);

        VALI_rech_RECH(ctx, par);

        entStateChange(this.state, StateType.RECH, event, ctx, par);

        stateChangeSet(StateType.RECH, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateAPRO(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateRECH(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
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

  protected void ent_VALI(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_VALI(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_APRO(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_APRO(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_RECH(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_RECH(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_creg(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_apro(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_rech(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void INIT_creg_VALI(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void VALI_apro_APRO(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void VALI_rech_RECH(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      case INIT :
        ext_INIT(currState, nextState, event, ctx, par);
        break;
      case VALI :
        ext_VALI(currState, nextState, event, ctx, par);
        break;
      case APRO :
        ext_APRO(currState, nextState, event, ctx, par);
        break;
      case RECH :
        ext_RECH(currState, nextState, event, ctx, par);
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
      case VALI :
        ent_VALI(currState, nextState, event, ctx, par);
        break;
      case APRO :
        ent_APRO(currState, nextState, event, ctx, par);
        break;
      case RECH :
        ent_RECH(currState, nextState, event, ctx, par);
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
      case apro :
        evt_apro(currState, nextState, event, ctx, par);
        break;
      case rech :
        evt_rech(currState, nextState, event, ctx, par);
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

  // --------------------------------------------------------------------------------

  public StateType getState() {
    return state;
  }

  public void setState(StateType state) {
    this.state = state;
  }

  // --------------------------------------------------------------------------------

  public EFieldState getFieldStateForDocumentField(DocumentField documentField) {
    switch (state) {
      case INIT :
        return fieldStateForINIT(documentField);
      case VALI :
        return fieldStateForVALI(documentField);
      case APRO :
        return fieldStateForAPRO(documentField);
      case RECH :
        return fieldStateForRECH(documentField);
      default :
        throwDefaultState(state, null); // TODO: null
        break;
    }
    
    return null; // Never happens
  }

  // --------------------------------------------------------------------------------

  private EFieldState fieldStateForINIT(DocumentField documentField) {
    switch (documentField) { // TODO fieldState in the template (see ftl)
      case codDep :
        return EFieldState.EDITABLE;
      case monto :
        return EFieldState.EDITABLE;
      case fecha :
        return EFieldState.EDITABLE;
      default :
        return EFieldState.HIDDEN;
    }
  }

  // --------------------------------------------------------------------------------

  private EFieldState fieldStateForVALI(DocumentField documentField) {
    switch (documentField) { // TODO fieldState in the template (see ftl)
      case codDep :
        return EFieldState.READONLY;
      case monto :
        return EFieldState.READONLY;
      case fecha :
        return EFieldState.READONLY;
      case comentario :
        return EFieldState.EDITABLE;
      default :
        return EFieldState.HIDDEN;
    }
  }

  // --------------------------------------------------------------------------------

  private EFieldState fieldStateForAPRO(DocumentField documentField) {
    switch (documentField) { // TODO fieldState in the template (see ftl)
      default :
        return EFieldState.HIDDEN;
    }
  }

  // --------------------------------------------------------------------------------

  private EFieldState fieldStateForRECH(DocumentField documentField) {
    switch (documentField) { // TODO fieldState in the template (see ftl)
      default :
        return EFieldState.HIDDEN;
    }
  }

  // --------------------------------------------------------------------------------
}
