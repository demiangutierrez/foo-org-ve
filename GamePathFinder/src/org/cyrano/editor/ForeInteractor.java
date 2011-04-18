package org.cyrano.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.cyrano.util.Hwh;

public class ForeInteractor extends APanelMouseInteractor {

  BufferedImage img;

  public ForeInteractor() {
    super(ForeInteractor.class.getName());
    try {
      img = ImageIO.read(new File("backgrounds/queen_1.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // --------------------------------------------------------------------------------

  public void attachPanelMouseInteractor(ASwingPanel swingPanel) {
  }

  public void detachPanelMouseInteractor(ASwingPanel swingPanel) {
  }

  // --------------------------------------------------------------------------------

  public void panelMouseClicked(APanelMouseEvent evt) {
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMouseEntered(APanelMouseEvent evt) {
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMouseExited(APanelMouseEvent evt) {
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMousePressed(APanelMouseEvent evt) {
    ForeTile ft = new ForeTile(//
        new ACtrlPoint(evt.getCanvasPoint().x, evt.getCanvasPoint().y), //
        new ACtrlPoint(evt.getCanvasPoint().x + Hwh.getW(img), evt.getCanvasPoint().y + Hwh.getH(img)), //
        img, img);

    ASwingPanel swingPanel = (ASwingPanel) evt.getSource();

    swingPanel.getCanvas().addController(ft);
    swingPanel.getCanvas().repaintRequest(null);
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMouseReleased(APanelMouseEvent evt) {
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMouseDragged(APanelMouseEvent evt) {
  }

  /**
   *
   *
   * @param evt
   */
  public void panelMouseMoved(APanelMouseEvent evt) {
  }
}
