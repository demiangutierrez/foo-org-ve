package org.cyrano.pacman3;

import java.awt.Toolkit;

import javax.swing.JFrame;

import org.cyrano.util.Hwh;

public class GameMain extends JFrame {

  // --------------------------------------------------------------------------------

  public GameMain() {
    GamePanel gamePanel;

    try {
      gamePanel = new GamePanel("map_big.txt");
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
        Hwh.getW(toolkit.getScreenSize()) - 600, //
        Hwh.getH(toolkit.getScreenSize()) - 200);

    setLocation(50, 50);

    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new GameMain();
  }
}
