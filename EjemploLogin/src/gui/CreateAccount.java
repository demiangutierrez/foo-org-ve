package gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import db.User;

public class CreateAccount extends ContentPane {

  private TextField txtNick;
  private TextField txtName;
  private PasswordField txtPass;
  private TextField txtEmail;

  public CreateAccount() {
    initGUI();
  }

  private void initGUI() {
    setBackground(new Color(117, 145, 118));

    Column col = new Column();
    col.add(new Label("Ïntroduzca sus datos para registrarse...!"));

    Grid grid = new Grid(2);

    txtNick = new TextField();
    Label lblNick = new Label("Nick/Apodo");
    grid.add(lblNick);
    grid.add(txtNick);

    txtName = new TextField();
    Label lblName = new Label("Nombre");
    grid.add(lblName);
    grid.add(txtName);

    txtPass = new PasswordField();
    Label lblPass = new Label("Contraseña");
    grid.add(lblPass);
    grid.add(txtPass);

    txtEmail = new TextField();
    Label lblEmail = new Label("Email");
    grid.add(lblEmail);
    grid.add(txtEmail);

    Button btnCreateAccount = new Button("Crear Cuenta");
    btnCreateAccount.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btnCreateAccount.setBackground(new Color(197, 217, 161));
    btnCreateAccount.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
    btnCreateAccount.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        bntCreateAccountClicked();
      }
    });

    Button btnCancel = new Button("Cancelar");
    btnCancel.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btnCancel.setBackground(new Color(197, 217, 161));
    btnCancel.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
    btnCancel.setWidth(new Extent(60));
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnCancelClicked();
      }
    });

    grid.add(btnCreateAccount);
    grid.add(btnCancel);

    col.add(grid);
    add(col);

  }

  protected void btnCancelClicked() {
    removeAll();
    add(new Welcome());
  }

  protected void bntCreateAccountClicked() {
    final WindowPane windowPane = new WindowPane();
    windowPane.setModal(true);
    windowPane.setTitleBackground(new Color(11, 46, 5));
    windowPane.setTitleForeground(Color.WHITE);
    windowPane.setBackground(new Color(197, 217, 161));

    Column col = new Column();
    col.setCellSpacing(new Extent(8));

    Button btnOK = new Button("Aceptar");
    btnOK.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        windowPane.userClose();
      }
    });

    Label lbl = new Label();

    if (!validateFields()) {

      windowPane.setTitle("Campos Obligatorios");

      lbl.setText("Faltan algunos campos por rellenar. Por favor ingrese todos sus datos");

      col.add(lbl);
      col.add(btnOK);
      windowPane.add(col);
      add(windowPane);

      return;
    }

    // XXX: Here you have a problem, you are checking user
    // and registering below in two different sessions / transactions.
    // In response to user event (for example click in a button) you
    // should open/use/close only one session and one transaccion
    // ("only one" is the the key phrase here). This way, checkUser
    // and register below should share the same session / transaction.
    if (checkUser()) {
      windowPane.setTitle("Nick existente");

      lbl.setText("Este nick ya existe. Intente con otro diferente");

      col.add(lbl);
      col.add(btnOK);
      windowPane.add(col);

      add(windowPane);
      return;

    }

    register();

    removeAll();
    add(new Welcome());

    windowPane.setTitle("Usuario registrado");
    lbl.setText("Usteda ha sido registrado satisfactoriamente");

    col.add(lbl);
    col.add(btnOK);
    windowPane.add(col);

    add(windowPane);
    return;

  }

  private void register() {
    // XXX: To a singleton
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // OK
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    User bean = new User();
    bean.setNick(txtNick.getText());
    bean.setPass(txtPass.getText());
    bean.setName(txtName.getText());
    bean.setEmail(txtEmail.getText());
    session.save(bean);

    session.getTransaction().commit();
    session.close();
  }

  private boolean checkUser() {
    // XXX: Check message above, share with register
    // XXX: To a singleton
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("nick", txtNick.getText()));
    if (criteria.list().size() == 0) {
      session.getTransaction().commit();
      session.close();
      return false;
    } else {
      session.getTransaction().commit();
      session.close();
      return true;
    }
  }

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
    if (txtEmail.getText().equals("")) {
      return false;
    }
    return true;
  }

}
