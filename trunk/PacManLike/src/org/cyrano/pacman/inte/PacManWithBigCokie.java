package org.cyrano.pacman.inte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Constants;
import org.cyrano.pacman.base.Interaction;
import org.cyrano.pacman.base.LevelExec;
import org.cyrano.pacman.game.GhostSprite;
import org.cyrano.pacman.game.PacManSprite;
import org.cyrano.util.PointInt;
import org.cyrano.util.game.Timer;
import org.cyrano.util.game.TimerBean;

public class PacManWithBigCokie implements Interaction {

  @Override
  public void interact(BaseSprite bs1, BaseSprite bs2) {
    final LevelExec levelExec = bs1.getLevelExec();

    PointInt grdCurr = bs1.getGrdCurr();

    char[][] data = levelExec.getData();

    data[grdCurr.x][grdCurr.y] = ' ';

    levelExec.setScore(levelExec.getScore() + Constants.SML_SCORE);
    levelExec.setSml(levelExec.getSml() + 1);

    final PacManSprite pacManSprite = (PacManSprite) bs1;

    pacManSprite.incDestroy();

    for (BaseSprite sprite : levelExec.getSpriteList()) {
      if (sprite instanceof GhostSprite) {
        GhostSprite ghostSprite = (GhostSprite) sprite;
        ghostSprite.incDestroy();
      }
    }

    ActionListener actionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        pacManSprite.decDestroy();

        for (BaseSprite sprite : levelExec.getSpriteList()) {
          if (sprite instanceof GhostSprite) {
            GhostSprite ghostSprite = (GhostSprite) sprite;
            ghostSprite.decDestroy();
          }
        }
      }
    };

    Timer timer = levelExec.getTimer();

    timer.addTimerBean( //
        new TimerBean(actionListener, new ActionEvent(this, 0, null), 5));
    timer.addTimerBean( //
        new TimerBean(actionListener, new ActionEvent(this, 0, null), 10));
  }
}
