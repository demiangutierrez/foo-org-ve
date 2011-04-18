package org.cyrano.game;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

  private GamePanel gamePanel;

  public GameFrame() {
    setSize(1024, 768);
    setVisible(true);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setLayout(new BorderLayout());
    gamePanel = new GamePanel();
    add(gamePanel, BorderLayout.CENTER);

    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        switch (evt.getKeyCode()) {
          case KeyEvent.VK_PAGE_DOWN :
            gamePanel.gameMap.player0.rotate(+1);
            break;
          case KeyEvent.VK_PAGE_UP :
            gamePanel.gameMap.player0.rotate(+7);
            break;
        }
        repaint();
      }
    });

  }

  // ----------------------------------------

  public static void main(String[] args) {
    new GameFrame();
  }
}
