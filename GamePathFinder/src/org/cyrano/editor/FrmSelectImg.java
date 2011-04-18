package org.cyrano.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.cyrano.util.Hwh;

public class FrmSelectImg extends JDialog {

  private EventListenerList eventListenerList = new EventListenerList();

  private ActionListenerProxy actionListenerProxy = new ActionListenerProxy(eventListenerList);

  private List<String> dirsList = new ArrayList<String>();

  private String basePath;

  // --------------------------------------------------------------------------------

  private JComboBox cboDirsList;

  private JList lstImages;

  private PnlImage pnlImage;

  private JButton btnOk;

  private JButton btnCancel;

  // --------------------------------------------------------------------------------

  public FrmSelectImg(Window parent, String basePath) {
    this.basePath = basePath;

    initGraphicsDirs();
    initGUI();
    updateGUI();
    cboDirsList.setSelectedIndex(0);

    setModal(true);

    //    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 600);
    setTitle("Choose Image / Tile");
    setLocation((Hwh.getW(parent) - Hwh.getW(this)) / 2, (Hwh.getH(parent) - Hwh.getH(this)) / 2);
  }

  // --------------------------------------------------------------------------------

  //  static Insets ins = new Insets(5, 5, 5, 5);
  //  public Insets getInsets() {
  //    return ins;
  //  }

  private void initGUI() {
    JComponent contentPane = (JComponent) getContentPane();
    contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    setLayout(new BorderLayout(5, 5));

    JPanel pnlWest = new JPanel();
    add(pnlWest, BorderLayout.WEST);
    pnlWest.setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    cboDirsList = new JComboBox(dirsList.toArray());
    cboDirsList.setMinimumSize(new Dimension(250, cboDirsList.getMinimumSize().height));
    cboDirsList.setPreferredSize(new Dimension(250, cboDirsList.getPreferredSize().height));
    cboDirsList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        cboDirsListClicked();
      }
    });
    gbc = new GridBagConstraints(0, 0, 1, 1, 1, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlWest.add(cboDirsList, gbc);

    lstImages = new JList(new DefaultListModel());
    lstImages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstImages.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        lstImagesClicked(evt);
      }
    });
    JScrollPane scpList = new JScrollPane(lstImages);
    gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, //
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, //
        new Insets(0, 0, 0, 0), 0, 0);
    pnlWest.add(scpList, gbc);

    pnlImage = new PnlImage();
    JScrollPane scpImage = new JScrollPane(pnlImage);
    add(scpImage, BorderLayout.CENTER);

    JPanel pnlSouth = new JPanel();
    add(pnlSouth, BorderLayout.SOUTH);
    pnlSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));

    btnOk = new JButton("OK");
    btnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnOkClicked();
      }
    });
    pnlSouth.add(btnOk);

    btnCancel = new JButton("Cancel");
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCancelClicked();
      }
    });
    pnlSouth.add(btnCancel);
  }

  // --------------------------------------------------------------------------------

  private void cboDirsListClicked() {
    String dirsPath = //
    (String) cboDirsList.getSelectedItem();

    File/*  */dirsFile = new File(dirsPath);
    File[]/**/fileList = dirsFile.listFiles();

    DefaultListModel dlm = //
    (DefaultListModel) lstImages.getModel();

    dlm.clear();

    for (File currFile : fileList) {
      dlm.addElement(currFile.getName());
    }

    if (!dlm.isEmpty()) {
      lstImages.setSelectedIndex(0);
    }

    updateGUI();
  }

  // --------------------------------------------------------------------------------

  private void lstImagesClicked(ListSelectionEvent evt) {
    if (lstImages.getSelectedValue() != null) {
      pnlImage.setImage(getImage());
    } else {
      pnlImage.setImage(null);
    }

    updateGUI();
  }

  // --------------------------------------------------------------------------------

  private void btnOkClicked() {
    actionListenerProxy.fireActionEvent(new ActionEvent(this, 0, null));
    dispose();
  }

  // --------------------------------------------------------------------------------

  private void btnCancelClicked() {
    dispose();
  }

  // --------------------------------------------------------------------------------

  private void updateGUI() {
    btnOk.setEnabled(lstImages.getSelectedValue() != null);
  }

  // --------------------------------------------------------------------------------

  private void initGraphicsDirs() {
    File/*  */fileBase = new File(basePath);
    File[]/**/fileList = fileBase.listFiles();
    boolean directories = false;

    for (File fileCurr : fileList) {
      if (fileCurr.isDirectory()) {
        downGraphicsDirs(fileCurr);
        directories = true;
      }
    }

    // ----------------------------------------

    if (!directories) {
      String userDir = System.getProperty("user.dir") + //
          System.getProperty("file.separator");

      dirsList.add( //
          fileBase.getAbsolutePath().substring(userDir.length()));
    }

    for (String filePath : dirsList) {
      System.out.println(filePath);
    }
  }

  // --------------------------------------------------------------------------------

  private void downGraphicsDirs(File fileBase) {
    boolean directories = false;

    File[] fileList = fileBase.listFiles();

    for (File fileCurr : fileList) {
      if (!fileCurr.isDirectory()) {
        continue;
      }

      directories = true;
      downGraphicsDirs(fileCurr);
    }

    // ----------------------------------------

    if (!directories) {
      String userDir = System.getProperty("user.dir") + //
          System.getProperty("file.separator");

      dirsList.add( //
          fileBase.getAbsolutePath().substring(userDir.length()));
    }
  }

  // --------------------------------------------------------------------------------

  public String getImage() {
    String dirsPath = //
    (String) cboDirsList.getSelectedItem();
    String filePath = //
    (String) lstImages.getSelectedValue();

    return dirsPath + //
        System.getProperty("file.separator") + filePath;
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  public void setActionListenerProxy(ActionListenerProxy actionListenerProxy) {
    this.actionListenerProxy = actionListenerProxy;
  }

  // --------------------------------------------------------------------------------
  //
  //  public static void main(String[] args) {
  //    FrmSelectImg frmSelectImg = new FrmSelectImg("./mapgraphics/");
  //
  //    frmSelectImg.getActionListenerProxy().addActionListener(new ActionListener() {
  //      public void actionPerformed(ActionEvent evt) {
  //        FrmSelectImg source = (FrmSelectImg) evt.getSource();
  //        System.err.println(source.getImage());
  //      }
  //    });
  //  }
}
