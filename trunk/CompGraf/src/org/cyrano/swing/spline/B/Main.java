package org.cyrano.swing.spline.B;

import javax.swing.JFrame;

public class Main extends JFrame {

  public Main() {
    add(new Canvas());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 600);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
