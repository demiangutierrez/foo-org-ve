/*
 * Created on 27/12/2006
 */
package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.mutable.MutableInt;

/**
 * @author Demián Gutierrez
 */
public class ColorIndex {

  public static final int COLOR_MAIN = 16;
  public static final int COLOR_EDGE = 24;

  // ----------------------------------------
  // Singleton 
  // ----------------------------------------

  private static ColorIndex instance;

  public static ColorIndex getInstance() throws Exception {
    if (instance == null) {
      instance = new ColorIndex();
    }

    return instance;
  }

  // ----------------------------------------
  // Props
  // ----------------------------------------

  private Map<MutableInt, ColorEntry> colorMap = new HashMap<MutableInt, ColorEntry>();
  private MutableInt key = new MutableInt();

  // ----------------------------------------

  public ColorIndex() throws Exception {
    String line;

    BufferedReader rd = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("ldconfig.ldr")));

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (!line.startsWith("0 !COLOUR")) {
        continue;
      }

      ColorEntry colorEntry = new ColorEntry();
      colorEntry.parse(line);

      colorMap.put(new MutableInt(colorEntry.partIndex), colorEntry);
    }
  }

  // ----------------------------------------

  public float[] getPartColor(int index) {
    key.setValue(index);

    ColorEntry colorEntry = (ColorEntry) colorMap.get(key);

    if (colorEntry == null) {
      int c = index;

      int g = c & 0xFF;
      c >>>= 8;
      int b = c & 0xFF;
      c >>>= 8;
      int r = c & 0xFF;
      c >>>= 8;
      int a = c & 0xFF;

      colorEntry = new ColorEntry();
      colorEntry.partIndex = index;

      colorEntry.partColor = new float[4];

      colorEntry.partColor[0] = r / 255f;
      colorEntry.partColor[1] = g / 255f;
      colorEntry.partColor[2] = b / 255f;

      // ??? 10f, magic number :-/ should be 255f
      colorEntry.partColor[3] = a / 10f;

      colorMap.put(new MutableInt(index), colorEntry);
    }

    return colorEntry.partColor;
  }

  public float[] getEdgeColor(int index) {
    key.setValue(index);

    ColorEntry colorEntry = (ColorEntry) colorMap.get(key);

    if (colorEntry == null) {
      key.setValue(COLOR_EDGE);
      colorEntry = (ColorEntry) colorMap.get(key);
    }

    float[] ret = colorEntry.edgeColor;

    if (ret == null) {
      ret = getPartColor(colorEntry.edgeIndex);
    }

    return ret;
  }
}