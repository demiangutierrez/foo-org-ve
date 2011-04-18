package org.cyrano.pacman2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Constructor;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.Hwh;

public class GamePanel extends JPanel implements Runnable {

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

  private List<GhostSprite> ghsSpriteList = new ArrayList<GhostSprite>();

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

  // --------------------------------------------------------------------------------

  public GamePanel(String filename) throws Exception {

    // ----------------------------------------
    // Init map
    // ----------------------------------------

    textMap = new TextMap();
    textMap.load(getClass().getResource(filename).getPath());

    // ----------------------------------------
    // Init sprite
    // ----------------------------------------

    pacSprite = new PacManSprite( //
        textMap.getInitialX(), textMap.getInitialY(), Constants.PAC_SPEED, textMap);

    pacSprite.getActionListenerProxy().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        handleExecNext((BaseSprite) evt.getSource());
      }
    });

    for (GhostBean ghsBean : textMap.getGhostBeanList()) {
      Class<? extends GhostSprite> clazz = ghsBean.getClazz();

      Constructor<? extends GhostSprite> constructor = clazz.getConstructor( //
          new Class<?>[]{int.class, int.class, int.class, TextMap.class, BaseSprite.class});

      GhostSprite ghsSprite = constructor.newInstance( //
          new Object[]{ghsBean.getX(), ghsBean.getY(), ghsBean.getSpeed(), textMap, pacSprite});

      ghsSprite.getActionListenerProxy().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
          handleExecNext((BaseSprite) evt.getSource());
        }
      });
      ghsSpriteList.add(ghsSprite);
    }

    setFocusable(true);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        GamePanel.this.keyPressed(e);
      }
    });
  }

  // --------------------------------------------------------------------------------

  private void handleExecNext(BaseSprite source) {
    if (source instanceof PacManSprite) {
      PacManSprite pacManSprite = (PacManSprite) source;

      IntPoint grdCurr = pacManSprite.getGrdCurr();

      char[][] data = textMap.getData();

      switch (data[grdCurr.x][grdCurr.y]) {
        case '.' :
          data[grdCurr.x][grdCurr.y] = ' ';
          score += Constants.SML_SCORE;
          sml++;
          break;
        case '*' :
          data[grdCurr.x][grdCurr.y] = ' ';
          score += Constants.BIG_SCORE;
          big++;
          break;
      }

      if (sml == textMap.getSmlCount() && big == textMap.getBigCount()) {
        won = true;
        end();
      }
    }

    // ----------------------------------------

    for (GhostSprite ghsSprite : ghsSpriteList) {
      IntPoint pacGrdCurr = pacSprite.getGrdCurr();
      IntPoint ghsGrdCurr = ghsSprite.getGrdCurr();

      if (pacGrdCurr.x == ghsGrdCurr.x && //
          pacGrdCurr.y == ghsGrdCurr.y) {
        die = true;
        end();
      }
    }
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return textMap.getW() * Constants.TILE_W;
  }

  public int getH() {
    // return textMap.getH() * Constants.TILE_H + Constants.SCORE_BAR_H;
    return textMap.getH() * Constants.TILE_H;
  }

  // --------------------------------------------------------------------------------

  private void beg() {
    running = true;
    started = true;

    Thread th = new Thread(this);
    th.start();
  }

  // --------------------------------------------------------------------------------

  private void end() {

    // -------------------------------------------------
    // Warning: not sync so don't try to end / beg again
    // -------------------------------------------------

    running = false;
  }

  // --------------------------------------------------------------------------------

  private void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_J : // LF
        pacSprite.setWantDir(Constants.DIR_LF);
        break;
      case KeyEvent.VK_L : // RG
        pacSprite.setWantDir(Constants.DIR_RG);
        break;
      case KeyEvent.VK_I : // UP
        pacSprite.setWantDir(Constants.DIR_UP);
        break;
      case KeyEvent.VK_K : // DW
        pacSprite.setWantDir(Constants.DIR_DW);
        break;
    }

    if (!running && !won && !die) {
      beg();
    }

    Log.log("wantDir = " + Log.dirToString(pacSprite.getWantDir()));
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

      double dt = (t2 - t1) / 1000000000.0;

      pacSprite.updateSpr(dt);
      pacSprite.updatePos(dt);

      for (GhostSprite ghostSprite : ghsSpriteList) {
        ghostSprite.updateSpr(dt);
        ghostSprite.updatePos(dt);
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

    AffineTransform at = AffineTransform.getTranslateInstance( //
        tx, ty);

    return at;
  }

  // --------------------------------------------------------------------------------

  private AffineTransform center() {
    int w = Hwh.getW(this);
    int h = Hwh.getH(this) - Constants.SCORE_BAR_H;

    double tx = (w - getW()) / 2;
    double ty = (h - getH()) / 2;

    AffineTransform at = AffineTransform.getTranslateInstance( //
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

    char[][] data = textMap.getData();

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (data[i][j] == 'X') {
          g2d.setColor(Color.WHITE);
          g2d.fillRect(i * Constants.TILE_W, j * Constants.TILE_H, Constants.TILE_W, Constants.TILE_H);
          g2d.setColor(Color.BLACK);
          g2d.drawRect(i * Constants.TILE_W, j * Constants.TILE_H, Constants.TILE_W, Constants.TILE_H);
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

    // ----------------------------------------

    if (!won && !die) {
      if (Constants.DEBUG) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect( //
            pacSprite.getGrdCurr().x * Constants.TILE_W, //
            pacSprite.getGrdCurr().y * Constants.TILE_H, //
            Constants.TILE_W, Constants.TILE_H);
      }

      pacSprite.paint(g2d);

      for (GhostSprite ghsSprite : ghsSpriteList) {
        if (Constants.DEBUG) {
          g2d.setColor(Color.DARK_GRAY);
          g2d.fillRect( //
              ghsSprite.getGrdCurr().x * Constants.TILE_W, //
              ghsSprite.getGrdCurr().y * Constants.TILE_H, //
              Constants.TILE_W, Constants.TILE_H);
        }

        ghsSprite.paint(g2d);
      }
    }

    // ----------------------------------------

    g2d.setTransform(prev);

    drawScore(g2d);

    g2d.transform(center());

    drawWon(g2d);
    drawDie(g2d);
    drawNotStarted(g2d);

    g2d.setTransform(prev);

  }

  // --------------------------------------------------------------------------------

  protected void drawScore(Graphics2D g2d) {
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

  protected void drawWon(Graphics2D g2d) {
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

  protected void drawDie(Graphics2D g2d) {
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

  protected void drawNotStarted(Graphics2D g2d) {
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
