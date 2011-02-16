package boxpush1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.cyrano.util.TimedKeyListener;

public class MultiPanel extends JPanel implements Runnable {

  public static final int FONT_DX = 5;
  public static final int FONT_DY = 15;

  public static final int TILE_W = 25;
  public static final int TILE_H = 25;

  public static final int NO = 0;
  public static final int LF = 1;
  public static final int RG = 2;
  public static final int UP = 3;
  public static final int DW = 4;

  // --------------------------------------------------------------------------------

  private Map<Character, Sprite> spriteMap = //
  new HashMap<Character, Sprite>();

  private TextMap textMap;

  private Sprite playerSprite;

  private boolean running;

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

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    Sprite sprite;

    sprite = initSprite(/**/11,/* */4,/* */2, 3, Color.BLUE,/*   */'B');
    sprite = initSprite(/**/13,/* */4,/* */3, 2, Color.YELLOW,/* */'C');
    sprite = initSprite(/**/15,/* */6,/* */1, 1, Color.ORANGE,/* */'D');
    sprite = initSprite(/**/10,/* */7,/**/10, 1, Color.CYAN,/*   */'E');
    sprite = initSprite(/**/11,/* */8,/* */3, 3, Color.RED,/*    */'F');
    sprite = initSprite(/* */3,/* */3,/* */2, 2, Color.MAGENTA,/**/'A');

    playerSprite = sprite;

    // ----------------------------------------
    // Start thread
    // ----------------------------------------

    running = true;
    Thread th = new Thread(this);
    th.start();
  }

  // --------------------------------------------------------------------------------

  protected Sprite initSprite( //
      int x, int y, int w, int h, Color color, char mapChar) {

    Sprite sprite = new Sprite(w, h);
    sprite.setColor(color);

    sprite.setX(x);
    sprite.setY(y);

    sprite.setMapChar(mapChar);
    sprite.setTextMap(textMap);
    sprite.initMap(sprite.getMapChar());

    spriteMap.put(sprite.getMapChar(), sprite);
    sprite.setSpriteMap(spriteMap);

    return sprite;
  }

  // --------------------------------------------------------------------------------

  protected void keyReleased(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_I :
      case KeyEvent.VK_K :
      case KeyEvent.VK_J :
      case KeyEvent.VK_L :
        playerSprite.setDirection(NO);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void keyPressed(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_I :
        playerSprite.setDirection(UP);
        break;
      case KeyEvent.VK_K :
        playerSprite.setDirection(DW);
        break;
      case KeyEvent.VK_J :
        playerSprite.setDirection(LF);
        break;
      case KeyEvent.VK_L :
        playerSprite.setDirection(RG);
        break;
      default :
        playerSprite.setDirection(NO);
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

//      playerSprite.resetUpdateFlag();
      playerSprite.updatePos(dt, System.currentTimeMillis());

      // ----------------------------------------------
      // Other (pushed) sprites are updated from player
      // ----------------------------------------------
      // for (Sprite spriteSome : spriteMap.values()) {
      //   spriteSome.updatePos(dt);
      // }
      // ----------------------------------------------

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

    for (Sprite spriteSome : spriteMap.values()) {
      spriteSome.paint(g2d);
    }

    for (int y = 0; y < textMap.getH(); y++) {
      for (int x = 0; x < textMap.getW(); x++) {
        g2d.drawString( //
            Character.toString(textMap.getData()[x][y]), //
            x * TILE_W + 5, y * TILE_H + 15);
      }
    }
  }
}
