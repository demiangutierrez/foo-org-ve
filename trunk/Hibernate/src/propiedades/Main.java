package propiedades;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

  public static void main(String[] args) throws Exception {
    Configuration configuration = new Configuration();
    configuration.configure("/propiedades/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MPropiedades prop = new MPropiedades();
    prop.setCal(Calendar.getInstance());
    prop.setDate1(Calendar.getInstance().getTime());

    // XXX: beware with the path
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
