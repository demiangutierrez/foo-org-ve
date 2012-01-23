package org.cyrano.jogl.base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.media.opengl.GLCanvas;
import javax.swing.event.EventListenerList;

public class GLMouseMotionListenerProxy implements MouseMotionListener {

  protected enum MethodEnum {
    MOUSE_DRAGGED, //
    MOUSE_MOVED
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

  public GLMouseMotionListenerProxy( //
      EventListenerList eventListenerList, GLCanvas glCanvas) {

    this.eventListenerList = eventListenerList;
    this.glCanvas/*      */= glCanvas;
  }

  // --------------------------------------------------------------------------------

  public void addMouseMotionListener(MouseMotionListener listener) {
    eventListenerList.add/*   */(MouseMotionListener.class, listener);
  }

  public void delMouseMotionListener(MouseMotionListener listener) {
    eventListenerList.remove/**/(MouseMotionListener.class, listener);
  }

  // --------------------------------------------------------------------------------

  public EventListener[] getMouseMotionListener() {
    return eventListenerList.getListeners(MouseMotionListener.class);
  }

  // --------------------------------------------------------------------------------

  protected void fireActionEvent(MouseEvent evt, MethodEnum met) {
    EventListener[] eventListeners = getMouseMotionListener();

    for (int i = 0; i < eventListeners.length; i++) {
      MouseMotionListener listener = (MouseMotionListener) eventListeners[i];

      switch (met) {

        case MOUSE_DRAGGED/**/:
          listener.mouseDragged(evt);
          break;
        case MOUSE_MOVED/*  */:
          listener.mouseMoved(evt);
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
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  public void mouseDragged(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_DRAGGED));
    glCanvas.repaint();
  }

  // --------------------------------------------------------------------------------

  public void mouseMoved(MouseEvent evt) {
    eventQueue.add(new EventBean(evt, MethodEnum.MOUSE_MOVED));
    glCanvas.repaint();
  }
}
