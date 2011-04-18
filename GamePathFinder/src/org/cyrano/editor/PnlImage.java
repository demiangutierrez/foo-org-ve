package org.cyrano.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.cyrano.util.Hwh;

public class PnlImage extends JPanel {

  private BufferedImage img;
  private Boolean lock = new Boolean(false);

  public PnlImage() {
    // Empty
  }

  public void setImage(final String fileBase) {
    if (fileBase == null) {
      img = null;
    } else {
      Thread imageLoader = new Thread() {
        @Override
        public void run() {
          synchronized (lock) {
            if (img != null) {
              return;
            }

            try {
              img = ImageIO.read(new File(fileBase));
            } catch (IOException e) {
              e.printStackTrace();
            }

            Dimension dim = new Dimension(Hwh.getW(img), Hwh.getH(img));

            setPreferredSize(dim);
            setMinimumSize(dim);
            setMaximumSize(dim);
            setSize(dim);
            repaint();
          }
        }
      };
      img = null;
      imageLoader.start();
    }
    repaint();
  }

  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, Hwh.getW(this), Hwh.getH(this));

    if (img != null) {
      g2d.drawImage(img, 0, 0, null);
    }
  }
}
