package boxpush4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.cyrano.util.TimedKeyListener;

public class MultiPanel extends JPanel implements Runnable {

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

  //  private int xView = 0;
  //  private int yView = 0;
  //
  //  private int X_MIN_PORT = 0;
  //  private int X_MAX_PORT = 0;
  //
  //  private int Y_MIN_PORT = 0;
  //  private int Y_MAX_PORT = 0;
  //
  //  private int X_MIN_VIEW = 5;
  //  private int X_MAX_VIEW = 7;
  //
  //  private int Y_MIN_VIEW = 5;
  //  private int Y_MAX_VIEW = 6;
  private ScrollInfo scrollInfo;

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

    scrollInfo = new ScrollInfo(textMap);
    //    X_MAX_PORT = textMap.getW() - MultiMain.SCREEN_W_IN_TILES;
    //    Y_MAX_PORT = textMap.getH() - MultiMain.SCREEN_H_IN_TILES + 1;

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    Sprite sprite;

    sprite = initSprite(/**/12,/* */4,/* */1, 3, Color.BLUE,/*   */'B');
    sprite = initSprite(/**/11,/* */4,/* */1, 3, Color.YELLOW,/* */'C');
    sprite = initSprite(/**/13,/* */4,/* */3, 2, Color.ORANGE,/* */'D');
    sprite = initSprite(/**/15,/* */6,/* */1, 1, Color.CYAN,/*   */'E');
    sprite = initSprite(/**/10,/* */7,/**/10, 1, Color.RED,/*    */'F');
    sprite = initSprite(/**/11,/* */8,/* */3, 3, Color.BLUE,/*   */'G');
    sprite = initSprite(/**/10,/**/11,/* */2, 3, Color.YELLOW,/* */'H');
    sprite = initSprite(/**/12,/**/11,/* */2, 2, Color.ORANGE,/* */'I');
    sprite = initSprite(/**/12,/**/13,/* */3, 1, Color.CYAN,/*   */'J');
    sprite = initSprite(/* */9,/**/14,/* */6, 1, Color.BLUE,/*   */'K');
    sprite = initSprite(/**/10,/**/15,/* */4, 2, Color.YELLOW,/* */'L');
    sprite = initSprite(/* */5,/* */5,/* */3, 3, Color.MAGENTA,/**/'A');

    playerSprite = sprite;
    playerSprite.scrollInfo = scrollInfo;

      scrollInfo.updateScrollInfo(playerSprite.scrCurr);

    
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

    Sprite sprite = new Sprite();
    sprite.setW(w);
    sprite.setH(h);
    sprite.setColor(color);

    sprite.setX(x);
    sprite.setY(y);

    sprite.setMapChar(mapChar);
    sprite.setTextMap(textMap);
    sprite.initMap(sprite.getMapChar(), Character.MAX_VALUE);

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

      playerSprite.updatePos(dt);

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

    AffineTransform prevAt = g2d.getTransform();

    AffineTransform at = AffineTransform.getTranslateInstance(-scrollInfo.xView, -scrollInfo.yView);
    g2d.transform(at);

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
            x * TILE_W + 10, y * TILE_H + 15);
      }
    }

    g2d.setTransform(prevAt);
  }
}
