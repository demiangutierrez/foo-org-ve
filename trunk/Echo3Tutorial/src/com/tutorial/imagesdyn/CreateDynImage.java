package com.tutorial.imagesdyn;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

// Step 1, how to generate a dynamic image with data from DB
public class CreateDynImage {

  public static void main(String[] args) throws Exception {
    BufferedImage bimg = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);

    Graphics2D g2d = (Graphics2D) bimg.getGraphics();

    g2d.clearRect(0, 0, bimg.getWidth(), bimg.getHeight());

    List<Planet> planetList = PlanetLoader.loadData(1);

    for (Planet planet : planetList) {
      g2d.setColor(planet.color);
      g2d.fillOval(planet.x - planet.r, planet.y - planet.r, //
          planet.r, planet.r);
    }

    g2d.dispose();

    // Write to a file
    ImageIO.write(bimg, "png", new File("output.png"));
  }
}
