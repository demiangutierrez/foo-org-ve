/*
 * Created on 13/04/2007
 */
package util;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;

import org.apache.commons.lang.StringUtils;

/**
 * @author Demián Gutierrez
 */
public class MessageBox extends WindowPane {

  public static final int YE_OPT = 1;
  public static final int NO_OPT = 2;
  public static final int OK_OPT = 4;
  public static final int CA_OPT = 8;

  // --------------------------------------------------------------------------------

  protected ActionListener actionListener;

  protected String message;

  protected int option;

  // --------------------------------------------------------------------------------

  public MessageBox(String title, String message, //
      ActionListener actionListener, //
      int w, int h, int option) {

    setTitle(title);

    this.message = message;

    this.actionListener = actionListener;

    this.option = option;

    setWidth(new Extent(w));
    setHeight(new Extent(h));

    setResizable(false);
    setClosable(false);
    setModal(true);

    initGUI();
  }

  // --------------------------------------------------------------------------------

  protected void initGUI() {
    Column col = new Column();
    col.setCellSpacing(new Extent(15));
    col.setInsets(new Insets(5, 5, 5, 5));
    add(col);

    ColumnLayoutData cld;

    // ----------------------------------------
    // Message
    // ----------------------------------------

    Label lblMessage = new Label(message);
    cld = new ColumnLayoutData();
    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
    lblMessage.setLayoutData(cld);
    col.add(lblMessage);

    // ----------------------------------------
    // Buttons row
    // ----------------------------------------

    Row row = new Row();
    row.setCellSpacing(new Extent(5));
    cld.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
    row.setLayoutData(cld);
    col.add(row);

    // ----------------------------------------
    // Ye button
    // ----------------------------------------

    if ((option & YE_OPT) != 0) {
      Button btnYe = new Button("Si"); // TODO: I18N
      btnYe.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
      btnYe.setWidth(new Extent(60));
      // TODO: btnYe.setStyle(ButtonEx.DEFAULT_STYLE);
      btnYe.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(evt, Integer.toString(YE_OPT)));
          };
          userClose();
        }
      });
      row.add(btnYe);
    }

    // ----------------------------------------
    // No button
    // ----------------------------------------

    if ((option & NO_OPT) != 0) {
      Button btnNo = new Button("No"); // TODO: I18N
      btnNo.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
      btnNo.setWidth(new Extent(60));
      // TODO: btnNo.setStyle(ButtonEx.DEFAULT_STYLE);
      btnNo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(evt, Integer.toString(NO_OPT)));
          };
          userClose();
        }
      });
      row.add(btnNo);
    }

    // ----------------------------------------
    // Ok button
    // ----------------------------------------

    if ((option & OK_OPT) != 0) {
      Button btnOk = new Button("Ok"); // TODO: I18N
      btnOk.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
      btnOk.setWidth(new Extent(60));
      // TODO: btnOk.setStyle(ButtonEx.DEFAULT_STYLE);
      btnOk.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(evt, Integer.toString(OK_OPT)));
          };
          userClose();
        }
      });
      row.add(btnOk);
    }

    // ----------------------------------------
    // Cancel button
    // ----------------------------------------

    if ((option & CA_OPT) != 0) {
      Button btnCa = new Button("Cancelar"); // TODO: I18N
      btnCa.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
      btnCa.setWidth(new Extent(60));
      // TODO: btnCa.setStyle(ButtonEx.DEFAULT_STYLE);
      btnCa.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          if (actionListener != null) {
            actionListener.actionPerformed(new ActionEvent(evt, Integer.toString(CA_OPT)));
          };
          userClose();
        }
      });
      row.add(btnCa);
    }
  }

  // --------------------------------------------------------------------------------

  // non standard desktop
  //  public static void msgBox(String title, String message, //
  //      ActionListener modalActionListener, //
  //      int w, int h, int option) {
  //    MessageOk box = new MessageOk(title, message, modalActionListener, w, h, option);
  //    BaseAppInstance.getDesktop().addForm(box);
  //  }

  // --------------------------------------------------------------------------------

  public static boolean isOption(ActionEvent evt, int option) {
    return StringUtils.equals(evt.getActionCommand(), Integer.toString(option));
  }
}
