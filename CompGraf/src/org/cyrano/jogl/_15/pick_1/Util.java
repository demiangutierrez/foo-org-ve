package org.cyrano.jogl._15.pick_1;

public class Util {

  public static byte[] intToBytes(long i) {
    //    if (i < Integer.MIN_VALUE || i > Integer.MAX_VALUE) {
    //      //      throw new IllegalArgumentException(Long.toString(i));
    //    }

    byte[] ba = new byte[4];

    ba[0] = (byte) ((((long) i) & 0x00FF0000) >> 0x10);
    ba[1] = (byte) ((((long) i) & 0x0000FF00) >> 0x08);
    ba[2] = (byte) ((((long) i) & 0x000000FF) >> 0x00);

    return ba;
  }

  // --------------------------------------------------------------------------------

  public static long bytesToInt(int[] ba) {
    if (!checkByte(ba[0]) || !checkByte(ba[1]) || //
        !checkByte(ba[2]) || !checkByte(ba[3])) {
      throw new RuntimeException( //
          ba[0] + ";" + ba[1] + ";" + ba[2] + ";" + ba[3]);
    }

    long i = 0;

    i |= ((long) ba[0] & 0x00FF) << 0x10;
    i |= ((long) ba[1] & 0x00FF) << 0x08;
    i |= ((long) ba[2] & 0x00FF) << 0x00;

    return i;
  }

  // --------------------------------------------------------------------------------

  public static long bytesToInt( //
      int b0, int b1, int b2, int b3) {

    return bytesToInt(new int[]{b0, b1, b2, b3});
  }

  // --------------------------------------------------------------------------------

  public static boolean checkByte(int b) {
    return b >= 0 && b <= 255;
  }

  // --------------------------------------------------------------------------------

  public static void test( //
      int byte0, int byte1, int byte2, int byte3) {

    long intV = bytesToInt(byte0, byte1, byte2, byte3);

    byte[] ba = intToBytes(intV);

    if (ba[0] != byte0 || ba[1] != byte1 || ba[2] != byte2 || ba[3] != byte3) {
      throw new RuntimeException( //
          /**/ba[0] + ";" + ba[1] + ";" + ba[2] + ";" + ba[3] + " vs " + //
              byte0 + ";" + byte1 + ";" + byte2 + ";" + byte3);
    }

    System.err.println("*********************************************");
    System.err.println(byte0 + ";" + byte1 + ";" + byte2 + ";" + byte3);
    System.err.println(intV);
    System.err.println(ba[0] + ";" + ba[1] + ";" + ba[2] + ";" + ba[3]);
  }

  public static byte ltb(long l) {
    if (l < 0 || l > 255) {
      throw new IllegalArgumentException(Long.toString(l));
    }

    return (byte) (l & 0xFF);
  }

  public static long btl(byte b) {
    return 0 | b;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 256; i++) {
      System.err.println(i + ";" + ltb(i) + ";" + btl(ltb(i)));
    }

    //    test(0x00, 0x00, 0x00, 0x00);
    //    test(0x01, 0x01, 0x01, 0x01);
    //test(0xFF, 0xFF, 0xFF, 0xFF);
  }
}
