package org.cyrano.pacman.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.Interaction;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.base.SpriteMatrix;
import org.cyrano.pacman.game.PacManSprite;
import org.cyrano.pacman.map.CharMap;
import org.cyrano.pacman.map.DynaMap;
import org.cyrano.pacman.map.InteMap;
import org.cyrano.util.game.Timer;
import org.cyrano.util.keyboard.InputManager;
import org.cyrano.util.misc.Hwh;
import org.cyrano.util.misc.PointInt;

public class GamePanel extends JPanel implements Runnable {

  // --------------------------------------------------------------------------------
  // The map & sprite 
  // --------------------------------------------------------------------------------

  private LevelExec levelExec;

  private PacManSprite pacSprite;

  // --------------------------------------------------------------------------------
  // Thread control
  // --------------------------------------------------------------------------------

  private boolean running;
  private boolean started;

  // --------------------------------------------------------------------------------
  // Score drawing
  // --------------------------------------------------------------------------------

  private Rectangle2D readyRect;
  private Font readyFont;
  private int readyYAsc;

  private Rectangle2D scoreRect;
  private Font scoreFont;
  private int scoreYAsc;

  private Rectangle2D endRect;
  private Font endFont;
  private int endYAsc;

  private boolean won;
  private boolean die;

  private Timer timer = new Timer();

  // --------------------------------------------------------------------------------

  public GamePanel(int level) throws Exception {

    // ----------------------------------------
    // Init map
    // ----------------------------------------

    CharMap charMap = new CharMap();
    charMap.load(ClassLoader.getSystemResource("level" + level + "/char.txt").getPath());

    DynaMap dynaMap = new DynaMap();
    dynaMap.load(ClassLoader.getSystemResource("level" + level + "/dyna.txt").getPath());

    InteMap inteMap = new InteMap();
    inteMap.load(ClassLoader.getSystemResource("level" + level + "/inte.txt").getPath());

    levelExec = new LevelExec(charMap, dynaMap, inteMap, timer);
    levelExec.init();

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    pacSprite = (PacManSprite) levelExec.getPlaySprite();

    for (BaseSprite baseSprite : levelExec.getSpriteList()) {
      baseSprite.getActionListenerProxy().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
          handleExecNext((BaseSprite) evt.getSource(), evt.getActionCommand());
        }
      });
    }

    setFocusable(true);

    KeyboardFocusManager.getCurrentKeyboardFocusManager(). //
        addKeyEventDispatcher(levelExec.getInputManager());

    //    addKeyListener(new TimedKeyListener() {
    //      @Override
    //      public void keyPressed(KeyEvent e) {
    //        GamePanel.this.keyPressed(e);
    //      }
    //
    //      @Override
    //      public void keyReleased(KeyEvent evt) {
    //        GamePanel.this.keyReleased(evt);
    //      }
    //    });

    // Only to start the thread
