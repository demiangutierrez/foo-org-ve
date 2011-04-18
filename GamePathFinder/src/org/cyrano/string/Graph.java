package org.cyrano.string;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Graph {

  private static final char[] SECT_CHARS = new char[]{//
  /**/'.', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'j', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
      'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'J', 'M', 'N', 'O', 'P', 'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  private int[][] data;
  private int[][] sect;

  private int datW;

  private int datH;

  //  private int blkW;
  //  private int blkH;

  private int currSectChar = 1;

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    String line = rd.readLine();

    Pattern pattern = Pattern.compile(" ");
    String[] arrayWH = pattern.split(line);

    if (arrayWH.length == 0) {
      throw new IllegalArgumentException("arrayWH.length == 0");
    }

    try {
      datW = Integer.parseInt(arrayWH[0]);
      datH = Integer.parseInt(arrayWH[1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(line);
    }

    data = new int[datW][datH];

    for (int j = 0; j < datH; j++) {
      line = rd.readLine();

      for (int i = 0; i < datW; i++) {

        if (line == null || line.trim().equals("")) {
          throw new IllegalArgumentException("empty line at " + j);
        }

        try {
          data[i][j] = Integer.parseInt(Character.toString(line.charAt(i)));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException( //
              line.charAt(i) + " at " + i + "," + j);
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void handleCoord(int x, int y, int minX, int minY, int maxX, int maxY) {

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

  public void defineSectors(int minX, int minY, int maxX, int maxY) {
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

  int blkW;
  int blkH;

  public void defineSectors(int w, int h) {
    blkW = w;
    blkH = h;

    sect = new int[datW][datH];

    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        sect[i][j] = 0;
      }
    }

    int totSecW = (int) Math.ceil((double) datW / w);
    int totSecH = (int) Math.ceil((double) datH / h);

    for (int j = 0; j < totSecH; j++) {
      for (int i = 0; i < totSecW; i++) {
        int maxX = (i * w + w) > datW ? datW : i * w + w;
        int maxY = (j * h + h) > datH ? datH : j * h + h;

        defineSectors(i * w, j * h, maxX, maxY);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public Set<Integer> getNeighbor(int x, int y, int blkW, int blkH) {
    //int currNodeId = sect[x][y]; // Keep for later use

    Set<Integer> ret = new HashSet<Integer>();

    int curr = sect[x][y];

    // +2 is a crappy hack ^^
    int[][] mark = new int[blkW + 2][blkH + 2];

    // ----------------------------------------
    // stack
    // ----------------------------------------

    int[] stackX = new int[(blkW + 2) * (blkH + 2)];
    int[] stackY = new int[(blkW + 2) * (blkH + 2)];
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

      mark[currX % (blkW + 2)][currY % (blkH + 2)] = -1;
      if (sect[currX][currY] != curr) {
        ret.add(sect[currX][currY]);
        continue;
      }

      //System.err.println("Handle: " + currX + "," + currY + " : " + sect[currX][currY]);
      //mark[currX][currY] = -1;
      // Handle means ignore or add to the list if it's different than original node!!!

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

          if (sect[i][j] == +0) {
            continue;
          }
          if (mark[i % (blkW + 2)][j % (blkH + 2)] == -1) {
            continue;
          }

          // ----------------------------------------
          // add to stack / mark as added (-1)
          // ----------------------------------------

          //sect[i][j] = -1;
          mark[i % (blkW + 2)][j % (blkH + 2)] = -1;

          stackX[currStackIndex] = i;
          stackY[currStackIndex] = j;
          currStackIndex++;
          //System.err.println("Add: " + i + "," + j + " : " + sect[i][j]);
        }
      }
    }

    return ret;
  }

  private Map<Integer, Set<Integer>> neighbor = new HashMap<Integer, Set<Integer>>();

  public void defineNeighbor() {
    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        if (sect[i][j] == 0) {
          continue;
        }

        if (!neighbor.containsKey(sect[i][j])) {
          Set<Integer> n = getNeighbor(i, j, blkW, blkH);
          neighbor.put(sect[i][j], n);
        }
      }
    }
  }

  int currIden;
  int foundX;
  int foundY;
  double distFound = Double.MAX_VALUE;

  public void flood(int x, int y, char stop, int curr) {
    for (int j = y - 1; j < y + 2; j++) {
      for (int i = x - 1; i < x + 2; i++) {
        if (i == x && j == y) {
          continue;
        }

        if (i < 0/**/|| i >= datW || j < 0/**/|| j >= datH) {
          continue;
        }

        if (iden[i][j] == -1) {
          continue; // Ignore start point
        }

        //        System.err.println("Testing " + i + ";" + j + ";" + SECT_CHARS[sect[i][j]]);

        // It's a dot
        if (sect[i][j] == 0) {
          continue;
        }

        if (from[i][j] == 0) {
          //          System.err.println("Found a new one");
          from[i][j] = iden[x][y];
          iden[i][j] = currIden;
          dist[i][j] = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));

          //        System.err.println("from: " + from[i][j] + "; iden: " + iden[i][j] + "; dist: " + dist[i][j]);
          currIden++;

          if (SECT_CHARS[sect[i][j]] == stop) {
            //        System.err.println("Found a stop char");

            if (foundX == -1) {
              foundX = i;
              foundY = j;
              distFound = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
              //        System.err.println("was new: " + foundX + ";" + foundY + "; distFound: " + distFound);
            } else {
              double dist3 = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
              if (dist3 < distFound) {
                foundX = i;
                foundY = j;
                distFound = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                //        System.err.println("was prev: " + foundX + ";" + foundY + "; distFound: " + distFound);
              }
            }

            continue;
          }

          if (sect[x][y] != curr) {
            continue;
          }

          flood(i, j, stop, curr);
        } else {
          double dist2 = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
          if (dist2 < dist[i][j]) {
            //  System.err.println("Found an old one");
            from[i][j] = iden[x][y];
            dist[i][j] = dist2;
            //System.err.println("from: " + from[i][j] + "; iden: " + iden[i][j] + "; dist: " + dist[i][j]);

            if (SECT_CHARS[sect[i][j]] == stop) {
              if (foundX == -1) {
                foundX = i;
                foundY = j;
                distFound = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                //  System.err.println("was new: " + foundX + ";" + foundY + "; distFound: " + distFound);
              } else {
                double dist3 = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                if (dist3 < distFound) {
                  foundX = i;
                  foundY = j;
                  distFound = dist[x][y] + Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                  //  System.err.println("was prev: " + foundX + ";" + foundY + "; distFound: " + distFound);
                }
              }

              continue;
            }

            if (sect[x][y] != curr) {
              continue;
            }

            flood(i, j, stop, curr);
          }
        }
      }
    }
  }

  private double dist[][];
  private int from[][];
  private int iden[][];

  public void floodBase(int x, int y, char stop) {
    dist = new double[datW][datH]; // XXX: Improve, it's oversized
    from = new int[datW][datH]; // XXX: Improve, it's oversized
    iden = new int[datW][datH]; // XXX: Improve, it's oversized

    iden[x][y] = -1; // Source id is -1 (to avoid initialize all iden to -1, so we use 0 as invalid)

    currIden = 1;
    foundX = -1;
    foundY = -1;

    flood(x, y, stop, sect[x][y]);
  }

  // --------------------------------------------------------------------------------

  public void printData() {
    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        System.err.print(data[i][j]);
      }
      System.err.println();
    }
  }

  // --------------------------------------------------------------------------------

  public void printSect() {
    for (int j = 0; j < datH; j++) {
      for (int i = 0; i < datW; i++) {
        System.err.print(SECT_CHARS[sect[i][j]]);
      }
      System.err.println();
    }
  }

  // --------------------------------------------------------------------------------

  public void printNeig() {
    for (Map.Entry<Integer, Set<Integer>> entry : neighbor.entrySet()) {
      System.err.print(SECT_CHARS[entry.getKey()] + ":");

      for (Integer integer : entry.getValue()) {
        System.err.print(SECT_CHARS[integer] + ", ");
      }

      System.err.println();
    }
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) throws Exception {
    Graph graph = new Graph();
    graph.load("map1.txt");

    System.err.println("--------------------------------------------------------------->");
    graph.printData();
    System.err.println("--------------------------------------------------------------->");
    //graph.defineSectors(3, 3);
    graph.defineSectors(10, 10);
    System.err.println("--------------------------------------------------------------->");
    graph.printSect();
    System.err.println("--------------------------------------------------------------->");
    graph.defineNeighbor();
    System.err.println("--------------------------------------------------------------->");
    graph.printNeig();
    System.err.println("--------------------------------------------------------------->");
    //graph.floodBase(1,   1, 'b');
    //graph.floodBase(27,  1, 'g'); // 30,  1
    //graph.floodBase(27,  4, 'c'); // 19,  3
    //graph.floodBase(0,   0, 'j'); // 10, 10
    //graph.floodBase(39, 39, 'u'); // 29, 33
    //graph.floodBase(0,  20, 'r'); // 10, 30
    //    graph.floodBase(0, 20, 'r'); // 10, 30
    //    System.err.println(graph.foundX + ";" + graph.foundY);
    //    System.err.println(SECT_CHARS[graph.sect[39][39]]);
    //    System.err.println(SECT_CHARS[graph.sect[graph.foundX][graph.foundY]]);
  }
}

