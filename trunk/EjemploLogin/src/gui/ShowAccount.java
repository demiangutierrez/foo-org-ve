package gui;

import org.hibernate.Session;

import db.SessionHibernate;
import db.User;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author Anna Lezama
 */

public class ShowAccount extends Panel {

	private User user;

	public ShowAccount(User user) {
		this.user = user;
		initGUI();
	}

	private void initGUI() {
		
		Session session = SessionHibernate.getInstance().openSession();
	    session.beginTransaction();

	    user = (User) session.load(User.class, user.getId());

	    Column col = new Column();
	    add(col);

	    col.add(new Label("Nombre: " + user.getName()));

	    col.add(new Label("Tu nick es: " + user.getNick()));
	    col.add(new Label("Tu email es: " + user.getEmail()));
	    
	    add(col);

		session.getTransaction().commit();
		session.close();

	}
	
}
