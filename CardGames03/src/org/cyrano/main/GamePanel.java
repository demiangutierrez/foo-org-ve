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
import org.cyrano.objects.DeckStack;
import org.cyrano.objects.Stack;
import org.cyrano.rules.accept.AceOnEmptyAcceptRule;
import org.cyrano.rules.borrow.NoneBorrowRule;
import org.cyrano.rules.borrow.TopOnlyBorrowRule;
import org.cyrano.util.Hwh;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

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

    NoneBorrowRule noneBorrowRule = new NoneBorrowRule();

    TopOnlyBorrowRule topOnlyBorrowRule = new TopOnlyBorrowRule();

    // ----------------------------------------

    stack = new Stack(180, 20, 35, 0);
    stack.addBorrowRule(topOnlyBorrowRule);
    stack.setShow(3);
    drawableList.add(stack);

    stack = new DeckStack(20, 20, 3, stack);
    stack.getCardList().addAll(cardLoader.getDeck());
    stack.addBorrowRule(noneBorrowRule);
    stack.setShow(1);
    stack.updateCards();
    drawableList.add(stack);

    // ----------------------------------------

    AceOnEmptyAcceptRule aceOnEmptyAcceptRule = //
    new AceOnEmptyAcceptRule();

    // ----------------------------------------

    stack = new Stack(465, 20, 0, 25);
//    stack.addAcceptRule(aceOnEmptyAcceptRule);
    drawableList.add(stack);

    // ----------------------------------------

    addMouseMotionListener(this);
    addMouseListener/*  */(this);
  }

  // --------------------------------------------------------------------------------

  private Stack getStackAtPoint(Point point) {
    for (Drawable drawable : drawableList) {
      if (!(drawable instanceof Stack)) {
        continue;
      }

      if (!drawable.contains(point)) {
        continue;
      }

      return (Stack) drawable;
    }

    return null;
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
  // MouseListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked/* */(MouseEvent evt) {
    for (Drawable drawable : drawableList) {
      if (!drawable.contains(evt.getPoint())) {
        continue;
      }

      drawable.mouseClicked(evt);
    }

    repaint();
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

    if (/*   */srcStack == null) {
      return;
    }

    int index = srcStack.getCardIndexAt(evt.getPoint());

    if (index == -1) {
      return;
    }

    if (/*  */!srcStack.testBorrowCards(index)) {
      return;
    }

    tmpStack = srcStack.execBorrowCards(index);

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

    if (/* */tgtStack == null || //
        /* */tgtStack == srcStack || //
        /**/!tgtStack.testAcceptCards(tmpStack)) {
      srcStack.execAcceptCards(tmpStack);
      srcStack.updateCards();
    } else {
      tgtStack.execAcceptCards(tmpStack);
      tgtStack.updateCards();
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
