/*
 * Created on 07/06/2006
 */
package dao.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Demián Gutierrez
 */
public class CledaStringUtils {

  private CledaStringUtils() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static String trim(String text, Set<String> trim) {

    if (text == null) {
      return null;
    }

    // ----------------------------------------
    // Trim forward
    // ----------------------------------------

    int beg = 0;

    for (/* Empty */; beg < text.length(); beg++) {
      if (!trim.contains(text.substring(beg, beg + 1))) {
        break;
      }
    }

    // ----------------------------------------
    // Trim backward
    // ----------------------------------------

    int end = text.length() - 1;

    for (/* Empty */; end >= 0; end--) {
      if (!trim.contains(text.substring(end, end + 1))) {
        break;
      }
    }

    // ----------------------------------------
    // Set the text
    // ----------------------------------------

    if (beg <= end) {
      text = text.substring(beg, end + 1);
    } else {
      text = "";
    }

    return text;
  }

  // --------------------------------------------------------------------------------

  public static String trim(String text, String trim) {
    Set<String> trimSet = new HashSet<String>();

    for (int i = 0; i < trim.length(); i++) {
      trimSet.add(trim.substring(i, i + 1));
    }

    return trim(text, trimSet);
  }

  // --------------------------------------------------------------------------------

  public static String dotIt(String... args) {
    StringBuffer ret = new StringBuffer();

    for (int i = 0; i < args.length; i++) {
      ret.append(args[i]);

      if (i + 1 < args.length) {
        ret.append(".");
      }
    }

    return ret.toString();
  }
}
