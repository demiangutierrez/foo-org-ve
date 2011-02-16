package boxpush1;

import javax.swing.JFrame;

public class MultiMain extends JFrame {

  public MultiMain() {
    add(new MultiPanel());

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1060, 640);
    setVisible(true);
  }

  public static void main(String[] args) {
    new MultiMain();
  }
}
