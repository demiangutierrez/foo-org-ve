package busqueda;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class ByExample {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    SearchObj sObj = new SearchObj();
    //    sObj.setCodigo("aoeu");
    //    sObj.setId(99); // TODO: REVISAR, encuentra varios registros
    sObj.setNum(1);

    // FROM SearchObj WHERE codigo = 'aoeu'
    List<SearchObj> results = //
    session.createCriteria(SearchObj.class).add(Example.create(sObj)).list();

    for (Object obj : results) {
      System.out.println(obj);
    }
    System.out.println();

    session.getTransaction().commit();
    session.close();
  }
}