//    addKeyListener(new KeyAdapter() {
//      @Override
//      public void keyPressed(KeyEvent e) {
//        GamePanel.this.keyPressed(e);
//      }
//
//      //          @Override
//      //          public void keyReleased(KeyEvent evt) {
//      //            GamePanel.this.keyReleased(evt);
//      //          }
//    });
    
    beg();
  }

  // --------------------------------------------------------------------------------

  private void handleExecNext(BaseSprite source, String command) {

    if (command != null && command.equals("die")) {
      levelExec.getSpriteMatrix().del( //
          source.getGrdCurr().x, source.getGrdCurr().y, source);

      levelExec.getSpriteList().remove(source);

      if (source instanceof PacManSprite) {
        die = true;
      }

      return;
    }

    SpriteMatrix layerArray = levelExec.getSpriteMatrix();

    List<BaseSprite> baseSpriteList = //
    layerArray.get(source.getGrdCurr().x, source.getGrdCurr().y);

    if (baseSpriteList != null) {
      for (BaseSprite currBaseSprite : baseSpriteList) {
        if (currBaseSprite != source) {
          currBaseSprite.execStepOn(source);
        }
      }
    }

    PointInt grdCurr = source.getGrdCurr();
    char[][] data = levelExec.getData();

    Interaction interaction = //
    levelExec.get(source, data[grdCurr.x][grdCurr.y]);

    if (interaction != null) {
      interaction.interact(source, null);
    }

    //      if (sml == levelLoad.getSmlCount() && big == levelLoad.getBigCount()) {
    //        won = true;
    //        end();
    //      }
    //    }
    //
    //    // ----------------------------------------
    //
    //    for (final GhostSprite ghsSprite : ghsSpriteList) {
    //      PointInt pacGrdCurr = pacSprite.getGrdCurr();
    //      PointInt ghsGrdCurr = ghsSprite.getGrdCurr();
    //
    //      if (pacGrdCurr.x == ghsGrdCurr.x && //
    //          pacGrdCurr.y == ghsGrdCurr.y) {
    //
    //        if (ghsSprite.getDestroy() > pacSprite.getDestroy()) {
    //          die = true;
    //          end();
    //        } else {
    //          ghsSprite.setDead(true);
    //
    //          ActionListener actionListener = new ActionListener() {
    //            @Override
    //            public void actionPerformed(ActionEvent evt) {
    //              ghsSprite.setDead(false);
    //            }
    //          };
    //          timer.addTimerBean( //
    //              new TimerBean(actionListener, new ActionEvent(this, 0, null), 10));
    //        }
    //      }
    //    }
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return levelExec.getW() * Constants.TILE_W;
  }

  public int getH() {
    return levelExec.getH() * Constants.TILE_H;
  }

  // --------------------------------------------------------------------------------

  private void beg() {
    running = true;
    started = true;

    Thread th = new Thread(this);
    th.start();
  }

  // --------------------------------------------------------------------------------

  //  private void end() {
  //
  //    // -------------------------------------------------
  //    // Warning: not sync so don't try to end / beg again
  //    // -------------------------------------------------
  //
  //    running = false;
  //  }

  // --------------------------------------------------------------------------------

  private void keyPressed(KeyEvent e) {
    //      switch (e.getKeyCode()) {
    //        case KeyEvent.VK_J : // LF
    //          pacSprite.setWantDir(Constants.DIR_LF);
    //          break;
    //        case KeyEvent.VK_L : // RG
    //          pacSprite.setWantDir(Constants.DIR_RG);
    //          break;
    //        case KeyEvent.VK_I : // UP
    //          pacSprite.setWantDir(Constants.DIR_UP);
    //          break;
    //        case KeyEvent.VK_K : // DW
    //          pacSprite.setWantDir(Constants.DIR_DW);
    //          break;
    //        default :
    //          pacSprite.setWantDir(Constants.DIR_VOID);
    //          break;
    //      }

    if (!running && !won && !die) {
      beg();
    }

    //    Log.log("wantDir = " + Log.dirToString(pacSprite.getWantDir()));
  }

  // --------------------------------------------------------------------------------

  //  private void keyReleased(KeyEvent evt) {
  //    //    pacSprite.setWantDir(Constants.DIR_VOID);
  //    //    Log.log("wantDir = " + Log.dirToString(pacSprite.getWantDir()));
  //  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    long t1 = System.currentTimeMillis();

    int sleep = 20;
    int count = 0;

    while (running) {
      try {
        Thread.sleep(sleep);

        repaint();

        count++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      long t2 = System.currentTimeMillis();

      double dt = (t2 - t1) / 1000.0;

      // ----------------------------------------
      // Update Timer
      // ----------------------------------------

      timer.handleDelta(dt);

      // ----------------------------------------
      // Update Sprites
      // ----------------------------------------

      // XXX: already being updated using dyna list
      // pacSprite.updateSpr(dt);
      // pacSprite.updatePos(dt);

      BaseSprite[] dynaArray = levelExec.getSpriteList().toArray(new BaseSprite[0]);

      for (BaseSprite baseSprite : dynaArray) {
        baseSprite.updateSpr(dt);
        baseSprite.updatePos(dt);
      }

      t1 = t2;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  private AffineTransform centerOrTranslate() {
    int w = Hwh.getW(this);
    int h = Hwh.getH(this) - Constants.SCORE_BAR_H;

    double tx;
    double ty;

    if (w < getW()) {
      tx = -(pacSprite.getScrCurr().x - w / 2);
    } else {
      tx = (w - getW()) / 2;
    }

    if (h < getH()) {
      ty = -(pacSprite.getScrCurr().y - h / 2);
    } else {
      ty = (h - getH()) / 2;
    }

    AffineTransform at = //
    AffineTransform.getTranslateInstance( //
        tx, ty);

    return at;
  }

  // --------------------------------------------------------------------------------

  private AffineTransform center() {
    int w = Hwh.getW(this);
    int h = Hwh.getH(this) - Constants.SCORE_BAR_H;

    double tx = (w - getW()) / 2;
    double ty = (h - getH()) / 2;

    AffineTransform at = //
    AffineTransform.getTranslateInstance( //
        tx, ty);

    return at;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    if (won || die || !started) {
      g2d.setBackground(Color.LIGHT_GRAY);
    } else {
      g2d.setBackground(Color.BLACK);
    }

    g2d.clearRect(0, 0, getWidth(), getHeight());

    // ----------------------------------------------------------------------
    // Be sure to draw in the middle of the screen no matter what w/h we have
    // ----------------------------------------------------------------------

    AffineTransform prev = g2d.getTransform();
    g2d.transform(centerOrTranslate());

    // ----------------------------------------------------------------------

    char[][] data = levelExec.getData();

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (data[i][j] == 'X') {
          g2d.setColor(Color.WHITE);
          g2d.fillRect( //
              i * Constants.TILE_W, j * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);

          g2d.setColor(Color.BLACK);
          g2d.drawRect( //
              i * Constants.TILE_W, j * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);
        }

        if (data[i][j] == '.') {
          g2d.setColor(Color.WHITE);

          double f1 = 0.4;
          double f2 = 1 - 2 * f1;

          g2d.fillOval( //
              (int) (i * Constants.TILE_W + Constants.TILE_W * f1), //
              (int) (j * Constants.TILE_H + Constants.TILE_H * f1), //
              (int) (Constants.TILE_W * f2), //
              (int) (Constants.TILE_H * f2));
        }

        if (data[i][j] == '*') {
          g2d.setColor(Color.WHITE);

          double f1 = 0.3;
          double f2 = 1 - 2 * f1;

          g2d.fillOval( //
              (int) (i * Constants.TILE_W + Constants.TILE_W * f1), //
              (int) (j * Constants.TILE_H + Constants.TILE_H * f1), //
              (int) (Constants.TILE_W * f2), //
              (int) (Constants.TILE_H * f2));
        }
      }
    }

    // ----------------------------------------------------------------------

    for (BaseSprite baseSprite : levelExec.getSpriteList()) {
      if (Constants.DEBUG) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect( //
            baseSprite.getGrdCurr().x * Constants.TILE_W, //
            baseSprite.getGrdCurr().y * Constants.TILE_H, //
            Constants.TILE_W, Constants.TILE_H);
      }

      baseSprite.paint(g2d);
    }

    //    if (!won && !die) {
    //      if (Constants.DEBUG) {
    //        g2d.setColor(Color.DARK_GRAY);
    //        g2d.fillRect( //
    //            pacSprite.getGrdCurr().x * Constants.TILE_W, //
    //            pacSprite.getGrdCurr().y * Constants.TILE_H, //
    //            Constants.TILE_W, Constants.TILE_H);
    //      }
    //
    //      //pacSprite.paint(g2d);
    //    }

    // ----------------------------------------------------------------------

    g2d.setTransform(prev);

    drawScore(g2d);

    g2d.transform(center());

    drawWon(g2d);
    drawDie(g2d);
    drawNotStarted(g2d);

    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void drawScore(Graphics2D g2d) {
    NumberFormat nfScore = NumberFormat.getInstance();
    nfScore.setMinimumIntegerDigits(10);
    nfScore.setGroupingUsed(false);

    String scoreText = "Score: " + nfScore.format(levelExec.getScore()) + //
        /*          */" Small: " + nfScore.format(levelExec.getSml()) + //
        /*          */" Big:   " + nfScore.format(levelExec.getBig());

    if (scoreFont == null) {
      scoreFont = new Font("Monospaced", Font.BOLD, 18);
      FontMetrics fontMetrics = g2d.getFontMetrics(scoreFont);
      scoreYAsc = fontMetrics.getAscent();
      scoreRect = fontMetrics.getStringBounds(scoreText, g2d);
    }

    int w = Hwh.getW(this);
    int h = Hwh.getH(this);

    int scoreX = //
    (w - Hwh.getW(scoreRect)) / 2;

    int scoreY = //
    h - Constants.SCORE_BAR_H + (Constants.SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + scoreYAsc;

    g2d.setColor(Color.BLACK);
    g2d.fillRect( //
        scoreX - 10, h - Constants.SCORE_BAR_H, //
        Hwh.getW(scoreRect) + 20, Constants.SCORE_BAR_H);

    g2d.setColor(Color.WHITE);
    g2d.setFont(scoreFont);

    g2d.drawString(scoreText, scoreX, scoreY);
  }

  // --------------------------------------------------------------------------------

  private void drawWon(Graphics2D g2d) {
    if (!won) {
      return;
    }

    String endText = "You won!!!";

    if (endFont == null) {
      endFont = new Font("Monospaced", Font.BOLD, 46);
      FontMetrics fontMetrics = g2d.getFontMetrics(endFont);
      endYAsc = fontMetrics.getAscent();
      endRect = fontMetrics.getStringBounds(endText, g2d);
    }

    g2d.setColor(Color.RED);
    g2d.setFont(endFont);

    int endX = (getW() - Hwh.getW(endRect)) / 2;
    int endY = (getH() - Constants.SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + endYAsc;

    g2d.drawString(endText, endX, endY);
  }

  // --------------------------------------------------------------------------------

  private void drawDie(Graphics2D g2d) {
    if (!die) {
      return;
    }

    String endText = "You die!!!";

    if (endFont == null) {
      endFont = new Font("Monospaced", Font.BOLD, 46);
      FontMetrics fontMetrics = g2d.getFontMetrics(endFont);
      endYAsc = fontMetrics.getAscent();
      endRect = fontMetrics.getStringBounds(endText, g2d);
    }

    g2d.setColor(Color.RED);
    g2d.setFont(endFont);

    int endX = (getW() - Hwh.getW(endRect)) / 2;
    int endY = (getH() - Constants.SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + endYAsc;

    g2d.drawString(endText, endX, endY);
  }

  // --------------------------------------------------------------------------------

  private void drawNotStarted(Graphics2D g2d) {
    if (started) {
      return;
    }

    String readyText = "Ready!!!";

    if (readyFont == null) {
      readyFont = new Font("Monospaced", Font.BOLD, 46);
      FontMetrics fontMetrics = g2d.getFontMetrics(readyFont);
      readyYAsc = fontMetrics.getAscent();
      readyRect = fontMetrics.getStringBounds(readyText, g2d);
    }

    g2d.setColor(Color.GREEN);
    g2d.setFont(readyFont);

    int readyX = (getW() - Hwh.getW(readyRect)) / 2;
    int readyY = (getH() - Constants.SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + readyYAsc;

    g2d.drawString(readyText, readyX, readyY);
  }
}
