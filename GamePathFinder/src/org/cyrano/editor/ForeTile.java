package org.cyrano.editor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ForeTile extends ARectangle {

  private BufferedImage dispImg;

  private BufferedImage maskImg;

  public ForeTile( //
      ACtrlPoint ctrlBeg, ACtrlPoint ctrlEnd, // 
      BufferedImage dispImg, BufferedImage maskImg) {
    super(ctrlBeg, ctrlEnd);

    ctrlBeg.setVisible(true);
    ctrlEnd.setVisible(true);

    this.dispImg = dispImg;
    this.maskImg = maskImg;

    setVisible(true);

    addRenderer(new ARenderer() {
      public void paint(Graphics2D g2d, AController controller) {
        g2d.drawImage(ForeTile.this.dispImg, ForeTile.this.ctrlBeg.getX(), ForeTile.this.ctrlBeg.getY(), null);
      }
    });
  }

  //  public void paint(Graphics2D g2d) {
  //    g2d.drawImage(dispImg, coords.x, coords.y, null);
  //  }
  //
  //  public Rectangle getBounds() {
  //    return new Rectangle( //
  //        coords.x, coords.y, //
  //        Hwh.getW(dispImg), Hwh.getW(maskImg));
  //  }
  //
  //  public boolean isStatic() {
  //    return false;
  //  }
}
