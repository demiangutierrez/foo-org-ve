package org.cyrano.pacman.inte;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.Interaction;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.util.PointInt;

public class PacManWithSmlCokie implements Interaction {

  @Override
  public void interact(BaseSprite bs1, BaseSprite bs2) {
    LevelExec levelExec = bs1.getLevelExec();

    PointInt grdCurr = bs1.getGrdCurr();

    char[][] data = levelExec.getData();

    data[grdCurr.x][grdCurr.y] = ' ';

    levelExec.setScore(levelExec.getScore() + Constants.SML_SCORE);
    levelExec.setSml(levelExec.getSml() + 1);
  }
}
