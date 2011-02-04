package imagerotation.animation;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Demo3Single extends JFrame {

  private BufferedImage bimg1;
  private BufferedImage bimg2;

  public Demo3Single() {
    try {
      bimg1 = ImageIO.read(getClass().getResource("alias1.jpg"));
      bimg2 = ImageIO.read(getClass().getResource("alias2.jpg"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1024, 768);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    // This is not using doublebuffer, compare performance of
    // this against Demo3Double (manually double buffered).
    // Here you can "see" the pictures paint, in Demo3Double
    // it is almost instantaneous
    Graphics2D g2d = (Graphics2D) g;

    g2d.drawImage(bimg1, 0, 0, null);

    // Painting with alpha is a slow operation
    // use doublebuffer if required
    AlphaComposite alphaComposite;

    // 000% transparent / 100% opaque
    alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f);
    g2d.setComposite(alphaComposite);
    g2d.drawImage(bimg2, 50, 50, null);

    // 025% transparent / 075% opaque
    alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);
    g2d.setComposite(alphaComposite);
    g2d.drawImage(bimg2, 300, 50, null);

    // 050% transparent / 050% opaque
    alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f);
    g2d.setComposite(alphaComposite);
    g2d.drawImage(bimg2, 550, 50, null);

    // 075% transparent / 025% opaque
    alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
    g2d.setComposite(alphaComposite);
    g2d.drawImage(bimg2, 800, 50, null);
  }

  public static void main(String[] args) {
    new Demo3Single();
  }
}
