package boxcollision5;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Demián Gutierrez
 */
public class MainFrame extends JFrame {

  public MainFrame() {
    setLayout(new BorderLayout());

    final GamePanel gp = new GamePanel();

    add(gp, BorderLayout.CENTER);

    JPanel pnlSouth = new JPanel();
    pnlSouth.setLayout(new FlowLayout());
    add(pnlSouth, BorderLayout.SOUTH);

    final JSlider sld = new JSlider();
    sld.setMinimum(0);
    sld.setMaximum(400);
    sld.setSnapToTicks(true);
    sld.setValue(100);

    sld.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        double val = sld.getValue() / 100.0;
        gp.setVal(val);
      }
    });

    pnlSouth.add(sld);

    JButton btn = new JButton("repaint");
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        gp.repaint();
      }
    });
    pnlSouth.add(btn);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(700, 500);
    setVisible(true);
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    new MainFrame();
  }
}
