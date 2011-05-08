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
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;

public class FormLogin extends Panel {

	private EventListenerList eventListenerList = new EventListenerList();

	private ActionListenerProxy actionListenerProxy = new ActionListenerProxy(
			eventListenerList);

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

		txtNick = new TextField();
		txtNick.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		Label lblNick = new Label("Nick");

		txtPass = new PasswordField();
		txtPass.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		Label lblPass = new Label("Contraseña");

		Button btnLogin = new Button("Entrar");
		btnLogin.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnLogin.setBackground(new Color(197, 217, 161));
		btnLogin.setWidth(new Extent(60));
		btnLogin.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnLoginClicked(evt);
			}
		});
		
		col.add(lblNick);
		col.add(txtNick);
		col.add(lblPass);
		col.add(txtPass);
		col.add(btnLogin);
		add(col);

	}

	private void btnLoginClicked(ActionEvent evt) {
		actionListenerProxy.fireActionEvent(evt);
	}

	public ActionListenerProxy getActionListenerProxy() {
		return actionListenerProxy;
	}

	public PasswordField getTxtPass() {
		return txtPass;
	}

	public TextField getTxtNick() {
		return txtNick;
	}

}
