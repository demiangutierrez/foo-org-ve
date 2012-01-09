package org.cyrano.util.misc;

import java.awt.Color;

public class ColorParser {

  public static Color parse(String str) {
    if (!str.startsWith("#")) {
      throw new RuntimeException(str);
    }

    if (str.length() != 7) {
      throw new RuntimeException(str);
    }

    String r = str.substring(1, 3);
    String g = str.substring(3, 5);
    String b = str.substring(5, 7);

    try {
      Color ret = new Color( //
          Integer.parseInt(r, 16), //
          Integer.parseInt(g, 16), //
          Integer.parseInt(b, 16));

      return ret;
    } catch (Exception e) {
      throw new RuntimeException(str);
    }
  }
}
