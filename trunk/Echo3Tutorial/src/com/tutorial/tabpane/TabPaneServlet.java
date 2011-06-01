package com.tutorial.tabpane;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class TabPaneServlet extends WebContainerServlet {

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new TabPaneApp();
  }
}
