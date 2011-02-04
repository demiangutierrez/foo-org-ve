package eventos.rojoverde;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MyFrame extends JFrame {

  public MyFrame() {

    MyPanel mp = new MyPanel();
    mp.addRojoVerdeListener(new RojoVerdeListener() {
      @Override
      public void rojoPerformed(RojoVerdeEvent evt) {
        System.err.println("ROJO!!!" + evt.getCoordenada());
      }

      @Override
      public void verdePerformed(RojoVerdeEvent evt) {
        System.err.println("VERDE!!!" + evt.getCoordenada());
      }
    });

    mp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("rojo")) {
          System.err.println("Action Listener ROJO!!!");
        } else {
          System.err.println("Action Listener VERDE!!!");
        }
      }
    });

    add(mp);

    setSize(640, 480);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new MyFrame();
  }
}
