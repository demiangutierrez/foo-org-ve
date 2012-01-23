package org.cyrano.jogl.base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.media.opengl.GLCanvas;
import javax.swing.event.EventListenerList;

public class GLMouseListenerProxy implements MouseListener {

  protected enum MethodEnum {
    MOUSE_RELEASED, //
    MOUSE_PRESSED, //
    MOUSE_CLICKED, //
    MOUSE_ENTERED, //
    MOUSE_EXITED //
  }

  // --------------------------------------------------------------------------------

  protected class EventBean {

    public MouseEvent evt;

    public MethodEnum met;

    public EventBean(MouseEvent evt, MethodEnum met) {
      this.evt = evt;
      this.met = met;
    }
  }

  // --------------------------------------------------------------------------------

  protected Queue<EventBean> eventQueue = //
  new ConcurrentLinkedQueue<EventBean>();

  // --------------------------------------------------------------------------------

  protected EventListenerList eventListenerList;

  protected GLCanvas glCanvas;

  // --------------------------------------------------------------------------------

  public GLMouseListenerProxy( //
      EventListenerList eventListenerList, GLCanvas glCanvas) {

    this.eventListenerList = eventListenerList;
    this.glCanvas/*      */= glCanvas;
  }

  // --------------------------------------------------------------------------------

  public void addMouseListener(MouseListener listener) {
    eventListenerList.add/*   */(MouseListener.class, listener);
  }

  public void delMouseListener(MouseListener listener) {
    eventListenerList.remove/**/(MouseListener.class, listener);
  }

  // --------------------------------------------------------------------------------

  public EventListener[] getMouseListener() {
    return eventListenerList.getListeners(MouseListener.class);
  }

  // --------------------------------------------------------------------------------

  protected void fireActionEvent(MouseEvent evt, MethodEnum met) {
    EventListener[] eventListeners = getMouseListener();

    for (int i = 0; i < eventListeners.length; i++) {
      MouseListener listener = (MouseListener) eventListeners[i];

      switch (met) {

        case MOUSE_RELEASED/**/:
          listener.mouseReleased(evt);
          break;
        case MOUSE_PRESSED/* */:
          listener.mousePressed(evt);
          break;
        case MOUSE_CLICKED/* */:
          listener.mouseClicked(evt);
          break;
        case MOUSE_ENTERED/* */:
          listener.mouseEntered(evt);
          break;
        case MOUSE_EXITED/*  */:
          listener.mouseExited(evt);
          break;

        default :
          throw new IllegalArgumentException( //
              met.toString());
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void fireQueue() {
    synchronized (eventQueue) {
      while (!eventQueue.isEmpty()) {
        EventBean eventBean = eventQueue.poll();
        fireActionEvent(eventBean.evt, eventBean.met);
      }
    }
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  public void mouseReleased(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_RELEASED));
    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mousePressed(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_PRESSED));
    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_CLICKED));
    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseEntered(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_ENTERED));
    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseExited(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_EXITED));
    glCanvas.repaint();
  }
}
