package org.cyrano.pacman3;

import java.util.regex.Pattern;

public class GhostBean {

  private Class<? extends GhostSprite> clazz;

  private int speed;

  private int x;
  private int y;

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public GhostBean(String line) throws Exception {
    Pattern p = Pattern.compile(" +");

    String[] lineArray = p.split(line);

    if (lineArray.length != 4) {
      throw new IllegalArgumentException( //
          "lineArray.length != 4 : " + line);
    }

    clazz = (Class<? extends GhostSprite>) Class.forName(lineArray[0]);

    x/* */= Integer.parseInt(lineArray[1]);
    y/* */= Integer.parseInt(lineArray[2]);
    speed = Integer.parseInt(lineArray[3]);
  }

  // --------------------------------------------------------------------------------

  public Class<? extends GhostSprite> getClazz() {
    return clazz;
  }

  // --------------------------------------------------------------------------------

  public int getSpeed() {
    return speed;
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
