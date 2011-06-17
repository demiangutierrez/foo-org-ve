package holamundo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/holamundo/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MHolaMundo bean = new MHolaMundo();
    bean.setName("Hola mundo");
    session.save(bean);

    session.getTransaction().commit();
    session.close();
  }
}
