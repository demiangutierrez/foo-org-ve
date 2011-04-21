package org.cyrano.pacman.base;

import java.util.regex.Pattern;

public class BaseBean {

  private Class<? extends BaseSprite> clazz;

  private String[] parmArray;

  private int x;
  private int y;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public BaseBean(String line) throws Exception {
    Pattern p = Pattern.compile(" +");

    String[] lineArray = p.split(line);

    if (lineArray.length < 3) {
      throw new IllegalArgumentException( //
          "lineArray.length < 3 : " + line);
    }

    clazz = (Class<? extends BaseSprite>) //
    Class.forName(lineArray[0]);

    x/* */= Integer.parseInt(lineArray[1]);
    y/* */= Integer.parseInt(lineArray[2]);

    parmArray = new String[lineArray.length - 3];

    for (int i = 0; i < parmArray.length; i++) {
      parmArray[i] = lineArray[i + 3];
    }
  }

  // --------------------------------------------------------------------------------

  public Class<? extends BaseSprite> getClazz() {
    return clazz;
  }

  // --------------------------------------------------------------------------------

  public int getX() {
    return x;
  }

  // --------------------------------------------------------------------------------

  public int getY() {
    return y;
  }

  // --------------------------------------------------------------------------------

  public BaseSprite init(LevelExec levelExec) {
    try {
      BaseSprite ret = clazz.newInstance();

      ret.init(levelExec, this, parmArray);

      return ret;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
