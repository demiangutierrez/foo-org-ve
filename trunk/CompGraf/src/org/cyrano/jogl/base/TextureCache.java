package org.cyrano.jogl.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * @author Demián Gutierrez
 */
public class TextureCache {

  private static TextureCache instance;

  // --------------------------------------------------------------------------------

  private Map<String, Texture> textureMapByKey = //
  new HashMap<String, Texture>();

  private String base;

  // --------------------------------------------------------------------------------

  private TextureCache(String base) {
    this.base = base;
  }

  // --------------------------------------------------------------------------------

  public static void init(String base) {
    if (instance != null) {
      throw new IllegalStateException("instance != null");
    }

    instance = new TextureCache(base);
  }

  // --------------------------------------------------------------------------------

  public static TextureCache getInstance() {
    if (instance == null) {
      throw new IllegalStateException("instance == null");
    }

    return instance;
  }

  // --------------------------------------------------------------------------------

  public Texture getTexture(String key) {
    Texture ret = textureMapByKey.get(key);

    if (ret != null) {
      return ret;
    }

    try {
      ret = loadTexture(base + "/" + key);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    textureMapByKey.put(key, ret);

    return ret;
  }

  // --------------------------------------------------------------------------------

  private Texture loadTexture(String path) throws IOException {
    InputStream is = ClassLoader.getSystemResourceAsStream(path);

    TextureData textureData = //
    TextureIO.newTextureData(is, true, null);

    return TextureIO.newTexture(textureData);
  }
}
