package gui;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Window;

/**
 * @author Anna Lezama
 */
public class MainApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Ejemplo Echo3 + Hibernate");

    ContentPane contentPane = new ContentPane();
    contentPane.add(new DesktopWelcome());

    window.setContent(contentPane);

    return window;
  }
}
