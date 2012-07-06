/*
 * Created on 26/12/2006
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Demián Gutierrez
 */
public class PartLoader {

  // ----------------------------------------
  // Singleton 
  // ----------------------------------------

  private static PartLoader instance;

  public static PartLoader getInstance() {
    if (instance == null) {
      instance = new PartLoader();
    }

    return instance;
  }

  // ----------------------------------------
  // Props
  // ----------------------------------------

  private Map parts = new HashMap();
  private List path = new ArrayList();

  // ----------------------------------------

  private PartLoader() {
    // Empty
  }

  // ----------------------------------------

  protected File findFile(String name) throws Exception {
    String fs = System.getProperty("file.separator");

    File ret = null;

    Iterator itt = path.iterator();

    while (itt.hasNext()) {
      String curPath = (String) itt.next();

      if (fs.equals("/")) {
        name = name.replace('\\', '/');
        name = name.toLowerCase();
      }

      ret = new File(curPath + fs + name);

      if (ret.exists()) {
        break;
      }

      ret = null;
    }

    if (ret == null) {
      throw new FileNotFoundException(name);
    }

    return ret;
  }

  public Part loadPart(String name) throws Exception {

    // ----------------------------------------
    //  For mpd support
    // ----------------------------------------

    List unresolvedBeanList = new ArrayList();
    Part ret = null;
    Part cur = null;

    // ----------------------------------------
    // Read the file
    // ----------------------------------------

    BufferedReader rd = new BufferedReader(new FileReader(findFile(name)));

    Pattern pattern = Pattern.compile(" +");

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("")) {
        continue;
      }

      String[] data = pattern.split(line);

      if (Integer.parseInt(data[0]) != 0) {
        Part itm = new Part();

        try {
          itm.parse(line);
        } catch (FileNotFoundException e) {
          UnresolvedBean unresolvedBean = new UnresolvedBean();

          unresolvedBean.name = e.getMessage();
          unresolvedBean.itm = itm;
          unresolvedBeanList.add(unresolvedBean);
        }

        if (cur == null) {
          cur = new Part();
          cur.name = data[2];
          cur.type = 1;
          cur.partList = new ArrayList();

          ret = cur;
        }

        cur.partList.add(itm);
      } else {
        if (data.length >= 3 && data[1].equalsIgnoreCase("FILE")) {
          cur = new Part();
          cur.name = data[2];
          cur.type = 1;
          cur.partList = new ArrayList();

          parts.put(cur.name, cur);

          if (ret == null) {
            ret = cur;
          }
        }
      }
    }

    // ----------------------------------------
    // Check unresolved
    // ----------------------------------------

    Iterator itt = unresolvedBeanList.iterator();

    while (itt.hasNext()) {
      UnresolvedBean unresolvedBean = (UnresolvedBean) itt.next();
      unresolvedBean.itm.partDeps = getPart((String) unresolvedBean.name);
    }

    return ret;
  }

  // ----------------------------------------

  public Part getPart(String name) throws Exception {
    Part ret = (Part) parts.get(name);

    if (ret == null) {
      try {
        ret = loadPart(name);
      } catch (FileNotFoundException e) {
        // name is used for mpd
        throw new FileNotFoundException(name);
      }

      parts.put(name, ret);
    }

    return ret;
  }

  // ----------------------------------------

  public List getPath() {
    return path;
  }

  // ----------------------------------------

  private static class UnresolvedBean {

    public String name;
    public Part itm;
  }
}
