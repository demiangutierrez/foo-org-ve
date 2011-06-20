package org.cyrano.pacman.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Interaction;

public class InteMap {

  private Map<String, Interaction> interactionByKey = //
  new HashMap<String, Interaction>();

  // --------------------------------------------------------------------------------

  public InteMap() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(String filename) throws Exception {
    BufferedReader rd = new BufferedReader(new FileReader(filename));

    Pattern p = Pattern.compile(" +");

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("") || line.startsWith("#")) {
        continue;
      }

      String[] interactionString = p.split(line);

      if (interactionString.length != 3) {
        throw new IllegalArgumentException(line);
      }

      if (interactionString[0].startsWith("'") && //
          interactionString[1].startsWith("'")) {
        throw new IllegalArgumentException(line);
      }

      String key1 = checkAndGetKey(interactionString[0], line);
      String key2 = checkAndGetKey(interactionString[1], line);

      Interaction spriteInteraction = //
      (Interaction) Class.forName(interactionString[2]).newInstance();

      interactionByKey.put(key1 + ":" + key2, spriteInteraction);
      interactionByKey.put(key2 + ":" + key1, spriteInteraction);
    }
  }

  // --------------------------------------------------------------------------------

  private String checkAndGetKey(String keypart, String line) {
    if (!keypart.startsWith("'")) {
      try {
        return Class.forName(keypart).getName();
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    } else {
      if (keypart.length() != 3) {
        throw new IllegalArgumentException(line);
      }

      return keypart.substring(1, 2);
    }
  }

  // --------------------------------------------------------------------------------

  public Interaction get(BaseSprite sprite1, BaseSprite sprite2) {
    return interactionByKey.get( //
        /**/sprite1.getClass().getName() + ":" + //
            sprite2.getClass().getName());
  }

  // --------------------------------------------------------------------------------

  public Interaction get(BaseSprite sprite1, char tilekey) {
    return interactionByKey.get( //
        /**/sprite1.getClass().getName() + ":" + //
            tilekey);
  }
}
