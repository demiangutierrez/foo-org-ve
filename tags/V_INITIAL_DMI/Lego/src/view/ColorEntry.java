/*
 * Created on 30/12/2006
 */
package view;

import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * @author Demián Gutierrez
 */
public class ColorEntry {

  float[] partColor; // RGB
  float[] edgeColor; // RGB

  int partIndex = ColorIndex.COLOR_MAIN;
  int edgeIndex = ColorIndex.COLOR_EDGE;

  // ----------------------------------------

  public ColorEntry() {
    // Empty
  }

  // ----------------------------------------

  public float[] htmlColorToIntRGBArray(String str) throws ParseException {
    str = str.trim();

    if (str.length() != 7) {
      throw new ParseException("str.length() != 7", 0);
    }

    if (!str.startsWith("#")) {
      throw new ParseException("!str.startsWith(\"#\")", 0);
    }

    float[] ret = new float[4];

    ret[0] = Integer.parseInt(str.substring(1, 3), 16) / 255f;
    ret[1] = Integer.parseInt(str.substring(3, 5), 16) / 255f;
    ret[2] = Integer.parseInt(str.substring(5, 7), 16) / 255f;
    ret[3] = 1;

    return ret;
  }

  // ----------------------------------------

  public void parse(String line) throws Exception {
    Pattern p = Pattern.compile(" +");
    String[] s = p.split(line);

    if (!s[0].equals("0")) {
      throw new ParseException("!s[0].equals(\"0\")", 0);
    }

    if (!s[1].equals("!COLOUR")) {
      throw new ParseException("!s[1].equals(\"!COLOUR\")", 0);
    }

    for (int i = 0; i < s.length; i++) {
      if (s[i].equalsIgnoreCase("CODE")) {
        i++;
        partIndex = Integer.parseInt(s[i]);
      } else if (s[i].equalsIgnoreCase("VALUE")) {
        i++;
        partColor = htmlColorToIntRGBArray(s[i]);
      } else if (s[i].equalsIgnoreCase("EDGE")) {
        i++;

        if (s[i].startsWith("#")) {
          edgeColor = htmlColorToIntRGBArray(s[i]);
        } else {
          edgeIndex = Integer.parseInt(s[i]);
        }
      } else if (s[i].equalsIgnoreCase("ALPHA")) {
        i++;
        partColor[3] = Integer.parseInt(s[i]) / 255f;
      }
    }
  }
}