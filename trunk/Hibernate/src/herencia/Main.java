package herencia;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 30, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/herencia/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Estudiante est = new Estudiante();
    est.setNombre("estudiante");
    est.setEdad(10);
    est.setNumMaterias(4);
    est.setPromedio(15);

    session.save(est);

    Empleado emp = new Empleado();
    emp.setNombre("empleado");
    emp.setEdad(30);
    emp.setCargo("cargo");
    emp.setAntiguedad(3);

    session.save(emp);

    session.getTransaction().commit();
    session.close();
  }
}