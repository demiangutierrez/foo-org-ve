package org.cyrano.jogl.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;

public class Export {

  public static BufferedImage exportRGBA(GL gl, int w, int h) {
    ByteBuffer pixelsRGB = ByteBuffer.allocateDirect(w * h * 4);

    gl.glReadPixels(0, 0, w, h, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, pixelsRGB);

    return transformPixelsRGBA(pixelsRGB, w, h);
  }

  // --------------------------------------------------------------------------------

  public static BufferedImage exportRGB(GL gl, int w, int h) {
    ByteBuffer pixelsRGB = ByteBuffer.allocateDirect(w * h * 3);

    gl.glReadPixels(0, 0, w, h, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, pixelsRGB);

    return transformPixelsRGB(pixelsRGB, w, h);
  }

  // --------------------------------------------------------------------------------

  public static BufferedImage transformPixelsRGBA( //
      ByteBuffer pixelsSrc, int w, int h) {

    int[] pixelsTgt = new int[w * h];

    int currSrc = 0;
    int currTgt = 0;

    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        int r = pixelsSrc.get(currSrc++);
        int g = pixelsSrc.get(currSrc++);
        int b = pixelsSrc.get(currSrc++);
        int a = pixelsSrc.get(currSrc++);

        //@begnf
        // ---------------------------------
        // |...A...|...R...|...G...|...B...|
        // 32      24      16      08     00
        // ---------------------------------
        pixelsTgt[currTgt++] =
            ((a & 0x000000FF) << 24) |
            ((r & 0x000000FF) << 16) |
            ((g & 0x000000FF) <<  8) |
            ((b & 0x000000FF)      );
        //@endnf
      }
    }

    BufferedImage ret = new BufferedImage( //
        w, h, BufferedImage.TYPE_INT_ARGB);

    ret.setRGB(0, 0, w, h, pixelsTgt, 0, w);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public static BufferedImage transformPixelsRGB( //
      ByteBuffer pixelsSrc, int w, int h) {

    int[] pixelsTgt = new int[w * h];

    int currSrc = 0;
    int currTgt = 0;

    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        int r = pixelsSrc.get(currSrc++);
        int g = pixelsSrc.get(currSrc++);
        int b = pixelsSrc.get(currSrc++);

        int a = 0x000000FF; // hardcoded

        //@begnf
        // ---------------------------------
        // |...A...|...R...|...G...|...B...|
        // 32      24      16      08     00
        // ---------------------------------
        pixelsTgt[currTgt++] =
            ((a & 0x000000FF) << 24) |
            ((r & 0x000000FF) << 16) |
            ((g & 0x000000FF) <<  8) |
            ((b & 0x000000FF)      );
        //@endnf
      }
    }

    BufferedImage ret = new BufferedImage( //
        w, h, BufferedImage.TYPE_INT_ARGB);

    ret.setRGB(0, 0, w, h, pixelsTgt, 0, w);

    return ret;
  }
}
