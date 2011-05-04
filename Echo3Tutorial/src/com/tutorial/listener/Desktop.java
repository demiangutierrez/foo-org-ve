package com.tutorial.listener;

import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;

public class Desktop extends ContentPane {

  private OutputWindow outputWindow;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
    setInsets(new Insets(5, 5, 5, 5));
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    InputWindow inputWindow = new InputWindow();
    inputWindow.setPositionX(new Extent(20));
    inputWindow.setPositionY(new Extent(20));
    add(inputWindow);

    inputWindow.addCustomABCListener(new CustomABCListener() {
      @Override
      public void actionAAAPerformed(CustomABCEvent evt) {
        outputWindow.switchAaaState();
        outputWindow.setData(evt.getSomeData());
      }

      @Override
      public void actionBBBPerformed(CustomABCEvent evt) {
        outputWindow.switchBbbState();
        outputWindow.setData(evt.getSomeData());
      }

      @Override
      public void actionCCCPerformed(CustomABCEvent evt) {
        outputWindow.switchCccState();
        outputWindow.setData(evt.getSomeData());
      }
    });

    outputWindow = new OutputWindow();
    outputWindow.setPositionX(new Extent(300));
    outputWindow.setPositionY(new Extent(300));
    add(outputWindow);
  }
}
