package relaciones.misc.mapa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jun 27, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new AnnotationConfiguration();
    configuration.configure("/relaciones/misc/mapa/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    UnoMapa unoMapa = new UnoMapa();
    for (int i = 0; i < 10; i++) {
      MuchosMapa muchosMapa = new MuchosMapa();
      muchosMapa.setCodigo(i);
      muchosMapa.setUnoMapaRef(unoMapa);
      unoMapa.getMuchosMap().put(i, muchosMapa);
    }

    session.save(unoMapa);

    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();

    unoMapa = null;
    unoMapa = (UnoMapa) session.load(UnoMapa.class, 1);
    for (int i = 0; i < 10; i++) {
      MuchosMapa muchosMapa = unoMapa.getMuchosMap().get(i);
      System.out.println("codigo: " + muchosMapa.getCodigo() + " ; id: " + muchosMapa.getId());
    }

    session.getTransaction().commit();
    session.close();
  }
}