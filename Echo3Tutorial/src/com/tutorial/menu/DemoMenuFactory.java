package com.tutorial.menu;

import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.DropDownMenu;
import nextapp.echo.extras.app.MenuBarPane;
import nextapp.echo.extras.app.menu.DefaultMenuModel;
import nextapp.echo.extras.app.menu.DefaultOptionModel;
import nextapp.echo.extras.app.menu.MenuModel;

public class DemoMenuFactory {

  public static MenuBarPane createDemoMenuBarPane() {
    DefaultMenuModel baseDmm = new DefaultMenuModel();

    baseDmm.setText("BASE");

    baseDmm.addItem(initMenu("Menu A", "Item A ", 5));
    baseDmm.addItem(initMenu("Menu B", "Item B ", 5));

    MenuBarPane mbp = new MenuBarPane(baseDmm);

    mbp.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        System.err.println("menu id: " + evt.getActionCommand());
      }
    });

    return mbp;
  }

  // --------------------------------------------------------------------------------

  public static DropDownMenu createDemoDropDownMenu() {
    DefaultMenuModel baseDmm = new DefaultMenuModel();

    baseDmm.setText("BASE");

    baseDmm.addItem(initMenu("Menu A", "Item A ", 5));
    baseDmm.addItem(initMenu("Menu B", "Item B ", 5));

    DropDownMenu ddm = new DropDownMenu(baseDmm);
    ddm.setSelectionText("Main Menu");
    ddm.setInsets(new Insets(3, 3, 20, 3));

    ddm.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        System.err.println("menu id: " + evt.getActionCommand());
      }
    });

    return ddm;
  }

  // --------------------------------------------------------------------------------

  public static MenuModel initMenu(String menuLabel, String itemBaseLabel, int count) {
    DefaultMenuModel ret = new DefaultMenuModel();
    ret.setText(menuLabel);

    for (int i = 0; i < count; i++) {
      DefaultMenuModel sub = new DefaultMenuModel();
      sub.setText(itemBaseLabel + i);
      ret.addItem(sub);

      for (int j = 0; j < count; j++) {
        DefaultOptionModel item = new DefaultOptionModel(
            itemBaseLabel + i + "-" + j, itemBaseLabel + i + "-" + j, null);

        sub.addItem(item);
      }
    }

    return ret;
  }
}
