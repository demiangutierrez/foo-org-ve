package relaciones.unoamucho;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
public class Main1 {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/relaciones/unoamucho/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MUno uno = new MUno();
    for (int i = 0; i < 10; i++) {
      MMuchos muchos = new MMuchos();
      muchos.setUnoRef(uno);
      uno.getMuchosList().add(muchos);
      //session.save(muchos);
    }

    session.save(uno);

    session.getTransaction().commit();
    session.close();
  }
}