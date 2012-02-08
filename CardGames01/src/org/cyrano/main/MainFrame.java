package org.cyrano.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.cyrano.util.misc.ImageCache;

/**
 * @author Demián Gutierrez
 */
public class MainFrame extends JFrame {

  public MainFrame() {
    setLayout(new BorderLayout());

    ImageCache.init(".");

    add(new GamePanel(), BorderLayout.CENTER);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1024, 768);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrame();
  }
}
