package multitile;

import javax.swing.JFrame;

public class MultiMain extends JFrame {

  public MultiMain() {
    add(new MultiPanel());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 400);
    setVisible(true);
  }

  public static void main(String[] args) {
    new MultiMain();
  }
}
