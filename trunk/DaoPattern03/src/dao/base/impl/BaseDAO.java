package dao.base.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.annotation.ManyToOne;
import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.connection.ConnectionBean;
import dao.connection.DtaSession;
import dao.reflection.CGLIBUtil;
import dao.reflection.ColumnDescriptor;
import dao.reflection.DTOMetadataUtil;
import dao.reflection.ReflectionUtil;

/**
 * @author Demián Gutierrez
 */
public abstract class BaseDAO implements IDAO {

  protected static final int CHECK_INSERT = 0;
  protected static final int CHECK_UPDATE = 1;
  protected static final int CHECK_DELETE = 2;
  protected static final int CHECK_IGNORE = 3;

  // --------------------------------------------------------------------------------

  protected Class<?> dataTransfObjectClass;

  protected ConnectionBean connectionBean;

  protected DtaSession dtaSession;
  protected Connection connection;

  protected FactoryDAO factoryDAO;

  // --------------------------------------------------------------------------------

  public BaseDAO(Class<?> dataObjectClass) {
    this.dataTransfObjectClass = dataObjectClass;
  }

  // --------------------------------------------------------------------------------

  @Override
  final public void init( //
      ConnectionBean connectionBean, FactoryDAO factoryDAO) {

    if (this.connectionBean != null) {
      throw new IllegalStateException("this.connectionBean != null");
    }

    this.connectionBean = connectionBean;

    dtaSession = connectionBean.getDtaSession();
    connection = connectionBean.getConnection();

    if (this.factoryDAO != null) {
      throw new IllegalStateException("this.factoryDAO != null");
    }

    this.factoryDAO = factoryDAO;
  }

  // --------------------------------------------------------------------------------

