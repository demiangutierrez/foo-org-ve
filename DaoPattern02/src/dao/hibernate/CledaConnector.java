/*
 * Created on 19/10/2005
 */
package dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

//--------------------------------------------------------------------------------
//Taken from CLEDA framework, see http://cleda.sourceforge.net/ for details
//--------------------------------------------------------------------------------

/**
 * @author Demián Gutierrez
 */
public class CledaConnector {

  private static final String HIBERNATE_CFG_XML = "hibernate.cfg.xml";

  private static CledaConnector instance = new CledaConnector();

  // --------------------------------------------------------------------------------

  private Map<String, SessionBean> sessionBeanMap = new HashMap<String, SessionBean>();

  private String defaultHibernateCfg = HIBERNATE_CFG_XML;

  private Log log = LogFactory.getLog(getClass());

  // --------------------------------------------------------------------------------

  private CledaConnector() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static CledaConnector getInstance() throws HibernateException {
    return instance;
  }

  // --------------------------------------------------------------------------------

  private synchronized SessionBean buildSessionBean(String hibernateCfg) {

    long time = System.currentTimeMillis();

    SessionBean ret = new SessionBean();

    ret.configuration = new AnnotationConfiguration();
    ret.configuration.setNamingStrategy(new CledaNamingStrategy(ret.configuration));
    ret.configuration.configure(hibernateCfg);

    ret.sessionFactory = ret.configuration.buildSessionFactory();

    sessionBeanMap.put(hibernateCfg, ret);

    time = System.currentTimeMillis() - time;

    log.info("hibernate configuration ready in " + time / 1000.0 + " secs");

    return ret;
  }

  // --------------------------------------------------------------------------------

  public String getDefaultHibernateCfg() {
    return defaultHibernateCfg;
  }

  public void setDefaultHibernateCfg(String defaultHibernateCfg) {
    this.defaultHibernateCfg = defaultHibernateCfg;
  }

  // --------------------------------------------------------------------------------

  public synchronized void dropDB() //
      throws HibernateException {

    dropDB(defaultHibernateCfg);
  }

  public synchronized void dropDB(String hibernateCfg) //
      throws HibernateException {

    SessionBean sessionBean = sessionBeanMap.get(hibernateCfg);

    if (sessionBean == null) {
      sessionBean = new SessionBean();
      sessionBeanMap.put(hibernateCfg, sessionBean);
    } else {
      sessionBean.sessionFactory.close();
    }

    sessionBean.configuration = new AnnotationConfiguration();
    sessionBean.configuration.setNamingStrategy(new CledaNamingStrategy(sessionBean.configuration));
    sessionBean.configuration.configure(hibernateCfg);
    sessionBean.configuration.getProperties().setProperty("hibernate.hbm2ddl.auto", "create");

    sessionBean.sessionFactory = sessionBean.configuration.buildSessionFactory();
  }

  // --------------------------------------------------------------------------------

  public synchronized Configuration getConfiguration() {
    return getConfiguration(defaultHibernateCfg);
  }

  public synchronized Configuration getConfiguration(String hibernateCfg) {
    SessionBean sessionBean = sessionBeanMap.get(hibernateCfg);

    if (sessionBean == null) {
      sessionBean = buildSessionBean(hibernateCfg);
    }

    return sessionBean.configuration;
  }

  // --------------------------------------------------------------------------------

  public synchronized Session getSession() //
      throws HibernateException {

    return getSession((String) null);
  }

  public synchronized Session getSession(String hibernateCfg) //
      throws HibernateException {

    if (hibernateCfg == null) {
      hibernateCfg = defaultHibernateCfg;
    }

    SessionBean sessionBean = sessionBeanMap.get(hibernateCfg);

    if (sessionBean == null) {
      sessionBean = buildSessionBean(hibernateCfg);
    }

    Session session = sessionBean.sessionFactory.openSession();
    session.setFlushMode(FlushMode.COMMIT);

    return session;
  }

  // --------------------------------------------------------------------------------

  public synchronized Session getSession(Interceptor interceptor) //
      throws HibernateException {

    return getSession(defaultHibernateCfg, interceptor);
  }

  public synchronized Session getSession(String hibernateCfg, Interceptor interceptor) //
      throws HibernateException {

    SessionBean sessionBean = sessionBeanMap.get(hibernateCfg);

    if (sessionBean == null) {
      sessionBean = buildSessionBean(hibernateCfg);
    }

    Session session = sessionBean.sessionFactory.openSession(interceptor);
    session.setFlushMode(FlushMode.COMMIT);

    return session;
  }

  // --------------------------------------------------------------------------------

  public static void beg(Session session) {
    if (session.getTransaction().isActive()) {
      throw new IllegalStateException();
    }

    session.beginTransaction();
  }

  public static void com(Session session) {
    if (session.getTransaction().isActive()) {
      session.getTransaction().commit();
    } else {
      throw new IllegalStateException();
    }
  }

  public static void rolAndThrow(Session session, Exception exception) //
      throws Exception {

    if (session.getTransaction().isActive()) {
      session.getTransaction().rollback();
    }

    throw exception;
  }

  public static void comAndClose(Session session) {
    if (session.getTransaction().isActive()) {
      session.getTransaction().commit();
    }

    session.close();
  }

  // --------------------------------------------------------------------------------

  private static class SessionBean {

    public SessionFactory sessionFactory;
    public Configuration configuration;
  }
}
