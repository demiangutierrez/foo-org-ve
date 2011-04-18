package org.cyrano.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;

public class FrmMain extends JFrame {

  private PnlThumbnail pnlThumbBack;
  private PnlThumbnail pnlThumbFore;

  private PnlEditor pnlEditor;

  public FrmMain() {
    initGUI();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1024, 768);
    setVisible(true);
    setTitle("Main");
  }

  private void initGUI() {
    JComponent contentPane = (JComponent) getContentPane();
    contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    setLayout(new BorderLayout(5, 5));

    JPanel pnlWest = new JPanel();
    add(pnlWest, BorderLayout.WEST);
    pnlWest.setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    int x = 0;

    // ----------------------------------------

    JButton btnNew = new JButton("New");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNewClicked();
      }
    });
    pnlWest.add(btnNew, gbc);

    // ----------------------------------------

    JButton btnOpen = new JButton("Open");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnOpenClicked();
      }
    });
    pnlWest.add(btnOpen, gbc);

    // ----------------------------------------

    JButton btnSave = new JButton("Save");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSaveClicked();
      }
    });
    pnlWest.add(btnSave, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlWest.add(new JSeparator(), gbc);

    // ----------------------------------------

    JButton btnSize = new JButton("Choose Size");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnSize.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnSizeClicked();
      }
    });
    pnlWest.add(btnSize, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlWest.add(new JSeparator(), gbc);

    // ----------------------------------------

    ButtonGroup buttonGroup = new ButtonGroup();

    // ----------------------------------------

    JToggleButton btnBack = new JToggleButton("Backgrounds");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnBackClicked();
      }
    });
    pnlWest.add(btnBack, gbc);
    buttonGroup.add(btnBack);

    pnlThumbBack = new PnlThumbnail("grass_H.png");
    pnlThumbBack.setSize(100, 100);
    pnlThumbBack.setPreferredSize(new Dimension(100, 100));
    pnlThumbBack.setMaximumSize(new Dimension(100, 100));
    pnlThumbBack.setMinimumSize(new Dimension(100, 100));
    pnlThumbBack.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlThumbBackClicked();
      }
    });
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.NONE, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlWest.add(pnlThumbBack, gbc);

    // ----------------------------------------

    JToggleButton btnFore = new JToggleButton("Foregrounds");
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    btnBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnForeClicked();
      }
    });
    pnlWest.add(btnFore, gbc);
    buttonGroup.add(btnFore);

    pnlThumbFore = new PnlThumbnail("grass_H.png");
    pnlThumbFore.setSize(100, 100);
    pnlThumbFore.setPreferredSize(new Dimension(100, 100));
    pnlThumbFore.setMaximumSize(new Dimension(100, 100));
    pnlThumbFore.setMinimumSize(new Dimension(100, 100));
    pnlThumbFore.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pnlThumbForeClicked();
      }
    });
    gbc = new GridBagConstraints(0, x++, 1, 1, 0, 1, //
        GridBagConstraints.NORTH, GridBagConstraints.NONE, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlWest.add(pnlThumbFore, gbc);

    // ----------------------------------------

    pnlEditor = new PnlEditor();
    pnlEditor.setGraphSize(50, 50);
    //    JScrollPane scrCenter = new JScrollPane(pnlEditor);
    add(pnlEditor, BorderLayout.CENTER);
  }

  // --------------------------------------------------------------------------------

  protected void btnNewClicked() {
  }

  // --------------------------------------------------------------------------------

  protected void btnOpenClicked() {
  }

  // --------------------------------------------------------------------------------

  protected void btnSaveClicked() {
  }

  // --------------------------------------------------------------------------------

  protected void btnSizeClicked() {
    FrmSize frmSize = new FrmSize(this, pnlEditor.getGraphW(), pnlEditor.getGraphH());
    frmSize.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FrmSize frmSize = (FrmSize) evt.getSource();
        pnlEditor.setGraphSize(frmSize.getWValue(), frmSize.getHValue());
      }
    });
    frmSize.setVisible(true);
  }

  // --------------------------------------------------------------------------------

  protected void btnBackClicked() {
  }

  // --------------------------------------------------------------------------------

  protected void pnlThumbBackClicked() {
    FrmSelectImg frmSelectImg = new FrmSelectImg(this, "./backgrounds");
    frmSelectImg.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FrmSelectImg frmSelectImg = (FrmSelectImg) evt.getSource();
        pnlThumbBack.setImage(frmSelectImg.getImage());
      }
    });
    frmSelectImg.setVisible(true);
  }

  // --------------------------------------------------------------------------------

  protected void btnForeClicked() {
  }

  // --------------------------------------------------------------------------------

  protected void pnlThumbForeClicked() {
    FrmSelectImg frmSelectImg = new FrmSelectImg(this, "./foregrounds");
    frmSelectImg.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        FrmSelectImg frmSelectImg = (FrmSelectImg) evt.getSource();
        pnlThumbFore.setImage(frmSelectImg.getImage());
      }
    });
    frmSelectImg.setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new FrmMain();
  }
}
