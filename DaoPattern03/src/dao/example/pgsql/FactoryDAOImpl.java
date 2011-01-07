package dao.example.pgsql;

import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.base.impl.LazyLoadProxy;
import dao.connection.ConnectionBean;
import dao.reflection.CGLIBUtil;

/**
 * @author Demián Gutierrez
 */
public class FactoryDAOImpl implements FactoryDAO {

  @SuppressWarnings("unchecked")
  public IDAO getDAO( //
      Class<?> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception {

    clazz = (Class<?>) //
    CGLIBUtil.getNotEnhancedClass(clazz);

    // ----------------------------------------

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(getClass().getPackage().getName());
    strbuf.append(".");

    String simpleName;

    simpleName = clazz.getSimpleName();
    simpleName = simpleName.substring(0, simpleName.length() - 3);

    strbuf.append(simpleName);
    strbuf.append("DAO");
    strbuf.append("Impl");

    IDAO ret = (IDAO) Class.forName( //
        strbuf.toString()).newInstance();

    ret.init(connectionBean, this);

    return ret;
  }

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  public IDTO getDTO( //
      Class<?> clazz, //
      ConnectionBean connectionBean) //
      throws ClassNotFoundException, Exception {

    clazz = (Class<?>) //
    CGLIBUtil.getNotEnhancedClass(clazz);

    // ----------------------------------------

    StringBuffer strbuf = new StringBuffer();

    strbuf.append(getClass().getPackage().getName());
    strbuf.append(".");
    strbuf.append(clazz.getSimpleName());
    strbuf.append("Impl");

    IDTO ret = (IDTO) //
    LazyLoadProxy.newInstance( //
        (Class<?>) Class.forName(strbuf.toString()));

    ret.init(connectionBean, this);

    return ret;
  }
}
