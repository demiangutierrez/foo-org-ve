package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Anna Lezama
 * @author Demián Gutierrez
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    User userBean = new User();
    userBean.setNick("usuario");
    userBean.setPass("123456");
    userBean.setName("Fulano de Tal");
    userBean.setMail("usuario@freemail.com");

    session.save(userBean);

    session.getTransaction().commit();
    session.close();
  }
}
