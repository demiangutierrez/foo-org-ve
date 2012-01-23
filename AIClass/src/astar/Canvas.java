package astar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.cyrano.util.geometry.PointInt;
import org.cyrano.util.misc.Hwh;

public class Canvas extends JPanel //
    implements
      MouseListener,
      MouseMotionListener {

  private Graph graph;

  private PointInt beg;
  private PointInt end;

  // --------------------------------------------------------------------------------

  public Canvas() {
    addMouseListener(this);
    addMouseMotionListener(this);

    try {
      BufferedImage bimg = ImageIO.read(new File("maze3.bmp"));

      graph = new Graph(bimg);
    } catch (IOException e) {
      throw new RuntimeException(e);
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

    g2d.setBackground(Color.LIGHT_GRAY);
    g2d.clearRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    AffineTransform prev = g2d.getTransform();

    g2d.translate(Hwh.getW(this) / 2, Hwh.getH(this) / 2);
    // g2d.scale(1, -1);

    BufferedImage bimg = graph.getBimg();

    g2d.drawImage(bimg, -Hwh.getW(bimg) / 2, -Hwh.getH(bimg) / 2, null);

    if (search != null) {
      for (Node node : search.getClosed()) {
        g2d.setColor(Color.GREEN);

        g2d.drawLine(node.x, node.y, node.x, node.y);
      }

      for (Node node : search.getBorder()) {
        g2d.setColor(Color.MAGENTA);

        g2d.drawLine(node.x, node.y, node.x, node.y);
      }

      System.err.println("search.getPath().size(): " + search.getPath().size());

      for (Node node : search.getPath()) {
        g2d.setColor(Color.ORANGE);

        g2d.drawLine(node.x, node.y, node.x, node.y);
      }
    }

    if (beg != null) {
      g2d.setColor(Color.RED);
      g2d.drawLine(beg.x - 5, beg.y - 5, beg.x + 5, beg.y + 5);
      g2d.drawLine(beg.x - 5, beg.y + 5, beg.x + 5, beg.y - 5);
    }

    if (end != null) {
      g2d.setColor(Color.BLUE);
      g2d.drawLine(end.x - 5, end.y - 5, end.x + 5, end.y + 5);
      g2d.drawLine(end.x - 5, end.y + 5, end.x + 5, end.y - 5);
    }

    g2d.setTransform(prev);
    
    g2d.setColor(Color.BLACK);
    g2d.drawString("x = " + cx, 10, 30);
    g2d.drawString("y = " + cy, 10, 60);
    g2d.drawString("D = " + Search.D, 10, 90);
  }

  // --------------------------------------------------------------------------------

  private Search search;

  @Override
  public void mouseClicked(MouseEvent evt) {
    int x = +evt.getPoint().x - Hwh.getW(this) / 2;
    int y = +evt.getPoint().y - Hwh.getH(this) / 2;

    if (!graph.validSelection(x, y) && //
        (beg == null || end == null)) {
      return;
    }

    /*   */if (beg == null) {
      beg = new PointInt();
      beg.x = 87;
      beg.y = 97;
      beg.x = x;
      beg.y = y;
    } else if (end == null) {
      end = new PointInt();
      end.x = 155;
      end.y = 145;
      end.x = x;
      end.y = y;

      search = new Search(graph, beg, end);
      search.search();
      graph.clearCache();
    } else {
      beg = null;
      end = null;

      search = null;
    }

    repaint();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mousePressed(MouseEvent evt) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void mouseReleased(MouseEvent evt) {
    // Empty
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
    // Empty
  }

  // --------------------------------------------------------------------------------

  int cx;
  int cy;
  
  @Override
  public void mouseMoved(MouseEvent evt) {
    cx = +evt.getPoint().x - Hwh.getW(this) / 2;
    cy = +evt.getPoint().y - Hwh.getH(this) / 2;
    repaint();
  }
}
