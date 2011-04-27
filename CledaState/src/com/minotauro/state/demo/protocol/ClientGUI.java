package com.minotauro.state.demo.protocol;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientGUI extends JFrame {

  private JProgressBar pbrTimeout;

  //  private JTextPane txtLog;
  private JTextArea txtLog;

  private ClientSocket clientSocket = new ClientSocket();

  private JButton btnRegister;

  private JButton btnCBid;

  private JButton btnPass;

  public ClientGUI() {
    initGUI();

    clientSocket.getActionListenerProxy().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        System.err.println(evt.getActionCommand());

        String text = txtLog.getText();

        if (!text.equals("")) {
          text += "\n";
        }

        text += evt.getActionCommand();

        txtLog.setText(text);

        Pattern p = Pattern.compile("CREG (\\d)+");
        Matcher m = p.matcher(evt.getActionCommand());

        if (m.matches()) {
          setTitle("Connected, Id is " + m.group(1));
          btnRegister.setEnabled(false);
          return;
        }

        if (evt.getActionCommand().equals("TURN")) {
          btnCBid.setEnabled(true);
          btnPass.setEnabled(true);
          runTimeout();
          return;
        }
      }
    });

    Thread th = new Thread(clientSocket);
    th.start();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(5, 5, 0, 5);

    txtLog = new JTextArea();
    txtLog.setEditable(false);
    add(new JScrollPane(txtLog), gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(5, 5, 0, 5);

    pbrTimeout = new JProgressBar();
    add(pbrTimeout, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(5, 5, 5, 5);

    JPanel pnlCommand = initPnlCommand();
    add(pnlCommand, gbc);
  }

  // --------------------------------------------------------------------------------

  private boolean running;

  private void runTimeout() {
    pbrTimeout.setMaximum(150);
    pbrTimeout.setMinimum(0);
    pbrTimeout.setValue(0);

    running = true;

    Thread th = new Thread() {
      public void run() {
        long bastime = System.currentTimeMillis();
        long tottime = ClientThread.TURN_TIMEOUT;

        while (running) {
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            // Never
          }

          long curtime = System.currentTimeMillis();

          // tottime           => 100;
          // curtime - bastime => x;

          double advance = (curtime - bastime) / (double) tottime;
          System.err.println((curtime - bastime) + ";" + advance);
          pbrTimeout.setValue((int) (advance * pbrTimeout.getMaximum()));
          pbrTimeout.repaint(); // TODO: I think this makes the counter go smooth...

          if (advance > 1) {
            running = false;
          }
        }

        pbrTimeout.setValue(0);
        btnCBid.setEnabled(false);
        btnPass.setEnabled(false);
      };
    };
    th.start();
  }

  private JPanel initPnlCommand() {
    JPanel ret = new JPanel();

    ret.setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(0, 0, 0, 5);

    btnRegister = new JButton("Register");
    btnRegister.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnRegisterClicked();
      }
    });
    ret.add(btnRegister, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(0, 0, 0, 5);

    btnCBid = new JButton("Bid");
    btnCBid.setEnabled(false);
    btnCBid.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnCBidClicked();
      }
    });
    ret.add(btnCBid, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(0, 0, 0, 5);

    btnPass = new JButton("Pass");
    btnPass.setEnabled(false);
    btnPass.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnPassClicked();
      }
    });
    ret.add(btnPass, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 3;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(0, 0, 0, 5);

    ret.add(new JLabel(""), gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 4;
    gbc.gridy = 0;
    gbc.weightx = 0;
    gbc.weighty = 0;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.insets = new Insets(0, 0, 0, 0);

    JButton btnExit = new JButton("Exit");
    ret.add(btnExit, gbc);
    btnExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        btnExitClicked();
      }
    });

    return ret;
  }

  // --------------------------------------------------------------------------------

  private void btnRegisterClicked() {
    clientSocket.sendMessage("CREG");
  }

  private void btnPassClicked() {
    clientSocket.sendMessage("PASS");
    running = false;
  }

  private void btnCBidClicked() {
    clientSocket.sendMessage("CBID");
    running = false;
  }

  private void btnExitClicked() {
    int option = JOptionPane.showConfirmDialog( //
        this, "Are you sure?", "Exit?", JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new ClientGUI();
  }
}
