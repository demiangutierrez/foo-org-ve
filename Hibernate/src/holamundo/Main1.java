package holamundo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Main1 {

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
