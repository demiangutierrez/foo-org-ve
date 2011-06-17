package relaciones.muchosamuchos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/relaciones/muchosamuchos/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MEntityA a = new MEntityA();
    for (int i = 0; i < 3; i++) {
      MEntityB b = new MEntityB();

      MAB ab = new MAB();

      ab.setEntityARef(a);
      ab.setEntityBRef(b);
      a.getAbList().add(ab);
      b.getAbList().add(ab);

      session.save(a);
      session.save(b);
    }

    MEntityB b = (MEntityB) session.load(MEntityB.class, 1);
    for (int i = 0; i < 3; i++) {
      a = new MEntityA();

      MAB ab = new MAB();

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