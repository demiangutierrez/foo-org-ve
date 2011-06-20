package org.cyrano.pacman.main;

import java.awt.Toolkit;

import javax.swing.JFrame;

import org.cyrano.util.Hwh;
import org.cyrano.util.ImageCache;

public class GameMain extends JFrame {

  // --------------------------------------------------------------------------------

  public GameMain() {
    ImageCache.init("org/cyrano/pacman/images");

    GamePanel gamePanel;

    try {
      gamePanel = new GamePanel(1);
      //gamePanel = new GamePanel("map.txt");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    add(gamePanel);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // ----------------------------------------------------------------
    // So getInsets works
    // ----------------------------------------------------------------
    // Do not uncomment or remove, this might be useful
    //    pack();
    //    setSize(gamePanel.getW() + 2 * getInsets().left, //
    //        gamePanel.getH() + getInsets().top + getInsets().bottom);
    // ----------------------------------------------------------------

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    setSize( //
        Hwh.getW(toolkit.getScreenSize()) - 100, //
        Hwh.getH(toolkit.getScreenSize()) - 100);

    setLocation(50, 50);

    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new GameMain();
  }
}
