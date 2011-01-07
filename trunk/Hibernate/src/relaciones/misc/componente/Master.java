package relaciones.misc.componente;

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
 * <br> Created on Jul 2, 2008
 */
@Entity
@Table(name = "t_master")
@Proxy(lazy = false)
public class Master {

	private int id;
	private Componente componente;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public static void main(String[] args) {
		Configuration configuration = new AnnotationConfiguration();
		configuration.configure("/relaciones/misc/componente/hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Master master = new Master();
		master.componente = new Componente();
		master.componente.setX(3);
		master.componente.setY(6);

		session.save(master);

		session.getTransaction().commit();
		session.close();
	}
}