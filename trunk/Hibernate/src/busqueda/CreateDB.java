package busqueda;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class CreateDB {

  public static final String[] codes = //
  {"aaa", "aab", "abb", "acc", "aoeu", //
      "ccc", "ddd", "dda", "eef", "fff"};

  public static final int[] nums = //
  {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, //
      6, 6, 7, 7, 8, 8, 9, 9, 0, 0};

  public static final String muchos[] = //
  {"muchos_a", "muchos_b", "muchos_c"};

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/busqueda/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    for (int i = 0; i < codes.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        MObjUno objUno = new MObjUno();
        objUno.setCodigo(codes[i]);
        objUno.setNum(nums[j]);

        // Creates a few MObjMuchos for each MObjUno
        for (int k = 0; k < muchos.length; k++) {
          MObjMuchos objMuchos = new MObjMuchos();
          objMuchos.setOther( //
              objUno.getCodigo() + ";" + objUno.getNum() + ";" + muchos[k]);

          objUno.getObjMuchosList().add(objMuchos);
          objMuchos.setObjUnoRef(objUno);
        }

        session.save(objUno);
      }
    }

    session.getTransaction().commit();
    session.close();
  }
}
