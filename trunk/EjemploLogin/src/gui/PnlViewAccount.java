package gui;

import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;

import org.hibernate.Session;

import db.SessionHibernate;
import db.User;

/**
 * @author Anna Lezama
 */
public class PnlViewAccount extends Panel {

  private User user;

  // --------------------------------------------------------------------------------

  public PnlViewAccount(User user) {
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

      Font boldFont = new Font( //
          Font.SANS_SERIF, Font.BOLD, new Extent(12, Extent.PT));

      // ----------------------------------------

      Label lblName = new Label("nombre:");
      lblName.setFont(boldFont);
      col.add(lblName);

      col.add(new Label(user.getName()));

      // ----------------------------------------

      Label lblNick = new Label("nick:");
      lblNick.setFont(boldFont);
      col.add(lblNick);

      col.add(new Label(user.getNick()));

      // ----------------------------------------

      Label lblMail = new Label("mail:");
      lblMail.setFont(boldFont);
      col.add(lblMail);

      col.add(new Label(user.getMail()));

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
