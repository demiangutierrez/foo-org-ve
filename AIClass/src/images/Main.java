package images;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class Main {

  //  public static final int CONVOLUTION_W = 2;
  //  public static int[] convolutionMatrix = new int[]{ //
  //  /**/-1, +1//
  //  };

  public static final int CONVOLUTION_W2 = 3;
  public static float[] convolutionMatrix2 = new float[]{ //
  /**/+0, -1, +1,//
      -1, +0, +1,//
      -1, +1, +0,//
  };

  //@begnf
  public static final int CONVOLUTION_W1 = 5;
  public static float[] convolutionMatrix1 = new float[]{ //
  /**/+1f/273, + 4f/273, + 7f/273, + 4f/273, +1f/273,//
      +4f/273, +16f/273, +26f/273, +16f/273, +4f/273,//
      +7f/273, +26f/273, +41f/273, +26f/273, +7f/273,//
      +4f/273, +16f/273, +26f/273, +16f/273, +4f/273,//
      +1f/273, + 4f/273, + 7f/273, + 4f/273, +1f/273,//
  };
  //@endnf

  public static void main(String[] args) throws Exception {

    BufferedImage bimgSrc = ImageIO.read( //
        new FileInputStream("bradbury_building.png"));

    BufferedImage bimgTgt;

    bimgTgt = Filter.applyKernel( //
        bimgSrc, convolutionMatrix1, CONVOLUTION_W1);

    bimgSrc = bimgTgt;

    bimgTgt = Filter.applyKernel( //
        bimgSrc, convolutionMatrix2, CONVOLUTION_W2);

    ImageIO.write(bimgTgt, "png", new FileOutputStream("result.png"));
  }
}
