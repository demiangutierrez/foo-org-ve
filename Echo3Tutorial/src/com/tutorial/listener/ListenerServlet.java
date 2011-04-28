package com.tutorial.listener;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class ListenerServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new ListenerApp();
  }
}
