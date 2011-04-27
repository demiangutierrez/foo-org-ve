package com.minotauro.state.demo.calc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.minotauro.state.demo.calc.Calc.EventType;

public class CalcGUI extends JFrame {

  private CalcImpl calcImpl;

  private ActionListener eventActionListener;

  private JTextField txtDisplay;

  // --------------------------------------------------------------------------------

  public CalcGUI() {
    initGUI();

    calcImpl = new CalcImpl(txtDisplay);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    pack();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    eventActionListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        handleEvent(evt);
      }
    };

    setLayout(new GridBagLayout());

    GridBagConstraints gbc;

    // ----------------------------------------

    gbc = new GridBagConstraints(0, 0, 1, 1, 1, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, //
        new Insets(5, 5, 5, 5), 0, 0);

    txtDisplay = new JTextField();
    txtDisplay.setHorizontalAlignment(JTextField.RIGHT);
    txtDisplay.setEditable(false);
    txtDisplay.setBackground(Color.WHITE);
    txtDisplay.setForeground(Color.BLACK);
    add(txtDisplay, gbc);

    // ----------------------------------------

    gbc = new GridBagConstraints(0, 1, 1, 1, 0, 1, //
        GridBagConstraints.CENTER, GridBagConstraints.BOTH, //
        new Insets(0, 5, 5, 5), 0, 0);

    JPanel pnlCommand = initPnlCommand();
    add(pnlCommand, gbc);
  }

  // --------------------------------------------------------------------------------

  private JButton addButton(String caption, String command, //
      int x, int y, Insets insets, JPanel pnl) {

    GridBagConstraints gbc = new GridBagConstraints(x, y, 1, 1, 0, 0, //
        GridBagConstraints.CENTER, GridBagConstraints.NONE, insets, 0, 0);

    JButton ret = new JButton(caption);
    ret.setPreferredSize(new Dimension(60, 30));
    ret.setMinimumSize(new Dimension(60, 30));
    ret.setMaximumSize(new Dimension(60, 30));

    ret.setActionCommand(command);
    ret.addActionListener(eventActionListener);
    pnl.add(ret, gbc);

    return ret;
  }

  // --------------------------------------------------------------------------------

  private JPanel initPnlCommand() {
    JPanel ret = new JPanel();

    ret.setLayout(new GridBagLayout());

    // ----------------------------------------

    addButton("AC",/**/"clea|A", 2, 0, new Insets(0, 0, 5, 5), ret);
    addButton("C",/* */"clea|C", 3, 0, new Insets(0, 0, 5, 0), ret);

    // ----------------------------------------

    addButton("7", "numb|7", 0, 1, new Insets(0, 0, 5, 5), ret);
    addButton("8", "numb|8", 1, 1, new Insets(0, 0, 5, 5), ret);
    addButton("9", "numb|9", 2, 1, new Insets(0, 0, 5, 5), ret);
    addButton("/", "oper|/", 3, 1, new Insets(0, 0, 5, 0), ret);

    addButton("4", "numb|4", 0, 2, new Insets(0, 0, 5, 5), ret);
    addButton("5", "numb|5", 1, 2, new Insets(0, 0, 5, 5), ret);
    addButton("6", "numb|6", 2, 2, new Insets(0, 0, 5, 5), ret);
    addButton("*", "oper|*", 3, 2, new Insets(0, 0, 5, 0), ret);

    addButton("1", "numb|1", 0, 3, new Insets(0, 0, 5, 5), ret);
    addButton("2", "numb|2", 1, 3, new Insets(0, 0, 5, 5), ret);
    addButton("3", "numb|3", 2, 3, new Insets(0, 0, 5, 5), ret);
    addButton("-", "oper|-", 3, 3, new Insets(0, 0, 5, 0), ret);

    addButton("0", "numb|0", 0, 4, new Insets(0, 0, 0, 5), ret);
    addButton(".", "decp|.", 1, 4, new Insets(0, 0, 0, 5), ret);
    addButton("=", "equa|=", 2, 4, new Insets(0, 0, 0, 5), ret);
    addButton("+", "oper|+", 3, 4, new Insets(0, 0, 0, 0), ret);

    // ----------------------------------------

    return ret;
  }

  // --------------------------------------------------------------------------------

  private void handleEvent(ActionEvent evt) {
    String com = evt.getActionCommand().substring(0, 4);
    String par = evt.getActionCommand().substring(5, 6);

    Map<String, Object> parMap = new HashMap<String, Object>();
    parMap.put(CalcImpl.PARAMETER_KEY, par);

    try {
      /*   */if (com.equals("numb")) {
        calcImpl.fireEvent(EventType.numb, null, parMap);
      } else if (com.equals("oper")) {
        calcImpl.fireEvent(EventType.oper, null, parMap);
      } else if (com.equals("decp")) {
        calcImpl.fireEvent(EventType.decp, null, parMap);
      } else if (com.equals("equa")) {
        calcImpl.fireEvent(EventType.equa, null, parMap);
      } else if (com.equals("clea")) {
        if (par.equals("A")) {
          calcImpl = new CalcImpl(txtDisplay);
          txtDisplay.setText("");
          return;
        }
        calcImpl.fireEvent(EventType.clea, null, parMap);
      }
    } catch (IllegalStateException e) {
      System.err.println(e.getMessage());
    }
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new CalcGUI();
  }
}
