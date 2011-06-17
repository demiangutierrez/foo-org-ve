package relaciones.unoamucho;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
public class Main2 {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/relaciones/unoamucho/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Query q = session.createQuery("FROM MUno WHERE id=:att_id");
    q.setParameter("att_id", 1);

    MUno uno = (MUno) q.uniqueResult();

    System.out.println(uno);
    System.out.println(uno.getId());

    Iterator<MMuchos> itt = uno.getMuchosList().iterator();

    while (itt.hasNext()) {
      MMuchos muchos = (MMuchos) itt.next();
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
