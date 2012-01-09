package org.cyrano.rubik.view2D;

import javax.swing.JFrame;

import org.cyrano.rubik.model.Model;

public class Main extends JFrame {

  public Main() {
    add(new Canvas(new Model(3)));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 600);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new Main();
  }
}
