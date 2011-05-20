package gui;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import db.SessionHibernate;
import db.User;

import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;
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
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/**
 * @author Anna Lezama
 */

public class CreateAccount extends ContentPane {

	private HtmlLayout htmlLayout;

	private TextField txtNick;
	private TextField txtName;
	private PasswordField txtPass;
	private TextField txtEmail;

	public CreateAccount() {
		initGUI();
	}

	private void initGUI() {
		try {
			htmlLayout = new HtmlLayout(
					//
					getClass().getResourceAsStream("createAccount.html"),
					"UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		setBackground(new Color(197, 217, 161));

		HtmlLayoutData hld;

		hld = new HtmlLayoutData("title");
		Label lblTitle = new Label("Ingrese sus datos para crear una cuenta!");
		lblTitle.setLayoutData(hld);
		htmlLayout.add(lblTitle);

		hld = new HtmlLayoutData("form");
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

		grid.setLayoutData(hld);
		htmlLayout.add(grid);

		hld = new HtmlLayoutData("buttons");
		Row row = new Row();
		row.setCellSpacing(new Extent(5));

		Button btnCreateAccount = new Button("Crear Cuenta");
		btnCreateAccount.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnCreateAccount.setBackground(new Color(117, 145, 118));
		btnCreateAccount.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bntCreateAccountClicked();
			}
		});

		Button btnCancel = new Button("Cancelar");
		btnCancel.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnCancel.setBackground(new Color(117, 145, 118));
		btnCancel
				.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
		btnCancel.setWidth(new Extent(60));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCancelClicked();
			}
		});

		row.add(btnCreateAccount);
		row.add(btnCancel);
		row.setLayoutData(hld);
		htmlLayout.add(row);

		add(htmlLayout);

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

		Session session = SessionHibernate.getInstance().openSession();
		session.beginTransaction();

		if (checkUser(session)) {

			session.getTransaction().commit();
			session.close();

			windowPane.setTitle("Nick existente");

			lbl.setText("Este nick ya existe. Intente con otro diferente");

			col.add(lbl);
			col.add(btnOK);
			windowPane.add(col);

			add(windowPane);
			return;
		}

		register(session);

		session.getTransaction().commit();
		session.close();

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

	private boolean checkUser(Session session) {
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("nick", txtNick.getText()));
		if (criteria.list().size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private void register(Session session) {
		User bean = new User();
		
		bean.setNick(txtNick.getText());
		bean.setPass(txtPass.getText());
		bean.setName(txtName.getText());
		bean.setEmail(txtEmail.getText());
		
		session.save(bean);
	}

	protected void btnCancelClicked() {
		removeAll();
		add(new Welcome());
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
