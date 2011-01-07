package org.cyrano.boxcollision.test3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Demián Gutierrez
 */
public class MainFrameMove extends JFrame {

  public MainFrameMove() {
    setLayout(new BorderLayout());

    final GamePanelMove gp = new GamePanelMove();

    add(gp, BorderLayout.CENTER);

    JPanel pnlSouth = new JPanel();
    pnlSouth.setLayout(new FlowLayout());
    add(pnlSouth, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(700, 500);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrameMove();
  }
}
