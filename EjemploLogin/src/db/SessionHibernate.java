package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class SessionHibernate {
	
	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	
	private SessionHibernate() {
		//Empty
	}
	
	public static SessionFactory getInstance(){
		if(sessionFactory == null){
			configuration = new AnnotationConfiguration();
			configuration.configure("/db/hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
		}
		return sessionFactory;
	}
}
