package gui;

import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import db.User;
import echopoint.HtmlLayout;
import echopoint.layout.HtmlLayoutData;

/**
 * @author Anna Lezama
 */

public class Main extends ContentPane {

	User user;
	HtmlLayout htmlLayout;

	public Main(User user) {
		this.user = user;
		initGUI();
	}

	private void initGUI() {
		try {
			htmlLayout = new HtmlLayout( //
					getClass().getResourceAsStream("main.html"), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		setBackground(new Color(197, 217, 161));

		HtmlLayoutData hld;

		hld = new HtmlLayoutData("buttons");
		Row row = initButtons();
		row.setLayoutData(hld);
		htmlLayout.add(row);

		hld = new HtmlLayoutData("panel");
		Start start = new Start(user);
		start.setId("panel");
		start.setLayoutData(hld);
		htmlLayout.add(start);

		add(htmlLayout);
	}

	private Row initButtons() {
		Row row = new Row();
		row.setCellSpacing(new Extent(5));

		Button btnStart = new Button("Ïnicio");
		btnStart.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnStart.setBackground(new Color(117, 145, 118));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartClicked();
			}
		});

		row.add(btnStart);

		Button btnShowAccount = new Button("Ver Perfil");
		btnShowAccount.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnShowAccount.setBackground(new Color(117, 145, 118));
		btnShowAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnShowAccountClicked();
			}
		});

		row.add(btnShowAccount);

		Button btnModifiedAccount = new Button("Modificar Perfil");
		btnModifiedAccount.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnModifiedAccount.setBackground(new Color(117, 145, 118));
		btnModifiedAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnModifiedAccountClicked();
			}
		});

		row.add(btnModifiedAccount);

		Button btnLogOut = new Button("Salir");
		btnLogOut.setBorder(new Border(new Extent(2), Color.BLACK, 1));
		btnLogOut.setBackground(new Color(117, 145, 118));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLogOutClicked();
			}
		});

		row.add(btnLogOut);

		return row;
	}

	private void btnStartClicked() {
		Start star = new Start(user);
		updatePanel(star);
	}

	private void btnLogOutClicked() {
		removeAll();
		add(new Welcome());
	}

	private void btnModifiedAccountClicked() {
		ModifiedAccount modifiedAccount = new ModifiedAccount(user);
		modifiedAccount.getActionListenerProxyError().addActionListener(listenerError);
		modifiedAccount.getActionListenerProxyOk().addActionListener(listenerOk);
		updatePanel(modifiedAccount);
	}
	
	ActionListener listenerError = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			listenerErrorPerformed(evt);
		}
	};

	private void listenerErrorPerformed(ActionEvent evt) {
		WindowPane win = (WindowPane) evt.getSource();
		add(win);
	}
	
	ActionListener listenerOk = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			listenerOkPerformed(evt);
		}
	};

	private void listenerOkPerformed(ActionEvent evt) {
		ShowAccount showAccount = new ShowAccount(user);
		updatePanel(showAccount);
		WindowPane win = (WindowPane) evt.getSource();
		add(win);
	}
	
	private void btnShowAccountClicked() {
		ShowAccount showAccount = new ShowAccount(user);
		updatePanel(showAccount);
	}

	private void updatePanel(Panel panel) {
		panel.setId("panel");

		HtmlLayoutData hld = new HtmlLayoutData("panel");
		panel.setLayoutData(hld);

		htmlLayout.remove(htmlLayout.getComponent("panel"));
		htmlLayout.add(panel);

	}

}
