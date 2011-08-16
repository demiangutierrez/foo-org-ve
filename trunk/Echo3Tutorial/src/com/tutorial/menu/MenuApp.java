package com.tutorial.menu;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.Window;
import nextapp.echo.app.WindowPane;
import nextapp.echo.extras.app.DropDownMenu;
import nextapp.echo.extras.app.MenuBarPane;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

public class MenuApp extends ApplicationInstance {

  public Window init() {

    // ----------------------------------------
    // Construye una ventana (del navegador)
    // ----------------------------------------

    Window window = new Window();
    window.setTitle("Hola Mundo");

    // ----------------------------------------
    // Construye un panel raiz y se lo asigna
    // a la ventana
    // ----------------------------------------

    ContentPane contentPane = new ContentPane();
    window.setContent(contentPane);

    // ----------------------------------------
    // Construye la GUI y la añade al panel
    // raiz recien construido
    // ----------------------------------------

    SplitPane split1 = new SplitPane();
    split1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
    split1.setSeparatorPosition(new Extent(30, Extent.PX));
    split1.setSeparatorColor(Color.BLACK);
    split1.setResizable(true);
    split1.setSeparatorHeight(new Extent(5, Extent.PX));
    contentPane.add(split1);

    MenuBarPane mbp = DemoMenuFactory.createDemoMenuBarPane();
    split1.add(mbp);

    SplitPane split2 = new SplitPane();
    split2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
    split2.setSeparatorPosition(new Extent(30, Extent.PX));
    split2.setSeparatorColor(Color.BLACK);
    split2.setResizable(true);
    split2.setSeparatorHeight(new Extent(5, Extent.PX));
    split1.add(split2);

    Row row = new Row();
    //row.add(DemoMenuFactory.createDemoMenuBarPane()); // Can't be done
    row.add(DemoMenuFactory.createDemoDropDownMenu());
    split2.add(row);

    HtmlLayout htmlLayout;

    try {
      htmlLayout = new HtmlLayout( //
          getClass().getResourceAsStream("template.html"), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    HtmlLayoutData hld;

    hld = new HtmlLayoutData("label1");
    Label lblLabel1 = new Label("Menu 1");
    lblLabel1.setLayoutData(hld);
    htmlLayout.add(lblLabel1);

    hld = new HtmlLayoutData("menu1");
    //MenuBarPane ddm1 = DemoMenuFactory.createDemoMenuBarPane(); // Can't be done
    DropDownMenu ddm1 = DemoMenuFactory.createDemoDropDownMenu();
    ddm1.setLayoutData(hld);
    htmlLayout.add(ddm1);

    hld = new HtmlLayoutData("label2");
    Label lblLabel2 = new Label("Menu 2");
    lblLabel2.setLayoutData(hld);
    htmlLayout.add(lblLabel2);

    hld = new HtmlLayoutData("menu2");
    DropDownMenu ddm2 = DemoMenuFactory.createDemoDropDownMenu();
    ddm2.setLayoutData(hld);
    htmlLayout.add(ddm2);

    split2.add(htmlLayout);

    WindowPane windowPane = new WindowPane();
    windowPane.setClosable(false);
    contentPane.add(windowPane);

    SplitPane split3 = new SplitPane();
    split3.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
    split3.setSeparatorPosition(new Extent(30, Extent.PX));
    split3.setSeparatorColor(Color.BLACK);
    split3.setResizable(true);
    split3.setSeparatorHeight(new Extent(5, Extent.PX));
    windowPane.add(split3);

    split3.add(DemoMenuFactory.createDemoMenuBarPane());

    return window;
  }
}
