package gui;

import org.hibernate.Session;

import db.SessionHibernate;
import db.User;
import echopoint.layout.HtmlLayoutData;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

/**
 * @author Anna Lezama
 */

public class ModifiedAccount extends Panel {

	private EventListenerList eventListenerListError = //
	new EventListenerList();

	private ActionListenerProxy actionListenerProxyError = //
	new ActionListenerProxy(eventListenerListError);

	private EventListenerList eventListenerListOk = //
	new EventListenerList();

	private ActionListenerProxy actionListenerProxyOk = //
	new ActionListenerProxy(eventListenerListOk);

	private User user;

	private TextField txtName;
	private PasswordField txtPass;
	private TextField txtEmail;

	public ModifiedAccount(User user) {
		this.user = user;
		initGUI();
	}

	private void initGUI() {
		Session session = SessionHibernate.getInstance().openSession();
		session.beginTransaction();

		user = (User) session.load(User.class, user.getId());

		session.getTransaction().commit();
		session.close();

		Grid grid = new Grid(2);

		txtName = new TextField();
		txtName.setText(user.getName());
		Label lblName = new Label("Nombre");
		grid.add(lblName);
		grid.add(txtName);

		txtPass = new PasswordField();
		txtPass.setText(user.getPass());
		Label lblPass = new Label("Contraseña");
		grid.add(lblPass);
		grid.add(txtPass);

		txtEmail = new TextField();
		txtEmail.setText(user.getEmail());
		Label lblEmail = new Label("Email");
		grid.add(lblEmail);
		grid.add(txtEmail);

		Button btnModifiedAccount = new Button("Modificar Cuenta");
		btnModifiedAccount.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnModifiedAccount.setBackground(new Color(117, 145, 118));
		btnModifiedAccount.setAlignment(new Alignment(Alignment.CENTER,
				Alignment.CENTER));
		btnModifiedAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bntModifiedAccountClicked();
			}
		});
		grid.add(btnModifiedAccount);

		add(grid);
	}

	protected void bntModifiedAccountClicked() {
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
			actionListenerProxyError.fireActionEvent(new ActionEvent(windowPane, null));
			return;
		}
		
		modified();
		
		windowPane.setTitle("Cuenta Modificada");

		lbl.setText("Su cuenta fue modificada con éxito!");

		col.add(lbl);
		col.add(btnOK);
		windowPane.add(col);
		
		actionListenerProxyOk.fireActionEvent(new ActionEvent(windowPane, null));

	}
	
	private void modified() {
		Session session = SessionHibernate.getInstance().openSession();
		session.beginTransaction();
		
		user.setPass(txtPass.getText());
		user.setName(txtName.getText());
		user.setEmail(txtEmail.getText());
		session.update(user);
		
		session.getTransaction().commit();
		session.close();
	}

	private boolean validateFields() {
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
	
	public ActionListenerProxy getActionListenerProxyError() {
		return actionListenerProxyError;
	}
	
	public ActionListenerProxy getActionListenerProxyOk() {
		return actionListenerProxyOk;
	}

}
