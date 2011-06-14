package gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.ActionListenerProxy;
import db.SessionHibernate;
import db.User;

/**
 * @author Anna Lezama
 */
public class PnlLogin extends Panel {

  private ActionListenerProxy actionListenerProxyError = //
  new ActionListenerProxy(new EventListenerList());

  private ActionListenerProxy actionListenerProxyLogin = //
  new ActionListenerProxy(new EventListenerList());

  // --------------------------------------------------------------------------------

  private TextField/* */txtNick;
  private PasswordField txtPass;

  // --------------------------------------------------------------------------------

  private String currentErrorMessage;

  private User user;

  // --------------------------------------------------------------------------------

  public PnlLogin() {
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

    Label lblNick = new Label("Nick");
    col.add(lblNick);

    txtNick = new TextField();
    txtNick.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
    col.add(txtNick);

    // ----------------------------------------

    Label lblPass = new Label("Contraseña");
    col.add(lblPass);

    txtPass = new PasswordField();
    txtPass.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
    col.add(txtPass);

    // ----------------------------------------

    Button btnLogin = new Button("Entrar");
    btnLogin.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);
    btnLogin.setWidth(new Extent(60));

    btnLogin.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnLoginClicked();
      }
    });
    col.add(btnLogin);
  }

  // --------------------------------------------------------------------------------

  private void btnLoginClicked() {
    if (!validateFields()) {
      return;
    }

    Session session = null;

    try {
      session = SessionHibernate.getInstance().getSession();
      session.beginTransaction();

      // Grouchy Smurf says: I hate criteria queries ^^
      Criteria criteria = session.createCriteria(User.class).add( //
          Restrictions.and( //
              Restrictions.eq("nick", txtNick.getText()), //
              Restrictions.eq("pass", txtPass.getText())));

      user = (User) criteria.uniqueResult();

      if (user == null) {
        currentErrorMessage = "Nick o Pass incorrectos";

        actionListenerProxyError.fireActionEvent( //
            new ActionEvent(this, null));
      } else {
        actionListenerProxyLogin.fireActionEvent( //
            new ActionEvent(this, null));
      }

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

  // XXX: @Anna: completely rewritten, compare with previous version
  private boolean validateFields() {
    boolean ok = true;

    ok &= !txtNick.getText().trim().equals("");
    ok &= !txtPass.getText().trim().equals("");

    if (ok) {
      return true;
    }

    // XXX: I don't like this approach
    // XXX: don't like this, can be improved with a better desktop
    currentErrorMessage = //
    "Por favor ingrese todos sus datos, faltan algunos campos por rellenar";

    actionListenerProxyError.fireActionEvent( //
        new ActionEvent(this, null));

    return false;
  }

  // --------------------------------------------------------------------------------

  public ActionListenerProxy getActionListenerProxyError() {
    return actionListenerProxyError;
  }

  public ActionListenerProxy getActionListenerProxyLogin() {
    return actionListenerProxyLogin;
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
