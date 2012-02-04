package org.cyrano.swing.event.custom;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public class MyPanel extends JPanel {

  private EventListenerList eventListenerList = new EventListenerList();

  public MyPanel() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getX() < getWidth() / 2) {
          fireRojoVerdeEvent(new RojoVerdeEvent(this, evt.getPoint()), RojoVerdeListener.METHOD_ROJO);

          fireActionEvent(new ActionEvent(this, 0, "rojo"));
        } else {
          fireRojoVerdeEvent(new RojoVerdeEvent(this, evt.getPoint()), RojoVerdeListener.METHOD_VERDE);

          fireActionEvent(new ActionEvent(this, 0, "verde"));
        }
      }
    });
  }

  public void addRojoVerdeListener(RojoVerdeListener listener) {
    eventListenerList.add(RojoVerdeListener.class, listener);
  }

  // delRojoVerdeListener
  public void removeRojoVerdeListener(RojoVerdeListener listener) {
    eventListenerList.remove(RojoVerdeListener.class, listener);
  }

  public RojoVerdeListener[] getRojoVerdeListeners() {
    return eventListenerList.getListeners(RojoVerdeListener.class);
  }

  private void fireRojoVerdeEvent(RojoVerdeEvent evt, int method) {
    RojoVerdeListener[] rojoVerdeListeners = getRojoVerdeListeners();

    for (RojoVerdeListener rojoVerdeListener : rojoVerdeListeners) {

      switch (method) {
        case RojoVerdeListener.METHOD_ROJO :
          rojoVerdeListener.rojoPerformed(evt);
          break;
        case RojoVerdeListener.METHOD_VERDE :
          rojoVerdeListener.verdePerformed(evt);
          break;
        default :
          throw new IllegalArgumentException();
      }
    }
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(0, 0, getWidth() / 2, getHeight());

    g.setColor(Color.GREEN);
    g.fillRect(getWidth() / 2, 0, getWidth(), getHeight());
  }

  public void addActionListener(ActionListener listener) {
    eventListenerList.add(ActionListener.class, listener);
  }

  // delRojoVerdeListener
  public void removeActionListener(ActionListener listener) {
    eventListenerList.remove(ActionListener.class, listener);
  }

  public ActionListener[] getActionListeners() {
    return eventListenerList.getListeners(ActionListener.class);
  }

  private void fireActionEvent(ActionEvent evt) {
    ActionListener[] actionListeners = getActionListeners();

    for (ActionListener actionListener : actionListeners) {
      actionListener.actionPerformed(evt);
    }
  }
}
