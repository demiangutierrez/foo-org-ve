package relaciones.unoauno;

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
    configuration.configure("/relaciones/unoauno/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    for (int i = 1; i < 6; i++) {
      MPersona p = new MPersona();
      p.setNombre("nombre " + i);
      session.save(p);

      if (i != 3) {
        MConyugue c = new MConyugue();
        c.setNombre("nombre " + i);

        p.setConyugue(c);
        c.setPersona(p);

        session.save(c);
      }
    }
    session.getTransaction().commit();
    session.close();

    //    session = sessionFactory.openSession();
    //    session.beginTransaction();
    //
    //    p = (MPersona) session.load(MPersona.class, 1);
    //    System.out.println(p.getConyugue());
    //    System.out.println(p.getConyugue().getNombre());
    //
    //    session.getTransaction().commit();
    //    session.close();
  }
}
