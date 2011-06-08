package org.cyrano.jogl.morton;

public class MortonUtil {

  private MortonUtil() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static boolean compare(int[] p, int[] q) {
    assert (p.length == q.length);

    int x = 0;
    int d = 0;

    for (int j = 0; j < p.length; j++) {
      int y = xormsb(p[j], q[j]);

      if (x < y) {
        x = y;
        d = j;
      }
    }

    return p[d] < q[d];
  }

  // --------------------------------------------------------------------------------

  private static int xormsb(int a, int b) {
    return msdb(a, b);
  }

  // --------------------------------------------------------------------------------
  // I'm not 100% of the double implementation. I have not a good way to test it
  // --------------------------------------------------------------------------------

  public static boolean compare(double[] p, double[] q) {
    assert (p.length == q.length);

    int x = 0;
    int d = 0;

    for (int j = 0; j < p.length; j++) {
      int y = xormsb(p[j], q[j]);

      if (x < y) {
        x = y;
        d = j;
      }
    }

    return p[d] < q[d];
  }

  // --------------------------------------------------------------------------------

  private static int xormsb(double a, double b) {
    int x = IEEE754Util.exponent(a);
    int y = IEEE754Util.exponent(b);

    if (x == y) {
      int z = msdb(IEEE754Util.mantissa(a), IEEE754Util.mantissa(b));

      x = x - z;

      return x;
    }

    if (y < x) {
      return x;
    } else {
      return y;
    }
  }

  // --------------------------------------------------------------------------------

  // @noformat{
  private static final int msdb(long a, long b) {
    long x = a ^ b;

    if (x == 0) { return 0; }

    int i = 63; // -> Long.SIZE - 1;

    while ((x & 0x8000000000000000l) != 0x8000000000000000l) {
      x <<= 1; i--;
    }

    return i;
  }
  // @}noformat

  // --------------------------------------------------------------------------------

  public static long[] interleave(long al, long bl) {
    long[] ret = new long[2];

    for (int i = 0; i < Long.SIZE / 2; i++) {
      ret[0] >>>= 1;
      ret[0] |= (al & 0x8000000000000000l);
      al <<= 1;

      ret[0] >>>= 1;
      ret[0] |= (bl & 0x8000000000000000l);
      bl <<= 1;
    }

    for (int i = 0; i < Long.SIZE / 2; i++) {
      ret[1] >>>= 1;
      ret[1] |= (al & 0x8000000000000000l);
      al <<= 1;

      ret[1] >>>= 1;
      ret[1] |= (bl & 0x8000000000000000l);
      bl <<= 1;
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static int[] interleave(int al, int bl) {
    int[] ret = new int[2];

    for (int i = 0; i < Integer.SIZE / 2; i++) {
      ret[0] <<= 1;
      ret[0] |= ((al & 0x80000000) >>> (Integer.SIZE - 1));
      al <<= 1;

      ret[0] <<= 1;
      ret[0] |= ((bl & 0x80000000) >>> (Integer.SIZE - 1));
      bl <<= 1;
    }

    for (int i = 0; i < Integer.SIZE / 2; i++) {
      ret[1] <<= 1;
      ret[1] |= ((al & 0x80000000) >>> (Integer.SIZE - 1));
      al <<= 1;

      ret[1] <<= 1;
      ret[1] |= ((bl & 0x80000000) >>> (Integer.SIZE - 1));
      bl <<= 1;
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  //  public static void main(String[] args) {
  //    System.err.println(msdb(0x00000000, 0x00000000)); //  0
  //    System.err.println(msdb(0xFFFFFFFF, 0XFFFFFFFF)); //  0
  //    System.err.println(msdb(0x80000000, 0x80000000)); //  0
  //    System.err.println(msdb(0x00000001, 0x00000001)); //  0
  //    System.err.println(msdb(0x00000001, 0x00000002)); //  1
  //    System.err.println(msdb(0x00000002, 0x00000001)); //  1
  //    System.err.println(msdb(0x00000002, 0x00000004)); //  2
  //    System.err.println(msdb(0x00000004, 0x00000002)); //  2
  //    System.err.println(msdb(0x80000000, 0x40000000)); // 31
  //    System.err.println(msdb(0x40000000, 0x80000000)); // 31
  //    System.err.println(msdb(0x00AAAAAA, 0x00555555)); // 23
  //    System.err.println(msdb(0x00555555, 0x00AAAAAA)); // 23
  //  }
}
