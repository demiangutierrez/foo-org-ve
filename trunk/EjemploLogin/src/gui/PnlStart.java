package gui;

import nextapp.echo.app.Column;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;

import org.hibernate.Session;

import db.SessionHibernate;
import db.User;

/**
 * @author Anna Lezama
 */
public class PnlStart extends Panel {

  private User user;

  // --------------------------------------------------------------------------------

  public PnlStart(User user) {
    this.user = user;
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setStyle(GUIStyles.DEFAULT_PANEL_STYLE);

    Session session = null;

    try {
      session = SessionHibernate.getInstance().getSession();
      session.beginTransaction();

      // ----------------------------------------
      // always reload the object before use it!
      // ----------------------------------------

      user = (User) session.load(User.class, user.getId());

      Column col = new Column();
      add(col);

      col.add(new Label("¡...bienvenido al sistema...!"));
      col.add(new Label(user.getName()));

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
}
