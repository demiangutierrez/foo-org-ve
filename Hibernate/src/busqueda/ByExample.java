package busqueda;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class ByExample {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MObjUno sObjUno = new MObjUno();
    sObjUno.setCodigo("aoeu");
    sObjUno.setNum(1);

    // XXX: hibernate ignores ids, relations and versions in
    // queries by example (see documentation)
    // sObj.setId(99); 

    // FROM MObjUno WHERE codigo = 'aoeu'
    List<MObjUno> results = //
    session.createCriteria(MObjUno.class).add(Example.create(sObjUno)).list();

    for (Object obj : results) {
      System.out.println(obj);
    }
    System.out.println();

    session.getTransaction().commit();
    session.close();
  }
}
