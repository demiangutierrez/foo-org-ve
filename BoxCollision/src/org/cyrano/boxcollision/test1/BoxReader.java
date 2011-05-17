package org.cyrano.boxcollision.test1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.cyrano.boxcollision.base.Box;

/**
 * @author Demián Gutierrez
 */
public class BoxReader {

  private BoxReader() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static List<Box> fsRead(InputStream is) {
    try {
      return exRead(is);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------------------------------------------------------------------------------

  public static List<Box> exRead(InputStream is) throws Exception {
    List<Box> ret = new ArrayList<Box>();

    Pattern p = Pattern.compile("( +)");

    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("")) {
        continue;
      }

      if (line.startsWith("#")) {
        continue;
      }

      String[] data = p.split(line);

      if (data.length != 13) {
        throw new ParseException(line, -1);
      }

      int i = 0;

      BoxImpl box = new BoxImpl();

      box.mv = data[i++].equals("t") ? true : false;

      box.cx = Integer.parseInt(data[i++]);
      box.cy = Integer.parseInt(data[i++]);
      box.bw = Integer.parseInt(data[i++]);
      box.bh = Integer.parseInt(data[i++]);
      box.vx = Integer.parseInt(data[i++]);
      box.vy = Integer.parseInt(data[i++]);
      //      box.ax = Integer.parseInt(data[i++]);
      //      box.ay = Integer.parseInt(data[i++]);

      box.color = new Color( //
          Integer.parseInt(data[i++]), //
          Integer.parseInt(data[i++]), //
          Integer.parseInt(data[i++]));

      box.id = data[i++];

      ret.add(box);
    }

    return ret;
  }
}
