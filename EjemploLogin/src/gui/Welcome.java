package gui;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Style;
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
		formLogin.getActionListenerProxy().addActionListener(
				new ActionListener() {
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
		if (validateFields()){
			return;
		}
		Configuration configuration = new AnnotationConfiguration();
		configuration.configure("/db/hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("nick", formLogin.getTxtNick().getText()));
		if (criteria.list().size() == 0)
			nonexistentAccount();
		else {
			User user = (User) criteria.list().get(0);
			if (user.getNick().equals(formLogin.getTxtNick().getText())
					&& user.getPass().equals(formLogin.getTxtPass().getText())) {
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

	private boolean validateFields() {
		if (!(formLogin.getTxtNick().getText().equals("") || formLogin.getTxtPass().getText().equals(""))) {
			return false;
		}else{
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
