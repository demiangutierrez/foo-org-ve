package gui;

import nextapp.echo.app.Button;
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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import util.ActionListenerProxy;
import db.SessionHibernate;
import db.User;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Anna Lezama
 */
public class PnlRegister extends Panel {

  private ActionListenerProxy actionListenerProxyEr = //
  new ActionListenerProxy(new EventListenerList());

  private ActionListenerProxy actionListenerProxyOk = //
  new ActionListenerProxy(new EventListenerList());

  // --------------------------------------------------------------------------------

  private String currentErrorMessage;

  // --------------------------------------------------------------------------------

  private PasswordField txtPass;

  private TextField txtNick;
  private TextField txtName;

  private TextField txtMail;

  // --------------------------------------------------------------------------------

  public PnlRegister() {
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

    Button btnCreate = new Button("Crear");
    btnCreate.setStyle(GUIStyles.DEFAULT_BUTTON_STYLE);
    btnCreate.setWidth(new Extent(60));
    btnCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        btnCreateClicked();
      }
    });
    row.add(btnCreate);
  }

  // --------------------------------------------------------------------------------

  private void btnCreateClicked() {
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

      // ----------------------------------------

      if (!checkNickAlreadyUsed(session)) {
        // XXX: don't like this, can be improved with a better desktop
        currentErrorMessage = //
        "El Nick seleccionado ya existe, intente con otro diferente";

        actionListenerProxyEr.fireActionEvent( //
            new ActionEvent(this, null));
        return; // REMEMBER finally always runs after return sentence
      }

      // ----------------------------------------

      register(session);

      // XXX: don't like this, can be improved with a better desktop
      actionListenerProxyOk.fireActionEvent( //
          new ActionEvent(this, null));
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

  private boolean checkNickAlreadyUsed(Session session) {
    Criteria criteria = session.createCriteria(User.class).add( //
        Restrictions.eq("nick", txtNick.getText()));

    if (criteria.list().size() != 0) {
      return false;
    } else {
      return true;
    }
  }

  // --------------------------------------------------------------------------------

  private void register(Session session) {
    User bean = new User();

    bean.setNick(txtNick.getText());
    bean.setPass(txtPass.getText());
    bean.setName(txtName.getText());
    bean.setMail(txtMail.getText());

    session.save(bean);
  }

  // --------------------------------------------------------------------------------

  private boolean validateFields() {
    if (txtNick.getText().equals("")) {
      return false;
    }

    if (txtName.getText().equals("")) {
      return false;
    }

    if (txtPass.getText().equals("")) {
      return false;
    }

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
}
