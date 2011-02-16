package boxpush1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.regex.Pattern;

public class TextMap {

  private int w;
  private int h;

  private char[][] data;

  private int initialX = -1;
  private int initialY = -1;

  private int smlCount;
  private int bigCount;

  // --------------------------------------------------------------------------------

  public TextMap() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    String line = rd.readLine();

    Pattern pattern = Pattern.compile(" +");
    String[] arrayWH = pattern.split(line);

    if (arrayWH.length == 0) {
      throw new IllegalArgumentException("arrayWH.length == 0");
    }

    try {
      w = Integer.parseInt(arrayWH[0]);
      h = Integer.parseInt(arrayWH[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(line);
    }

    data = new char[w][h];

    for (int j = 0; j < h; j++) {
      line = rd.readLine();

      for (int i = 0; i < w; i++) {

        if (line == null || line.trim().equals("")) {
          throw new IllegalArgumentException("empty line at " + j);
        }

        data[i][j] = line.charAt(i);

        if (data[i][j] == 'P') {
          initialX = i;
          initialY = j;
        }

        if (data[i][j] == '.') {
          smlCount++;
        }

        if (data[i][j] == '*') {
          bigCount++;
        }
      }
    }

    //    if (initialX == -1 || initialY == -1) {
    //      throw new Exception("initialX == -1 || initialY == -1");
    //    }
  }

  // --------------------------------------------------------------------------------

  public char[][] getData() {
    return data;
  }

  // --------------------------------------------------------------------------------

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }

  // --------------------------------------------------------------------------------

  public int getInitialX() {
    return initialX;
  }

  public int getInitialY() {
    return initialY;
  }

  // --------------------------------------------------------------------------------

  public int getSmlCount() {
    return smlCount;
  }

  public int getBigCount() {
    return bigCount;
  }

  // --------------------------------------------------------------------------------

  public void dump(PrintStream ps) {
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        ps.print(data[x][y]);
      }

      ps.println();
    }
  }

  @Override
  public String toString() {
    StringBuffer strbuf = new StringBuffer();

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        strbuf.append(data[x][y]);
      }

      strbuf.append("\n");
    }

    return strbuf.toString();
  }

  public static void main(String[] args) throws Exception {
    TextMap tm = new TextMap();
    tm.load(TextMap.class.getResource("map.txt").getPath());
    tm.dump(System.err);
  }
}
