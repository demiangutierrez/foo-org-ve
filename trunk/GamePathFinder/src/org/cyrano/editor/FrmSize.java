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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.cyrano.util.Hwh;

public class FrmSize extends JDialog {

  private EventListenerList eventListenerList = new EventListenerList();

  private ActionListenerProxy actionListenerProxy = new ActionListenerProxy(eventListenerList);

  private JSlider sldW;
  private JTextField txtW;

  private JSlider sldH;
  private JTextField txtH;

  private JButton btnOk;

  private JButton btnCancel;

  private final int w;

  private final int h;

  // --------------------------------------------------------------------------------

  public FrmSize(Window parent, int w, int h) {
    this.w = w;
    this.h = h;

    initGUI();
    setModal(true);

    sldWStateChanged();
    sldHStateChanged();

    //    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(320, 150);
    setTitle("Choose Size");

    setLocation((Hwh.getW(parent) - Hwh.getW(this)) / 2, (Hwh.getH(parent) - Hwh.getH(this)) / 2);
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    JComponent contentPane = (JComponent) getContentPane();
    contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    setLayout(new BorderLayout(5, 5));

    JPanel pnlCenter = new JPanel();
    add(pnlCenter, BorderLayout.CENTER);
    pnlCenter.setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    JLabel lblW = new JLabel("Width:");
    // gridx, gridy, gridw, gridh, weightx, weighty,
    gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, //
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(lblW, gbc);

    sldW = new JSlider(0, 600, w);
    sldW.setSnapToTicks(true);
    sldW.setMinorTickSpacing(1);
    sldW.setMajorTickSpacing(10);
    sldW.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        sldWStateChanged();
      }
    });
    gbc = new GridBagConstraints(1, 0, 1, 1, 0, 1, //
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(sldW, gbc);

    txtW = new JTextField();
    txtW.setEditable(false);
    txtW.setPreferredSize(new Dimension(40, Hwh.getH(txtW.getPreferredSize())));
    gbc = new GridBagConstraints(2, 0, 1, 1, 0.1, 1, //
        GridBagConstraints.WEST, GridBagConstraints.NONE, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(txtW, gbc);

    JLabel lblH = new JLabel("Height:");
    gbc = new GridBagConstraints(0, 1, 1, 1, 0, 0, //
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(lblH, gbc);

    sldH = new JSlider(0, 600, h);
    sldH.setSnapToTicks(true);
    sldH.setMinorTickSpacing(1);
    sldH.setMajorTickSpacing(10);
    sldH.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        sldHStateChanged();
      }
    });
    gbc = new GridBagConstraints(1, 1, 1, 1, 0, 1, //
        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(sldH, gbc);

    txtH = new JTextField();
    txtH.setEditable(false);
    txtH.setPreferredSize(new Dimension(40, Hwh.getH(txtH.getPreferredSize())));
    gbc = new GridBagConstraints(2, 1, 1, 1, 0.1, 1, //
        GridBagConstraints.WEST, GridBagConstraints.NONE, //
        new Insets(0, 0, 5, 0), 0, 0);
    pnlCenter.add(txtH, gbc);

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

  private void sldWStateChanged() {
    txtW.setText(Integer.toString( //
        sldW.getValue()));
  }

  // --------------------------------------------------------------------------------

  private void sldHStateChanged() {
    txtH.setText(Integer.toString( //
        sldH.getValue()));
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

  public ActionListenerProxy getActionListenerProxy() {
    return actionListenerProxy;
  }

  public void setActionListenerProxy(ActionListenerProxy actionListenerProxy) {
    this.actionListenerProxy = actionListenerProxy;
  }

  // --------------------------------------------------------------------------------

  public int getWValue() {
    return sldW.getValue();
  }

  public void setWValue(int wValue) {
    sldW.setValue(wValue);
  }

  // --------------------------------------------------------------------------------

  public int getHValue() {
    return sldH.getValue();
  }

  public void setHValue(int hValue) {
    sldH.setValue(hValue);
  }

  // --------------------------------------------------------------------------------

  //  public static void main(String[] args) {
  //    FrmSize frmSize = new FrmSize();
  //    frmSize.setWValue(450);
  //    frmSize.setHValue(150);
  //
  //    frmSize.getActionListenerProxy().addActionListener(new ActionListener() {
  //      public void actionPerformed(ActionEvent evt) {
  //        FrmSize source = (FrmSize) evt.getSource();
  //        System.err.println(source.getWValue() + ";" + source.getHValue());
  //      }
  //    });
  //  }
}
