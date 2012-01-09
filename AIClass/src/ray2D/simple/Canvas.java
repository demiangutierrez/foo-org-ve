package ray2D.simple;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private List<Geometry> gList = new ArrayList<Geometry>();

  private List<Line>/* */lList = new ArrayList<Line>();
  private List<Ray>/*  */rList = new ArrayList<Ray>();

  // --------------------------------------------------------------------------------

  private CtrlPoint curr;

  private double dx;
  private double dy;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    Loader loader = new Loader();

    try {
      loader.load( //
          getClass().getResourceAsStream("map.txt"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    lList.addAll(loader.getLineList());
    gList.addAll(loader.getLineList());

    // ----------------------------------------

    rList.add(loader.getRay());
    gList.add(loader.getRay());

    // ----------------------------------------

    trace();
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
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    for (Geometry geometry : gList) {
      geometry.draw(g2d);
    }
  }

  // --------------------------------------------------------------------------------

  public void trace() {
    for (Ray ray : rList) {
      ray.trace(lList);
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseClicked(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mousePressed(MouseEvent evt) {
    for (Geometry geometry : gList) {
      for (CtrlPoint test : geometry.getCtrlPointList()) {
        if (test.contains(evt.getX(), evt.getY())) {
          curr = test;

          dx = curr.x - evt.getX();
          dy = curr.y - evt.getY();

          return;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseReleased(MouseEvent evt) {
    curr = null;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseEntered(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseExited(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseDragged(MouseEvent evt) {
    if (curr == null) {
      return;
    }

    curr.x = evt.getX() + dx;
    curr.y = evt.getY() + dy;

    trace();

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseMoved(MouseEvent evt) {
    // Empty
  }
}
