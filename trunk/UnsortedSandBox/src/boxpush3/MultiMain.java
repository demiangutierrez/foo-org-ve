package boxpush3;

import javax.swing.JFrame;

public class MultiMain extends JFrame {

  public static final int SCREEN_W_IN_TILES = 15;
  public static final int SCREEN_H_IN_TILES = 15;

  public MultiMain() {
    add(new MultiPanel());

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    setSize( //
        MultiPanel.TILE_W * SCREEN_W_IN_TILES, //
        MultiPanel.TILE_H * SCREEN_H_IN_TILES);

    setVisible(true);
  }

  public static void main(String[] args) {
    new MultiMain();
  }
}
