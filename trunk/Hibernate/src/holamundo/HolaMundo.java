package holamundo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Proxy;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 19, 2008
 */
@Entity
@Table(name = "t_hola_mundo")
@Proxy(lazy = false)
public class HolaMundo {

  private int id;
  private String name;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/holamundo/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    HolaMundo bean = new HolaMundo();
    bean.setName("Hola mundo");
    session.save(bean);

    session.getTransaction().commit();
    session.close();
  }
}
