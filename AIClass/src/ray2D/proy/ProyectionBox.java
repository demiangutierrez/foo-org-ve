package ray2D.proy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.util.draw.CtrlPoint;
import org.cyrano.util.draw.CtrlPoint.Type;
import org.cyrano.util.geometry.PointDbl;
import org.cyrano.util.misc.MathUtil;

public class ProyectionBox implements Geometry {

  public List<CtrlPoint> ctrlPointList = //
  new ArrayList<CtrlPoint>();

  public CtrlPoint pPos = new CtrlPoint();
  public CtrlPoint pNea = new CtrlPoint();
  //public CtrlPoint pFar = new CtrlPoint();
  public CtrlPoint pRot = new CtrlPoint();

  private double w;
  private double h;

  public double sizeNear = 50;
  public double near = 50;
  public double back = 100;

  // --------------------------------------------------------------------------------

  public ProyectionBox() {
    pPos.setX(0);
    pPos.setY(0);
    pPos.getPropertyChangeSupport().addPropertyChangeListener( //
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            double delta = //
            (Double) evt.getNewValue() - (Double) evt.getOldValue();

            if (evt.getPropertyName().equals("x")) {
              pNea.setX(pNea.getX() + delta);
              //              pFar.setX(pFar.getX() + delta);
              pRot.setX(pRot.getX() + delta);
            }

            if (evt.getPropertyName().equals("y")) {
              pNea.setY(pNea.getY() + delta);
              //              pFar.setY(pFar.getY() + delta);
              pRot.setY(pRot.getY() + delta);
            }
          }
        });
    ctrlPointList.add(pPos);

    pRot.setX(0);
    pRot.setY(100);
    pRot.getPropertyChangeSupport().addPropertyChangeListener( //
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
          }
        });
    ctrlPointList.add(pRot);

    //    pNea.setX(0);
    //    pNea.setY(50);
    //    ctrlPointList.add(pNea);

    //    pFar.setX(0);
    //    pFar.setY(100);
    //    ctrlPointList.add(pFar);

  }

  //  private void calcNearFarWidth() {
  //
  //    CtrlPoint unitPPos = new CtrlPoint();
  //    unitPPos.setX(pRot.getX() - pPos.getX());
  //    unitPPos.setY(pRot.getY() - pPos.getY());
  //
  //    double pRotMod = MathUtil.mod(new double[]{ //
  //        unitPPos.getX(), unitPPos.getY()});
  //
  //    unitPPos.setX(unitPPos.getX() / pRotMod);
  //    unitPPos.setY(unitPPos.getY() / pRotMod);
  //
  //    near = //
  //    /**/unitPPos.getX() * pNea.getX() + //
  //        unitPPos.getY() * pNea.getY();
  //
  //    far = pRotMod;
  //
  //    // --------------------------------------------------
  //    // x with y is not a bug it's orthogonal dot product 
  //    // --------------------------------------------------
  //
  //    width = //
  //    2 * (unitPPos.getY() * pNea.getX() + //
  //    /* */unitPPos.getX() * pNea.getY());
  //  }

  //  private void calcPoints() {
  //  }

  // --------------------------------------------------------------------------------

  @Override
  public double distanceOf(Ray ray) {
    throw new UnsupportedOperationException();
  }

  // --------------------------------------------------------------------------------

  public void setW(double w) {
    this.w = w;
  }

  public void setH(double h) {
    this.h = h;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcLook() {
    PointDbl lookPt = new PointDbl();

    lookPt.x = pRot.getX() - pPos.getX();
    lookPt.y = pRot.getY() - pPos.getY();

    double modRot = MathUtil.mod( //
        new double[]{lookPt.x, lookPt.y, 0});

    lookPt.x /= modRot;
    lookPt.y /= modRot;

    return lookPt;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcOrth() {
    PointDbl lookPt = calcLook();

    PointDbl orthPt = new PointDbl();

    orthPt.x = -lookPt.y;
    orthPt.y = +lookPt.x;

    return orthPt;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcNear() {
    PointDbl lookPt = calcLook();

    PointDbl nearPt = new PointDbl();

    nearPt.x = lookPt.x * near + pPos.getX();
    nearPt.y = lookPt.y * near + pPos.getY();

    return nearPt;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcNearLft() {
    PointDbl orthPt = calcOrth();
    PointDbl nearPt = calcNear();

    PointDbl nearLft = new PointDbl();

    nearLft.x = orthPt.x * (+sizeNear / 2) + nearPt.x;
    nearLft.y = orthPt.y * (+sizeNear / 2) + nearPt.y;

    return nearLft;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcNearRgh() {
    PointDbl orthPt = calcOrth();
    PointDbl nearPt = calcNear();

    PointDbl nearRgh = new PointDbl();

    nearRgh.x = orthPt.x * (-sizeNear / 2) + nearPt.x;
    nearRgh.y = orthPt.y * (-sizeNear / 2) + nearPt.y;

    return nearRgh;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcBack() {
    PointDbl unitLook = calcLook();

    PointDbl backPt = new PointDbl();

    backPt.x = unitLook.x * (near + back) + pPos.getX();
    backPt.y = unitLook.y * (near + back) + pPos.getY();

    return backPt;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcBackLft() {
    PointDbl orthPt = calcOrth();
    PointDbl backPt = calcBack();

    PointDbl backLft = new PointDbl();

    double sizeBack = sizeNear / near * (near + back);

    backLft.x = orthPt.x * (+sizeBack / 2) + backPt.x;
    backLft.y = orthPt.y * (+sizeBack / 2) + backPt.y;

    return backLft;
  }

  // --------------------------------------------------------------------------------

  public PointDbl calcBackRgh() {
    PointDbl orthPt = calcOrth();
    PointDbl backPt = calcBack();

    PointDbl backRgh = new PointDbl();

    double sizeBack = sizeNear / near * (near + back);

    backRgh.x = orthPt.x * (-sizeBack / 2) + backPt.x;
    backRgh.y = orthPt.y * (-sizeBack / 2) + backPt.y;

    return backRgh;
  }

  // --------------------------------------------------------------------------------

  @Override
  public void draw(Graphics2D g2d) {
    g2d.setColor(Color.RED);
    calcBorder().draw(g2d);

    // ----------------------------------------

    g2d.setColor(Color.YELLOW);

    for (CtrlPoint ctrlPoint : ctrlPointList) {
      ctrlPoint.draw(g2d);
    }

    // ----------------------------------------

    PointDbl nearPt = calcNear();

    g2d.drawLine( //
        (int) pPos.getX(), (int) pPos.getY(), //
        (int) nearPt.x, (int) nearPt.y);

    // ----------------------------------------

    PointDbl backPt = calcBack();

    g2d.setColor(Color.RED);
    g2d.drawLine( //
        (int) nearPt.x, (int) nearPt.y, //
        (int) backPt.x, (int) backPt.y);

    // ----------------------------------------

    PointDbl nearLftPt = calcNearLft();

    g2d.setColor(Color.CYAN);
    g2d.drawLine( //
        (int) nearPt.x, (int) nearPt.y, //
        (int) nearLftPt.x, (int) nearLftPt.y);

    PointDbl nearRghPt = calcNearRgh();

    g2d.setColor(Color.MAGENTA);
    g2d.drawLine( //
        (int) nearPt.x, (int) nearPt.y, //
        (int) nearRghPt.x, (int) nearRghPt.y);

    PointDbl backLftPt = calcBackLft();

    g2d.setColor(Color.CYAN);
    g2d.drawLine( //
        (int) backPt.x, (int) backPt.y, //
        (int) backLftPt.x, (int) backLftPt.y);

    PointDbl backRghPt = calcBackRgh();

    g2d.setColor(Color.MAGENTA);
    g2d.drawLine( //
        (int) backPt.x, (int) backPt.y, //
        (int) backRghPt.x, (int) backRghPt.y);

    g2d.setColor(Color.YELLOW);
    g2d.drawLine( //
        (int) pPos.getX(), (int) pPos.getY(), //
        (int) backLftPt.x, (int) backLftPt.y);
    g2d.drawLine( //
        (int) pPos.getX(), (int) pPos.getY(), //
        (int) backRghPt.x, (int) backRghPt.y);
  }

  // --------------------------------------------------------------------------------

  private CtrlPoint calcBorder() {

    Line top = new Line();
    top.p1.setX(-w / 2);
    top.p1.setY(+h / 2);
    top.p2.setX(+w / 2);
    top.p2.setY(+h / 2);

    Line bot = new Line();
    bot.p1.setX(-w / 2);
    bot.p1.setY(-h / 2);
    bot.p2.setX(+w / 2);
    bot.p2.setY(-h / 2);

    Line lft = new Line();
    lft.p1.setX(-w / 2);
    lft.p1.setY(-h / 2);
    lft.p2.setX(-w / 2);
    lft.p2.setY(+h / 2);

    Line rgh = new Line();
    rgh.p1.setX(+w / 2);
    rgh.p1.setY(-h / 2);
    rgh.p2.setX(+w / 2);
    rgh.p2.setY(+h / 2);

    Ray ray = new Ray();
    ray.p0.setX(pPos.getX());
    ray.p0.setY(pPos.getY());
    ray.pu.setX(pRot.getX());
    ray.pu.setY(pRot.getY());

    double dist = Double.NaN;

    if (dist < 0 || Double.isNaN(dist)) {
      dist = top.distanceOf(ray);
    }

    if (dist < 0 || Double.isNaN(dist)) {
      dist = bot.distanceOf(ray);
    }

    if (dist < 0 || Double.isNaN(dist)) {
      dist = lft.distanceOf(ray);
    }

    if (dist < 0 || Double.isNaN(dist)) {
      dist = rgh.distanceOf(ray);
    }

    CtrlPoint ret = new CtrlPoint();
    ret.setX(ray.calcX(dist));
    ret.setY(ray.calcY(dist));
    ret.setColor(Color.RED);
    ret.setType(Type.STAR);

    return ret;
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<CtrlPoint> getCtrlPointList() {
    return ctrlPointList;
  }
}
