package org.cyrano.pathfollow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PathReader {

  public List<Point> pathList = new ArrayList<Point>();

  private int index = 0;

  // --------------------------------------------------------------------------------

  public PathReader() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    String line;

    Pattern pattern = Pattern.compile(" +");

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("")) {
        continue;
      }

      String[] arrayXY = pattern.split(line);

      if (arrayXY.length != 2) {
        throw new Exception("arrayXY.length != 0 for line: " + line);
      }

      Point p = new Point( //
          Integer.parseInt(arrayXY[0]), Integer.parseInt(arrayXY[1]));
      pathList.add(p);
    }
  }

  // --------------------------------------------------------------------------------

  public Point curr() {
    return pathList.get(index % pathList.size());
  }

  // --------------------------------------------------------------------------------

  public Point next() {
    index++;
    return pathList.get(index % pathList.size());
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics2D g2d) {
    Point pathPrev = null;
    Point pathFrst = null;

    for (Point pathCurr : pathList) {
      if (pathFrst == null) {
        pathFrst = pathCurr;
      }

      if (pathPrev != null) {
        g2d.setColor(Color.WHITE);
        g2d.drawLine(pathPrev.x, pathPrev.y, pathCurr.x, pathCurr.y);
      }

      pathPrev = pathCurr;
    }

    g2d.drawLine(pathPrev.x, pathPrev.y, pathFrst.x, pathFrst.y);
  }
}
