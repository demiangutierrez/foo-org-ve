package org.cyrano.pacman.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class CharMap {

  private char[][] data;

  private int w;
  private int h;

  // --------------------------------------------------------------------------------

  public CharMap() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    String line = rd.readLine();

    Pattern p = Pattern.compile(" +");

    String[] arrayWH = p.split(line);

    if (arrayWH.length != 2) {
      throw new IllegalArgumentException("arrayWH.length != 2");
    }

    try {
      w = Integer.parseInt(arrayWH[0]);
      h = Integer.parseInt(arrayWH[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(line);
    }

    data = new char[w][h];

    for (int j = 0; j < h; j++) {
      line = rd.readLine();

      for (int i = 0; i < w; i++) {

        if (line == null || line.trim().equals("")) {
          throw new IllegalArgumentException("empty line at " + j);
        }

        data[i][j] = line.charAt(i);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public char[][] getData() {
    return data;
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }
}
