package com.tutorial.serverpush;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.tutorial.table.GUIStyles;

//Autor Anna Lezama
public class Desktop extends ContentPane {

  private Label lbl;

  TaskQueueHandle taskQueue;

  TimedServerPush timedServerPush;

  // --------------------------------------------------------------------------------

  public Desktop() {
    taskQueue = ApplicationInstance.getActive().createTaskQueue();
    initGUI();
    setInsets(new Insets(5, 5, 5, 5));
  }

  // --------------------------------------------------------------------------------

  private void initGUI() {

    ApplicationInstance app = ApplicationInstance.getActive();

    timedServerPush = new TimedServerPush(1000, app, taskQueue, new Runnable() {
      @Override
      public void run() {
        lbl.setText("" + (Integer.parseInt(lbl.getText()) + 1));
      }
    });

    Row row = new Row();
    row.setCellSpacing(new Extent(10, Extent.PX));
    row.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
    add(row);

    Label lblSec = new Label("Segundos:  ");
    row.add(lblSec);

    lbl = new Label("0");
    row.add(lbl);

    Button btnStart = new Button("Start");
    btnStart.setStyle(GUIStyles.DEFAULT_STYLE);
    btnStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        timedServerPush.beg();
      }
    });
    row.add(btnStart);

    Button btnStop = new Button("Stop");
    btnStop.setStyle(GUIStyles.DEFAULT_STYLE);
    btnStop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        timedServerPush.end();
      }
    });
    row.add(btnStop);
  }
}
