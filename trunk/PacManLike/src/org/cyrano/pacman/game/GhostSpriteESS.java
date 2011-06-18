package org.cyrano.pacman.game;

import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.pathfinder.EmptySpaceStrategy;

public class GhostSpriteESS implements EmptySpaceStrategy {

  private GhostSprite ghostSprite;

  // --------------------------------------------------------------------------------

  public GhostSpriteESS(GhostSprite ghostSprite) {
    this.ghostSprite = ghostSprite;
  }

  // --------------------------------------------------------------------------------

  @Override
  public boolean isEmptySpace(int x, int y) {
    LevelExec levelExec = ghostSprite.getLevelExec();

    return levelExec.getData()[x][y] != 'X' && //
        levelExec.getSpriteMatrix().testStepOn(x, y, ghostSprite);
  }
}
