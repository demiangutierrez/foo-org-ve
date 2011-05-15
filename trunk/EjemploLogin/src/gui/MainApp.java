package gui;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Window;

public class MainApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Ejemplo de Conexiones");

    ContentPane contentPane = new ContentPane();
    contentPane.add(new Welcome());

    window.setContent(contentPane);

    return window;
  }
}
