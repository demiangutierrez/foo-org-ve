package gui;

import nextapp.echo.app.Alignment;
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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import db.User;

public class Welcome extends ContentPane {

  private FormLogin formLogin;

  public Welcome() {
    initGUI();
  }

  private void initGUI() {

    setBackground(new Color(197, 217, 161));

    Column col = new Column();
    col.setCellSpacing(new Extent(5));

    col.add(new Label("Bienvenido...!"));

    formLogin = new FormLogin();
    formLogin.getActionListenerProxy().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        formLoginClickedActionPerformed();
      }
    });
    col.add(formLogin);

    Button btn = new Button("Registrarse");
    btn.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btn.setBackground(new Color(197, 217, 161));
    btn.setWidth(new Extent(70));
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnCreateAccountClicked();
      }
    });

    col.add(btn);

    add(col);

  }

  private void formLoginClickedActionPerformed() {
    // XXX: From my point of view validation should be done directly in
    // FormLogin (you can add a "validate" method). If the proble is that
    // the form handles the errors and error messages are displayed by
    // "Welcome", then you can easily fire an event from FrmLogin that
    // tells the parent panel/container that there are error messages to
    // show
    if (validateFields()) {
      return;
    }

    // XXX: This is wrong. Configuration/Session factory should
    // be created only once (because it's an expensive operation).
    // Therefore, you should send Configuration initialization to a
    // singleton.
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // XXX: Beware with session handling, especially if you have a lot
    // of If/elses like in this case. For example, if there is no account
    // "criteria.list().size() == 0" then you are not closing neither the
    // session nor the transaction
    // See below for a possible more safe alternative implementation
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Criteria criteria = session.createCriteria(User.class).add(
        Restrictions.eq("nick", formLogin.getTxtNick().getText()));

    // XXX: You should use an criteria.uniqueResult(), you will not
    // have two accounts with the same login.
    if (criteria.list().size() == 0)
      nonexistentAccount();
    else {
      User user = (User) criteria.list().get(0);
      if (user.getNick().equals(formLogin.getTxtNick().getText())
          && user.getPass().equals(formLogin.getTxtPass().getText())) {

        // XXX It's ok that you pass only the id
        // (this way you force to reload the user object at Main)
        // But some times it's better to pass the user object anyway.
        // It is Ok as long you know that you should reload it before
        // use it. It's much better from my point of view to have a
        // "User" property than a "int userId" property.
        Main main = new Main(user.getId());
        session.getTransaction().commit();
        session.close();
        removeAll();
        add(main);
      } else {
        session.getTransaction().commit();
        session.close();
        invalidFields();
      }
    }
  }

  // --------------------------------------------------------------------------------
  // Alternative 1 to formLoginClickedActionPerformed
  private void formLoginClickedActionPerformed2() {
    if (validateFields()) {
      return;
    }

    // XXX: Like I already said, this should go to a singleton
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = null;

    try {
      session = sessionFactory.openSession();
      session.beginTransaction();

      Criteria criteria = session.createCriteria(User.class).add(
          Restrictions.eq("nick", formLogin.getTxtNick().getText()));

      User user = (User) criteria.uniqueResult();

      if (user == null) {
        nonexistentAccount();
      } else {
        if (user.getNick().equals(formLogin.getTxtNick().getText()) && //
            user.getPass().equals(formLogin.getTxtPass().getText())) {

          Main main = new Main(user.getId());
          removeAll();
          add(main);
        } else {
          invalidFields();
        }
      }

    } finally {
      // In the finally block, no matter what happen
      // we do our best effort to commit transaction
      // and close session
      if (session != null) {
        if (session.getTransaction() != null) {
          session.getTransaction().commit();
        }

        session.close();
      }
    }
  }
  // --------------------------------------------------------------------------------

  private boolean validateFields() {
    if (!(formLogin.getTxtNick().getText().equals("") || formLogin.getTxtPass().getText().equals(""))) {
      return false;
    } else {
      final WindowPane windowPane = new WindowPane();
      windowPane.setModal(true);
      windowPane.setTitleBackground(new Color(11, 46, 5));
      windowPane.setTitleForeground(Color.WHITE);
      windowPane.setBackground(new Color(197, 217, 161));
      windowPane.setTitle("Campos Obligatorios");

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
      lbl.setText("Faltan algunos campos por rellenar. Por favor ingrese todos sus datos");

      col.add(lbl);
      col.add(btnOK);
      windowPane.add(col);
      add(windowPane);
      return true;
    }
  }

  private void invalidFields() {
    final WindowPane windowPane = new WindowPane();
    windowPane.setModal(true);
    windowPane.setTitleBackground(new Color(11, 46, 5));
    windowPane.setTitleForeground(Color.WHITE);
    windowPane.setBackground(new Color(197, 217, 161));
    windowPane.setTitle("Error");

    Column col = new Column();
    col.setCellSpacing(new Extent(8));

    Label lbl = new Label();
    lbl.setText("Nick o Contraseña incorrectas");

    Button btnOK = new Button("Aceptar");
    btnOK.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btnOK.setBackground(new Color(197, 217, 161));
    btnOK.setWidth(new Extent(70));
    btnOK.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        windowPane.userClose();
      }
    });

    col.add(lbl);
    col.add(btnOK);
    windowPane.add(col);
    add(windowPane);

  }

  private void nonexistentAccount() {
    final WindowPane windowPane = new WindowPane();
    windowPane.setModal(true);
    windowPane.setTitleBackground(new Color(11, 46, 5));
    windowPane.setTitleForeground(Color.WHITE);
    windowPane.setBackground(new Color(197, 217, 161));
    windowPane.setTitle("Error");

    Column col = new Column();
    col.setCellSpacing(new Extent(8));

    Label lbl = new Label();
    lbl.setText("Usuario no registrado");

    Button btnOK = new Button("Aceptar");
    btnOK.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btnOK.setBackground(new Color(197, 217, 161));
    btnOK.setWidth(new Extent(70));
    btnOK.setAlignment(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        windowPane.userClose();
      }
    });

    col.add(lbl);
    col.add(btnOK);
    windowPane.add(col);
    add(windowPane);
  }

  protected void btnCreateAccountClicked() {
    removeAll();
    add(new CreateAccount());
  }

}
