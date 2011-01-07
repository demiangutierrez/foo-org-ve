package org.cyrano.boxcollision.test4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.boxcollision.base.Box;
import org.cyrano.boxcollision.base.CollisionDetector.Side;
import org.cyrano.masscenter.MassCenterCalculator;

public class CollisionManager {

  public static void hitAsteroidAsteroid(Asteroid aa, Asteroid bb, Side saa, Side sbb, List<BoxImpl> drawBoxList) {

    synchronized (drawBoxList) {

      List<Box> fragmentsList = new ArrayList<Box>();
      drawBoxList.remove(aa);

      if (aa.bw > 20 && aa.bh > 20) {
        Asteroid aa11 = new Asteroid();
        aa11.size = aa.size - 1;
        aa11.cx = aa.minX();
        aa11.cy = aa.minY();
        aa11.bw = aa.bw / 2 - 2;
        aa11.bh = aa.bh / 2 - 2;
        aa11.color = aa.color;
        aa11.mv = true;
        drawBoxList.add(aa11);
        fragmentsList.add(aa11);

        Asteroid aa12 = new Asteroid();
        aa12.size = aa.size - 1;
        aa12.cx = (aa.minX() + aa.maxX()) / 2;
        aa12.cy = aa.minY();
        aa12.bw = aa.bw / 2 - 2;
        aa12.bh = aa.bh / 2 - 2;
        aa12.color = aa.color;
        aa12.mv = true;
        drawBoxList.add(aa12);
        fragmentsList.add(aa12);

        Asteroid aa21 = new Asteroid();
        aa21.size = aa.size - 1;
        aa21.cx = aa.minX();
        aa21.cy = (aa.minY() + aa.maxY()) / 2;
        aa21.bw = aa.bw / 2 - 2;
        aa21.bh = aa.bh / 2 - 2;
        aa21.color = aa.color;
        aa21.mv = true;
        drawBoxList.add(aa21);
        fragmentsList.add(aa21);

        Asteroid aa22 = new Asteroid();
        aa22.size = aa.size - 1;
        aa22.cx = (aa.minX() + aa.maxX()) / 2;
        aa22.cy = (aa.minY() + aa.maxY()) / 2;
        aa22.bw = aa.bw / 2 - 2;
        aa22.bh = aa.bh / 2 - 2;
        aa22.color = aa.color;
        aa22.mv = true;
        drawBoxList.add(aa22);
        fragmentsList.add(aa22);
      }

      drawBoxList.remove(bb);
      if (bb.bw > 20 && bb.bh > 20) {

        Asteroid bb11 = new Asteroid();
        bb11.size = bb.size - 1;
        bb11.cx = bb.minX();
        bb11.cy = bb.minY();
        bb11.bw = bb.bw / 2 - 2;
        bb11.bh = bb.bh / 2 - 2;
        bb11.color = bb.color;
        bb11.mv = true;
        drawBoxList.add(bb11);
        fragmentsList.add(bb11);

        Asteroid bb12 = new Asteroid();
        bb12.size = bb.size - 1;
        bb12.cx = (bb.minX() + bb.maxX()) / 2;
        bb12.cy = bb.minY();
        bb12.bw = bb.bw / 2 - 2;
        bb12.bh = bb.bh / 2 - 2;
        bb12.color = bb.color;
        bb12.mv = true;
        drawBoxList.add(bb12);
        fragmentsList.add(bb12);

        Asteroid bb21 = new Asteroid();
        bb21.size = bb.size - 1;
        bb21.cx = bb.minX();
        bb21.cy = (bb.minY() + bb.maxY()) / 2;
        bb21.bw = bb.bw / 2 - 2;
        bb21.bh = bb.bh / 2 - 2;
        bb21.color = bb.color;
        bb21.mv = true;
        drawBoxList.add(bb21);
        fragmentsList.add(bb21);

        Asteroid bb22 = new Asteroid();
        bb22.size = bb.size - 1;
        bb22.cx = (bb.minX() + bb.maxX()) / 2;
        bb22.cy = (bb.minY() + bb.maxY()) / 2;
        bb22.bw = bb.bw / 2 - 2;
        bb22.bh = bb.bh / 2 - 2;
        bb22.color = bb.color;
        bb22.mv = true;
        drawBoxList.add(bb22);
        fragmentsList.add(bb22);
      }

      Point p = MassCenterCalculator.calcMassCenter(fragmentsList, false);

      int c = 0;

      for (Box box : fragmentsList) {
        c++;
        BoxImpl bi = (BoxImpl) box;
        int midx = (bi.minX() + bi.maxX()) / 2;
        int midy = (bi.minY() + bi.maxY()) / 2;

        bi.vx = (midx - p.x) * 2;
        bi.vy = (midy - p.y) * 2;

        double mod = Math.sqrt(bi.vx * bi.vx + bi.vy * bi.vy);
        bi.vx /= mod;
        bi.vy /= mod;

        if (c <= 3) {
          double mod2 = Math.sqrt(aa.vx * aa.vx + aa.vy * aa.vy);
          bi.vx *= mod2;
          bi.vy *= mod2;
        } else {
          double mod2 = Math.sqrt(bb.vx * bb.vx + bb.vy * bb.vy);
          bi.vx *= mod2;
          bi.vy *= mod2;
        }
      }
    }
  }

  public static void hitAsteroidPlayer(Asteroid box1, Player box2, Side box1Side, Side box2Side,
      List<BoxImpl> drawBoxList) {
    drawBoxList.remove(box2); // GAME OVER
  }

  public static void hitPlayerBullet(Player box1, Bullet box2, Side box1Side, Side box2Side, List<BoxImpl> drawBoxList) {
    drawBoxList.remove(box1); // GAME OVER
  }

  public static void hitBulletAsteroid(Bullet box1, Asteroid box2, Side box1Side, Side box2Side,
      List<BoxImpl> drawBoxList) {
    drawBoxList.remove(box1); // BYE BYE BULLET
    drawBoxList.remove(box2); // BYE BYE ASTEROID
  }

  //  public static void hitAsteroidBullet(Asteroid a1, Bullet a2) {
  //  }

}
