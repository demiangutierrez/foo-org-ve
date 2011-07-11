package org.cyrano.util.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageCache {

  private static ImageCache instance;

  // --------------------------------------------------------------------------------

  private Map<String, BufferedImage> imageMapByKey = //
  new HashMap<String, BufferedImage>();

  private String base;

  // --------------------------------------------------------------------------------

  private ImageCache(String base) {
    this.base = base;
  }

  // --------------------------------------------------------------------------------

  public static void init(String base) {
    if (instance != null) {
      throw new IllegalStateException("instance != null");
    }

    instance = new ImageCache(base);
  }

  // --------------------------------------------------------------------------------

  public static ImageCache getInstance() {
    if (instance == null) {
      throw new IllegalStateException("instance == null");
    }

    return instance;
  }

  // --------------------------------------------------------------------------------

  public BufferedImage getImage(String key) {
    BufferedImage ret = imageMapByKey.get(key);

    if (ret != null) {
      return ret;
    }

    try {
      ret = ImageIO.read( //
          ClassLoader.getSystemResourceAsStream(base + "/" + key));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    imageMapByKey.put(key, ret);

    return ret;
  }
}
