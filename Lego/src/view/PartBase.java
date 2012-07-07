/*
 * Created on 26/12/2006
 */
package view;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Demián Gutierrez
 */
public class PartBase {

  protected Log log = LogFactory.getLog(getClass());

  protected String name;

  protected int type;
  protected int colr = -1;

  protected float[] x = new float[4];
  protected float[] y = new float[4];
  protected float[] z = new float[4];

  protected Part partDeps;
  protected List<Part> partList;

  public PartBase() {
    // Empty
  }

  protected void loadCoord(String[] s, int n) {
    for (int i = 0; i < n; i++) {
      x[i] = Float.parseFloat(s[i * 3 + 2]);
      y[i] = Float.parseFloat(s[i * 3 + 3]);
      z[i] = Float.parseFloat(s[i * 3 + 4]);
    }
  }

  protected String coordToString(int n) {
    StringBuffer ret = new StringBuffer();

    for (int i = 0; i < n; i++) {
      ret.append(x[i]);
      ret.append(" ");
      ret.append(y[i]);
      ret.append(" ");
      ret.append(z[i]);

      if (i + 1 < n) {
        ret.append(" ");
      }
    }

    return ret.toString();
  }

  public void parse(String line) throws Exception {
    try {
      Pattern p = Pattern.compile(" +");
      String[] s = p.split(line);

      type = Integer.parseInt(s[0]);

      if (type == 0) {
        throw new ParseException("type == 0", 0);
      }

      colr = Integer.parseInt(s[1]);

      switch (type) {
        case 1 :
          loadCoord(s, 4);
          PartLoader partLoader = PartLoader.getInstance();
          partDeps = partLoader.getPart(s[4 * 3 + 2]);
          break;
        case 2 :
          loadCoord(s, 2);
          break;
        case 3 :
          loadCoord(s, 3);
          break;
        case 4 :
          loadCoord(s, 4);
          break;
        default :
          log.warn("type == " + type);
      }
    } catch (Exception e) {
      System.err.println(line);
      throw e;
    }
  }
}
