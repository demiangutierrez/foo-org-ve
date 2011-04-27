[#ftl]
// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package ${package};

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import stateMachine.EFieldState;

public class ${name} {

  protected Logger log = Logger.getLogger(getClass().getName());

  // --------------------------------------------------------------------------------

  public enum EventType {
    [#list eventList as event]
    ${event.name}, //
    [/#list]
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    [#list stateList as state]
    ${state.name}, //
    [/#list]
  }

  // --------------------------------------------------------------------------------

  public enum DocumentField {
    [#list fieldList as field]
    ${field.name}, //
    [/#list]
  }

  // --------------------------------------------------------------------------------

  public enum Role { // TODO: Rename to RoleType
    [#list roleList as role]
    ${role.name}, //
    [/#list]
  }

  // --------------------------------------------------------------------------------

  protected StateType state = StateType.${initial.name};

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
      [#list stateList as state]
      case ${state.name} :
        state${state.name}(event, ctx, par);
        break;
      [/#list]
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

  [#list stateList as state]
  // --------------------------------------------------------------------------------

  private void state${state.name}(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      [#list eventList as event]
      	[#assign eventFound=false]
        [#list state.transSetList as transSet]
        [#list transSet.transList as trans]

          [#if trans.event.name == event.name]
          	[#assign eventFound=true]
      case ${trans.event.name} :
        stateChangeMsg(StateType.${trans.target.name}, event, ctx, par);

        extStateChange(this.state, StateType.${trans.target.name}, event, ctx, par);
        evtStateChange(this.state, StateType.${trans.target.name}, event, ctx, par);

        ${state.name}_${trans.event.name}_${trans.target.name}(ctx, par);

        entStateChange(this.state, StateType.${trans.target.name}, event, ctx, par);

        stateChangeSet(StateType.${trans.target.name}, event, ctx, par);
        break;
          [/#if]
        [/#list]
        [#if eventFound == false]
      case ${event.name} :
        throwIllegalState(state, event);
        break;
        [/#if]
      [/#list]
      [/#list]
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  [/#list]
  [#list stateList as state]
  // --------------------------------------------------------------------------------

  protected void ent_${state.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_${state.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  [/#list]
  [#list eventList as event]
  // --------------------------------------------------------------------------------

  protected void evt_${event.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  [/#list]
  [#list stateList as state]
      [#list state.transSetList as transSet]
      [#list transSet.transList as trans]
  // --------------------------------------------------------------------------------

  protected void ${state.name}_${trans.event.name}_${trans.target.name}(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

      [/#list]
      [/#list]
  [/#list]
  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      [#list stateList as state]
      case ${state.name} :
        ext_${state.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void entStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (nextState) {
      [#list stateList as state]
      case ${state.name} :
        ent_${state.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void evtStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (event) {
      [#list eventList as event]
      case ${event.name} :
        evt_${event.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
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
      [#list stateList as state]
      case ${state.name} :
        return fieldStateFor${state.name}(documentField);
      [/#list]
      default :
        throwDefaultState(state, null); // TODO: null
        break;
    }
    
    return null; // Never happens
  }

  [#list stateList as state]
  // --------------------------------------------------------------------------------

  private EFieldState fieldStateFor${state.name}(DocumentField documentField) {
    switch (documentField) { // TODO fieldState in the template (see ftl)
      [#list state.fieldState as fieldState]
      case ${fieldState.fieldRef.name} :
        return EFieldState.${fieldState.state};
      [/#list]
      default :
        return EFieldState.HIDDEN;
    }
  }

  [/#list]
  // --------------------------------------------------------------------------------
}
