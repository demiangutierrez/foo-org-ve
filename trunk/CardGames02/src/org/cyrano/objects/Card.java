package org.cyrano.objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.cyrano.common.Drawable;
import org.cyrano.main.Settings;
import org.cyrano.objects.CardEnums.CardColor;
import org.cyrano.objects.CardEnums.CardFace;
import org.cyrano.objects.CardEnums.CardNumb;
import org.cyrano.objects.CardEnums.CardSuit;

public class Card implements Drawable {

  private BufferedImage forebimg;
  private BufferedImage backbimg;

  private CardFace cardFace = CardFace.UP;

  private CardSuit cardSuit;
  private CardNumb cardNumb;

  private Point coords = new Point();

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

  public CardColor getCardColor() {
    return CardColor.getCardColor(cardSuit);
  }

  // --------------------------------------------------------------------------------

  public CardSuit getCardSuit() {
    return cardSuit;
  }

  public CardNumb getCardNumb() {
    return cardNumb;
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
  // Misc
  // --------------------------------------------------------------------------------

  @Override
  public String toString() {
    return cardSuit + "-" + getCardColor() + "-" + cardNumb + //
        " " + super.toString();
  }
}
