package org.cyrano.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

import org.cyrano.common.GridCreator;
import org.cyrano.graph.Dijkstra;
import org.cyrano.graph.Node;
import org.cyrano.util.Hwh;

public class GamePanel extends JPanel {

  public GameMap gameMap = new GameMap();

  // ----------------------------------------

  public GamePanel() {
    GridCreator gridCreator = new GridCreator();
    gridCreator.createQua(26, 16, 50, 50);
    gameMap.setGraph(gridCreator.getGraph());

    // XXX: Offsets (-20, -20 has to be changed in GameMap paint
    // when painting the mask (yes ugly, I know...)
    gridCreator.cleanMap(gameMap.map, gameMap.getGraph(), -20, -20);

    gameMap.setSrc(gameMap.getGraph().getNodeMap().get("0:0"));
    gameMap.player0.setCurrNode(gameMap.getGraph().getNodeMap().get("0:0"));

    gameMap.player0.getCoords().x = gameMap.getSrc().getCoords().x;
    gameMap.player0.getCoords().y = gameMap.getSrc().getCoords().y;

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        //            if (gameMap.getTgt() != null) {
        //              gameMap.getTgt().setNodeMode(NodeMode.STD);
        //            }

        int w = Hwh.getW(GamePanel.this);
        int h = Hwh.getH(GamePanel.this);

        Point p = new Point( //
            gameMap.player0.getCoords().x - w / 2 + evt.getPoint().x, //
            gameMap.player0.getCoords().y - h / 2 + evt.getPoint().y);

        gameMap.setTgt(gameMap.getNearest(p));
        //            gameMap.getTgt().setNodeMode(NodeMode.TGT);
        repaint();

        Dijkstra d = new Dijkstra(gameMap.getGraph(), gameMap.player0.getCurrNode());
        List<Node> path = d.pathTo(gameMap.getTgt());
        gameMap.setPath(path);

        //            gameMap.getSrc().setNodeMode(NodeMode.STD);
        gameMap.setSrc(gameMap.getTgt());
        //            gameMap.getTgt().setNodeMode(NodeMode.SRC);
      }
    });

    Animator animator = Animator.getInstance();
    animator.addRepaintListener(new RepaintListener() {
      @Override
      public void repaintRequest(ActionEvent evt) {
        repaint();
      }
    });
    animator.start();

    setDoubleBuffered(true);
  }

  // ----------------------------------------

  @Override
  public void paint(Graphics g) {
    update(g);
  }

  // ----------------------------------------

  Image img;

  @Override
  public void update(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    if (img == null || img.getWidth(null) != getWidth() || img.getHeight(null) != getHeight()) {
      img = createImage(getWidth(), getHeight());
    }

    gameMap.paint(g2d, getWidth(), getHeight(), img);
  }
}
