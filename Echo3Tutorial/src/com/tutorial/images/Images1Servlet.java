package com.tutorial.images;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;

/**
 * @author Demián Gutierrez
 * <br> Created on Jun 24, 2008
 */
public class Images1Servlet extends WebContainerServlet {

  @Override
  public ApplicationInstance newApplicationInstance() {
    return new Images1App();
  }
}
