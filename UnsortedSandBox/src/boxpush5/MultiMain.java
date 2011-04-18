package boxpush5;

import javax.swing.JFrame;

public class MultiMain extends JFrame {

  public MultiMain() {
    add(new MultiPanel());

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setSize( //
        Constants.TILE_W * Constants.SCREEN_W_IN_TILES, //
        Constants.TILE_H * Constants.SCREEN_H_IN_TILES);

    setVisible(true);
  }

  public static void main(String[] args) {
    new MultiMain();
  }
}
