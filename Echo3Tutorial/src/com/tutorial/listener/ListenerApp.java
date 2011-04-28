package com.tutorial.listener;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class ListenerApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Hello World");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
