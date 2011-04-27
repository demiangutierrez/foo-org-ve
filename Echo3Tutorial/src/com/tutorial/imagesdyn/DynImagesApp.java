package com.tutorial.imagesdyn;

import java.util.List;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.HttpImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.Window;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.ImageMap;
import echopoint.model.CircleSection;
import echopoint.model.PolygonSection;
import echopoint.model.RectangleSection;

/**
 * @author Demián Gutierrez <br>
 *         Created on Jun 24, 2008
 */
public class DynImagesApp extends ApplicationInstance {

  private TextField txtId;
  private Label lblSelected;
  private ImageMap imageMap;
  private Column col;

  // --------------------------------------------------------------------------------

  public Window init() {
    Window window = new Window();

    ContentPane contentPane = new ContentPane();
    contentPane.setInsets(new Insets(2, 2, 2, 2));
    window.setContent(contentPane);

    col = new Column();
    col.setCellSpacing(new Extent(5));
    contentPane.add(col);

    // HttpImageReference ir = new HttpImageReference(
    // "http://www.google.com/intl/en_ALL/images/srpr/logo1w.png");

    HttpImageReference ir = new HttpImageReference("imagesdyn?user_id=-1");

    imageMap = new ImageMap(ir);

    // This has to be hardcoded to the same size of the image generated in
    // the servlet
    // (I suggest to put it in a constant...)
    imageMap.setWidth(new Extent(400));
    imageMap.setHeight(new Extent(400));

    imageMap.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        lblSelected.setText(evt.getActionCommand());
      }
    });

    imageMap.addSection(new CircleSection(70, 70, 50, "circle"));
    imageMap.addSection(new RectangleSection(90, 220, 290, 270, "rectangle"));
    imageMap.addSection(new PolygonSection(new int[]{275, 120, 450, 120, 500, 75, 425, 35, 330, 100, 275, 50},
        "polygon"));

    col.add(imageMap);

    Row row = new Row();
    row.setCellSpacing(new Extent(5));
    col.add(row);

    txtId = new TextField();
    row.add(txtId);

    Button btnGo = new Button("Go...");
    btnGo.setToolTipText("Este es el tooltip");
    btnGo.setStyle(GUIStyles.DEFAULT_STYLE);
    btnGo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        btnGoClicked();
      }
    });
    row.add(btnGo);

    lblSelected = new Label("nothing selected");
    col.add(lblSelected);

    return window;
  }

  // --------------------------------------------------------------------------------

  private void btnGoClicked() {
    HttpImageReference ir = new HttpImageReference( //
        "imagesdyn?user_id=" + txtId.getText());

    String userIdStr = txtId.getText();

    int userIdInt = -1;

    try {
      userIdInt = Integer.parseInt(userIdStr);
    } catch (NumberFormatException e) {
      // ----------------------------------------
      // Do nothing, userIdInt will remain in -1
      // ----------------------------------------
    }

    List<Planet> planetList = PlanetLoader.loadData(userIdInt);

    imageMap.setImage(ir.getUri());
    imageMap.removeAllSections();

    for (Planet planet : planetList) {
      imageMap.addSection(new CircleSection(planet.x, planet.y, planet.r, planet.name));
    }

    // There is an issue with imagemap.setImage, it's not refreshing the
    // image, so I'm being forced to remove the component from its container
    // and then read it again to refresh. Also there might be some cache problems
    // For example, a user changes of sector and the cached image shows the previous
    // sector in the client browser. I think this cache issue will depend if the
    // query is done based on user id or sector id (based on sector id would be better
    // because for performance reasons would be nice to cache the image, not only in the
    // client but also in the server).
    col.remove(imageMap);
    col.add(imageMap, 0);
  }
}
