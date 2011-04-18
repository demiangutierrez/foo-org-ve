package org.cyrano.pacman1.copy;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;

public class FollowPanel extends JPanel implements Runnable {

  private static final boolean DEBUG = false;

  private static final int DIR_LF = 0;
  private static final int DIR_RG = 1;
  private static final int DIR_UP = 2;
  private static final int DIR_DW = 3;

  public static final int TILE_W = 30;
  public static final int TILE_H = 30;

  public static final int SCORE_BAR_H = 50;

  public static final int SML_SCORE = 25;
  public static final int BIG_SCORE = 50;

  // --------------------------------------------------------------------------------
  // Score counters 
  // --------------------------------------------------------------------------------

  public int score;
  public int sml;
  public int big;

  // --------------------------------------------------------------------------------
  // The map & sprite 
  // --------------------------------------------------------------------------------

  private TextMap textMap;

  private Sprite sprite;

  // --------------------------------------------------------------------------------
  // Where am I and where do I go to
  // --------------------------------------------------------------------------------

  private int currDir = DIR_RG;
  private int wantDir = DIR_RG;

  private int currX = 1;
  private int currY = 2;

  private Point next;

  private double speed = 150;

  // --------------------------------------------------------------------------------
  // Thread control
  // --------------------------------------------------------------------------------

  private boolean running;
  private boolean started;

  // --------------------------------------------------------------------------------
  // Score drawing
  // --------------------------------------------------------------------------------

  private Font scoreFont;

  private Rectangle2D scoreRect;

  private int scoreYAsc;

  private boolean won;

  private Font wonFont;

  private int wonYAsc;

  private Rectangle2D wonRect;

  private Font readyFont;

  private int readyYAsc;

  private Rectangle2D readyRect;

  // --------------------------------------------------------------------------------

