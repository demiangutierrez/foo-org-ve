package org.cyrano.swing.java2D.imgalias;

import javax.swing.JFrame;

public class Demo3OnPanel extends JFrame {
  public Demo3OnPanel() {
    Demo3Panel demo3Panel = new Demo3Panel();
    add(demo3Panel);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1024, 768);
    setVisible(true);
  }

  public static void main(String[] args) {
    new Demo3OnPanel();
  }
}
