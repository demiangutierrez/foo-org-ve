package org.cyrano.objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.cyrano.common.Drawable;
import org.cyrano.main.Settings;
import org.cyrano.objects.CardEnums.CardColor;
import org.cyrano.objects.CardEnums.CardFace;
import org.cyrano.objects.CardEnums.CardNumb;
import org.cyrano.objects.CardEnums.CardSuit;

/**
 * @author Demián Gutierrez
 */
public class Card implements Drawable {

  protected BufferedImage forebimg;
  protected BufferedImage backbimg;

  protected CardFace cardFace = CardFace.UP;

  protected CardSuit cardSuit;
  protected CardNumb cardNumb;

  protected Point coords = new Point();

  // --------------------------------------------------------------------------------

  public Card( //
      BufferedImage forebimg, BufferedImage backbimg, //
      CardSuit cardSuit, CardNumb cardNumb) {

    this.forebimg = forebimg;
    this.backbimg = backbimg;
    this.cardSuit = cardSuit;
    this.cardNumb = cardNumb;
  }

  // --------------------------------------------------------------------------------

  public CardFace getCardFace() {
    return cardFace;
  }

  public void setCardFace(CardFace cardFace) {
    this.cardFace = cardFace;
  }

  // --------------------------------------------------------------------------------

  public CardSuit getCardSuit() {
    return cardSuit;
  }

  public CardNumb getCardNumb() {
    return cardNumb;
  }

  // --------------------------------------------------------------------------------

  public CardColor getCardColor() {
    return CardColor.getCardColor(cardSuit);
  }

  // --------------------------------------------------------------------------------
  // Drawable
  // --------------------------------------------------------------------------------

  @Override
  public boolean contains(Point point) {
    return getRectangle().contains(point);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void moveDt(int dx, int dy) {
    coords.setLocation( //
        coords.x + dx, //
        coords.y + dy);
  }

  @Override
  public void moveTo(int cx, int cy) {
    coords.setLocation(cx, cy);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    BufferedImage bimg;

    if (cardFace == CardFace.UP) {
      bimg = forebimg;
    } else {
      bimg = backbimg;
    }

    g2d.drawImage(bimg, coords.x, coords.y, null);
  }

  // --------------------------------------------------------------------------------

  @Override
  public Rectangle getRectangle() {
    return new Rectangle( //
        coords.x, coords.y, //
        Settings.CARD_W, Settings.CARD_H);
  }

  // --------------------------------------------------------------------------------

  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // Misc
  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return cardSuit + "-" + getCardColor() + "-" + cardNumb + //
        " " + super.toString();
  }
}
