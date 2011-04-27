package com.tutorial.serverpush;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

//Autor Anna Lezama
public class ServerPushApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Ejemplo ServerPush");

    Desktop desktop = new Desktop();
    window.setContent(desktop);

    return window;
  }
}
