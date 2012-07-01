package org.cyrano.swing.event.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.EventListenerList;

public class SourceOfActionEvents {

  // --------------------------------------------------------------------------------
  // This is the logic for the event
  // --------------------------------------------------------------------------------

  // List of Listeners
  private EventListenerList eventListenerList = new EventListenerList();

  public void addActionListener(ActionListener l) {
    eventListenerList.add(ActionListener.class, l);
  }

  public void removeActionListener(ActionListener l) {
    eventListenerList.remove(ActionListener.class, l);
  }

  public ActionListener[] getActionListeners() {
    return eventListenerList.getListeners(ActionListener.class);
  }

  // This is just an utility to fire ActionEvents from this class
  private void fireActionEvent(ActionEvent evt) {
    ActionListener[] actionListenerArray = getActionListeners();

    for (ActionListener l : actionListenerArray) {
      l.actionPerformed(evt);
    }
  }

  // --------------------------------------------------------------------------------
  // This is the logic for your class (i.e. button)
  // --------------------------------------------------------------------------------

  // for example, detect a mouse click or something else...
  public void doSomethingThatGeneratesAnEvent() {

    // Do something...

    // Don't care about the id or the command
    ActionEvent evt = new ActionEvent(this, 0, null);
    fireActionEvent(evt);

    // Do something else
  }
}
