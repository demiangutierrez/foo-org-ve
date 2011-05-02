package dao.example.hibernate;

import java.sql.Statement;
import java.util.List;

import org.hibernate.Query;

import dao.base.api.IDTO;
import dao.base.impl.BaseDAO;
import dao.example.base.DeptDAO;

/**
 * @author Demián Gutierrez
 */
// This class should have package visibility,
// but it's used in Test0 directly for educational purposes,
// so we need to make it public.
// It's also a nice example on when and why use package visibility.
public//
class DeptDAOImpl extends BaseDAO implements DeptDAO {

  protected Class<? extends IDTO> dtoClass;

  // --------------------------------------------------------------------------------

  public DeptDAOImpl() {
    dtoClass = DeptDTOImpl.class;
  }

  // --------------------------------------------------------------------------------
  // IDAO
  // --------------------------------------------------------------------------------

  public void createTable() throws Exception {
    Statement st = connectionBean.getSession().connection().createStatement();
    st.execute("TRUNCATE TABLE t_dept");
    st.close();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dto) throws Exception {
    if (dto.getId() != 0) {
      throw new IllegalArgumentException("dto.getId() != 0");
    }

    if (connectionBean.getSession().contains(dto)) {
      throw new IllegalArgumentException("connectionBean.getSession().contains(dto)");
    }

    connectionBean.getSession().save(dto);
  }

  // --------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------

  @Override
  public void update(IDTO dto) throws Exception {
    if (!connectionBean.getSession().contains(dto)) {
      throw new IllegalArgumentException("!connectionBean.getSession().contains(dto)");
    }

    int cachedId = (Integer) connectionBean.getSession().getIdentifier(dto);

    if (dto.getId() != cachedId) {
      throw new IllegalArgumentException("dto.getId() != cachedId");
    }

    connectionBean.getSession().update(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void delete(IDTO dto) throws Exception {
    if (!connectionBean.getSession().contains(dto)) {
      throw new IllegalArgumentException("!connectionBean.getSession().contains(dto)");
    }

    int cachedId = (Integer) connectionBean.getSession().getIdentifier(dto);

    if (dto.getId() != cachedId) {
      throw new IllegalArgumentException("dto.getId() != cachedId");
    }

    connectionBean.getSession().delete(dto);
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO loadById(int id) throws Exception {
    //    return (IDTO) connectionBean.getSession().load(dtoClass, new Integer(id));

    List<IDTO> ret = listBy(IDTO.ID, new Integer(id));

    if (ret.size() == 1) {
      return ret.get(0);
    }

    return null;
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
      q.setFirstResult(off);
      q.setMaxResults(lim);
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
