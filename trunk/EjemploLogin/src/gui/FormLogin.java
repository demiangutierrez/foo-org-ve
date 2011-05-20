package gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import db.SessionHibernate;
import db.User;

/**
 * @author Anna Lezama
 */

public class FormLogin extends Panel {

	private EventListenerList eventListenerListError = //
	new EventListenerList();

	private ActionListenerProxy actionListenerProxyError = //
	new ActionListenerProxy(eventListenerListError);

	private EventListenerList eventListenerListOk = //
	new EventListenerList();

	private ActionListenerProxy actionListenerProxyOk = //
	new ActionListenerProxy(eventListenerListOk);

	private TextField txtNick;
	private PasswordField txtPass;

	public FormLogin() {
		initGUI();
	}

	private void initGUI() {
		setBackground(new Color(117, 145, 118));
		setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		setBorder(new Border(new Extent(2), Color.GREEN, 1));
		setWidth(new Extent(200));

		Column col = new Column();
		
		Label lblNick = new Label("Nick");
		col.add(lblNick);

		txtNick = new TextField();
		txtNick.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		col.add(txtNick);
		
		Label lblPass = new Label("Contraseña");
		col.add(lblPass);
		
		txtPass = new PasswordField();
		txtPass.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		col.add(txtPass);

		Button btnLogin = new Button("Entrar");
		btnLogin.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnLogin.setBackground(new Color(197, 217, 161));
		btnLogin.setWidth(new Extent(60));
		btnLogin.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				formLoginClickedActionPerformed2();
			}
		});
		col.add(btnLogin);
		
		add(col);

	}

	private void formLoginClickedActionPerformed2() {
		if (validateFields()) {
			return;
		}

		Session session = null;

		try {
			session = SessionHibernate.getInstance().openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(User.class).add(
					Restrictions.eq("nick", txtNick.getText()));

			User user = (User) criteria.uniqueResult();

			if (user == null) {
				nonexistentAccount();
			} else {
				if (user.getNick().equals(txtNick.getText()) && //
						user.getPass().equals(txtPass.getText())) {
					actionListenerProxyOk.fireActionEvent(new ActionEvent(user, null));
				} else {
					invalidFields();
				}
			}

		} finally {
			if (session != null) {
				if (session.getTransaction() != null) {
					session.getTransaction().commit();
				}

				session.close();
			}
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
	    actionListenerProxyError.fireActionEvent(new ActionEvent(windowPane, null));
		
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
	    actionListenerProxyError.fireActionEvent(new ActionEvent(windowPane, null));
		
	}

	private boolean validateFields() {
		if (!(txtNick.getText().equals("") || txtPass.getText().equals(""))) {
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

			actionListenerProxyError.fireActionEvent(new ActionEvent(windowPane,
					null));
			return true;
		}
	}

	public ActionListenerProxy getActionListenerProxyError() {
		return actionListenerProxyError;
	}
	
	public ActionListenerProxy getActionListenerProxyOk() {
		return actionListenerProxyOk;
	}

}
