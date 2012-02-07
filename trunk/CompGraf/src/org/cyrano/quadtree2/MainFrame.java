package org.cyrano.quadtree2;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * @author Demián Gutierrez
 */
public class MainFrame extends JFrame {

  public MainFrame() {
    setLayout(new BorderLayout());

    final GamePanel gp = new GamePanel();

    add(gp, BorderLayout.CENTER);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1200, 800);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrame();
  }
}
