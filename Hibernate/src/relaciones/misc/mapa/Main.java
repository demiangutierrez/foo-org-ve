package relaciones.misc.mapa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Demián Gutierrez
 * @author Alejandro Salas 
 * <br> Created on Jul 1, 2008
 */
public class Main {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.configure("/relaciones/misc/mapa/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    MUnoMapa unoMapa = new MUnoMapa();
    for (int i = 0; i < 10; i++) {
      MMuchosMapa muchosMapa = new MMuchosMapa();
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
    unoMapa = (MUnoMapa) session.load(MUnoMapa.class, 1);
    for (int i = 0; i < 10; i++) {
      MMuchosMapa muchosMapa = unoMapa.getMuchosMap().get(i);
      System.out.println("codigo: " + muchosMapa.getCodigo() + " ; id: " + muchosMapa.getId());
    }

    session.getTransaction().commit();
    session.close();
  }
}
