package org.cyrano.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.cyrano.graph.Node;
import org.cyrano.util.Hwh;

public class Player implements Sprite {

  private static final double PIXEL_PLAYER_SPEED = 100;
  private static final double PIXEL_SPRITE_SPEED = 10;

  // --------------------------------------------------------------------------------

  private enum POS {
    NEG, EQL, POS;

    public static POS test(double n0, double n1) {
      POS ret = null;

      if (n0 == n1) {
        ret = EQL;
      } else if (n0 < n1) {
        ret = NEG;
      } else if (n0 > n1) {
        ret = POS;
      }

      return ret;
    }
  }

  // --------------------------------------------------------------------------------

  private BufferedImage[] imgNX;
  private BufferedImage[] imgNE;
  private BufferedImage[] imgNW;
  private BufferedImage[] imgSX;
  private BufferedImage[] imgSE;
  private BufferedImage[] imgSW;
  private BufferedImage[] imgEX;
  private BufferedImage[] imgWX;

  private BufferedImage[] imgCC;

  private BufferedImage[][] imgArray;

  private BufferedImage img;

  // --------------------------------------------------------------------------------

  private List<Node> currPath = new ArrayList<Node>();

  // --------------------------------------------------------------------------------

  private Point coords = new Point();

  public double currSprite;

  private Node currNode;

  // --------------------------------------------------------------------------------

  public Player() {
    loadSprites();
  }

  // --------------------------------------------------------------------------------

  private void calculateImgArray(Node src, Node tgt) {
    POS xpos = POS.test( //
        src.getCoords().x, tgt.getCoords().x);
    POS ypos = POS.test( //
        src.getCoords().y, tgt.getCoords().y);

    if (false) {
      // Dummy
    } else if (xpos == POS.NEG && ypos == POS.NEG) {
      imgCC = imgSE;
    } else if (xpos == POS.NEG && ypos == POS.EQL) {
      imgCC = imgEX;
    } else if (xpos == POS.NEG && ypos == POS.POS) {
      imgCC = imgNE;
    } else if (xpos == POS.EQL && ypos == POS.NEG) {
      imgCC = imgSX;
    } else if (xpos == POS.EQL && ypos == POS.EQL) {
      imgCC = imgSX;
    } else if (xpos == POS.EQL && ypos == POS.POS) {
      imgCC = imgNX;
    } else if (xpos == POS.POS && ypos == POS.NEG) {
      imgCC = imgSW;
    } else if (xpos == POS.POS && ypos == POS.EQL) {
      imgCC = imgWX;
    } else if (xpos == POS.POS && ypos == POS.POS) {
      imgCC = imgNW;
    }
  }

  // --------------------------------------------------------------------------------

  private BufferedImage[] loadSprites(String prefix, int indexSize) {
    BufferedImage[] ret = new BufferedImage[indexSize];

    for (int i = 0; i < indexSize; i++) {
      try {
        ret[i] = ImageIO.read(new File(prefix + i + ".png"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  private void loadSprites() {
    imgNX = loadSprites("walking nx000", 8);
    imgNE = loadSprites("walking ne000", 8);
    imgNW = loadSprites("walking nw000", 8);
    imgSX = loadSprites("walking sx000", 8);
    imgSE = loadSprites("walking se000", 8);
    imgSW = loadSprites("walking sw000", 8);
    imgEX = loadSprites("walking ex000", 8);
    imgWX = loadSprites("walking wx000", 8);

    imgArray = new BufferedImage[][]{ //
    imgNX, imgNE, imgEX, imgSE, imgSX, imgSW, imgWX, imgNW};

    imgCC = imgSX;
    img = imgCC[0];
  }

  // --------------------------------------------------------------------------------

  private double walkEdge(double reqdist, Node tgt) {
    double curdist = coords.distance(tgt.getCoords());

    if (reqdist - curdist > 0) {
      coords.x = tgt.getCoords().x;
      coords.y = tgt.getCoords().y;
    } else {
      double xStep = 0;
      double yStep = 0;

      if (coords.x != tgt.getCoords().x && //
          coords.y != tgt.getCoords().y) {

        double side = reqdist / 1.41421356237309504880; // sqrt(2)

        yStep = coords.y < tgt.getCoords().y ? side : -side;
        xStep = coords.x < tgt.getCoords().x ? side : -side;
      } else if (coords.x == tgt.getCoords().x) {
        yStep = coords.y < tgt.getCoords().y ? reqdist : -reqdist;
      } else if (coords.y == tgt.getCoords().y) {
        xStep = coords.x < tgt.getCoords().x ? reqdist : -reqdist;
      }

      coords.x += xStep;
      coords.y += yStep;
    }

    return curdist < reqdist ? reqdist - curdist : 0;
  }

  // --------------------------------------------------------------------------------

  public void animate(long t1, long t2) {
    synchronized (currPath) {
      if (currPath.isEmpty()) {
        return;
      }

      double timedff = (t2 - t1) / 1000.0;
      double reqdist = timedff * PIXEL_PLAYER_SPEED;

      currSprite += reqdist / PIXEL_SPRITE_SPEED;
      img = imgCC[((int) currSprite) % 8];

      while (reqdist > 0 && !currPath.isEmpty()) {
        reqdist = walkEdge(reqdist, currPath.get(1));

        if (reqdist > 0) {
          currPath.remove(0);
          currNode = currPath.get(0);
        }

        if (currPath.size() == 1) {
          currPath.clear();
        } else {
          calculateImgArray(currPath.get(0), currPath.get(1));
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public List<Node> getWalkPath() {
    return currPath;
  }

  public void setWalkPath(List<Node> walkPath) {
    synchronized (currPath) {
      if (!currPath.isEmpty()) {
        Node currSrc = currPath.get(0);
        Node nextTgt = walkPath.get(1);

        currPath.clear();

        if (nextTgt != currSrc) {
          currPath.add(currSrc);
        }
      }

      currPath.addAll(walkPath);

      calculateImgArray( //
          currPath.get(0), currPath.get(1));
    }
  }

  // --------------------------------------------------------------------------------

  public Node getCurrNode() {
    synchronized (currPath) {
      if (!currPath.isEmpty()) {
        return currPath.get(1);
      } else {
        return currNode;
      }
    }
  }

  public void setCurrNode(Node currNode) {
    this.currNode = currNode;
  }

  // --------------------------------------------------------------------------------

  public Point getCoords() {
    return coords;
  }

  public void setCoords(Point coords) {
    this.coords = coords;
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    g2d.drawImage(img, //
        coords.x - Hwh.getW(img) / 2, //
        coords.y - Hwh.getH(img) / 2, //
        null);
  }

  // --------------------------------------------------------------------------------

  public void rotate(int delta) {
    for (int i = 0; i < imgArray.length; i++) {
      if (imgArray[i] == imgCC) {
        imgCC = imgArray[(i + delta) % 8];
        img = imgCC[0];
        break;
      }
    }
  }
}
