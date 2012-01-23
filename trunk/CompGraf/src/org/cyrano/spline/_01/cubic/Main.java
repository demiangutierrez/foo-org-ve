package org.cyrano.spline._01.cubic;

import javax.swing.JFrame;

public class Main extends JFrame {

  public Main() {
    add(new Canvas());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
