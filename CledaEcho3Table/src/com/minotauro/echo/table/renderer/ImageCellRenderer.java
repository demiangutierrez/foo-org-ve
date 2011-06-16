/*
 * Created on 15/06/2011
 */
package com.minotauro.echo.table.renderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;

import com.minotauro.echo.table.base.ETable;

/**
 * @author Demián Gutierrez
 */
public class ImageCellRenderer extends BaseCellRenderer {

  public ImageCellRenderer() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public Component getCellRenderer(ETable table, Object value, int col, int row) {
    Label ret = new Label();

    if (foreground != null) {
      ret.setForeground(foreground);
    }

    if (value == null) {
      ret.setText("-");
    } else {
      ret.setIcon((ImageReference) value);
    }

    return ret;
  }
}
