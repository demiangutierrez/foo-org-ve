package org.cyrano.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.cyrano.util.Hwh;

public class PnlThumbnail extends JPanel {

  private EventListenerList eventListenerList = //
  new EventListenerList();

  private ActionListenerProxy actionListenerProxy = //
  new ActionListenerProxy(eventListenerList);

  private BufferedImage img;

  // --------------------------------------------------------------------------------

  public PnlThumbnail(String baseFile) {
    setImage(baseFile);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        actionListenerProxy.fireActionEvent( //
            new ActionEvent(PnlThumbnail.this, 0, null));
      }
    });
  }

  // --------------------------------------------------------------------------------

  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    int pw = Hwh.getW(this);
    int ph = Hwh.getH(this);

    int iw = Hwh.getW(img);
    int ih = Hwh.getH(img);

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, pw, ph);

    if (iw / ih < 1) {
      int xw = iw * ph / ih;
      g2d.drawImage(img, (pw - xw) / 2, 0, xw, ph, null);
    } else {
      int xh = ih * pw / iw;
      g2d.drawImage(img, 0, (ph - xh) / 2, pw, xh, null);
    }
  }

  // --------------------------------------------------------------------------------

  public void setImage(String baseFile) {
    try {
      img = ImageIO.read(new File(baseFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
    repaint();
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }
}
