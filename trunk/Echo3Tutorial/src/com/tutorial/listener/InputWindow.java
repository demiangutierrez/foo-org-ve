package com.tutorial.listener;

import java.util.EventListener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;
import nextapp.echo.app.layout.ColumnLayoutData;

import com.tutorial.listener.CustomABCListener.Method;

public class InputWindow extends WindowPane {

	private EventListenerList eventListenerList = new EventListenerList();

	private TextField txtField;

	// --------------------------------------------------------------------------------

	public InputWindow() {
		setTitle("Hello World!");
		setInsets(new Insets(5, 5, 5, 5));
		setClosable(false);

		initGUI();
	}

	// --------------------------------------------------------------------------------

	private void initGUI() {
		ColumnLayoutData cld;

		Column col = new Column();
		add(col);

		txtField = new TextField();
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		txtField.setLayoutData(cld);
		col.add(txtField);

		// ----------------------------------------

		Row rowCommand = new Row();
		rowCommand.setCellSpacing(new Extent(5, Extent.PX));
		rowCommand.setInsets(new Insets(0, 5, 0, 0));
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		rowCommand.setLayoutData(cld);
		col.add(rowCommand);

		// ----------------------------------------

		Button btnAaa = new Button("AAA");
		btnAaa.setStyle(GUIStyles.DEFAULT_STYLE);
		btnAaa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAaaClicked(evt);
			}
		});
		rowCommand.add(btnAaa);

		Button btnBbb = new Button("BBB");
		btnBbb.setStyle(GUIStyles.DEFAULT_STYLE);
		btnBbb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBbbClicked(evt);
			}
		});
		rowCommand.add(btnBbb);

		Button btnCcc = new Button("CCC");
		btnCcc.setStyle(GUIStyles.DEFAULT_STYLE);
		btnCcc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCccClicked(evt);
			}
		});
		rowCommand.add(btnCcc);

	}

	protected void btnAaaClicked(ActionEvent evt) {
		fireCustomABCEvent(new CustomABCEvent(this, txtField.getText()),
				Method.AAA);
	}

	protected void btnBbbClicked(ActionEvent evt) {
		fireCustomABCEvent(new CustomABCEvent(this, txtField.getText()),
				Method.BBB);
	}

	protected void btnCccClicked(ActionEvent evt) {
		fireCustomABCEvent(new CustomABCEvent(this, txtField.getText()),
				Method.CCC);
	}

	// --------------------------------------------------------------------------------

	public void addCustomABCListener(CustomABCListener listener) {
		eventListenerList.addListener(CustomABCListener.class, listener);
	}

	public void delCustomABCListener(CustomABCListener listener) {
		eventListenerList.removeListener(CustomABCListener.class, listener);
	}

	public EventListener[] getCustomABCListener() {
		return eventListenerList.getListeners(CustomABCListener.class);
	}

	public void fireCustomABCEvent(CustomABCEvent evt, Method method) {
		EventListener[] eventListeners = getCustomABCListener();

		for (int i = 0; i < eventListeners.length; i++) {
			CustomABCListener listener = (CustomABCListener) eventListeners[i];

			switch (method) {
			case AAA:
				listener.actionAAAPerformed(evt);
				break;
			case BBB:
				listener.actionBBBPerformed(evt);
				break;
			case CCC:
				listener.actionCCCPerformed(evt);
				break;

			default:
				throw new IllegalArgumentException();
			}
		}
	}
}
