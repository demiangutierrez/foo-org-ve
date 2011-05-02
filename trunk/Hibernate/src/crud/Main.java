package crud;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/crud/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    // --------------------------------------------------------------------------------

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Crud crud = new Crud();
    crud.setDescripcion("descripcion");
    session.save(crud);

    crud = new Crud();
    crud.setDescripcion("d1");
    session.save(crud);

    crud = new Crud();
    crud.setDescripcion("d2");
    session.save(crud);

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    crud = null;
    crud = (Crud) session.load(Crud.class, 1);
    System.out.println("Crud.descripcion: " + crud.getDescripcion());

    crud.setDescripcion("Otra desc");

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    crud = null;
    crud = (Crud) session.createQuery("FROM Crud AS c WHERE c.id = '1'").uniqueResult();
    System.out.println("Crud.descripcion: " + crud.getDescripcion());

    session.delete(crud);

    session.getTransaction().commit();
    session.close();

    // --------------------------------------------------------------------------------

    session = sessionFactory.openSession();
    session.beginTransaction();

    List<Crud> list = session.createCriteria(Crud.class).list();
    for (Crud obj : list) {
      System.out.println(obj.getDescripcion());
    }

    session.getTransaction().commit();
    session.close();
  }
}
