package holamundo;

import org.hibernate.Session;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Main2 {

  public static void main(String[] args) {
    Session session = SessionHibernate.getInstance().getSession();
    session.beginTransaction();

    MHolaMundo bean = new MHolaMundo();
    bean.setName("Hola mundo");
    session.save(bean);

    session.getTransaction().commit();
    session.close();
  }
}
