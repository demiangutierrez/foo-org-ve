package org.cyrano.swing.event.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsesSomethingThatFiresEvents {

  public static void main(String[] args) {

    SourceOfActionEvents sourceOfActionEvents1 = new SourceOfActionEvents();
    SourceOfActionEvents sourceOfActionEvents2 = new SourceOfActionEvents();

    // Add the action listener to the objects...
    sourceOfActionEvents1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.err.println("Hello Source Of Action Events 1!!!");
      }
    });

    sourceOfActionEvents2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.err.println("Hello Source Of Action Events 2!!!");
      }
    });

    // Something happen and the events are fired

    sourceOfActionEvents1.doSomethingThatGeneratesAnEvent();
    sourceOfActionEvents2.doSomethingThatGeneratesAnEvent();
  }
}
