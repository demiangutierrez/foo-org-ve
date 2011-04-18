package org.cyrano.game;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

public class Animator extends Thread {

  private static final int LOOP_SLEEP = 50;

  protected static Animator instance = new Animator();

  public static Animator getInstance() {
    return instance;
  }

  // --------------------------------------------------------------------------------

  protected EventListenerList eventListenerList = new EventListenerList();

  protected List<Sprite> spriteList = new ArrayList<Sprite>();

  // --------------------------------------------------------------------------------

  private Animator() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void addSprite(Sprite sprite) {
    spriteList.add(sprite);
  }

  public void delSprite(Sprite sprite) {
    spriteList.remove(sprite);
  }

  // --------------------------------------------------------------------------------

  public void addRepaintListener(RepaintListener listener) {
    eventListenerList.add(RepaintListener.class, listener);
  }

  public void delRepaintListener(RepaintListener listener) {
    eventListenerList.remove(RepaintListener.class, listener);
  }

  public EventListener[] getRepaintListener() {
    return eventListenerList.getListeners(RepaintListener.class);
  }

  // --------------------------------------------------------------------------------

  public void fireRepaintEvent(ActionEvent evt) {
    EventListener[] eventListeners = //
    eventListenerList.getListeners(RepaintListener.class);

    for (int i = 0; i < eventListeners.length; i++) {
      RepaintListener listener = (RepaintListener) eventListeners[i];
      listener.repaintRequest(evt);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    try {
      long t1 = System.currentTimeMillis();
      long t2 = t1;

      while (true) {
        t1 = t2;
        t2 = System.currentTimeMillis();

        for (Sprite sprite : spriteList) {
          sprite.animate(t1, t2);
        }

        fireRepaintEvent(new ActionEvent(this, 0, null));

        Thread.sleep(LOOP_SLEEP);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
