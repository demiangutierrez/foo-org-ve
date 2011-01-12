package dao.base.impl;

import java.sql.Connection;

import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.DtaSession;

/**
 * @author Demián Gutierrez
 */
public abstract class BaseDAO implements IDAO {

  protected static final int CHECK_INSERT = 0;
  protected static final int CHECK_UPDATE = 1;
  protected static final int CHECK_DELETE = 2;
  protected static final int CHECK_IGNORE = 3;

  // --------------------------------------------------------------------------------

  protected ConnectionBean connectionBean;

  protected DtaSession dtaSession;
  protected Connection connection;

  // --------------------------------------------------------------------------------

  public BaseDAO() {
    // Empty
  }

  // --------------------------------------------------------------------------------
  // IDAO
  // --------------------------------------------------------------------------------

  @Override
  final public void init(ConnectionBean connectionBean) {
    if (this.connectionBean != null) {
      throw new IllegalStateException("this.connectionBean != null");
    }

    this.connectionBean = connectionBean;

    dtaSession = connectionBean.getDtaSession();
    connection = connectionBean.getConnection();
  }

  // --------------------------------------------------------------------------------

  @Override
  public String getTableName() {
    return getClass().getSimpleName();
  }

  // --------------------------------------------------------------------------------
  // Misc
  // --------------------------------------------------------------------------------

  protected String singleQuotes(String str) {
    return "'" + str + "'";
  }

  // --------------------------------------------------------------------------------

  protected void checkClass( //
      IDTO dto, Class<? extends IDTO> dtoClass, int reqIdVal) {

    if (dto == null || dtoClass == null) {
      throw new IllegalArgumentException( //
          "dto == null || dtoClass == null");
    }

    // XXX: DMI: To support inheritance
    if (!dtoClass.isAssignableFrom(dtoClass)) {
      throw new IllegalArgumentException( //
          "!dtoClass.isAssignableFrom(dtoClass)");
    }
    //    if (dto.getClass() != dtoClass) {
    //      throw new IllegalArgumentException( //
    //          "dto.getClass() != dtoClass");
    //    }

    if (reqIdVal == CHECK_IGNORE) {
      return;
    }

    if (reqIdVal == CHECK_INSERT //
        && dto.getId() != 0) {
      throw new IllegalArgumentException( //
          "reqIdVal == CHECK_INSERT && dto.getId() != 0");
    }

    if ((reqIdVal == CHECK_UPDATE || reqIdVal == CHECK_DELETE) //
        && dto.getId() <= 0) {
      throw new IllegalArgumentException( //
          "(reqIdVal == CHECK_UPDATE || reqIdVal == CHECK_DELETE)" + //
              " && dto.getId() <= 0");
    }
  }

  // --------------------------------------------------------------------------------

  protected void checkCache(IDTO dto, int mode) {
    switch (mode) {
      case CHECK_INSERT :
        // In cache (deleted)
        if (dtaSession.getDelByKey(dto) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDelByKey(dto) != null : " + //
                  dtaSession.createKey(dto));
        }
        // In cache (by key)
        if (dtaSession.getDtaByKey(dto) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDtaByKey(dto) != null : " + //
                  dtaSession.createKey(dto));
        }
        // In cache (by val / tampered id)
        if (dtaSession.getDtaByVal(dto) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDtaByVal(dto) != null : " + //
                  dtaSession.createKey(dto));
        }
        break;
      case CHECK_UPDATE :
      case CHECK_DELETE :
        // In cache (deleted)
        if (dtaSession.getDelByKey(dto) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDelByKey(dto) != null : " + //
                  dtaSession.createKey(dto));
        }
        // Not in cache (by key)
        if (dtaSession.getDtaByKey(dto) == null) {
          throw new IllegalArgumentException( //
              "CHECK_UPDATE / CHECK_DELETE: " + //
                  "dtaSession.getByKey(dto) == null : " + //
                  dtaSession.createKey(dto));
        }
        break;
      default :
        throw new IllegalArgumentException("mode: " + mode);
    }
  }
}
