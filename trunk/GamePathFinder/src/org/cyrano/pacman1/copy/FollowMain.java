package org.cyrano.pacman1.copy;

import javax.swing.JFrame;

public class FollowMain extends JFrame {

  // --------------------------------------------------------------------------------

  public FollowMain() {
    FollowPanel followPanel;

    try {
      followPanel = new FollowPanel("map.txt");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    add(followPanel);

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    pack(); // So getInsets works
    setSize(followPanel.getW() + 2 * getInsets().left, //
        followPanel.getH() + getInsets().top + getInsets().bottom);

    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new FollowMain();
  }
}
