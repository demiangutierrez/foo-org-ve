package org.cyrano.util;

public class StringUtil {

  // --------------------------------------------------------------------------------

  public static String filler(char fill, int size) {
    StringBuffer ret = new StringBuffer();

    for (int i = 0; i < size; i++) {
      ret.append(fill);
    }

    return ret.toString();
  }

  // --------------------------------------------------------------------------------

  public static String fillto(char fill, int size, String str) {
    return filler(fill, size - str.length()) + str;
  }
}
