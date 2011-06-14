package gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import util.MessageBox;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Anna Lezama
 */
public class DesktopWelcome extends ContentPane {

  private HtmlLayout htmlLayout;

  // --------------------------------------------------------------------------------

  public DesktopWelcome() {
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setBackground(new Color(197, 217, 161));

    htmlLayout = loadHtmlTemplate("welcome.html");
    add(htmlLayout);

    // ----------------------------------------

    Label lblTitle = new Label("¡...bienvenido...!");
    lblTitle.setLayoutData(new HtmlLayoutData("title"));
    htmlLayout.add(lblTitle);

    // ----------------------------------------

    updatePanel(initPnlLogin());

    // ----------------------------------------

    Row row = initButtons();
    row.setLayoutData(new HtmlLayoutData("buttons"));
    htmlLayout.add(row);
  }

  // --------------------------------------------------------------------------------

  private Row initButtons() {
    Row row = new Row();
    row.setCellSpacing(new Extent(5));
    row.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));

    // ----------------------------------------

    Button btnLogin = new Button("login");
    btnLogin.setWidth(new Extent(120));
    btnLogin.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnLoginClicked();
      }
    });

    row.add(btnLogin);

    // ----------------------------------------

    Button btnRegister = new Button("registrarse");
    btnRegister.setWidth(new Extent(120));
    btnRegister.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);

    btnRegister.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnRegisterClicked();
      }
    });

    row.add(btnRegister);

    // ----------------------------------------

    return row;
  }

  // --------------------------------------------------------------------------------
  // PnlLogin
  // --------------------------------------------------------------------------------

  private PnlLogin initPnlLogin() {
    PnlLogin pnlLogin = new PnlLogin();

    pnlLogin.getActionListenerProxyError().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerPnlLoginErrorPerformed(evt);
          }
        });

    pnlLogin.getActionListenerProxyLogin().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerPnlLoginLoginPerformed(evt);
          }
        });

    return pnlLogin;
  }

  // --------------------------------------------------------------------------------

  private void listenerPnlLoginErrorPerformed(ActionEvent evt) {
    PnlLogin pnlLogin = (PnlLogin) evt.getSource();

    MessageBox messageBox = new MessageBox( //
        "Error", //
        pnlLogin.getCurrentErrorMessage(), //
        null, //
        400, 130, MessageBox.OK_OPT);

    add(messageBox);
  }

  // --------------------------------------------------------------------------------

  private void listenerPnlLoginLoginPerformed(ActionEvent evt) {
    PnlLogin formLogin = (PnlLogin) evt.getSource();

    removeAll();
    add(new DesktopMain(formLogin.getUser()));
  }

  // --------------------------------------------------------------------------------

  private PnlRegister initPnlRegister() {
    PnlRegister pnlRegister = new PnlRegister();

    pnlRegister.getActionListenerProxyEr().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerPnlRegisterErPerformed(evt);
          }
        });

    pnlRegister.getActionListenerProxyOk().addActionListener( //
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            listenerPnlRegisterOkPerformed(evt);
          }
        });

    return pnlRegister;
  }

  // --------------------------------------------------------------------------------

  private void listenerPnlRegisterErPerformed(ActionEvent evt) {
    PnlRegister pnlRegister = (PnlRegister) evt.getSource();

    MessageBox messageBox = new MessageBox( //
        "Error", //
        pnlRegister.getCurrentErrorMessage(), //
        null, //
        400, 130, MessageBox.OK_OPT);

    add(messageBox);
  }

  // --------------------------------------------------------------------------------

  private void listenerPnlRegisterOkPerformed(ActionEvent evt) {
    MessageBox messageBox = new MessageBox( //
        "Ok", //
        "Usteda ha sido registrado satisfactoriamente", //
        null, //
        400, 130, MessageBox.OK_OPT);
    add(messageBox);

    updatePanel(initPnlLogin());
  }

  // --------------------------------------------------------------------------------

  private void btnLoginClicked() {
    updatePanel(initPnlLogin());
  }

  // --------------------------------------------------------------------------------

  private void btnRegisterClicked() {
    updatePanel(initPnlRegister());
  }

  // --------------------------------------------------------------------------------

  private void updatePanel(Panel panel) {
    panel.setId("form");
    panel.setLayoutData(new HtmlLayoutData("form"));

    htmlLayout.remove(htmlLayout.getComponent("form"));
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
