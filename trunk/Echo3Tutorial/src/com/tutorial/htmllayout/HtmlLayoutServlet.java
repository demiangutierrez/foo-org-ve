package com.tutorial.htmllayout;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

public class HtmlLayoutServlet extends WebContainerServlet {

  public ApplicationInstance newApplicationInstance() {
    return new HtmlLayoutApp();
  }
}