  public FollowPanel(String filename) throws Exception {

    // ----------------------------------------
    // Init map
    // ----------------------------------------

    textMap = new TextMap();
    textMap.load(getClass().getResource(filename).getPath());

    currX = textMap.getInitialX();
    currY = textMap.getInitialY();

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    sprite = new Sprite();

    sprite.setX(currX * TILE_W);
    sprite.setY(currY * TILE_H);

    // XXX: Clean up, see keyPressed...
    next = calculateNext(false);
    sprite.setGoingto(next);

    setFocusable(true);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        FollowPanel.this.keyPressed(e);
      }
    });
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return textMap.getW() * TILE_W;
  }

  public int getH() {
    return textMap.getH() * TILE_H + SCORE_BAR_H;
  }

  // --------------------------------------------------------------------------------

  private void beg() {
    running = true;
    started = true;

    Thread th = new Thread(this);
    th.start();
  }

  // --------------------------------------------------------------------------------

  public void end() {

    // -------------------------------------------------
    // Warning: not sync so don't try to end / beg again
    // -------------------------------------------------

    running = false;
  }

  // --------------------------------------------------------------------------------

  private String dirToString(int dir) {
    switch (dir) {
      case DIR_LF :
        return "DIR_LF";
      case DIR_RG :
        return "DIR_RG";
      case DIR_UP :
        return "DIR_UP";
      case DIR_DW :
        return "DIR_DW";
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  protected void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_J : // LF
        wantDir = DIR_LF;
        break;
      case KeyEvent.VK_L : // RG
        wantDir = DIR_RG;
        break;
      case KeyEvent.VK_I : // UP
        wantDir = DIR_UP;
        break;
      case KeyEvent.VK_K : // DW
        wantDir = DIR_DW;
        break;
    }

    // Start at the first user keystroke
    if (!running && !won) {
      // XXX: Clean up, remove in the constructor
      currDir = wantDir;
      next = calculateNext(false);
      sprite.setGoingto(next);
      beg();
    }

    log("wantDir = " + dirToString(wantDir));
  }

  // --------------------------------------------------------------------------------

  @Override
  public void run() {
    long t1 = System.nanoTime();

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

      long t2 = System.nanoTime();

      // Time in seconds
      double dt = (t2 - t1) / 1000000000.0; // We should use int/long for performance

      sprite.update(dt);

      if (next == null) {
        next = calculateNext(false);
        sprite.setGoingto(calculateGoingto());
        t1 = t2; // And go again...
        continue;
      }

      while (dt > 0 && next != null) {
        // Dist to go in this loop
        double dst = speed * dt;

        // Dist to target
        double x = sprite.getX();
        double y = sprite.getY();

        double dstTgt = Math.sqrt((x - next.x) * (x - next.x) + (y - next.y) * (y - next.y));

        double xTh1 = next.x - x;
        double yTh1 = next.y - y;

        if (dstTgt < dst) {
          // We get first to the target
          dt -= dstTgt / speed;

          x = next.x;
          y = next.y;

          next = calculateNext(true);
          sprite.setGoingto(calculateGoingto());
        } else {
          double dx = (next.x - x) / dstTgt;
          double dy = (next.y - y) / dstTgt;

          x += speed * dx * dt;
          y += speed * dy * dt;

          dt = 0;

          double xTh2 = next.x - x;
          double yTh2 = next.y - y;

          if (Math.signum(xTh1) != Math.signum(xTh2) || Math.signum(yTh1) != Math.signum(yTh2)) {
            x = next.x;
            y = next.y;

            next = calculateNext(true);
            sprite.setGoingto(calculateGoingto());
          }

          t1 = t2; // And go again...
        }

        sprite.setX(x);
        sprite.setY(y);
      }
    }
  }

  // --------------------------------------------------------------------------------

  private Point calculateNext(boolean increment) {
    log("begin - calculateNext ---------------------------------------->");

    log("calculateNext / bef currDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);

    if (increment) {
      switch (currDir) {
        case DIR_LF :
          currX -= 1;
          break;
        case DIR_RG :
          currX += 1;
          break;
        case DIR_UP :
          currY -= 1;
          break;
        case DIR_DW :
          currY += 1;
          break;
      }

      handlePosChange();
    }

    log("calculateNext / aft currDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);

    // ----------------------------------------

    Point next = null;

    char[][] data = textMap.getData();

    switch (wantDir) {
      case DIR_LF :
        if (data[currX - 1][currY] != 'X') {
          next = new Point((currX - 1) * TILE_W, currY * TILE_H);
        }
        break;
      case DIR_RG :
        if (data[currX + 1][currY] != 'X') {
          next = new Point((currX + 1) * TILE_W, currY * TILE_H);
        }
        break;
      case DIR_UP :
        if (data[currX][currY - 1] != 'X') {
          next = new Point(currX * TILE_W, (currY - 1) * TILE_H);
        }
        break;
      case DIR_DW :
        if (data[currX][currY + 1] != 'X') {
          next = new Point(currX * TILE_W, (currY + 1) * TILE_H);
        }
        break;
    }

    log("calculateNext / aft wantDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);
    log("calculateNext / aft wantDir - " + //
        "next: " + next);

    if (next != null) {
      currDir = wantDir;
      return next;
    }

    // ----------------------------------------

    switch (currDir) {
      case DIR_LF :
        if (data[currX - 1][currY] != 'X') {
          next = new Point((currX - 1) * TILE_W, currY * TILE_H);
        }
        break;
      case DIR_RG :
        if (data[currX + 1][currY] != 'X') {
          next = new Point((currX + 1) * TILE_W, currY * TILE_H);
        }
        break;
      case DIR_UP :
        if (data[currX][currY - 1] != 'X') {
          next = new Point(currX * TILE_W, (currY - 1) * TILE_H);
        }
        break;
      case DIR_DW :
        if (data[currX][currY + 1] != 'X') {
          next = new Point(currX * TILE_W, (currY + 1) * TILE_H);
        }
        break;
    }

    log("calculateNext / aft currDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);
    log("calculateNext / aft currDir - " + //
        "next: " + next);

    return next;
  }

  // --------------------------------------------------------------------------------

  private void handlePosChange() {
    char[][] data = textMap.getData();

    switch (data[currX][currY]) {
      case '.' :
        data[currX][currY] = ' ';
        score += SML_SCORE;
        sml++;
        break;
      case '*' :
        data[currX][currY] = ' ';
        score += BIG_SCORE;
        big++;
        break;
    }

    if (sml == textMap.getSmlCount() && big == textMap.getBigCount()) {
      won = true;
      end();
    }
  }

  // --------------------------------------------------------------------------------

  private Point calculateGoingto() {
    log("begin - calculateGoingto ---------------------------------------->");

    log("calculateGoingto / bef currDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);

    Point next = null;

    switch (currDir) {
      case DIR_LF :
        next = new Point((currX - 1) * TILE_W, currY * TILE_H);
        break;
      case DIR_RG :
        next = new Point((currX + 1) * TILE_W, currY * TILE_H);
        break;
      case DIR_UP :
        next = new Point(currX * TILE_W, (currY - 1) * TILE_H);
        break;
      case DIR_DW :
        next = new Point(currX * TILE_W, (currY + 1) * TILE_H);
        break;
    }

    log("calculateGoingto / aft currDir - " + //
        "currDir: " + currDir + "; wantDir:" + wantDir + "; currX: " + currX + "; currY: " + currY);
    log("calculateGoingto / aft currDir - " + //
        "next: " + next);

    return next;
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

    if (won || !started) {
      g2d.setBackground(Color.LIGHT_GRAY);
    } else {
      g2d.setBackground(Color.BLACK);
    }

    g2d.clearRect(0, 0, getWidth(), getHeight());

    // ----------------------------------------------------------------------
    // Be sure to draw in the middle of the screen no matter what w/h we have
    // ----------------------------------------------------------------------

    int w = Hwh.getW(this);
    int h = Hwh.getH(this);

    AffineTransform prev = g2d.getTransform();

    AffineTransform at = AffineTransform.getTranslateInstance( //
        (w - getW()) / 2, (h - getH()) / 2);
    g2d.transform(at);

    // ----------------------------------------------------------------------

    char[][] data = textMap.getData();

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (data[i][j] == 'X') {
          g2d.setColor(Color.WHITE);
          g2d.fillRect(i * TILE_W, j * TILE_H, TILE_W, TILE_H);
          g2d.setColor(Color.BLACK);
          g2d.drawRect(i * TILE_W, j * TILE_H, TILE_W, TILE_H);
        }

        if (data[i][j] == '.') {
          g2d.setColor(Color.WHITE);

          double f1 = 0.4;
          double f2 = 1 - 2 * f1;

          g2d.fillOval( //
              (int) (i * TILE_W + TILE_W * f1), //
              (int) (j * TILE_H + TILE_H * f1), //
              (int) (TILE_W * f2), //
              (int) (TILE_H * f2));
        }

        if (data[i][j] == '*') {
          g2d.setColor(Color.WHITE);

          double f1 = 0.3;
          double f2 = 1 - 2 * f1;

          g2d.fillOval( //
              (int) (i * TILE_W + TILE_W * f1), //
              (int) (j * TILE_H + TILE_H * f1), //
              (int) (TILE_W * f2), //
              (int) (TILE_H * f2));
        }
      }
    }

    if (DEBUG) {
      g2d.setColor(Color.DARK_GRAY);
      g2d.fillRect(currX * TILE_W, currY * TILE_H, TILE_W, TILE_H);
    }

    if (!won) {
      sprite.paint(g2d);
    }

    // ----------------------------------------

    NumberFormat nfScore = NumberFormat.getInstance();
    nfScore.setMinimumIntegerDigits(10);
    nfScore.setGroupingUsed(false);

    String scoreText = "Score: " + nfScore.format(score) + //
        /*          */" Small: " + nfScore.format(sml) + //
        /*          */" Big:   " + nfScore.format(big);

    if (scoreFont == null) {
      scoreFont = new Font("Monospaced", Font.BOLD, 18);
      FontMetrics fontMetrics = g2d.getFontMetrics(scoreFont);
      scoreYAsc = fontMetrics.getAscent();
      scoreRect = fontMetrics.getStringBounds(scoreText, g2d);
    }

    g2d.setColor(Color.WHITE);
    g2d.setFont(scoreFont);

    int scoreX = (getW() - Hwh.getW(scoreRect)) / 2;
    int scoreY = getH() - SCORE_BAR_H + (SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + scoreYAsc;

    g2d.drawString(scoreText, scoreX, scoreY);

    if (won) {
      String wonText = "You won the game!!!";

      if (wonFont == null) {
        wonFont = new Font("Monospaced", Font.BOLD, 46);
        FontMetrics fontMetrics = g2d.getFontMetrics(wonFont);
        wonYAsc = fontMetrics.getAscent();
        wonRect = fontMetrics.getStringBounds(wonText, g2d);
      }

      g2d.setColor(Color.RED);
      g2d.setFont(wonFont);

      int wonX = (getW() - Hwh.getW(wonRect)) / 2;
      int wonY = (getH() - SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + wonYAsc;

      g2d.drawString(wonText, wonX, wonY);
    }

    if (!started) {
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
      int readyY = (getH() - SCORE_BAR_H - Hwh.getH(scoreRect)) / 2 + readyYAsc;

      g2d.drawString(readyText, readyX, readyY);
    }

    // Restore prev transform
    g2d.setTransform(prev);
  }

  // --------------------------------------------------------------------------------

  private void log(String str) {
    if (DEBUG) {
      System.err.println(str);
    }
  }
}
