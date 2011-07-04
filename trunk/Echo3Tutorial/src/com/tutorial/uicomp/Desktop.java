package com.tutorial.uicomp;

import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.SelectField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.list.DefaultListModel;

public class Desktop extends ContentPane {

  private Label lblSelectField;

  // --------------------------------------------------------------------------------

  public Desktop() {
    initGUI();
    setInsets(new Insets(5, 5, 5, 5));
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {

    // ----------------------------------------
    // Layout stuff
    // ----------------------------------------

    Grid grid = new Grid(3);
    add(grid);

    // ----------------------------------------

    grid.add(new Label("Select Field Demo:"));

    SelectField selectField = new SelectField();
    selectField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        selectFieldChanged(evt);
      }
    });

    DefaultListModel dlm = new DefaultListModel();
    selectField.setModel(dlm);

    dlm.add(new Pais("Venezuela", 1));
    dlm.add(new Pais("España", 2));
    dlm.add(new Pais("Canada", 3));
    dlm.add(new Pais("Australia", 4));
    dlm.add(new Pais("Chile", 5));
    dlm.add(new Pais("Argentina", 6));

    grid.add(selectField);

    lblSelectField = new Label();
    grid.add(lblSelectField);

    // TODO: Add list field, check boxes, radio buttos, calendars and other controls
  }

  // --------------------------------------------------------------------------------

  private void selectFieldChanged(ActionEvent evt) {
    SelectField selectField = (SelectField) evt.getSource();

    Pais pais = (Pais) selectField.getSelectedItem();

    lblSelectField.setText(pais.getNombre() + "/" + pais.getId());
  }
}