  private void dropTable(DTOMetadataUtil dtoMetadataUtil) //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("DROP TABLE IF EXISTS ");
    strbuf.append(dtoMetadataUtil.getTableName());
    strbuf.append(" CASCADE");

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  private void dropSequence(DTOMetadataUtil dtoMetadataUtil) //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("DROP SEQUENCE IF EXISTS ");
    strbuf.append("seq_");
    strbuf.append(dtoMetadataUtil.getTableName());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  private void createSequence(DTOMetadataUtil dtoMetadataUtil) //
      throws Exception {

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("CREATE SEQUENCE ");
    strbuf.append("seq_");
    strbuf.append(dtoMetadataUtil.getTableName());

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());
  }

  // --------------------------------------------------------------------------------

  @Override
  public void createTable() throws Exception {

    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    // ----------------------------------------

    dropTable(dtoMetadataUtil);
    dropSequence(dtoMetadataUtil);

    // ----------------------------------------

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("CREATE TABLE ");
    strbuf.append(dtoMetadataUtil.getTableName());
    strbuf.append(" (");

    // ----------------------------------------
    // PK Column
    // ----------------------------------------

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    strbuf.append(pkColumnDescriptor.getName());
    strbuf.append(" ");
    strbuf.append(pkColumnDescriptor.getSQLType().value());
    strbuf.append(" PRIMARY KEY");

    // ----------------------------------------

    List<ColumnDescriptor> columnDescriptorList;

    // ----------------------------------------
    // SQLTypeNotPK Columns
    // ----------------------------------------

    columnDescriptorList = //
    dtoMetadataUtil.getSQLTypeNotPKColumnDescriptor();

    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      strbuf.append(", ");
      strbuf.append(columnDescriptor.getName());
      strbuf.append("  ");
      strbuf.append(columnDescriptor.getSQLType().value());
    }

    // ----------------------------------------
    // ManyToOne Columns
    // ----------------------------------------

    columnDescriptorList = //
    dtoMetadataUtil.getManyToOneColumnDescriptor();

    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      ManyToOne manyToOne = columnDescriptor.getManyToOne();

      Class<?> refDataTransfObjectClass = //
      manyToOne.dtoClass();

      DTOMetadataUtil refDtoMetadataUtil = //
      new DTOMetadataUtil(refDataTransfObjectClass);

      ColumnDescriptor refPkColumnDescriptor = //
      refDtoMetadataUtil.getPKColumnDescriptor();

      strbuf.append(", ");
      strbuf.append(columnDescriptor.getName());
      strbuf.append("  ");
      strbuf.append(refPkColumnDescriptor.getSQLType().value());
      strbuf.append(" REFERENCES ");

      strbuf.append(refDtoMetadataUtil.getTableName());
    }

    strbuf.append(")");

    // ----------------------------------------

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    // ----------------------------------------

    createSequence(dtoMetadataUtil);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void insert(IDTO dataTransfObject) throws Exception {
    checkCache(dataTransfObject, CHECK_INSERT);
    checkClass(dataTransfObject, dataTransfObjectClass, CHECK_INSERT);

    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    // ----------------------------------------

    dataTransfObject.setId(getNextId(dtoMetadataUtil));

    // ----------------------------------------

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("INSERT INTO ");
    strbuf.append(dtoMetadataUtil.getTableName());
    strbuf.append(" (");

    // ----------------------------------------

    List<ColumnDescriptor> columnDescriptorList = //
    dtoMetadataUtil.getAllTableColumnDescriptor();

    Iterator<ColumnDescriptor> itt;

    // ----------------------------------------
    // Creates INSERT properties list
    // ----------------------------------------

    itt = columnDescriptorList.iterator();

    while (itt.hasNext()) {
      ColumnDescriptor columnDescriptor = itt.next();

      strbuf.append(columnDescriptor.getName());

      if (itt.hasNext()) {
        strbuf.append(", ");
      }
    }

    // ----------------------------------------
    // Creates INSERT values list
    // ----------------------------------------

    strbuf.append(") VALUES (");

    itt = columnDescriptorList.iterator();

    while (itt.hasNext()) {
      itt.next();

      strbuf.append("?");

      if (itt.hasNext()) {
        strbuf.append(", ");
      }
    }

    strbuf.append(")");

    System.err.println(strbuf.toString());

    // ----------------------------------------
    // Creates PS and fills it
    // ----------------------------------------

    PreparedStatement ps = //
    connection.prepareStatement(strbuf.toString());

    itt = columnDescriptorList.iterator();

    for (int i = 1; itt.hasNext(); /**/) {
      ColumnDescriptor columnDescriptor = itt.next();

      Object obj = null;

      // ----------------------------------------

      if (IDTO.class.isAssignableFrom( //
          columnDescriptor.getType())) {

        ReferenceUtil referenceUtil = new ReferenceUtil( //
            dataTransfObject, columnDescriptor);

        obj = referenceUtil.getIdAsObject();
      } else {
        obj = ReflectionUtil.callGet( //
            dataTransfObject, columnDescriptor.getName());
      }

      ps.setObject(i, obj);

      i++;
    }

    // ----------------------------------------
    // Executes PS
    // ----------------------------------------

    ps.execute();
    ps.close();

    dataTransfObject.setLoaded(true);

    dtaSession.add(dataTransfObject);
  }

  // --------------------------------------------------------------------------------

  @Override
  public void update(IDTO dataTransfObject) throws Exception {
    checkCache(dataTransfObject, CHECK_UPDATE);
    checkClass(dataTransfObject, dataTransfObjectClass, CHECK_UPDATE);

    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    // ----------------------------------------

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("UPDATE ");
    strbuf.append(dtoMetadataUtil.getTableName());
    strbuf.append(" SET ");

    // ----------------------------------------

    List<ColumnDescriptor> columnDescriptorList = //
    dtoMetadataUtil.getAllTableColumnDescriptor();

    Iterator<ColumnDescriptor> itt;

    // ----------------------------------------
    // Creates INSERT properties list
    // ----------------------------------------

    itt = columnDescriptorList.iterator();

    while (itt.hasNext()) {
      ColumnDescriptor columnDescriptor = itt.next();

      strbuf.append(columnDescriptor.getName());

      strbuf.append(" = ?");

      if (itt.hasNext()) {
        strbuf.append(", ");
      }
    }

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    strbuf.append(" WHERE ");
    strbuf.append(pkColumnDescriptor.getName());
    strbuf.append(" = ");
    strbuf.append(ReflectionUtil.callGet( //
        dataTransfObject, pkColumnDescriptor.getName()));

    System.err.println(strbuf.toString());

    // ----------------------------------------
    // Creates PS and fills it
    // ----------------------------------------

    PreparedStatement ps = //
    connection.prepareStatement(strbuf.toString());

    itt = columnDescriptorList.iterator();

    for (int i = 1; itt.hasNext(); /**/) {
      ColumnDescriptor columnDescriptor = itt.next();

      Object obj = null;

      // ----------------------------------------

      if (IDTO.class.isAssignableFrom( //
          columnDescriptor.getType())) {

        ReferenceUtil referenceUtil = new ReferenceUtil( //
            dataTransfObject, columnDescriptor);

        obj = referenceUtil.getIdAsObject();
      } else {
        obj = ReflectionUtil.callGet( //
            dataTransfObject, columnDescriptor.getName());
      }

      ps.setObject(i, obj);

      i++;
    }

    // ----------------------------------------
    // Executes PS
    // ----------------------------------------

    ps.execute();
    ps.close();
  }

  // --------------------------------------------------------------------------------

  @Override
  public void delete(IDTO dataTransfObject) throws Exception {
    checkCache(dataTransfObject, CHECK_DELETE);
    checkClass(dataTransfObject, dataTransfObjectClass, CHECK_DELETE);

    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("DELETE FROM ");
    strbuf.append(dtoMetadataUtil.getTableName());

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    strbuf.append(" WHERE ");
    strbuf.append(pkColumnDescriptor.getName());
    strbuf.append(" = ");
    strbuf.append(ReflectionUtil.callGet( //
        dataTransfObject, pkColumnDescriptor.getName()));

    System.err.println(strbuf.toString());

    connection.createStatement().execute(strbuf.toString());

    dtaSession.del(dataTransfObject);
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO loadById(Object id) throws Exception {
    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(dtoMetadataUtil.getTableName());

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    strbuf.append(" WHERE ");
    strbuf.append(pkColumnDescriptor.getName());
    strbuf.append(" = ");
    strbuf.append(id);

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    if (rs.next()) {
      return resultSetToDO(rs);
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public List<IDTO> listBy(String key, Object val) //
      throws Exception {
    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    if (key == null || val == null) {
      throw new IllegalArgumentException("key == null || val == null");
    }

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(dtoMetadataUtil.getTableName());

    strbuf.append(" WHERE ");
    strbuf.append(key);
    strbuf.append(" = ");
    strbuf.append(val);

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    List<IDTO> ret = new ArrayList<IDTO>();

    while (rs.next()) {
      ret.add(resultSetToDO(rs));
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll(int lim, int off) throws Exception {
    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT * FROM ");
    strbuf.append(dtoMetadataUtil.getTableName());

    if (lim >= 0 && off >= 0) {
      strbuf.append(" LIMIT  ");
      strbuf.append(lim);
      strbuf.append(" OFFSET ");
      strbuf.append(off);
    }

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    List<IDTO> ret = new ArrayList<IDTO>();

    while (rs.next()) {
      ret.add(resultSetToDO(rs));
    }

    return ret;
  }

  //--------------------------------------------------------------------------------

  @Override
  public List<IDTO> listAll() throws Exception {
    return listAll(-1, -1);
  }

  // --------------------------------------------------------------------------------

  @Override
  public int countAll() throws Exception {
    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT COUNT(*) FROM ");
    strbuf.append(dtoMetadataUtil.getTableName());

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    rs.next();

    return rs.getInt("count");
  }

  // --------------------------------------------------------------------------------

  @Override
  public IDTO resultSetToDO(ResultSet rs) throws Exception {
    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObjectClass);

    // ----------------------------------------

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    //    IDTO ret = //
    //    (IDTO) dtaSession.getDtaByKey( //
    //        dataTransfObjectClass, //
    //        rs.getInt(pkColumnDescriptor.getName()));
    //
    //    if (ret != null) {
    //      return ret;
    //    }
    //
    //    ret = factoryDAO.getDTO(dataTransfObjectClass, connectionBean);

    // Dirty, but it seems to be the only way to be consistent with
    // the cache and the cglib nightmare we have right now...
    IDTO newDto = factoryDAO.getDTO(dataTransfObjectClass, connectionBean);

    IDTO ret = //
    (IDTO) dtaSession.getDtaByKey( //
        newDto.getClass(), //
        rs.getInt(pkColumnDescriptor.getName()));

    if (ret != null) {
      return ret;
    }

    ret = newDto;

    List<ColumnDescriptor> columnDescriptorList = //
    dtoMetadataUtil.getAllTableColumnDescriptor();

    Iterator<ColumnDescriptor> itt;

    // ----------------------------------------

    itt = columnDescriptorList.iterator();

    while (itt.hasNext()) {
      ColumnDescriptor columnDescriptor = itt.next();

      Object obj = rs.getObject(columnDescriptor.getName());

      if (IDTO.class.isAssignableFrom( //
          columnDescriptor.getType())) {

        //        ReferenceUtil referenceUtil = new ReferenceUtil( //
        //            ret, columnDescriptor);

        if (obj != null) {
          ManyToOne manyToOne = columnDescriptor.getManyToOne();

          Class<?> refDataTransfObjectClass = //
          manyToOne.dtoClass();

          DTOMetadataUtil refDtoMetadataUtil = //
          new DTOMetadataUtil(refDataTransfObjectClass);

          //          BaseDAO dataAccessObject = (BaseDAO) //
          //          factoryDAO.getDAO(refDataTransfObjectClass, connectionBean);

          BaseDTO ref = (BaseDTO) //
          factoryDAO.getDTO(refDataTransfObjectClass, connectionBean);
          //dataAccessObject.dataTransfObjectClass.newInstance();

          ColumnDescriptor refPkColumnDescriptor = //
          refDtoMetadataUtil.getPKColumnDescriptor();

          ReflectionUtil.callSet(ref, refPkColumnDescriptor.getName(), obj);

          obj = ref;
        }

        //        obj = referenceUtil.getIdAsObject();
      }

      System.err.println(ret + ";" + columnDescriptor.getName() + ";" + obj);
      ReflectionUtil.callSet( //
          ret, columnDescriptor.getName(), obj);
    }

    //      if (columnDescriptor.getType() == ReferenceList.class) {
    //        continue;
    //      }

    // TODO: Next is ok, but clean it up and comment

    //      if (columnDescriptor.getType().equals(Reference.class)) {
    //        if (obj == null) {
    //          DTOMetadataUtil.callSet(ret, columnDescriptor.getName(), //
    //              columnDescriptor.getType().newInstance());
    //        } else {
    //          Reference<?> ref = (Reference<?>) columnDescriptor.getType().newInstance();
    //          ref.setRefIdent((Integer) obj);
    //          DTOMetadataUtil.callSet(ret, columnDescriptor.getName(), ref);
    //        }
    //      } else {
    //        DTOMetadataUtil.callSet(ret, columnDescriptor.getName(), obj);
    //      }
    //    }

    // Fill all OneToMany attributes with LazyList...

    List<ColumnDescriptor> oneToManyColumnDescriptorList = //
    dtoMetadataUtil.getOneToManyColumnDescriptor();

    for (ColumnDescriptor oneToManyColumnDescriptor : oneToManyColumnDescriptorList) {
      ReflectionUtil.callSet(ret, oneToManyColumnDescriptor.getName(), new LazyList());
    }

    ret.setLoaded(true);

    return dtaSession.add(ret);
  }

  // --------------------------------------------------------------------------------

  protected String singleQuotes(String str) {
    return "'" + str + "'";
  }

  // --------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  protected void checkClass( //
      /*            */IDTO/* */dataTransfObject, //
      Class<?>/**/dataTransfObjectClassFromDAO, //
      int reqIdVal) {

    Class<?> dataTransfObjectClassFromOBJ = //
    (Class<?>) CGLIBUtil.getNotEnhancedClass(dataTransfObject);

    // ----------------------------------------

    if (dataTransfObject == null || dataTransfObjectClassFromDAO == null) {
      throw new IllegalArgumentException( //
          "dataObject == null || dataObjectClass == null | " + //
              dataTransfObject + ";" + dataTransfObjectClassFromDAO);
    }

    // ----------------------------------------

    //if (dataTransfObjectClassFromOBJ != dataTransfObjectClassFromDAO) {
    if (!dataTransfObjectClassFromDAO.isAssignableFrom(dataTransfObjectClassFromOBJ)) {
      throw new IllegalArgumentException( //
          "!dataTransfObjectClassFromDAO.isAssignableFrom(dataTransfObjectClassFromOBJ) | " + //
              dataTransfObjectClassFromOBJ + ";" + dataTransfObjectClassFromDAO);
    }

    // ----------------------------------------

    if (reqIdVal == CHECK_IGNORE) {
      return;
    }

    // ----------------------------------------

    if (reqIdVal == CHECK_INSERT //
        && dataTransfObject.getId() != 0) {
      throw new IllegalArgumentException( //
          "reqIdVal == CHECK_INSERT && dataTransfObject.getId() != 0");
    }

    // ----------------------------------------

    if ((reqIdVal == CHECK_UPDATE || reqIdVal == CHECK_DELETE) //
        && dataTransfObject.getId() <= 0) {
      throw new IllegalArgumentException( //
          "(reqIdVal == CHECK_UPDATE || reqIdVal == CHECK_DELETE)" + //
              " && dataTransfObject.getId() <= 0");
    }
  }

  // --------------------------------------------------------------------------------

  protected void checkCache(IDTO dataTransfObject, int mode) throws Exception {

    switch (mode) {
      case CHECK_INSERT :
        // In cache (deleted)
        if (dtaSession.getDelByKey(dataTransfObject) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDelByKey(dataTransfObject) != null : " + //
                  dtaSession.createKey(dataTransfObject));
        }
        // In cache (by key)
        if (dtaSession.getDtaByKey(dataTransfObject) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDtaByKey(dataTransfObject) != null : " + //
                  dtaSession.createKey(dataTransfObject));
        }
        // In cache (by val / tampered id)
        if (dtaSession.getDtaByVal(dataTransfObject) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDtaByVal(dataTransfObject) != null : " + //
                  dtaSession.createKey(dataTransfObject));
        }
        break;
      case CHECK_UPDATE :
      case CHECK_DELETE :
        // In cache (deleted)
        if (dtaSession.getDelByKey(dataTransfObject) != null) {
          throw new IllegalArgumentException( //
              "CHECK_INSERT: " + //
                  "dtaSession.getDelByKey(dataTransfObject) != null : " + //
                  dtaSession.createKey(dataTransfObject));
        }
        // Not in cache (by key)
        if (dtaSession.getDtaByKey(dataTransfObject) == null) {
          throw new IllegalArgumentException( //
              "CHECK_UPDATE / CHECK_DELETE: " + //
                  "dtaSession.getByKey(dataTransfObject) == null : " + //
                  dtaSession.createKey(dataTransfObject));
        }
        break;
      default :
        throw new IllegalArgumentException("mode: " + mode);
    }
  }

  // --------------------------------------------------------------------------------

  protected int getNextId(DTOMetadataUtil dtoMetadataUtil) throws SQLException {
    StringBuffer strbuf = new StringBuffer();

    strbuf.append("SELECT nextval(");
    strbuf.append(singleQuotes("seq_" + dtoMetadataUtil.getTableName()));
    strbuf.append(")");

    System.err.println(strbuf.toString());

    ResultSet rs = //
    connection.createStatement().executeQuery(strbuf.toString());

    if (!rs.next()) {
      throw new IllegalStateException("!rs.next()");
    }

    return rs.getInt("nextval");
  }

  // --------------------------------------------------------------------------------

  //  public void loadReferenceListList( //
  //      ReferenceList<DataTransfObject> referenceList, //
  //      DataTransfObject dataObject) throws Exception {
  //
  //    checkCache(dataObject, CHECK_UPDATE);
  //    //checkClass(dataObject, dataObjectClass, CHECK_UPDATE);
  //
  //    OneToMany oneToMany = referenceList.getOneToMany();
  //
  //    InterfaceDAO interfaceDAO = (InterfaceDAO) FactoryDAO.getDAO( //
  //        oneToMany.daoClass(), connectionBean);
  //
  //    referenceList.getList().clear();
  //    referenceList.getList().addAll( //
  //        interfaceDAO.listByProperty(oneToMany.mappedBy(), dataObject.getId()));
  //  }
  //
  //  // --------------------------------------------------------------------------------
  //
  //  public List<DataTransfObject> listByProperty(String property, Object value) throws Exception {
  //    StringBuffer strbuf = new StringBuffer();
  //
  //    strbuf.append("SELECT * FROM ");
  //    strbuf.append(getTableName());
  //    strbuf.append(" WHERE ");
  //    strbuf.append(property);
  //    strbuf.append(" = ?");
  //
  //    System.err.println(strbuf.toString());
  //
  //    PreparedStatement ps = //
  //    connection.prepareStatement(strbuf.toString());
  //
  //    ps.setObject(1, value);
  //
  //    ResultSet rs = ps.executeQuery();
  //
  //    List<DataTransfObject> ret = new ArrayList<DataTransfObject>();
  //
  //    while (rs.next()) {
  //      ret.add(resultSetToDO(rs));
  //    }
  //
  //    ps.close();
  //
  //    return ret;
  //  }
  //
  //  public Class<? extends DataTransfObject> getDataTransfObjectClass() {
  //    return dataTransfObjectClass;
  //  }
}
