package relaciones.varias;

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
    configuration.configure("/relaciones/varias/hibernate.cfg.xml");
    SessionFactory sessionFactory = configuration.buildSessionFactory();

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    for (int i = 0; i < 2; i++) {
      MVariasPrincipal variasPrincipal = new MVariasPrincipal();
      variasPrincipal.setAtt("att " + i);

      MVariasUno variasUno = new MVariasUno();
      variasUno.setNombre("nombre " + i);

      variasPrincipal.setVariasUnoRef(variasUno);
      variasUno.setVariasPrincipalRef(variasPrincipal);

      for (int j = 0; j < 10; j++) {
        MVariasMuchos variasMuchos = new MVariasMuchos();
        variasMuchos.setOther("Hola " + j + ";" + i);
        variasMuchos.setVariasPrincipalRef(variasPrincipal);
        variasPrincipal.getVariasMuchosList().add(variasMuchos);
        //session.save(muchos);
      }

      session.save(variasPrincipal);
    }

    session.getTransaction().commit();
    session.close();
  }
}
