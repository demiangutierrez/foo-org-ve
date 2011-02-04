package imagerotation.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Demo4 extends JFrame implements ImageObserver {

  private BufferedImage doubleBuffer;
  private ImageIcon ximg0;
  private ImageIcon ximg1;
  private ImageIcon ximg2;

  public Demo4() {
    ximg0 = new ImageIcon(getClass().getResource("animated0.gif"));
    ximg1 = new ImageIcon(getClass().getResource("animated1.gif"));
    ximg2 = new ImageIcon(getClass().getResource("animated2.gif"));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    if (doubleBuffer == null) {
      doubleBuffer = (BufferedImage) createImage(getWidth(), getHeight());
    }

    Graphics2D g2d = (Graphics2D) doubleBuffer.getGraphics();

    g2d.setBackground(Color.WHITE);
    g2d.clearRect(0, 0, getWidth(), getHeight());

    g2d.drawImage(ximg0.getImage(), 20, 20, 200, 200, this);
    g2d.drawImage(ximg1.getImage(), 300, 100, this);
    g2d.drawImage(ximg2.getImage(), 250, 200, this);

    g2d.dispose();

    g.drawImage(doubleBuffer, 0, 0, null);
  }

  @Override
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
    System.out.println("Image update: infoflags=" + infoflags + " x=" + x + " y=" + y + " w=" + w + " h=" + h);

    // No need to, super class does this for JFrame
    // repaint();
    return super.imageUpdate(img, infoflags, x, y, w, h);
  }

  public static void main(String[] args) {
    new Demo4();
  }
}
