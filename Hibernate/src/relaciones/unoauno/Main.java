package relaciones.unoauno;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 30, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/relaciones/unoauno/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Persona p = new Persona();
    Conyugue c = new Conyugue();
    c.setNombre("nombre");

    p.setConyugue(c);
    //    c.setPersona(p);

    session.save(p);

    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();

    p = (Persona) session.load(Persona.class, 1);
    System.out.println(p.getConyugue().getNombre());

    session.getTransaction().commit();
    session.close();
  }
}