package com.tutorial.uicomp;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class UICompServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new UICompApp();
  }
}
