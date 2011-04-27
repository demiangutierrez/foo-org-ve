package com.tutorial.imagesdyn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// This class simulates some kind of DAO that brings planet data from DB according to
// the location of a user given by its id
public class PlanetLoader {

  // This simulates a query to the DB that returns some DTOs of type Planet
  public static List<Planet> loadData(int userIdInt) {
    List<Planet> ret = new ArrayList<Planet>();

    switch (userIdInt) {
      case 1 :
        // USER 1, some map
        ret.add(new Planet("Mars", 200, 150, 50, Color.RED));
        ret.add(new Planet("Earth", 40, 50, 20, Color.BLUE));
        ret.add(new Planet("Mercury", 100, 300, 60, Color.YELLOW));
        ret.add(new Planet("Neptune", 300, 260, 45, Color.CYAN));
        // Etc...
        break;
      case 2 :
        // USER 1, some different map
        ret.add(new Planet("Jupiter", 250, 100, 40, Color.PINK));
        ret.add(new Planet("Uranus", 350, 150, 20, Color.GRAY));
        ret.add(new Planet("WTF planet is green???", 50, 300, 60, Color.GREEN));
        ret.add(new Planet("Venus", 200, 260, 45, Color.MAGENTA));
        // Etc...
        break;
      default :
        // Empty, black map
        break;
    }

    return ret;
  }
}
