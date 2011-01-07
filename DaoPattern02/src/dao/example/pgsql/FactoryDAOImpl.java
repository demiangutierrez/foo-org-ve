package dao.example.pgsql;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;

/**
 * @author Demián Gutierrez
 */
public class FactoryDAOImpl implements FactoryDAO {

  public IDAO getDAO( //
      Class<? extends IDAO> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(getClass().getPackage().getName());
    strbuf.append(".");
    strbuf.append(clazz.getSimpleName());
    strbuf.append("Impl");

    IDAO ret = (IDAO) Class.forName( //
        strbuf.toString()).newInstance();

    ret.init(connectionBean);

    return ret;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDTO( //
      Class<? extends IDTO> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(getClass().getPackage().getName());
    strbuf.append(".");
    strbuf.append(clazz.getSimpleName());
    strbuf.append("Impl");

    IDTO ret = (IDTO) Class.forName( //
        strbuf.toString()).newInstance();

    ret.init(connectionBean);

    return ret;
  }
}