//aaaaaaa.bbcccccccccceeeeeeeeeegggggggggg
//aaaaaaa.bbcccccccccceeeeeeeeeegggggggggg
//aaaaaaa.bbcccccccccceeeeeeeeeegggggggggg
//aaaaaaa.bbcccccccccceeeeeeeeeegggggggggg
//aaaaaaa..............eeeeeeeeegggggggggg
//aaaaaaaaa...............................
//aaaaaaaaaad..............fffffhhhhhhhhhh
//aaaaaaaaaaddd..............fffhhhhhhhhhh
//aaaaaaaaaaddddd..............fhhhhhhhhhh
//aaaaaaaaaaddddddd..............hhhhhhhhh
//iiiiiiiiiijjjjj..............kjjjjjjjjjj
//iiiiiiiiiijjj..............kkkjjjjjjjjjj
//iiiiiiiiiij..............kkkkkjjjjjjjjjj
//iiiiiiiii..............kkkkkkkjjjjjjjjjj
//iiiiiii..............kkkkkkkkkjjjjjjjjjj
//iiiiiii..............kkkkkkkkkjjjjjjjjjj
//iiiiiii..............kkkkkkkkkjjjjjjjjjj
//iiiiiii..............kkkkkkkkkjjjjjjjjjj
//iiiiiii..............kkkkkkkkkjjjjjjjjjj
//.....................kkkkkkkkkjjjjjjjjjj
//mmmmmmmm.............ooooooooopppppppppp
//mmmmmmmmm............ooooooooopppppppppp
//mmmmmmmmmm...........ooooooooopppppppppp
//mmmmmmmmmmn..........ooooooooopppppppppp
//mmmmmmmmmmnn.........ooooooooopppppppppp
//mmmmmmmmmmnn.........ooooooooopppppppppp
//mmmmmmmmmmnnn........ooooooooopppppppppp
//mmmmmmmmmmnnnn.......ooooooooopppppppppp
//mmmmmmmmmmnnnnn......ooooooooopppppppppp
//mmmmmmmmmmnnnnnn.....ooooooooopppppppppp
//qqqqqqqqqqrrrrrrr....tttttttttwwwwwwwwww
//qqqqqqqqqqrrrrrrrr...tttttttttwwwwwwwwww
//qqqqqqqqqqrrrrrrrrr.....................
//qqqqqqqqqqrrrrrrrrrr.uuuuuuuuuxxxxxxxxxx
//qqqqqqqqqqrrrrrr......................xx
//qqqqqqqqqqrrrrr.......................xx
//qqqqqqqqqqrrrr..ssssvvvvvvvvvvyyyyyyy.xx
//qqqqqqqqqqrrr..sssssvvvvvvvvvvyyyyyyy.xx
//qqqqqqqqqqrr..ssssssvvvvvvvvvvyyyyyyy.xx
//qqqqqqqqqqr..sssssssvvvvvvvvvvyyyyyyy.xx
