package crud;

import java.util.List;

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
 * <br> Created on Jun 30, 2008
 */
@Entity
@Table(name = "t_crud")
@Proxy(lazy = false)
public class Crud {

	private int id;
	private String descripcion;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static void main(String[] args) {
		Configuration configuration = new AnnotationConfiguration();
		configuration.configure("/crud/hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

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

		/////

		session = sessionFactory.openSession();
		session.beginTransaction();

		crud = null;
		crud = (Crud) session.load(Crud.class, 1);
		System.out.println("Crud.descripcion: " + crud.getDescripcion());

		crud.setDescripcion("Otra desc");

		session.getTransaction().commit();
		session.close();

		/////

		session = sessionFactory.openSession();
		session.beginTransaction();

		crud = null;
		crud = (Crud) session.createQuery("FROM Crud AS c WHERE c.id = '1'").uniqueResult();
		System.out.println("Crud.descripcion: " + crud.getDescripcion());

		session.delete(crud);

		session.getTransaction().commit();
		session.close();

		/////

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