package boxpush5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;
import org.cyrano.util.TimedKeyListener;

public class MultiPanel extends JPanel implements Runnable {

  private Map<Character, Sprite> spriteMap = //
  new HashMap<Character, Sprite>();

  private TextMap textMap;

  private Sprite playerSprite;

  private boolean running;

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
    // Init level
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

    sprite = initSprite(/**/20,/* */4, Color.BLUE,/*   */'B', "sprite2.txt");
    sprite = initSprite(/**/12,/**/11, Color.RED,/*    */'C', "sprite3.txt");
    sprite = initSprite(/**/12,/* */4, Color.CYAN,/*   */'A', "sprite1.txt");
    //    sprite = initSprite(/**/11,/* */4,/* */1, 3, Color.YELLOW,/* */'C');
    //    sprite = initSprite(/**/13,/* */4,/* */3, 2, Color.ORANGE,/* */'D');
    //    sprite = initSprite(/**/15,/* */6,/* */1, 1, Color.CYAN,/*   */'E');
    //    sprite = initSprite(/**/10,/* */7,/**/10, 1, Color.RED,/*    */'F');
    //    sprite = initSprite(/**/11,/* */8,/* */3, 3, Color.BLUE,/*   */'G');
    //    sprite = initSprite(/**/10,/**/11,/* */2, 3, Color.YELLOW,/* */'H');
    //    sprite = initSprite(/**/12,/**/11,/* */2, 2, Color.ORANGE,/* */'I');
    //    sprite = initSprite(/**/12,/**/13,/* */3, 1, Color.CYAN,/*   */'J');
    //    sprite = initSprite(/* */9,/**/14,/* */6, 1, Color.BLUE,/*   */'K');
    //    sprite = initSprite(/**/10,/**/15,/* */4, 2, Color.YELLOW,/* */'L');
    //    sprite = initSprite(/* */5,/* */5,/* */3, 3, Color.MAGENTA,/**/'A');

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
      int x, int y, Color color, char mapChar, String playMapName) {

    Sprite sprite = new Sprite();

    sprite.setGrdX(x);
    sprite.setGrdY(y);
    //    sprite.setGrdW(w);
    //    sprite.setGrdH(h);

    sprite.setColor(color);

    sprite.setPlayChar(mapChar);

    sprite.setTextMap(textMap);

    TextMap playMap = new TextMap();

    try {
      playMap.load(getClass().getResource(playMapName).getPath());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    sprite.setPlayMap(playMap);

    sprite.initMap(sprite.getPlayChar(), Character.MAX_VALUE);

    spriteMap.put(sprite.getPlayChar(), sprite);
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
        playerSprite.setDirection(Constants.NO);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void keyPressed(KeyEvent evt) {
    switch (evt.getKeyCode()) {
      case KeyEvent.VK_I :
        playerSprite.setDirection(Constants.UP);
        break;
      case KeyEvent.VK_K :
        playerSprite.setDirection(Constants.DW);
        break;
      case KeyEvent.VK_J :
        playerSprite.setDirection(Constants.LF);
        break;
      case KeyEvent.VK_L :
        playerSprite.setDirection(Constants.RG);
        break;
      default :
        playerSprite.setDirection(Constants.NO);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    long t1 = System.currentTimeMillis();

    int sleep = 20;

    while (running) {
      try {
        Thread.sleep(sleep);

        repaint();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      long t2 = System.currentTimeMillis();

      double dt = (t2 - t1) / 1000.0;

      // to debug
      // double dt = 0.02;

      // ----------------------------------------
      // Update Sprites
      // ----------------------------------------

      playerSprite.updatePos(dt);

      t1 = t2;
    }
  }

  // --------------------------------------------------------------------------------

  protected void initScrollInfo() {
    scrollInfo = new ScrollInfo(//
        Constants.TILE_H * 4, Constants.TILE_H * 4, //
        Constants.TILE_W * 4, Constants.TILE_W * 4, //
        textMap.getW() * Constants.TILE_W, //
        textMap.getH() * Constants.TILE_H, //
        Hwh.getW(this), Hwh.getH(this));
    scrollInfo.updateScrollInfo(playerSprite);
    playerSprite.scrollInfo = scrollInfo;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(Graphics g) {
    if (scrollInfo == null) {
      initScrollInfo();
    }

    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    AffineTransform prevAt = g2d.getTransform();

    AffineTransform at = AffineTransform.getTranslateInstance(-scrollInfo.xView, -scrollInfo.yView);
    g2d.transform(at);

    for (int y = 0; y < textMap.getH(); y++) {
      for (int x = 0; x < textMap.getW(); x++) {
        if (textMap.getData()[x][y] == 'X') {
          g2d.setColor(Color.GRAY);
          g2d.fillRect(x * Constants.TILE_W, y * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);

          g2d.setColor(Color.RED);
          g2d.drawRect(x * Constants.TILE_W, y * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);
        }
        if (textMap.getData()[x][y] == ' ') {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(x * Constants.TILE_W, y * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);
          g2d.setColor(Color.RED);
          g2d.drawRect(x * Constants.TILE_W, y * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);
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
            x * Constants.TILE_W + 10, y * Constants.TILE_H + 15);
      }
    }

    g2d.setTransform(prevAt);
  }
}
