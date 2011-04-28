package com.tutorial.listener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.Style;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.layout.ColumnLayoutData;

public class OutputWindow extends WindowPane {

	private Label lblData;

	private Label lblAaa;
	private Label lblBbb;
	private Label lblCcc;

	// --------------------------------------------------------------------------------

	public OutputWindow() {
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

		lblData = new Label("...");
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		lblData.setLayoutData(cld);
		col.add(lblData);

		Row rowCommand = new Row();
		rowCommand.setCellSpacing(new Extent(5, Extent.PX));
		rowCommand.setInsets(new Insets(0, 5, 0, 0));
		cld = new ColumnLayoutData();
		cld.setAlignment(Alignment.ALIGN_CENTER);
		rowCommand.setLayoutData(cld);
		col.add(rowCommand);

		lblAaa = new Label("AAA");
		lblAaa.setStyle(GUIStyles.LABEL_0_STYLE);
		rowCommand.add(lblAaa);

		lblBbb = new Label("BBB");
		lblBbb.setStyle(GUIStyles.LABEL_0_STYLE);
		rowCommand.add(lblBbb);

		lblCcc = new Label("CCC");
		lblCcc.setStyle(GUIStyles.LABEL_0_STYLE);
		rowCommand.add(lblCcc);
	}

	// --------------------------------------------------------------------------------

	public void setData(String data) {
		lblData.setText(data);
	}

	// --------------------------------------------------------------------------------

	public void switchAaaState() {
		Style newStyle = lblAaa.getStyle() == GUIStyles.LABEL_1_STYLE //
		/*    */? GUIStyles.LABEL_0_STYLE
				: GUIStyles.LABEL_1_STYLE;

		lblAaa.setStyle(newStyle);
	}

	public void switchBbbState() {
		Style newStyle = lblBbb.getStyle() == GUIStyles.LABEL_1_STYLE //
		/*    */? GUIStyles.LABEL_0_STYLE
				: GUIStyles.LABEL_1_STYLE;

		lblBbb.setStyle(newStyle);
	}

	public void switchCccState() {
		Style newStyle = lblCcc.getStyle() == GUIStyles.LABEL_1_STYLE //
		/*    */? GUIStyles.LABEL_0_STYLE
				: GUIStyles.LABEL_1_STYLE;

		lblCcc.setStyle(newStyle);
	}
}
