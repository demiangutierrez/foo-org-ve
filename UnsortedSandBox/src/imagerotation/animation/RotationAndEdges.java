package imagerotation.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.cyrano.util.misc.Hwh;

public class RotationAndEdges extends JFrame {

  private BufferedImage simpleImg;

  public RotationAndEdges() {
    try {
      simpleImg = ImageIO.read(getClass().getResource("whiteSquare.png"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  public void drawTextureRotatedImage(Graphics2D g2d, BufferedImage bimg, int x, int y, double angle) {
    AffineTransform prev = g2d.getTransform();
    Paint prevPaint = g2d.getPaint();

    // --------------------------------------------------------------------------------
    // Creates a texture with the given image (and sets it as paint to g2d)
    // --------------------------------------------------------------------------------
    TexturePaint texturePaint = new TexturePaint(bimg, new Rectangle2D.Float(0, 0, bimg.getWidth(), bimg.getHeight()));
    g2d.setPaint(texturePaint);

    // --------------------------------------------------------------------------------
    // Rotates around the center of the image
    // --------------------------------------------------------------------------------
    g2d.rotate(angle, x + Hwh.getW(bimg) / 2, y + Hwh.getH(bimg) / 2);
    g2d.fillRect(x, y, bimg.getWidth(), bimg.getHeight()); // Now fill a rect (which will paint the image as texture)

    // --------------------------------------------------------------------------------
    // Restores prev transform / paint
    // --------------------------------------------------------------------------------
    g2d.setTransform(prev);
    g2d.setPaint(prevPaint);

    // --------------------------------------------------------------------------------
    // Draws center of rotation (just to visual debug)
    // --------------------------------------------------------------------------------
    g2d.setColor(Color.RED);
    g2d.fillOval(x + Hwh.getW(bimg) / 2 - 4, y + Hwh.getH(bimg) / 2 - 4, 8, 8);
  }

  public void drawSimpleRotatedImage(Graphics2D g2d, BufferedImage bimg, int x, int y, double angle) {
    AffineTransform prev = g2d.getTransform();

    // --------------------------------------------------------------------------------
    // Rotates around the center of the image
    // --------------------------------------------------------------------------------
    g2d.rotate(angle, x + Hwh.getW(bimg) / 2, y + Hwh.getH(bimg) / 2);
    g2d.drawImage(bimg, x, y, null);

    // --------------------------------------------------------------------------------
    // Restores prev transform
    // --------------------------------------------------------------------------------
    g2d.setTransform(prev);

    // --------------------------------------------------------------------------------
    // Draws center of rotation (just to visual debug)
    // --------------------------------------------------------------------------------
    g2d.setColor(Color.RED);
    g2d.fillOval(x + Hwh.getW(bimg) / 2 - 4, y + Hwh.getH(bimg) / 2 - 4, 8, 8);
  }

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, getWidth(), getHeight());

    drawSimpleRotatedImage(g2d, simpleImg, 25, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, 0);

    drawSimpleRotatedImage(g2d, simpleImg, 100, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, Math.PI / 8);

    // Anti alias does nothing (cause it only works on drawing primitives)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    drawSimpleRotatedImage(g2d, simpleImg, 175, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, Math.PI / 8);

    // Anti alias does nothing (cause it only works on drawing primitives)
    // KEY_INTERPOLATION Also does nothing cause sun's default VM implementation 
    // DOES not alias image edges. (But works in some apple VM implementations)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    drawSimpleRotatedImage(g2d, simpleImg, 250, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, Math.PI / 8);

    // Anti alias does nothing (cause it only works on drawing primitives)
    // KEY_INTERPOLATION Also does nothing cause sun's default VM implementation 
    // DOES not alias image edges. (But works in some apple VM implementations)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    drawSimpleRotatedImage(g2d, simpleImg, 325, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, Math.PI / 8);

    // ---------------------------------------------------------------------------------------------------
    // THIS IS THE WORKING SOLUTION TO SOFT EDGES
    // So here is the workarround:
    // Taken from: http://weblogs.java.net/blog/2007/03/10/java-2d-trickery-antialiased-image-transforms
    // Basically it uses the image as a texture (there is support aliased borders on textures) and paints
    // a rectangle with the given texture
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    drawTextureRotatedImage(g2d, simpleImg, 400, (Hwh.getH(this) - Hwh.getH(simpleImg)) / 2, Math.PI / 8);
    // ---------------------------------------------------------------------------------------------------
  }

  public static void main(String[] args) {
    new RotationAndEdges();
  }
}
