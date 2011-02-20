package scroll;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.cyrano.util.Hwh;

public class ScrollPanel extends JPanel implements MouseListener, MouseMotionListener {

  private static final int INS_T = 100;
  private static final int INS_B = 100;
  private static final int INS_L = 100;
  private static final int INS_R = 100;

  // --------------------------------------------------------------------------------

  private BufferedImage bimg;

  private Box box;

  private int dx;
  private int dy;

  private boolean drag;

  private int xView;
  private int yView;

  private int maxXView; // The max w of the real screen to display
  private int maxYView; // The max h of the real screen to display

  // --------------------------------------------------------------------------------

  public ScrollPanel() {
    try {
      bimg = ImageIO.read(getClass().getResource("sml_picture.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    maxXView = Hwh.getW(bimg);
    maxYView = Hwh.getH(bimg);

    addMouseListener(this);
    addMouseMotionListener(this);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        calculateScroll();
      }
    });

    box = new Box();

    box.x = 200;
    box.y = 200;
    box.w = 200;
    box.h = 200;
  }

  // --------------------------------------------------------------------------------

  private void calculateScroll() {
    if (box.x < (xView + INS_L)) {
      xView = box.x - INS_L;
    }

    if (xView < 0) {
      xView = 0;
    }

    // ----------------------------------------

    if (box.y < (yView + INS_T)) {
      yView = box.y - INS_T;
    }

    if (yView < 0) {
      yView = 0;
    }

    // ----------------------------------------

    if (box.maxX() > (xView + Hwh.getW(this) - INS_R)) {
      xView = box.maxX() - Hwh.getW(this) + INS_R;
    }

    if (xView > (maxXView - Hwh.getW(this))) {
      xView = maxXView - Hwh.getW(this);
    }

    // ----------------------------------------

    if (box.maxY() > (yView + Hwh.getH(this) - INS_B)) {
      yView = box.maxY() - Hwh.getH(this) + INS_B;
    }

    if (yView > (maxYView - Hwh.getH(this))) {
      yView = maxYView - Hwh.getH(this);
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

    AffineTransform prev = g2d.getTransform();
    g2d.transform(AffineTransform.getTranslateInstance(-xView, -yView));

    g2d.drawImage(bimg, 0, 0, null);

    g2d.setStroke(new BasicStroke(2));
    g2d.setColor(Color.BLUE);
    g2d.drawRect( //
        box.x, //
        box.y, //
        box.w, //
        box.h);

    g2d.setTransform(prev);

    g2d.setStroke(new BasicStroke(2));
    g2d.setColor(Color.RED);
    g2d.drawRect( //
        INS_L, //
        INS_T, //
        Hwh.getW(this) - INS_L - INS_R, //
        Hwh.getH(this) - INS_T - INS_B);
  }

  // --------------------------------------------------------------------------------
  // MouseListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {

  }

  @Override
  public void mousePressed(MouseEvent evt) {
    Rectangle r = new Rectangle(-xView + box.x, -yView + box.y, box.w, box.h);

    if (r.contains(evt.getPoint())) {
      dx = box.x - evt.getPoint().x;
      dy = box.y - evt.getPoint().y;
      drag = true;
    }
  }

  @Override
  public void mouseReleased(MouseEvent evt) {
    drag = false;
    calculateScroll();
    repaint();
  }

  @Override
  public void mouseEntered(MouseEvent evt) {

  }

  @Override
  public void mouseExited(MouseEvent evt) {

  }

  // --------------------------------------------------------------------------------
  // MouseMotionListener
  // --------------------------------------------------------------------------------

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (drag) {
      box.x = evt.getPoint().x + dx;
      box.y = evt.getPoint().y + dy;
      calculateScroll();
      repaint();
    }
  }

  @Override
  public void mouseMoved(MouseEvent evt) {

  }
}
