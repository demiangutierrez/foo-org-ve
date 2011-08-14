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
 * @author Demián Gutierrez
 */
public class PnlViewAccount extends Panel {

  private User user;

  private Label lblDtaName;
  private Label lblDtaNick;
  private Label lblDtaMail;

  // --------------------------------------------------------------------------------

  public PnlViewAccount(User user) {
    this.user = user;
    initGUI();
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {
    setStyle(GUIStyles.DEFAULT_PANEL_STYLE);

    Column col = new Column();
    add(col);

    Font boldFont = new Font( //
        Font.SANS_SERIF, Font.BOLD, new Extent(12, Extent.PT));

    // ----------------------------------------

    Label lblName = new Label("nombre:");
    lblName.setFont(boldFont);
    col.add(lblName);

    lblDtaName = new Label();
    col.add(lblDtaName);

    // ----------------------------------------

    Label lblNick = new Label("nick:");
    lblNick.setFont(boldFont);
    col.add(lblNick);

    lblDtaNick = new Label();
    col.add(lblDtaNick);

    // ----------------------------------------

    Label lblMail = new Label("mail:");
    lblMail.setFont(boldFont);
    col.add(lblMail);

    lblDtaMail = new Label();
    col.add(lblDtaMail);

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

      lblDtaNick.setText(user.getNick());
      lblDtaName.setText(user.getName());
      lblDtaMail.setText(user.getMail());

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
