package relaciones.muchosamuchos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/relaciones/muchosamuchos/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    EntityA a = new EntityA();
    for (int i = 0; i < 3; i++) {
      EntityB b = new EntityB();

      AB ab = new AB();

      ab.setEntityARef(a);
      ab.setEntityBRef(b);
      a.getAbList().add(ab);
      b.getAbList().add(ab);

      session.save(a);
      session.save(b);
    }

    EntityB b = (EntityB) session.load(EntityB.class, 1);
    for (int i = 0; i < 3; i++) {
      a = new EntityA();

      AB ab = new AB();

      ab.setEntityARef(a);
      ab.setEntityBRef(b);
      a.getAbList().add(ab);
      b.getAbList().add(ab);

      session.save(a);
      session.save(b);
    }

    session.getTransaction().commit();
    session.close();
  }
}