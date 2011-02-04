package socket.auction2;

// --------------------------------------------------------------------------------
// The nice thing of this way is: you can easily write a unit test for the code!!!
// --------------------------------------------------------------------------------

public class ClientProtocol {

  // --------------------------------------------------------------------------------

  public enum StateType {
    INIT, HEAD, BACK, LOSE, SALE
  }

  // --------------------------------------------------------------------------------

  public enum EventType {
    UBID, PASS, OBID, DONE
  }

  // --------------------------------------------------------------------------------

  private StateType state = StateType.INIT;

  // --------------------------------------------------------------------------------

  public ClientProtocol() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  // No parametrized event, so no need for a class like in GlobalProtocol 
  public void fireEvent(EventType event) {
    switch (state) {
      case INIT :
        stateINIT(event);
        break;
      case BACK :
        stateBACK(event);
        break;
      case HEAD :
        stateHEAD(event);
        break;
      case LOSE :
        stateLOSE(event);
        break;
      case SALE :
        stateSALE(event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateINIT(EventType event) {
    switch (event) {
      case UBID :
        stateChange(event, StateType.HEAD);
        break;
      case PASS :
      case OBID :
        stateChange(event, StateType.BACK);
        break;
      case DONE :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateBACK(EventType event) {
    switch (event) {
      case PASS :
      case OBID :
        stateChange(event, state);
        break;
      case UBID :
        stateChange(event, StateType.HEAD);
        break;
      case DONE :
        stateChange(event, StateType.LOSE);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateHEAD(EventType event) {
    switch (event) {
      case OBID :
        stateChange(event, StateType.BACK);
        break;
      case PASS :
        stateChange(event, state);
        break;
      case DONE :
        stateChange(event, StateType.SALE);
        break;
      case UBID :
        // No sense to bid if I'm at the head...
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateLOSE(EventType event) {
    // Terminal
    switch (event) {
      case UBID :
      case OBID :
      case PASS :
      case DONE :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateSALE(EventType event) {
    // Terminal
    switch (event) {
      case UBID :
      case OBID :
      case PASS :
      case DONE :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  public StateType getState() {
    return state;
  }

  // --------------------------------------------------------------------------------
  // XXX: Duplicated, does it worth it to send them to a super class?
  // --------------------------------------------------------------------------------

  private void throwIllegalState(StateType state, EventType event) {
    throw new IllegalStateException( //
        /**/state + " for event " + event);
  }

  private void throwDefaultState(StateType state, EventType event) {
    throw new RuntimeException( //
        "default : this is for sure a bug in the state machine for state " + //
            state + " and event " + event);
  }

  // --------------------------------------------------------------------------------

  private void stateChange(EventType event, StateType state) {
    System.err.println( //
        "Curr state is: " + this.state + //
            "; event is: " + event + "; next state is: " + state);

    this.state = state;
  }
}
