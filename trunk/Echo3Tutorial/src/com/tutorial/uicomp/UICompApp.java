package com.tutorial.uicomp;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class UICompApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Hello World");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
