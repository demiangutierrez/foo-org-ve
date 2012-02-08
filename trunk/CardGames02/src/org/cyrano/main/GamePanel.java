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
import org.cyrano.objects.CardEnums.CardNumb;
import org.cyrano.objects.CardEnums.CardSuit;
import org.cyrano.objects.Stack;
import org.cyrano.rules.accept.AceOnEmptyAcceptRule;
import org.cyrano.rules.accept.AscNumbAcceptRule;
import org.cyrano.rules.accept.SameSuitAcceptRule;
import org.cyrano.rules.borrow.DscNumbBorrowRule;
import org.cyrano.rules.borrow.SameSuitBorrowRule;
import org.cyrano.rules.borrow.TopOnlyBorrowRule;
import org.cyrano.util.misc.Hwh;

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

    TopOnlyBorrowRule topOnlyBorrowRule = //
    new TopOnlyBorrowRule();

    SameSuitBorrowRule sameSuitBorrowRule = //
    new SameSuitBorrowRule();

    DscNumbBorrowRule dscNumbBorrowRule = //
    new DscNumbBorrowRule();

    // ----------------------------------------

    stack = new Stack(20, 20, 0, 40);

    stack.getCardList().add(cardLoader.getCard(CardSuit.CLU, CardNumb.N03));
    stack.getCardList().add(cardLoader.getCard(CardSuit.CLU, CardNumb.N02));
    stack.getCardList().add(cardLoader.getCard(CardSuit.CLU, CardNumb.N0A));

    stack.getCardList().add(cardLoader.getCard(CardSuit.DIA, CardNumb.N03));
    stack.getCardList().add(cardLoader.getCard(CardSuit.DIA, CardNumb.N02));
    stack.getCardList().add(cardLoader.getCard(CardSuit.DIA, CardNumb.N0A));

    stack.getCardList().add(cardLoader.getCard(CardSuit.SPA, CardNumb.N03));
    stack.getCardList().add(cardLoader.getCard(CardSuit.SPA, CardNumb.N02));
    stack.getCardList().add(cardLoader.getCard(CardSuit.SPA, CardNumb.N0A));

    stack.getCardList().add(cardLoader.getCard(CardSuit.HEA, CardNumb.N03));
    stack.getCardList().add(cardLoader.getCard(CardSuit.HEA, CardNumb.N02));
    stack.getCardList().add(cardLoader.getCard(CardSuit.HEA, CardNumb.N0A));

    cardLoader.getDeck().removeAll(stack.getCardList());

    stack.addBorrowRule(topOnlyBorrowRule);
    stack.addBorrowRule(sameSuitBorrowRule);
    stack.addBorrowRule(dscNumbBorrowRule);

    stack.updateCards();
    drawableList.add(stack);

    // ----------------------------------------

    AceOnEmptyAcceptRule aceOnEmptyAcceptRule = //
    new AceOnEmptyAcceptRule();

    AscNumbAcceptRule ascNumbAcceptRule = //
    new AscNumbAcceptRule();

    SameSuitAcceptRule sameSuitAcceptRule = //
    new SameSuitAcceptRule();

    // ----------------------------------------

    stack = new Stack(180, 20, 0, 25);
    stack.addAcceptRule(aceOnEmptyAcceptRule);
    stack.addAcceptRule(ascNumbAcceptRule);
    stack.addAcceptRule(sameSuitAcceptRule);
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(340, 20, 0, 25);
    stack.addAcceptRule(aceOnEmptyAcceptRule);
    stack.addAcceptRule(ascNumbAcceptRule);
    stack.addAcceptRule(sameSuitAcceptRule);
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(500, 20, 0, 25);
    stack.addAcceptRule(aceOnEmptyAcceptRule);
    stack.addAcceptRule(ascNumbAcceptRule);
    stack.addAcceptRule(sameSuitAcceptRule);
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(660, 20, 0, 25);
    stack.addAcceptRule(aceOnEmptyAcceptRule);
    stack.addAcceptRule(ascNumbAcceptRule);
    stack.addAcceptRule(sameSuitAcceptRule);
    drawableList.add(stack);

    // ----------------------------------------

    stack = new Stack(820, 20, 0, 25);
    stack.addAcceptRule(aceOnEmptyAcceptRule);
    stack.addAcceptRule(ascNumbAcceptRule);
    stack.addAcceptRule(sameSuitAcceptRule);
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
