package org.cyrano.geom.pathfollow;

import javax.swing.JFrame;

public class FollowMain extends JFrame {

  public FollowMain() {
    add(new FollowPanel());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    setVisible(true);
  }

  public static void main(String[] args) {
    new FollowMain();
  }
}
