package propiedades;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "t_propiedades")
@Proxy(lazy = false)
public class Propiedades {

	private int id;
	private Calendar cal;
	private Date date1;
	private byte[] img;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public Date getDate1() {
		return date1;
	}

	@Temporal(TemporalType.TIME)
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	@Lob
	@Column(length = 70000)
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public static void main(String[] args) throws Exception {
		Configuration configuration = new AnnotationConfiguration();
		configuration.configure("/propiedades/hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Propiedades prop = new Propiedades();
		prop.setCal(Calendar.getInstance());
		prop.setDate1(Calendar.getInstance().getTime());

		File file = new File("/home/dmi/workspace/Hibernate/src/propiedades/img.jpg");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buf = new byte[(int) file.length()];
		bis.read(buf);

		prop.setImg(buf);

		session.save(prop);

		session.getTransaction().commit();
		session.close();
	}
}