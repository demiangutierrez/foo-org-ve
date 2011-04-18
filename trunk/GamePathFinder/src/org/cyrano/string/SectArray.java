package org.cyrano.string;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class SectArray {

  public static final char[] SECT_CHARS = new char[]{//
  /**/'.', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'j', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
      'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'J', 'M', 'N', 'O', 'P', 'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  // --------------------------------------------------------------------------------

  public int datW;
  public int datH;

  public int blkW;
  public int blkH;

  public int[][] data;
  public int[][] sect;

  public Map<Integer, Pair> sectCenter = new HashMap<Integer, Pair>();

  private int currSectChar = 1;

  // --------------------------------------------------------------------------------

  public SectArray(int[][] data, int datW, int datH, int blkW, int blkH) {
    this.data = data;

    this.datW = datW;
    this.datH = datH;

    this.blkW = blkW;
    this.blkH = blkH;

    sect = new int[datW][datH];
  }

  // --------------------------------------------------------------------------------

  private void handleCoord(int x, int y, int minX, int minY, int maxX, int maxY) {

    // ----------------------------------------
    // check if we can process this node
    // ----------------------------------------

    if (sect[x][y] != 0 || data[x][y] == 1) {
      return;
    }

    // ----------------------------------------
    // stack
    // ----------------------------------------

    int[] stackX = new int[(maxX - minX) * (maxY - minY)];
    int[] stackY = new int[(maxX - minX) * (maxY - minY)];
    int currStackIndex = 0;

    // ----------------------------------------
    // add the first element to the stack
    // ----------------------------------------

    stackX[currStackIndex] = x;
    stackY[currStackIndex] = y;
    currStackIndex++;

    while (currStackIndex > 0) {

      // ----------------------------------------
      // take the first element from the stack
      // ----------------------------------------

      currStackIndex--;
      int currX = stackX[currStackIndex];
      int currY = stackY[currStackIndex];

      // ----------------------------------------
      // handle and check neighbor nodes
      // ----------------------------------------

      sect[currX][currY] = currSectChar;

      for (int j = currY - 1; j < currY + 2; j++) {
        for (int i = currX - 1; i < currX + 2; i++) {

          // ----------------------------------------
          // check if we have to add this to stack
          // ----------------------------------------

          if (i == currX && j == currY) {
            continue;
          }

          if (i < 0/**/|| i >= datW || j < 0/**/|| j >= datH) {
            continue;
          }

          if (i < minX || i >= maxX || j < minY || j >= maxY) {
            continue;
          }

          if (sect[i][j] == -1) {
            continue;
          }

          if (sect[i][j] != +0) {
            continue;
          }

          if (data[i][j] == +1) {
            continue;
          }

          // ----------------------------------------
          // add to stack / mark as added (-1)
          // ----------------------------------------

          sect[i][j] = -1;

          stackX[currStackIndex] = i;
          stackY[currStackIndex] = j;
          currStackIndex++;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void defineSectors(int minX, int minY, int maxX, int maxY) {
    for (int j = minY; j < maxY; j++) {
      for (int i = minX; i < maxX; i++) {
        if (sect[i][j] == 0 && data[i][j] != 1) {
          handleCoord(i, j, minX, minY, maxX, maxY);
          currSectChar++;
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void calculateSectArray() {
    if (currSectChar != 1) {
      throw new IllegalStateException("currSectChar != 1");
    }

    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        sect[i][j] = 0;
      }
    }

    int totSecW = (int) Math.ceil((double) datW / blkW);
    int totSecH = (int) Math.ceil((double) datH / blkH);

    for (int j = 0; j < totSecH; j++) {
      for (int i = 0; i < totSecW; i++) {
        int maxX = (i * blkW + blkW) > datW ? datW : i * blkW + blkW;
        int maxY = (j * blkH + blkH) > datH ? datH : j * blkH + blkH;

        defineSectors(i * blkW, j * blkH, maxX, maxY);
      }
    }
  }

  // --------------------------------------------------------------------------------

  int currentXMass;
  int currentYMass;
  int totalMass;
  int zeroX;
  int zeroY;
  int[][] visited;

  public void calculateSectCentr() {
    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        if (data[i][j] == 1) {
          continue;
        }

        if (sectCenter.containsKey(sect[i][j])) {
          continue;
        }

        currentXMass = 0;
        currentYMass = 0;
        totalMass = 0;
        zeroX = i;
        zeroY = j;
        visited = new int[blkW][blkH];
        calculateSectCentr(i, j);

        Pair pair = new Pair();
        pair.x = zeroX + currentXMass / totalMass;
        pair.y = zeroY + currentYMass / totalMass;
        sectCenter.put(sect[i][j], pair);
      }
    }
  }

  private void calculateSectCentr(int currX, int currY) {
    if (currX < 0 || currY < 0) {
      return;
    }

    if (visited[currX % blkW][currY % blkH] == 1) {
      return;
    }

    visited[currX % blkW][currY % blkH] = 1;

    int xd = currX - zeroX;
    int yd = currY - zeroY;
    currentXMass += xd;
    currentYMass += yd;
    totalMass++;

    for (int j = currY - 1; j < currY + 2; j++) {
      for (int i = currX - 1; i < currX + 2; i++) {
        calculateSectCentr(i, j);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void print(PrintStream ps) {
    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        ps.print(SECT_CHARS[sect[i][j]]);
      }
      ps.println();
    }
  }

  // --------------------------------------------------------------------------------

  public void print2(PrintStream ps) {
    for (Map.Entry<Integer, Pair> centEnt : sectCenter.entrySet()) {
      System.err.println(SECT_CHARS[centEnt.getKey()] + ";" + centEnt.getValue().x + ";" + centEnt.getValue().y);
    }
  }
}
