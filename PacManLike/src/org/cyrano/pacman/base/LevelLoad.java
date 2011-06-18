package org.cyrano.pacman.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LevelLoad {

  private List<BaseBean> baseBeanList = //
  new ArrayList<BaseBean>();

  private char/**/[][] data;

  private int w;
  private int h;

  // --------------------------------------------------------------------------------

  public LevelLoad() {
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

    // ----------------------------------------
    // Load layer 0
    // ----------------------------------------

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

    // ----------------------------------------
    // Load dyna/stat Layers
    // ----------------------------------------

    List<BaseBean> currList = null;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("")) {
        continue;
      }

      if (line.equals("#dyna:")) {
        currList = baseBeanList;
        continue;
      }

      BaseBean baseBean = new BaseBean(line);

      currList.add(baseBean);
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseBean> getBaseBeanList() {
    return baseBeanList;
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
