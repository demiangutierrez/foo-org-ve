package crud;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/crud/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // --------------------------------------------------------------------------------

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MCrud crud = new MCrud();
    crud.setDescripcion("descripcion");
    session.save(crud);

    crud = new MCrud();
    crud.setDescripcion("d1");
    session.save(crud);

    crud = new MCrud();
    crud.setDescripcion("d2");
    session.save(crud);

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    crud = null;
    crud = (MCrud) session.load(MCrud.class, 1);
    System.out.println("Crud.descripcion: " + crud.getDescripcion());

    crud.setDescripcion("Otra desc");

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    crud = null;
    crud = (MCrud) session.createQuery("FROM MCrud AS c WHERE c.id = '1'").uniqueResult();
    System.out.println("Crud.descripcion: " + crud.getDescripcion());

    session.delete(crud);

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    List<MCrud> list = session.createCriteria(MCrud.class).list();
    for (MCrud obj : list) {
      System.out.println(obj.getDescripcion());
    }

    session.getTransaction().commit();
    session.close();
  }
}
