package multitile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class MultiPanel extends JPanel implements Runnable {

  private boolean running;

  public static final int TILE_W = 50;
  public static final int TILE_H = 50;

  private Point next;
  private double speed = 100;

  private TextMap textMap;

  private Sprite sprite;

  // --------------------------------------------------------------------------------

  public MultiPanel() {

    setFocusable(true);
    addKeyListener(new TimedKeyListener() {
      @Override
      public void keyPressed(KeyEvent evt) {
        super.keyPressed(evt);
        MultiPanel.this.keyPressed(evt);
      }

      @Override
      public void keyReleased(KeyEvent evt) {
        super.keyReleased(evt);

        if (getReleased()) {
          MultiPanel.this.keyReleased(evt);
        }
      }
    });

    // ----------------------------------------
    // Init path
    // ----------------------------------------

    textMap = new TextMap();

    try {
      textMap.load(getClass().getResource("map.txt").getPath());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    sprite = new Sprite();

    sprite.setX(3);
    sprite.setY(3);

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    //    sprite = new Sprite();
    //    sprite.setX(pathReader.curr().x);
    //    sprite.setY(pathReader.curr().y);
    //
    //    next = pathReader.next();
    //    sprite.setGoingto(next);

    // ----------------------------------------
    // Start thread
    // ----------------------------------------

    running = true;
    Thread th = new Thread(this);
    th.start();
  }

  public static final int NO = 0;
  public static final int LF = 1;
  public static final int RG = 2;
  public static final int UP = 3;
  public static final int DW = 4;

  protected int direction;

  protected void keyReleased(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_UP :
      case KeyEvent.VK_DOWN :
      case KeyEvent.VK_LEFT :
      case KeyEvent.VK_RIGHT :
        System.err.println("---------------------------------");
        sprite.setDirection(NO);
        break;
    }
  }

  protected void keyPressed(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_UP :
        sprite.setDirection(UP);
        break;
      case KeyEvent.VK_DOWN :
        sprite.setDirection(DW);
        break;
      case KeyEvent.VK_LEFT :
        sprite.setDirection(LF);
        break;
      case KeyEvent.VK_RIGHT :
        sprite.setDirection(RG);
        break;
      default :
        System.err.println("*****************************+");
        sprite.setDirection(NO);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    long t1 = System.nanoTime();

    int sleep = 20;

    while (running) {
      try {
        Thread.sleep(sleep);

        repaint();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      long t2 = System.nanoTime();

      double dt = (t2 - t1) / 1000000000.0;

      // ----------------------------------------
      // Update Sprites
      // ----------------------------------------

      sprite.updatePos(dt);

      t1 = t2;
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
    g2d.clearRect(0, 0, getWidth(), getHeight());

    //    pathReader.paint(g2d);

    for (int y = 0; y < textMap.getH(); y++) {
      for (int x = 0; x < textMap.getW(); x++) {
        if (textMap.getData()[x][y] == 'X') {
          g2d.setColor(Color.GRAY);
          g2d.fillRect(x * TILE_W, y * TILE_H, TILE_W, TILE_H);
          g2d.setColor(Color.RED);
          g2d.drawRect(x * TILE_W, y * TILE_H, TILE_W, TILE_H);
        }
        if (textMap.getData()[x][y] == ' ') {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(x * TILE_W, y * TILE_H, TILE_W, TILE_H);
          g2d.setColor(Color.RED);
          g2d.drawRect(x * TILE_W, y * TILE_H, TILE_W, TILE_H);
        }
      }
    }

    sprite.paint(g2d);
  }
}
