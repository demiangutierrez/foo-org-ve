package org.cyrano.pacman.game;

import java.util.regex.Pattern;

import org.cyrano.pacman.base.BaseSprite;

public class ItemBean {

  private Class<? extends BaseSprite> clazz;

  private String[] parameters;

  private int x;
  private int y;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public ItemBean(String line) throws Exception {
    Pattern p = Pattern.compile(" +");

    String[] lineArray = p.split(line);

    if (lineArray.length < 3) {
      throw new IllegalArgumentException( //
          "lineArray.length < 3 : " + line);
    }

    clazz = (Class<? extends GhostSprite>) Class.forName(lineArray[0]);

    x/* */= Integer.parseInt(lineArray[1]);
    y/* */= Integer.parseInt(lineArray[2]);

    parameters = new String[lineArray.length - 3];

    for (int i = 3; i < lineArray.length; i++) {
      parameters[i - 3] = lineArray[i];
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
}
