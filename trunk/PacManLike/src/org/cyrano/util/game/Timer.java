package org.cyrano.util.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Timer {

  private List<TimerBean> timerBeanList = new ArrayList<TimerBean>();

  public Timer() {
    // Empty
  }

  public void handleDelta(double delta) {
    Iterator<TimerBean> itt = timerBeanList.iterator();

    while (itt.hasNext()) {
      TimerBean timerBean = itt.next();

      timerBean.setDelta(timerBean.getDelta() - delta);

      if (timerBean.getDelta() <= 0) {
        fireTimerBean(timerBean);
        itt.remove();
      }
    }
  }

  private void fireTimerBean(TimerBean timerBean) {
    timerBean.getActionListener().actionPerformed(timerBean.getActionEvent());
  }

  public void addTimerBean(TimerBean timerBean) {
    timerBeanList.add(timerBean);
  }
}
