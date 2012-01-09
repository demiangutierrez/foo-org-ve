package org.cyrano.rubik.view2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import org.cyrano.rubik.model.Axis;
import org.cyrano.rubik.model.Cubie;
import org.cyrano.rubik.model.Facelet;
import org.cyrano.rubik.model.Model;
import org.cyrano.rubik.model.Turn;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      KeyListener {

  private Model model;

  // --------------------------------------------------------------------------------

  public Canvas(Model model) {
    addKeyListener(this);
    setFocusable(true);

    this.model = model;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // --------------------------------------------------------------------------------

  public static final int FACE_BLOCK = 44;
  public static final int FACE_WIDTH = 48;

  //  public static final int SIZE = 3;

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    //    +--+
    //    |tp|
    // +--+--+--+
    // |lf|fr|rg|
    // +--+--+--+
    //    |bt|
    //    +--+
    //    |bk|
    //    +--+

    int offX;
    int offY;

    // TP
    offX = 1 * model.getN() * FACE_WIDTH;
    offY = 0 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.Y_POS, +model.getN() / 2, offX, offY);

    // FR
    offX = 1 * model.getN() * FACE_WIDTH;
    offY = 1 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.Z_POS, +model.getN() / 2, offX, offY);

    // BT
    offX = 1 * model.getN() * FACE_WIDTH;
    offY = 2 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.Y_NEG, -model.getN() / 2, offX, offY);

    // BK
    offX = 1 * model.getN() * FACE_WIDTH;
    offY = 3 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.Z_NEG, -model.getN() / 2, offX, offY);

    // LF
    offX = 0 * model.getN() * FACE_WIDTH;
    offY = 1 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.X_NEG, -model.getN() / 2, offX, offY);

    // RG
    offX = 2 * model.getN() * FACE_WIDTH;
    offY = 1 * model.getN() * FACE_WIDTH;
    drawFace(g2d, Axis.X_POS, +model.getN() / 2, offX, offY);

    //    AffineTransform prev = g2d.getTransform();
    //
    //    g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) / 2);
    //    g2d.scale(1, -1);

    //    g2d.setTransform(prev);
  }

  private void drawFace(Graphics2D g2d, //
      Axis axis, int l, int offX, int offY) {

    int beg = -model.getN() / 2;
    int end = +model.getN() / 2;

    for (int j = beg; j <= end; j++) {
      for (int i = beg; i <= end; i++) {

        if (model.getN() % 2 == 0 && //
            (i == 0 || j == 0 || l == 0)) {
          continue;
        }

        int[] coord = axis.trans(i, j, l);

        Cubie cubie = model.getCube().get( //
            coord[0], coord[1], coord[2]);

        int[] unitV = axis.unitVector();

        for (Facelet f : cubie.faceletList) {
          if (f.normal.x != unitV[0] || //
              f.normal.y != unitV[1] || //
              f.normal.z != unitV[2]) {
            continue;
          }

          g2d.setColor(f.faceletColor.translateColor());

          int row = model.getN() % 2 == 0 && i >= 0 //
          ? i + model.getN() / 2 - 1 //
          : i + model.getN() / 2;

          int col = model.getN() % 2 == 0 && j >= 0 //
          ? j + model.getN() / 2 - 1 //
          : j + model.getN() / 2;

          g2d.fillRect( //
              row * FACE_WIDTH + offX, //
              col * FACE_WIDTH + offY, //
              FACE_BLOCK, FACE_BLOCK);

          g2d.setColor(Color.WHITE);
          g2d.setFont(new Font("dialog", Font.PLAIN, 9));

          // ----------------------------------------
          // FOR DEBUGGING
          // ----------------------------------------
          g2d.drawString( //
              format(i) + "; " + format(j), //
              row * FACE_WIDTH + offX, //
              col * FACE_WIDTH + offY + 10);

          g2d.drawString( //
              /**/format(coord[0]) + "; " + //
                  format(coord[1]) + "; " + //
                  format(coord[2]), //
              row * FACE_WIDTH + offX, //
              col * FACE_WIDTH + offY + 25);
          // ----------------------------------------
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void keyTyped(KeyEvent evt) {
    switch (evt.getKeyChar()) {
      case 'S' :
      case 's' :
        // FR
        model.rotate(Axis.Z_POS, Turn.CW, +1);
        repaint();
        break;
      case 'X' :
      case 'x' :
        // BK
        model.rotate(Axis.Z_NEG, Turn.CW, -1);
        repaint();
        break;
      case 'W' :
      case 'w' :
        // TP
        model.rotate(Axis.Y_POS, Turn.CW, +1);
        repaint();
        break;
      case 'Z' :
      case 'z' :
        // BT
        model.rotate(Axis.Y_NEG, Turn.CW, -1);
        repaint();
        break;
      case 'A' :
      case 'a' :
        // LF
        model.rotate(Axis.X_NEG, Turn.CW, -1);
        repaint();
        break;
      case 'D' :
      case 'd' :
        // RG
        model.rotate(Axis.X_POS, Turn.CW, +1);
        repaint();
        break;
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void keyPressed(KeyEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void keyReleased(KeyEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private String format(int i) {
    return i >= 0 ? " " + i : Integer.toString(i);
  }
}
