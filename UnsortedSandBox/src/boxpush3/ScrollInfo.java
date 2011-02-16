package boxpush3;

import org.cyrano.util.PointDbl;

public class ScrollInfo {

  private int X_MIN_VIEW = 5 * MultiPanel.TILE_W;
  private int X_MAX_VIEW = 7 * MultiPanel.TILE_H;

  private int Y_MIN_VIEW = 5 * MultiPanel.TILE_W;
  private int Y_MAX_VIEW = 6 * MultiPanel.TILE_H;

  public int xView = 0;
  public int yView = 0;

  private int X_MIN_PORT = 0;
  private int X_MAX_PORT = 0;

  private int Y_MIN_PORT = 0;
  private int Y_MAX_PORT = 0;

  public ScrollInfo(TextMap textMap) {
    X_MAX_PORT = (textMap.getW() - MultiMain.SCREEN_W_IN_TILES/**/) * MultiPanel.TILE_W;
    Y_MAX_PORT = (textMap.getH() - MultiMain.SCREEN_H_IN_TILES + 1) * MultiPanel.TILE_H;
  }

  void updateScrollInfo(PointDbl scrCurr) {
    if ((scrCurr.x - X_MAX_VIEW) > xView && xView < X_MAX_PORT) {
      xView = (int) (scrCurr.x - X_MAX_VIEW);
    }
    if ((scrCurr.x - X_MIN_VIEW) < xView && xView > X_MIN_PORT) {
      xView = (int) (scrCurr.x - X_MIN_VIEW);
    }

    if ((scrCurr.y - Y_MAX_VIEW) > yView && yView < Y_MAX_PORT) {
      yView = (int) (scrCurr.y - Y_MAX_VIEW);
    }
    if ((scrCurr.y - Y_MIN_VIEW) < yView && yView > Y_MIN_PORT) {
      yView = (int) (scrCurr.y - Y_MIN_VIEW);
    }
  }
}
