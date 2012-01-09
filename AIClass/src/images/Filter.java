package images;

import java.awt.image.BufferedImage;

import org.cyrano.util.misc.Hwh;

public class Filter {

  private static float getConvolutionAt( //
      int i, int j, float[] convolutionMatrix, int convolutionW) {

    if (convolutionMatrix.length % convolutionW != 0) {
      throw new RuntimeException( //
          "convolutionMatrix % CONVOLUTION_W != 0");
    }

    int index = j * convolutionW + i;

    if (index > convolutionMatrix.length) {
      throw new IllegalArgumentException( //
          "index > convolutionMatrix.length");
    }

    return convolutionMatrix[index];
  }

  // --------------------------------------------------------------------------------

  private static int getValueAt(BufferedImage bimg, int x, int y, int d) {
    if (x >= Hwh.getW(bimg) || y >= Hwh.getH(bimg)) {
      return d;
    }

    return bimg.getRaster().getSample(x, y, 0);
  }

  // --------------------------------------------------------------------------------

  private static void setValueAt(BufferedImage bimg, int x, int y, int v) {
    if (x >= Hwh.getW(bimg) || y >= Hwh.getH(bimg)) {
      throw new IllegalArgumentException( //
          "x >= Hwh.getW(bimg) || y >= Hwh.getH(bimg)");
    }

    if (v < 0) {
      v = 0;
    }

    if (v > 255) {
      v = 255;
    }

    bimg.getRaster().setSample(x, y, 0, v);
  }

  // --------------------------------------------------------------------------------

  private static void applyKernel( //
      BufferedImage bimg1, BufferedImage bimg2, //
      int x, int y, //
      float[] convolutionMatrix, int convolutionW) {

    float pixel = 0;

    for (int j = 0; j < convolutionMatrix.length / convolutionW; j++) {
      for (int i = 0; i < convolutionW; i++) {
        pixel += getValueAt(bimg1, x + i, y + j, 0) * getConvolutionAt(i, j, convolutionMatrix, convolutionW);
      }
    }

    setValueAt(bimg2, x, y, (int) pixel);
  }

  // --------------------------------------------------------------------------------

  public static BufferedImage applyKernel( //
      BufferedImage bimgSrc, //
      float[] convolutionMatrix, int convolutionW) {

    int w = Hwh.getW(bimgSrc);
    int h = Hwh.getH(bimgSrc);

    BufferedImage bimgTgt = //
    new BufferedImage(w, h, bimgSrc.getType());

    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        applyKernel(bimgSrc, bimgTgt, x, y, //
            convolutionMatrix, convolutionW);
      }
    }

    return bimgTgt;
  }
}
