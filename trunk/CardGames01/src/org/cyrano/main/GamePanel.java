package org.cyrano.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.common.Drawable;
import org.cyrano.objects.Card;
import org.cyrano.objects.Stack;
import org.cyrano.util.misc.Hwh;

/**
 * @author Demián Gutierrez
 */
public class GamePanel extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private List<Drawable> drawableList = new ArrayList<Drawable>();

  private Stack tmpStack;
  private Stack srcStack;

  private int dragDx;
  private int dragDy;

  // --------------------------------------------------------------------------------

  public GamePanel() {
    CardLoader cardLoader = new CardLoader();

    try {
      cardLoader.load();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // ----------------------------------------

    Stack stack;

    // ----------------------------------------

    stack = new Stack(20, 20, 0, 25);

    for (Card card : cardLoader.getDeck()) {
      switch (card.getCardNumb()) {
        case N0J :
        case N0Q :
        case N0K :
          stack.getCardList().add(card);
          break;
      }
    }

    // ----------------------------------------

    stack.updateCardList();
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(180, 20, 0, 25);
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(340, 20, 0, 25);
    drawableList.add(stack);

    // ----------------------------------------

    addMouseMotionListener(this);
    addMouseListener/*  */(this);
  }

  // --------------------------------------------------------------------------------
  // JPanel
  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.WHITE);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    // ----------------------------------------

    for (Drawable drawable : drawableList) {
      drawable.draw(g2d);
    }

    // ----------------------------------------

    if (tmpStack != null) {
      tmpStack.draw(g2d);
    }
  }

  // --------------------------------------------------------------------------------

  private Stack getStackAtPoint(Point point) {
    for (Drawable drawable : drawableList) {
      if (!drawable.contains(point)) {
        continue;
      }

      if (!(drawable instanceof Stack)) {
        continue;
      }

      return (Stack) drawable;
    }

    return null;
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked/* */(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseEntered/* */(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseExited/*  */(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mousePressed/* */(MouseEvent evt) {
    srcStack = getStackAtPoint(evt.getPoint());

    if (srcStack == null) {
      return;
    }

    tmpStack = srcStack.borrowCards(evt.getPoint());

    if (tmpStack == null) {
      return;
    }

    Rectangle rect = tmpStack.getRectangle();

    dragDx = rect.x - evt.getPoint().x;
    dragDy = rect.y - evt.getPoint().y;

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseReleased/**/(MouseEvent evt) {
    if (tmpStack == null) {
      return;
    }

    Stack tgtStack = getStackAtPoint(evt.getPoint());

    if (tgtStack != null && //
        tgtStack != tmpStack) {
      tgtStack.acceptCards(tmpStack);
    } else {
      srcStack.returnCards(tmpStack);
    }

    tmpStack = null;
    srcStack = null;

    repaint();
  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseDragged/**/(MouseEvent evt) {
    if (tmpStack == null) {
      return;
    }

    tmpStack.moveTo( //
        evt.getX() + dragDx, evt.getY() + dragDy);

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved/*  */(MouseEvent evt) {
    // Empty
  }
}
