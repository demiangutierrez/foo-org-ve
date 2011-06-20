package relaciones.misc.componente;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/relaciones/misc/componente/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MMaster master = new MMaster();
    master.setComponente(new Componente());
    master.getComponente().setX(3);
    master.getComponente().setY(6);

    session.save(master);

    session.getTransaction().commit();
    session.close();
  }
}
