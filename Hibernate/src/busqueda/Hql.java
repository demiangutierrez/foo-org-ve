package busqueda;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Hql {

  public static void simpleList(Session session) {
    String queryStr;
    Query query;

    // QUERY 1 ----------------------------------------

    queryStr = "FROM MObjUno";
    query = session.createQuery(queryStr);
    list(query, queryStr);

    // QUERY 2 ----------------------------------------

    queryStr = "FROM MObjUno WHERE num > 7";
    query = session.createQuery(queryStr);
    list(query, queryStr);
  }

  // --------------------------------------------------------------------------------

  public static void usingParameters(Session session) {
    String queryStr;
    Query query;

    // QUERY 1 ----------------------------------------

    queryStr = "FROM MObjUno WHERE num > ?";
    query = session.createQuery(queryStr);
    query.setInteger(0, 7); // Index are 0-based (JDBC are 1 based iirc)
    //query.setParameter(0, 7);
    list(query, queryStr);

    // QUERY 2 ----------------------------------------

    queryStr = "FROM MObjUno WHERE num > :numPlaceHolder";
    query = session.createQuery(queryStr);
    query.setInteger("numPlaceHolder", 7);
    //query.setParameter("numPlaceHolder", 7);
    list(query, queryStr);
  }

  // --------------------------------------------------------------------------------

  public static void selectingAttributes(Session session) {
    String queryStr;
    Query query;

    queryStr = "SELECT id FROM MObjUno WHERE num >= :numInf AND num < :numSup";
    query = session.createQuery(queryStr);
    query.setInteger("numInf", 2);
    query.setInteger("numSup", 4);

    list(query, queryStr);

    queryStr = "SELECT id, num FROM MObjUno WHERE num >= :numInf AND num < :numSup";
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

  }

  // --------------------------------------------------------------------------------

  public static void basicCartesianProduct(Session session) {
    String queryStr = "SELECT u, m FROM MObjUno AS u, MObjMuchos AS m";
    Query query = session.createQuery(queryStr);

    for (Object obj : query.list()) {
      Object[] objArray = (Object[]) obj;
      System.out.println(objArray[0] + ";" + objArray[1]);
    }
  }

  // --------------------------------------------------------------------------------

  public static void basicJoin(Session session) {
    // Notice there is no ON expression (Hibernate does not support this),
    // instead, you have to specify the "join path" after the JOIN keyword,
    // like this: u.objMuchosList
    String queryStr = "SELECT u, m FROM MObjUno AS u JOIN u.objMuchosList AS m";
    Query query = session.createQuery(queryStr);

    for (Object obj : query.list()) {
      Object[] objArray = (Object[]) obj;
      System.out.println(objArray[0] + " **** " + objArray[1]);
    }
  }

  // --------------------------------------------------------------------------------

  public static void usingLike(Session session) {
    String queryStr = "FROM MObjUno AS obj WHERE obj.codigo LIKE :cod ORDER BY obj.num";

    Query query = session.createQuery(queryStr);
    query.setString("cod", "%ab");

    list(query, queryStr);
  }

  // --------------------------------------------------------------------------------

  // Just an utility that prints the results
  public static void list(Query query, String queryStr) {
    System.out.println("Query: " + queryStr);

    for (Object obj : query.list()) {
      System.out.println(obj);
    }

    System.out.println("----------------------------------------");
  }

  // --------------------------------------------------------------------------------

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    simpleList(session);
    usingParameters(session);
    selectingAttributes(session);
    basicCartesianProduct(session);
    basicJoin(session);
    usingLike(session);

    session.getTransaction().commit();
    session.close();
  }
}
