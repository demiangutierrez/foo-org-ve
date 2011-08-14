package gui;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import org.hibernate.Session;

import util.ActionListenerProxy;
import db.SessionHibernate;
import db.User;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Anna Lezama
 * @author Demián Gutierrez
 */
public class PnlEditAccount extends Panel {

  private ActionListenerProxy actionListenerProxyEr = //
  new ActionListenerProxy(new EventListenerList());

  private ActionListenerProxy actionListenerProxyOk = //
  new ActionListenerProxy(new EventListenerList());

  // --------------------------------------------------------------------------------

  private String currentErrorMessage;

  // --------------------------------------------------------------------------------

  private User user;

  // --------------------------------------------------------------------------------

  private PasswordField txtPass;

  private TextField txtNick;
  private TextField txtName;

  private TextField txtMail;

  // --------------------------------------------------------------------------------

  public PnlEditAccount(User user) {
    this.user = user;
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setStyle(GUIStyles.DEFAULT_PANEL_STYLE);

    // ----------------------------------------

    Column col = new Column();
    col.setCellSpacing(new Extent(5));
    add(col);

    // ----------------------------------------

    Grid grid = new Grid(2);
    grid.setInsets(new Insets(3));
    grid.setLayoutData(new HtmlLayoutData("form"));
    col.add(grid);

    // ----------------------------------------

    Label lblNick = new Label("Nick:");
    grid.add(lblNick);

    txtNick = new TextField();
    txtNick.setEnabled(false);
    txtNick.setBackground(Color.LIGHTGRAY);
    grid.add(txtNick);

    // ----------------------------------------

    Label lblPass = new Label("Contraseña:");
    grid.add(lblPass);

    txtPass = new PasswordField();
    grid.add(txtPass);

    // ----------------------------------------

    Label lblName = new Label("Nombre:");
    grid.add(lblName);

    txtName = new TextField();
    grid.add(txtName);

    // ----------------------------------------

    Label lblEmail = new Label("Email:");
    grid.add(lblEmail);

    txtMail = new TextField();
    grid.add(txtMail);

    // ----------------------------------------

    Row row = new Row();
    row.setCellSpacing(new Extent(5));
    row.setLayoutData(new HtmlLayoutData("buttons"));
    col.add(row);

    Button btnUpdate = new Button("Guardar");
    btnUpdate.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);
    btnUpdate.setWidth(new Extent(60));
    btnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnUpdateClicked();
      }
    });
    row.add(btnUpdate);

    loadData();
  }

  // --------------------------------------------------------------------------------

  private void loadData() {
    Session session = null;

    try {
      session = SessionHibernate.getInstance().getSession();
      session.beginTransaction();

      // ----------------------------------------
      // always reload the object before use it!
      // ----------------------------------------

      user = (User) session.load(User.class, user.getId());

      txtNick.setText(user.getNick());
      txtName.setText(user.getName());
      txtMail.setText(user.getMail());

    } finally {

      // ----------------------------------------
      // whatever happens, always close
      // ----------------------------------------

      if (session != null) {
        if (session.getTransaction() != null) {
          session.getTransaction().commit();
        }

        session.close();
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void btnUpdateClicked() {
    if (!validateFields()) {
      // XXX: don't like this, can be improved with a better desktop
      currentErrorMessage = //
      "Por favor ingrese todos sus datos, faltan algunos campos por rellenar";

      actionListenerProxyEr.fireActionEvent( //
          new ActionEvent(this, null));
      return;
    }

    // ----------------------------------------

    Session session = null;

    try {
      session = SessionHibernate.getInstance().getSession();
      session.beginTransaction();

      update(session);
    } finally {

      // ----------------------------------------
      // whatever happens, always close
      // ----------------------------------------

      if (session != null) {
        if (session.getTransaction() != null) {
          session.getTransaction().commit();
        }

        session.close();
      }
    }

    // XXX: don't like this, can be improved with a better desktop
    // XXX: fire after commit or view will not update correctly
    actionListenerProxyOk.fireActionEvent( //
        new ActionEvent(this, null));
  }

  // --------------------------------------------------------------------------------

  private void update(Session session) {
    if (!txtPass.getText().equals("")) {
      user.setPass(txtPass.getText());
    }

    user.setName(txtName.getText());
    user.setMail(txtMail.getText());

    session.update(user);
  }

  // --------------------------------------------------------------------------------

  private boolean validateFields() {
    if (txtName.getText().equals("")) {
      return false;
    }

    //    if (txtPass.getText().equals("")) {
    //      return false;
    //    }

    if (txtMail.getText().equals("")) {
      return false;
    }

    return true;
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxyEr() {
    return actionListenerProxyEr;
  }

  public ActionListenerProxy getActionListenerProxyOk() {
    return actionListenerProxyOk;
  }

  // --------------------------------------------------------------------------------

  public String getCurrentErrorMessage() {
    return currentErrorMessage;
  }

  // --------------------------------------------------------------------------------

  public User getUser() {
    return user;
  }
}
