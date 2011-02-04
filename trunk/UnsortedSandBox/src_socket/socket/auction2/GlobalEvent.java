package socket.auction2;

public class GlobalEvent {

  // --------------------------------------------------------------------------------

  public enum StateType {
    INIT, NOBD, RUNI, RUNC, SALE, NOSA
  }

  // --------------------------------------------------------------------------------

  public enum EventType {
    UREG, ABEG, PASS, TURN, UBID, TIME
  }

  // --------------------------------------------------------------------------------

  public final EventType eventType;

  // --------------------------------------------------------------------------------

  public GlobalEvent(EventType eventType) {
    this.eventType = eventType;
  }

  // --------------------------------------------------------------------------------

  public static class EventUREG extends GlobalEvent {

    public final ClientThread clientThread;

    public EventUREG(ClientThread clientThread) {
      super(EventType.UREG);

      this.clientThread = clientThread;
    }
  }

  // --------------------------------------------------------------------------------

  public static class EventABEG extends GlobalEvent {

    public EventABEG() {
      super(EventType.ABEG);
    }
  }

  // --------------------------------------------------------------------------------

  public static class EventPASS extends GlobalEvent {

    public EventPASS() {
      super(EventType.PASS);
    }
  }

  // --------------------------------------------------------------------------------

  public static class EventTURN extends GlobalEvent {

    public EventTURN() {
      super(EventType.TURN);
    }
  }

  // --------------------------------------------------------------------------------

  public static class EventUBID extends GlobalEvent {

    public EventUBID() {
      super(EventType.UBID);
    }
  }

  // --------------------------------------------------------------------------------

  public static class EventTIME extends GlobalEvent {

    public EventTIME() {
      super(EventType.TIME);
    }
  }
}
