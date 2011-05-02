package busqueda;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class ByCriteria {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    // FROM SearchObj
    Criteria criteria = session.createCriteria(SearchObj.class);
    list(criteria);

    // FROM SearchObj WHERE num > 7
    criteria = session.createCriteria(SearchObj.class).add(Restrictions.gt("num", 7));
    list(criteria);

    // SELECT id FROM SearchObj WHERE num >= :numInf AND num < :numSup
    criteria = session.createCriteria(SearchObj.class).add(Restrictions.ge("num", 2)).add(Restrictions.lt("num", 4));
    criteria.setProjection(Projections.id());
    list(criteria);

    // FROM SearchObj AS obj WHERE obj.codigo LIKE :cod ORDER BY obj.num
    criteria = session.createCriteria(SearchObj.class).add(Restrictions.like("codigo", "%ab"))
        .addOrder(Order.asc("num"));
    list(criteria);

    session.getTransaction().commit();
    session.close();
  }

  public static void list(Criteria criteria) {
    for (Object obj : criteria.list()) {
      System.out.println(obj);
    }
    System.out.println();
  }
}