/*
 * Created on 27/08/2008
 */
package org.cyrano.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

/**
 * @author Demián Gutierrez
 */
public class ActionListenerProxy {

  protected EventListenerList eventListenerList;

  // --------------------------------------------------------------------------------

  public ActionListenerProxy(EventListenerList eventListenerList) {
    this.eventListenerList = eventListenerList;
  }

  // --------------------------------------------------------------------------------

  public void addActionListener(ActionListener listener) {
    eventListenerList.add(ActionListener.class, listener);
  }

  public void delActionListener(ActionListener listener) {
    eventListenerList.remove(ActionListener.class, listener);
  }

  public EventListener[] getActionListener() {
    return eventListenerList.getListeners(ActionListener.class);
  }

  public void fireActionEvent(ActionEvent evt) {
    EventListener[] eventListeners = getActionListener();

    for (int i = 0; i < eventListeners.length; i++) {
      ActionListener listener = (ActionListener) eventListeners[i];
      listener.actionPerformed(evt);
    }
  }
}
