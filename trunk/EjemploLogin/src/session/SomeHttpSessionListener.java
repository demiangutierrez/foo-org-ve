package session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Demián Gutierrez
 */
public class SomeHttpSessionListener implements HttpSessionListener {

  @Override
  public void sessionDestroyed(HttpSessionEvent evt) {
    System.err.println("sessionDestroyed: " + evt.getSession().getId());
  }

  @Override
  public void sessionCreated(HttpSessionEvent evt) {
    System.err.println("sessionCreated: " + evt.getSession().getId());
  }
}
