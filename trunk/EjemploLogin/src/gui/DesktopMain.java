package gui;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import util.MessageBox;
import db.User;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Anna Lezama
 */
public class DesktopMain extends ContentPane {

  // --------------------------------------------------------------------------------

  private HtmlLayout htmlLayout;

  private User user; // <----- this is the current logged user

  // --------------------------------------------------------------------------------

  public DesktopMain(User user) {
    this.user = user;

    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setBackground(new Color(197, 217, 161));

    htmlLayout = loadHtmlTemplate("main.html");
    add(htmlLayout);

    // ----------------------------------------

    Row row = initButtons();
    row.setLayoutData(new HtmlLayoutData("buttons"));
    htmlLayout.add(row);

    // ----------------------------------------

    updatePanel(new PnlStart(user));
  }

  // --------------------------------------------------------------------------------

  private Row initButtons() {
    Row row = new Row();
    row.setCellSpacing(new Extent(5));

    // ----------------------------------------

    Button btnStart = new Button("inicio");
    btnStart.setWidth(new Extent(120));
    btnStart.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnStartClicked();
      }
    });

    row.add(btnStart);

    // ----------------------------------------

    Button btnViewAccount = new Button("ver perfil");
    btnViewAccount.setWidth(new Extent(120));
    btnViewAccount.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnViewAccount.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnViewAccountClicked();
      }
    });

    row.add(btnViewAccount);

    // ----------------------------------------

    Button btnEditAccount = new Button("edt perfil");
    btnEditAccount.setWidth(new Extent(120));
    btnEditAccount.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnEditAccount.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnEditAccountClicked();
      }
    });

    row.add(btnEditAccount);

    // ----------------------------------------

    Button btnLogout = new Button("salir");
    btnLogout.setWidth(new Extent(120));
    btnLogout.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnLogout.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnLogoutClicked();
      }
    });

    row.add(btnLogout);

    // ----------------------------------------

    return row;
  }

  // --------------------------------------------------------------------------------

  private void btnStartClicked() {
    updatePanel(new PnlStart(user));
  }

  // --------------------------------------------------------------------------------

  private void btnViewAccountClicked() {
    updatePanel(new PnlViewAccount(user));
  }

  // --------------------------------------------------------------------------------

  private void btnEditAccountClicked() {
    PnlEditAccount pnlEditAccount = new PnlEditAccount(user);

    pnlEditAccount.getActionListenerProxyEr().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerErPerformed(evt);
          }
        });

    pnlEditAccount.getActionListenerProxyOk().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerOkPerformed(evt);
          }
        });

    updatePanel(pnlEditAccount);
  }

  private void listenerErPerformed(ActionEvent evt) {
    PnlEditAccount pnlEditAccount = (PnlEditAccount) evt.getSource();

    MessageBox messageBox = new MessageBox( //
        "Error", //
        pnlEditAccount.getCurrentErrorMessage(), //
        null, //
        400, 130, MessageBox.OK_OPT);

    add(messageBox);
  }

  private void listenerOkPerformed(ActionEvent evt) {
    PnlEditAccount pnlEditAccount = (PnlEditAccount) evt.getSource();

    MessageBox messageBox = new MessageBox( //
        "Ok", //
        "Su cuenta fue modificada con éxito", //
        null, //
        400, 130, MessageBox.OK_OPT);
    add(messageBox);

    user = pnlEditAccount.getUser();

    updatePanel(new PnlViewAccount(user));
  }

  // --------------------------------------------------------------------------------

  private void btnLogoutClicked() {
    removeAll();
    add(new DesktopWelcome());
  }

  // --------------------------------------------------------------------------------

  private void updatePanel(Panel panel) {
    panel.setId("panel");
    panel.setLayoutData(new HtmlLayoutData("panel"));

    htmlLayout.remove(htmlLayout.getComponent("panel"));
    htmlLayout.add(panel);
  }

  // --------------------------------------------------------------------------------

  private HtmlLayout loadHtmlTemplate(String templateName) {
    try {
      return new HtmlLayout( //
          getClass().getResourceAsStream(templateName), "UTF-8");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
