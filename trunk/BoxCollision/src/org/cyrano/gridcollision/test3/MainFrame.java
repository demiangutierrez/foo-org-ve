package org.cyrano.gridcollision.test3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @author Demián Gutierrez
 */
public class MainFrame extends JFrame {

  public MainFrame() {
    setLayout(new BorderLayout());

    final GamePanel gp = new GamePanel();

    add(gp, BorderLayout.CENTER);

    JButton btnNext = new JButton("Next");
    btnNext.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        gp.next();
      }
    });
    add(btnNext, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(700, 500);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrame();
  }
}
