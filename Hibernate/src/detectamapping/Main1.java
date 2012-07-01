package detectamapping;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 */
public class Main1 {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/detectamapping/hibernate.cfg.xml");

    configuration.buildMappings(); // Esto lee del xml antes de construir la sesion

    Iterator itt = configuration.getClassMappings();

    System.err.println("*************************************");
    while (itt.hasNext()) {
      Object object = (Object) itt.next();
      System.err.println(object);
    }
    System.err.println("*************************************");

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
