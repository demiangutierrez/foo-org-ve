package org.cyrano.pacman.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.pacman.base.BaseBean;

public class DynaMap {

  private List<BaseBean> baseBeanList = //
  new ArrayList<BaseBean>();

  // --------------------------------------------------------------------------------

  public DynaMap() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("") || line.startsWith("#")) {
        continue;
      }

      baseBeanList.add(new BaseBean(line));
    }
  }

  // --------------------------------------------------------------------------------

  public List<BaseBean> getBaseBeanList() {
    return baseBeanList;
  }
}
