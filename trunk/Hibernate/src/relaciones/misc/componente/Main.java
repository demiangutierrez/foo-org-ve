package relaciones.misc.componente;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/relaciones/misc/componente/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Master master = new Master();
    master.setComponente(new Componente());
    master.getComponente().setX(3);
    master.getComponente().setY(6);

    session.save(master);

    session.getTransaction().commit();
    session.close();
  }
}
