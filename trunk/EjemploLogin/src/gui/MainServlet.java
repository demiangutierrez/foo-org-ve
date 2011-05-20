package gui;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Anna Lezama
 */

public class MainServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new MainApp();
  }
}
