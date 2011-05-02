package dao.example.hibernate;

import java.util.List;

import org.hibernate.Query;

import dao.base.api.IDTO;
import dao.base.impl.BaseDAO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
class ProfDAOImpl extends BaseDAO implements ProfDAO {

  protected Class<? extends IDTO> dtoClass;

  // --------------------------------------------------------------------------------

  public ProfDAOImpl() {
    dtoClass = ProfDTO.class;
  }

  // --------------------------------------------------------------------------------
  // IDAO
  // --------------------------------------------------------------------------------

  public void createTable() throws Exception {
    // Empty
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    connectionBean.getSession().saveOrUpdate(dto);
  }

  // --------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------

  @Override
  public void update(IDTO dto) throws Exception {
    connectionBean.getSession().saveOrUpdate(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void delete(IDTO dto) throws Exception {
    connectionBean.getSession().delete(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO loadById(int id) throws Exception {
    return (IDTO) connectionBean.getSession().load(dtoClass, new Integer(id));
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("FROM ");
    strbuf.append(dtoClass.getName());

    System.err.println(strbuf.toString());

    Query q = (Query) connectionBean.getSession().createQuery(strbuf.toString());

    if (lim >= 0 && off >= 0) {
      q.setFirstResult(lim);
      q.setMaxResults(off);
    }

    return q.list();
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll() throws Exception {
    return listAll(-1, -1);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int countAll() throws Exception {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT COUNT(*) FROM ");
    strbuf.append(dtoClass.getName());

    System.err.println(strbuf.toString());

    Query q = (Query) connectionBean.getSession().createQuery(strbuf.toString());

    return ((Long) q.uniqueResult()).intValue();
  }

  // --------------------------------------------------------------------------------

  public List<IDTO> listBy(String key, Object val) //
      throws Exception {

    if (key == null || val == null) {
      throw new IllegalArgumentException("key == null || val == null");
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("FROM ");
    strbuf.append(dtoClass.getName());
    strbuf.append(" AS x WHERE ");
    strbuf.append(key);
    strbuf.append(" = :");
    strbuf.append(key);

    System.err.println(strbuf.toString());

    Query q = (Query) connectionBean.getSession().createQuery(strbuf.toString());

    q.setParameter(key, val);

    return q.list();
  }
}
