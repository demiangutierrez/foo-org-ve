package org.cyrano.boxcollision.test1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

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

    final JSlider sld = new JSlider();
    sld.setMinimum(0);
    sld.setMaximum(400);
    sld.setSnapToTicks(true);
    sld.setValue(100);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(700, 500);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrameMove();
  }
}
