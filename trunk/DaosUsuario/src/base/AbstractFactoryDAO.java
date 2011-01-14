package base;

import dao.base.api.FactoryDAO;

/**
 * @author Demián Gutierrez
 */
public class AbstractFactoryDAO {

  private enum DBType {
    PGSQL, MYSQL
  }

  // --------------------------------------------------------------------------------

  private static final DBType dbType = DBType.MYSQL;

  // --------------------------------------------------------------------------------

  private AbstractFactoryDAO() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static FactoryDAO getFactoryDAO() {
    switch (dbType) {
      case PGSQL :
        // return new dao.example.pgsql.FactoryDAOImpl();
        throw new IllegalStateException( //
            dbType.toString());

      case MYSQL :
        return new mysql.FactoryDAOImpl();

      default :
        throw new IllegalStateException( //
            dbType.toString());
    }
  }
}
