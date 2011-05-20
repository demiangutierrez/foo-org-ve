package gui;

import org.hibernate.Session;

import db.SessionHibernate;
import db.User;
import nextapp.echo.app.Column;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;

/**
 * @author Anna Lezama
 */

public class Start extends Panel{
	
	private User user;

	public Start(User user) {
		this.user = user;
		initGUI();
	}

	private void initGUI() {
		
		Session session = SessionHibernate.getInstance().openSession();
	    session.beginTransaction();
	    
	    user = (User) session.load(User.class, user.getId());
	    
	    Column col = new Column();
	    
	    col.add(new Label("Bienvenido al sistema"));
	    col.add(new Label(user.getName()));
	    
	    add(col);
	    
	    session.getTransaction().commit();
		session.close();
		
	}

}
