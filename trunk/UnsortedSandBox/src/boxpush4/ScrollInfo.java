package boxpush4;

public class ScrollInfo {

  public int xView = 0;
  public int yView = 0;

  private int topIns;
  private int botIns;

  private int lftIns;
  private int rghIns;

  private int lvlW;
  private int lvlH;

  private int scrW;
  private int scrH;

  // --------------------------------------------------------------------------------

  public ScrollInfo( //
      int topIns, int botIns, int lftIns, int rghIns, //
      int lvlW, int lvlH, //
      int scrW, int scrH) {

    this.topIns = topIns;
    this.botIns = botIns;
    this.lftIns = lftIns;
    this.rghIns = rghIns;

    this.lvlW = lvlW;
    this.lvlH = lvlH;
    this.scrW = scrW;
    this.scrH = scrH;
  }

  // --------------------------------------------------------------------------------

  void updateScrollInfo(Box box) {
    if (box.minX() < (xView + lftIns)) {
      xView = box.minX() - lftIns;
    }

    if (xView < 0) {
      xView = 0;
    }

    // ----------------------------------------

    if (box.minY() < (yView + topIns)) {
      yView = box.minY() - topIns;
    }

    if (yView < 0) {
      yView = 0;
    }

    // ----------------------------------------

    if (box.maxX() > (xView + scrW - rghIns)) {
      xView = box.maxX() - scrW + rghIns;
    }

    if (xView > (lvlW - scrW)) {
      xView = lvlW - scrW;
    }

    // ----------------------------------------

    if (box.maxY() > (yView + scrH - botIns)) {
      yView = box.maxY() - scrH + botIns;
    }

    if (yView > (lvlH - scrH)) {
      yView = lvlH - scrH;
    }
  }
}
