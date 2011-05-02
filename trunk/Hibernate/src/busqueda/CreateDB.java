package busqueda;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class CreateDB {

  public static final String[] codes = {"aaa", "aab", "abb", "acc", "aoeu", "ccc", "ddd", "dda", "eef", "fff"};
  public static final int[] nums = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 0, 0};

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    for (int i = 0; i < codes.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        SearchObj obj = new SearchObj();
        obj.setCodigo(codes[i]);
        obj.setNum(nums[j]);

        session.save(obj);
      }
    }

    session.getTransaction().commit();
    session.close();
  }
}