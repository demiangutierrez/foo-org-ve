package relaciones.unoamucho;

import java.util.Iterator;

import org.hibernate.Query;
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
    configuration.configure("/relaciones/unoamucho/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    //    Session session = sessionFactory.openSession();
    //    session.beginTransaction();
    //
    //    Uno uno = new Uno();
    //    for (int i = 0; i < 10; i++) {
    //      Muchos muchos = new Muchos();
    //      muchos.setUnoRef(uno);
    //      uno.getMuchosList().add(muchos);
    //      //session.save(muchos);
    //    }
    //
    //    session.save(uno);
    //
    //    session.getTransaction().commit();
    //    session.close();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Query q = session.createQuery("FROM Uno WHERE id=:att_id");
    q.setParameter("att_id", 1);

    Uno uno = (Uno) q.uniqueResult();

    System.out.println(uno);
    System.out.println(uno.getId());

    Iterator<Muchos> itt = uno.getMuchosList().iterator();

    while (itt.hasNext()) {
      Muchos muchos = (Muchos) itt.next();
      System.out.println(muchos.getId());

      if (muchos.getId() == 6) {
        itt.remove();
        muchos.setUnoRef(null);
      }
    }

    session.delete(uno);

    session.getTransaction().commit();
    session.close();
  }
}