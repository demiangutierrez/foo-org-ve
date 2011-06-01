package com.tutorial.tabpane;

/**
 * @author: Antonio López
 */
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class TabPaneApp extends ApplicationInstance {

  @Override
  public Window init() {
    Window ventana = new Window();
    ventana.setTitle("TabPane");

    Desktop desktop = new Desktop();
    ventana.setContent(desktop);

    return ventana;
  }
}
