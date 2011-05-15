package gui;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import db.User;

public class Main extends ContentPane {

  private final int userId;

  public Main(int userId) {
    this.userId = userId;
    initGUI();
  }

  private void initGUI() {
    setBackground(new Color(117, 145, 118));

    // XXX: To a singleton
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // Ok
    Session session = sessionFactory.openSession();
    session.beginTransaction();

    // This is ok, you have a fresh "current session" related
    // user object ready to use. This way you won't have lazy
    // loading troubles.
    User user = (User) session.load(User.class, userId);

    Column col = new Column();
    add(col);

    col.add(new Label("Bienvenido (a) " + user.getName()));

    col.add(new Label("Tu nick es: " + user.getNick()));
    col.add(new Label("Tu email es: " + user.getEmail()));

    Button btnLogOut = new Button("Salir");
    btnLogOut.setBorder(new Border(new Extent(2), Color.BLACK, 1));
    btnLogOut.setBackground(new Color(197, 217, 161));
    btnLogOut.setWidth(new Extent(35));
    btnLogOut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        btnLogOutClicked();
      }
    });

    col.add(btnLogOut);
    
    
    // XXX: Close the session / transaction
  }

  private void btnLogOutClicked() {
    removeAll();
    add(new Welcome());
  }

}
