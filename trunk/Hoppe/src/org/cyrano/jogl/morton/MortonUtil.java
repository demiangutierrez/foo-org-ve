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

  // XXX: 100% sure this will not work out of the box...
  // XXX: Confirmed... it does not work :( ... yet
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

  // XXX: original c++ code from
  // https://svn.cs.hmc.edu/svn/robotics/Summer08/vision/mac/Process/StannInclude/float_lt.hpp
  // Michael Connor and Piyush Kumar
  //  int xormsb(double p, double q)
  //  {
  //    int xp, xq;
  //    int y;
  //    mp.d = frexp(p, &xp);
  //    mq.d = frexp(q, &xq);
  //    
  //    if(xp < xq)
  //      {
  //  y = xq;
  //      }
  //    else if(xp == xq)
  //      {
  //  if(mp.l==mq.l)
  //    y = 0;
  //  else
  //    {
  //      (mp.l)=((mp.l)^(mq.l))|(lzero.l);
  //      mp.d = frexp(mp.d-.5, &xq);
  //      y = xp+xq;
  //    }
  //      }
  //    else
  //      {
  //  y = xp;
  //      }
  //    return y;
  //  }

  // XXX: Another interesting link:
  // https://bbs.archlinux.org/viewtopic.php?id=84139
  
  private static int xormsb(double a, double b) {
    long x = IEEE754Util.exponent(a);
    long y = IEEE754Util.exponent(b);

    if (x == y) {
      int z = msdb(IEEE754Util.mantissa(a), IEEE754Util.mantissa(b));

      // this has to be a mistake!!!
      return (int) (x - z);
    }

    if (y < x) {
      return (int) x;
    } else {
      return (int) y;
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

  public static void main(String[] args) {
    System.err.println(msdb(0x00000000, 0x00000000)); //  0
    System.err.println(msdb(0xFFFFFFFF, 0XFFFFFFFF)); //  0
    System.err.println(msdb(0x80000000, 0x80000000)); //  0
    System.err.println(msdb(0x00000001, 0x00000001)); //  0
    System.err.println(msdb(0x00000001, 0x00000002)); //  1
    System.err.println(msdb(0x00000002, 0x00000001)); //  1
    System.err.println(msdb(0x00000002, 0x00000004)); //  2
    System.err.println(msdb(0x00000004, 0x00000002)); //  2
    System.err.println(msdb(0x80000000, 0x40000000)); // 31
    System.err.println(msdb(0x40000000, 0x80000000)); // 31
    System.err.println(msdb(0x00AAAAAA, 0x00555555)); // 23
    System.err.println(msdb(0x00555555, 0x00AAAAAA)); // 23
  }
}
