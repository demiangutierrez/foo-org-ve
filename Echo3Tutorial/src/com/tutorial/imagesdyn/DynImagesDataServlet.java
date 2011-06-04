package com.tutorial.imagesdyn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Demián Gutierrez <br>
 *         Created on Jun 24, 2008
 */
public class DynImagesDataServlet extends HttpServlet {

  // Here we create a buffered image dynamically and then we transform it
  // to a byte array in order to stream it to the client.
  private byte[] loadImage(List<Planet> planetList) throws IOException {
    BufferedImage bimg = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);

    Graphics2D g2d = (Graphics2D) bimg.getGraphics();

    // Leer la imagen lo menos posible...
    File file = new File(getClass().getResource("pulgoso.gif").getPath());
    BufferedImage pulgoso = ImageIO.read(file);

    // g2d.setBackground(Color.CYAN);
    g2d.setBackground(Color.BLACK);
    g2d.clearRect(0, 0, bimg.getWidth(), bimg.getHeight());

    for (Planet planet : planetList) {
      g2d.setColor(planet.color);
      g2d.fillOval(planet.x - planet.r, planet.y - planet.r, //
          2 * planet.r, 2 * planet.r);

      g2d.drawImage(pulgoso, planet.x, planet.y, null);
    }

    g2d.dispose();

    // Write to a a byte array instead of a file
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bimg, "png", baos);

    return baos.toByteArray();
  }

  // --------------------------------------------------------------------------------

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) //
      throws ServletException, IOException {

    String userIdStr = req.getParameter("user_id");

    int userIdInt = -1;

    try {
      userIdInt = Integer.parseInt(userIdStr);
    } catch (NumberFormatException e) {
      // ----------------------------------------
      // Do nothing, userIdInt will remain in -1
      // ----------------------------------------
    }

    List<Planet> planetList = PlanetLoader.loadData(userIdInt);
    byte[] data = loadImage(planetList);

    res.setContentType("image/png");
    res.getOutputStream().write(data);
  }
}
