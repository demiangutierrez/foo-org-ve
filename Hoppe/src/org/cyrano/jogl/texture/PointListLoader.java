package org.cyrano.jogl.texture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PointListLoader {

  private List<double[]> pointList;

  private double[] max;
  private double[] min;

  // --------------------------------------------------------------------------------

  public PointListLoader() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    pointList = new ArrayList<double[]>();
    max = new double[]{-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
    min = new double[]{+Double.MAX_VALUE, +Double.MAX_VALUE, +Double.MAX_VALUE};

    Pattern pattern = Pattern.compile(" +");

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.matches("#(.)*|d(.)*|s(.)*|g(.)*")) {
        continue;
      }

      if (!line.startsWith("p")) {
        System.err.println("WARNING: " + line);
      }

      String[] data = pattern.split(line);

      if (data.length != 4) {
        throw new ParseException(line, -1);
      }

      double[] point = new double[3];

      point[0] = Double.parseDouble(data[1]);
      point[1] = Double.parseDouble(data[2]);
      point[2] = Double.parseDouble(data[3]);

      max[0] = Math.max(point[0], max[0]);
      max[1] = Math.max(point[1], max[1]);
      max[2] = Math.max(point[2], max[2]);

      min[0] = Math.min(point[0], min[0]);
      min[1] = Math.min(point[1], min[1]);
      min[2] = Math.min(point[2], min[2]);

      pointList.add(point);
    }

    rd.close();
  }

  // --------------------------------------------------------------------------------

  public void normalize() {
    double[] mid = new double[3];

    mid[0] = (min[0] + max[0]) / 2;
    mid[1] = (min[1] + max[1]) / 2;
    mid[2] = (min[2] + max[2]) / 2;

    for (double[] point : pointList) {
      point[0] -= mid[0];
      point[1] -= mid[1];
      point[2] -= mid[2];
    }
  }

  // --------------------------------------------------------------------------------

  public List<double[]> getPointList() {
    return pointList;
  }
}
