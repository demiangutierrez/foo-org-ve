package busqueda;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Hql {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    String queryStr;
    Query query;

    //    String queryStr = "FROM SearchObj";
    //    Query query = session.createQuery(queryStr);
    //    list(query, queryStr);

    //    queryStr = "FROM SearchObj WHERE num > 7";
    //    query = session.createQuery(queryStr);
    //    list(query, queryStr);

    //        queryStr = "SELECT id FROM SearchObj WHERE num >= :numInf AND num < :numSup";
    //        query = session.createQuery(queryStr);
    //        query.setInteger("numInf", 2);
    //        query.setInteger("numSup", 4);
    //        list(query, queryStr);

    // FROM Profesor AS p JOIN Departamento AS d ON p.departamentoRef=d
    //    query = session.createQuery(queryStr);
    // [0] => Profesor
    // [1] => Departamento
    //    List<Object[]> list = query.list();

    queryStr = "SELECT id, num FROM SearchObj WHERE num >= :numInf AND num < :numSup";
    query = session.createQuery(queryStr);
    query.setInteger("numInf", 2);
    query.setInteger("numSup", 4);
    // [0] -> id
    // [1] -> num    
    //list(query, queryStr);
    for (Object obj : query.list()) {
      Object[] objArray = (Object[]) obj;
      System.out.println(objArray[0] + ";" + objArray[1]);
    }

    //    queryStr = "FROM SearchObj AS obj WHERE obj.codigo LIKE :cod ORDER BY obj.num";
    //    query = session.createQuery(queryStr);
    //    query.setString("cod", "%ab");
    //    list(query, queryStr);

    session.getTransaction().commit();
    session.close();
  }

  public static void list(Query query, String queryStr) {
    System.out.println("Query: " + queryStr);

    for (Object obj : query.list()) {
      System.out.println(obj);
    }
    System.out.println();
  }
}