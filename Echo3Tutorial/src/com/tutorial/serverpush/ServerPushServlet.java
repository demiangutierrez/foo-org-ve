package com.tutorial.serverpush;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

//Autor Anna Lezama
public class ServerPushServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new ServerPushApp();
  }
}
