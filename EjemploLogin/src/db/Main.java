package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/db/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    User bean = new User();
    bean.setNick("annitap4");
    bean.setPass("123");
    bean.setName("Anna Lezama");
    bean.setEmail("annitap4@gmail.com");
    session.save(bean);

    session.getTransaction().commit();
    session.close();
  }
}
