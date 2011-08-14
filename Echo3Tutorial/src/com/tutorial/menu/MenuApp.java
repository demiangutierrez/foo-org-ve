package com.tutorial.menu;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.MenuBarPane;
import nextapp.echo.extras.app.menu.DefaultMenuModel;
import nextapp.echo.extras.app.menu.DefaultOptionModel;
import nextapp.echo.extras.app.menu.MenuModel;

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

    SplitPane split = new SplitPane();
    split.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
    split.setSeparatorPosition(new Extent(20, Extent.PX));
    split.setSeparatorColor(Color.BLACK);
    split.setSeparatorHeight(new Extent(5, Extent.PX));
    contentPane.add(split);

    DefaultMenuModel baseDmm = new DefaultMenuModel();
    baseDmm.setText("BASE");

    baseDmm.addItem(initMenu("Menu A", "Item A ", 5));
    baseDmm.addItem(initMenu("Menu B", "Item B ", 5));

    MenuBarPane mbp = new MenuBarPane(baseDmm);

    mbp.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent evt) {
        System.err.println("¿?" + evt.getActionCommand());
      }
    });

    split.add(mbp);

    return window;
  }

  public MenuModel initMenu(String menuLabel, String itemBaseLabel, int count) {
    DefaultMenuModel ret = new DefaultMenuModel();
    ret.setText(menuLabel);

    for (int i = 0; i < count; i++) {
      DefaultMenuModel sub = new DefaultMenuModel();
      sub.setText(itemBaseLabel + i);
      ret.addItem(sub);

      for (int j = 0; j < count; j++) {
        DefaultOptionModel item = new DefaultOptionModel(itemBaseLabel + i + "-" + j, itemBaseLabel + i + "-" + j, null);
        //      item.setText(itemBaseLabel + i);
        sub.addItem(item);
      }
    }

    return ret;
  }

}
