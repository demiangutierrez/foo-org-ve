package org.cyrano.pacman.inte;

import java.awt.event.ActionEvent;

import org.cyrano.pacman.base.BaseSprite;
import org.cyrano.pacman.base.Interaction;
import org.cyrano.pacman.game.GhostSprite;
import org.cyrano.pacman.game.PacManSprite;

public class PacManWithGhost implements Interaction {

  @Override
  public void interact(BaseSprite bs1, BaseSprite bs2) {
    if (bs1 instanceof PacManSprite) {
      internalInteract((PacManSprite) bs1, (GhostSprite) bs2);
    } else {
      internalInteract((PacManSprite) bs2, (GhostSprite) bs1);
    }
  }

  private void internalInteract(PacManSprite pacManSprite, GhostSprite ghostSprite) {
    if (pacManSprite.getDestroy() <= ghostSprite.getDestroy()) {
      pacManSprite.getActionListenerProxy().fireActionEvent(new ActionEvent(pacManSprite, 0, "die"));
    } else {
      pacManSprite.getActionListenerProxy().fireActionEvent(new ActionEvent(ghostSprite, 0, "die"));
    }
  }
}
