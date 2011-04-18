package org.cyrano.pacman3;

public class Log {

  public static void log(String str) {
    if (Constants.DEBUG) {
      System.err.println(str);
    }
  }

  // --------------------------------------------------------------------------------

  public static String dirToString(int dir) {
    switch (dir) {
      case Constants.DIR_LF :
        return "DIR_LF";
      case Constants.DIR_RG :
        return "DIR_RG";
      case Constants.DIR_UP :
        return "DIR_UP";
      case Constants.DIR_DW :
        return "DIR_DW";
    }

    return null;
  }
}
