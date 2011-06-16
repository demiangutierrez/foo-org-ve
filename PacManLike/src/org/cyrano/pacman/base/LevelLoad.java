package org.cyrano.pacman.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LevelLoad {

  private List<BaseBean> dynaList = new ArrayList<BaseBean>(); // Dyna Beans: Lay 3
  private List<BaseBean> sta2List = new ArrayList<BaseBean>(); // Stat Beans: Lay 2
  private List<BaseBean> sta1List = new ArrayList<BaseBean>(); // Stat Beans: Lay 1

  private char/**/[][] data; // Base Layer: Lay 0

  private BaseBean playBean;

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
        currList = dynaList;
        continue;
      }

      if (line.equals("#sta2:")) {
        currList = sta2List;
        continue;
      }

      if (line.equals("#sta1:")) {
        currList = sta1List;
        continue;
      }

      BaseBean baseBean = new BaseBean(line);

//      if (playBean == null) {
//        playBean = baseBean;
//      }

      currList.add(baseBean);

      //      if (playBean != null) {
      //        currList.add(baseBean);
      //      } else {
      //        playBean = baseBean;
      //      }
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseBean> getDynaList() {
    return dynaList;
  }

  public List<BaseBean> getSta2List() {
    return sta2List;
  }

  public List<BaseBean> getSta1List() {
    return sta1List;
  }

  // --------------------------------------------------------------------------------

  public char[][] getData() {
    return data;
  }

  // --------------------------------------------------------------------------------

//  public BaseBean getPlayBean() {
//    return playBean;
//  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }
}
