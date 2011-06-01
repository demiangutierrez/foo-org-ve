package com.tutorial.tabpane;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

/**
 * @author: Antonio López 
 */
public class Desktop extends ContentPane {

  private TabPane tabPane;
  private int countTab = 4;

  public Desktop() {
    initGUI();
  }

  private void initGUI() {
    WindowPane ventana = new WindowPane();
    ventana.setTitle("TabPane");
    add(ventana);

    tabPane = new TabPane();
    ventana.add(tabPane);

    tabPane.setTabActiveBackground(new Color(240, 236, 187));
    tabPane.setTabActiveForeground(Color.BLACK);

    tabPane.setTabInactiveBackground(new Color(228, 228, 228));
    tabPane.setTabInactiveForeground(Color.BLACK);

    tabPane.setBackground(new Color(240, 236, 187));

    tabPane.setBorder(new Border(1, Color.BLACK, Border.STYLE_SOLID));

    TabPaneLayoutData tpld = new TabPaneLayoutData();
    tpld.setTitle("Tab 1");

    // The content of each panel can be put it it's own class
    Column col = new Column();
    col.setLayoutData(tpld);

    Label label1 = new Label("text1");
    col.add(label1);

    Button btnNew = new Button("Crear Tab");
    btnNew.setStyle(GUIStyles.DEFAULT_STYLE);
    btnNew.setWidth(new Extent(60));
    btnNew.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnNewClicked();
      }
    });
    col.add(btnNew);

    tabPane.add(col);

    tpld = new TabPaneLayoutData();
    tpld.setTitle("Tab 2");

    Label label2 = new Label("text2");
    label2.setLayoutData(tpld);
    tabPane.add(label2);

    tpld = new TabPaneLayoutData();
    tpld.setTitle("Tab 3");

    Label label3 = new Label("text3");
    label3.setLayoutData(tpld);
    tabPane.add(label3);

    tabPane.setTabCloseEnabled(true);
  }

  private void btnNewClicked() {
    TabPaneLayoutData tpld = new TabPaneLayoutData();
    tpld.setTitle("Tab " + countTab);

    Label l = new Label("text " + countTab);
    l.setEnabled(true);
    l.setLayoutData(tpld);

    tabPane.add(l);

    countTab++;
  }
}
