package multitile;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TimedKeyListenerDemo extends JFrame {
  public TimedKeyListenerDemo() {
    // some code...
    TimedKeyListener kl = new TimedKeyListener() {
      public void keyPressed(KeyEvent evt) {
        // Must be called prior to any other action!
        super.keyPressed(evt);
        System.out.println(".");
      }

      public void keyReleased(KeyEvent evt) {
        // Must be called prior to any other action!
        super.keyReleased(evt);
        // Do we have a real final key release?
        if (getReleased()) {
          // Yes, indeed :)
          System.out.println("#");
        }
      }
    };
//    JButton button = new JButton("Press any key");
    addKeyListener(kl);
    // some code...
    setSize(100, 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new TimedKeyListenerDemo();
  }
}