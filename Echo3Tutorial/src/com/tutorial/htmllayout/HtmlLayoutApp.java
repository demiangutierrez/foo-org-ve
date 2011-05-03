package com.tutorial.htmllayout;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

public class HtmlLayoutApp extends ApplicationInstance {

  public Window init() {
    Window window = new Window();
    window.setTitle("Html Layout Panel");

    HtmlLayoutPanel htmlLayoutPanel = new HtmlLayoutPanel();
    window.setContent(htmlLayoutPanel);

    return window;
  }
}
