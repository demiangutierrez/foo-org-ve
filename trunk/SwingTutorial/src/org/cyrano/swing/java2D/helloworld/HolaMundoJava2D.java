package org.cyrano.swing.java2D.helloworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class HolaMundoJava2D extends JFrame {

  private BufferedImage bimg;
  private BufferedImage buffer;

  private int x = 20;
  private int y = 20;

  public HolaMundoJava2D() {
    try {
      bimg = ImageIO.read(getClass().getResourceAsStream("walking ex0000.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        buffer = null;
      }
    });

    Thread thread = new Thread() {
      @Override
      public void run() {
        int dx = 5;
        int dy = 5;

        while (true) {
          if (x + bimg.getWidth() > HolaMundoJava2D.this.getWidth() || x < 0) {
            dx *= -1;
          }
          if (y + bimg.getHeight() > HolaMundoJava2D.this.getHeight() || y < 0) {
            dy *= -1;
          }

          x += dx;
          y += dy;

          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          HolaMundoJava2D.this.repaint();
        }
      }
    };
    thread.start();

    //    setLayout(new BorderLayout());
    //    add(new PnlPaint(), BorderLayout.CENTER);

    setTitle("HolaMundoJava2D");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(400, 300);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  @Override
  public void update(Graphics g) {
    if (buffer == null) {
      buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    Graphics2D g2dScreen = (Graphics2D) g;

    Graphics2D g2d = (Graphics2D) buffer.getGraphics();

    g2d.setColor(Color.GREEN);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    g2d.setColor(Color.RED);
    g2d.drawOval(30, 30, 40, 100);

    g2d.setColor(Color.BLACK);
    g2d.drawLine(300, 30, 100, 200);

    g2d.drawImage(bimg, x, y, null);

    g2dScreen.drawImage(buffer, 0, 0, null);
  }

  public static void main(String[] args) {
    new HolaMundoJava2D();
  }
}
