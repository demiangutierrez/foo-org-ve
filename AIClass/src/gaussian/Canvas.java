package gaussian;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.JPanel;

import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel implements Runnable {

  private int[] count = new int[1];

  // --------------------------------------------------------------------------------

  public Canvas() {
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent evt) {
        count = new int[Hwh.getW(Canvas.this)];
      }
    });

    Thread t = new Thread(this);
    t.start();
  }

  // --------------------------------------------------------------------------------

  public static final int BOUND = 3;

  @Override
  public void run() {
    Random random = new Random();

    while (true) {

      for (int i = 0; i < 20; i++) {
        double gaussian = random.nextGaussian();

        if (gaussian < -BOUND || gaussian > +BOUND) {
          continue;
        }

        gaussian += BOUND; // so it is between 0-10

        // 10       -> l
        // gaussian -> x

        int index = (int) (gaussian * count.length / (2 * BOUND));

        count[index]++;
      }

      repaint();

      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // never
      }
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    AffineTransform prev = g2d.getTransform();

    g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) - 10);
    g2d.scale(1, -1);

    g2d.setColor(Color.RED);
    
    for (int i = 0; i < count.length; i++) {
      g2d.drawLine(i - count.length / 2, 0, i - count.length / 2, count[i]);
    }

    g2d.setColor(Color.YELLOW);

    g2d.drawLine(-Hwh.getW(this) / 2, 0, +Hwh.getW(this) / 2, 0);
    g2d.drawLine(0, -10, 0, Hwh.getH(this));

    g2d.setTransform(prev);
  }
}
