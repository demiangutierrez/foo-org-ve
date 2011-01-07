package dao.connection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dao.base.api.IDTO;
import dao.reflection.CGLIBUtil;
import dao.reflection.ColumnDescriptor;
import dao.reflection.DTOMetadataUtil;
import dao.reflection.ReflectionUtil;

/**
 * @author Demián Gutierrez
 */
public class DtaSession {

  private Map<String, IDTO> dtaObjectByKey = //
  new HashMap<String, IDTO>();

  private Set<IDTO> delObject = //
  new HashSet<IDTO>();

  // --------------------------------------------------------------------------------

  public DtaSession() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public IDTO add(IDTO addDO) throws Exception {
    if (addDO == null) {
      return null;
    }

    IDTO curDO = getDtaByKey(addDO);

    if (curDO == null) {
      dtaObjectByKey.put(createKey(addDO), addDO);
      curDO = addDO;
    }

    return curDO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByKey(IDTO getDO) throws Exception {
    if (getDO == null) {
      return null;
    }

    IDTO curDO = dtaObjectByKey.get(createKey(getDO));

    if (curDO != null && curDO != getDO) {
      throw new IllegalArgumentException( //
          "curDO != null && curDO != getDO : " + //
              createKey(getDO));
    }

    return curDO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByVal(IDTO getDO) {
    if (dtaObjectByKey.values().contains(getDO)) {
      return getDO;
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByKey(Class<?> clazz, int id) {
    if (clazz == null || id < 1) {
      throw new IllegalArgumentException("clazz == null || id < 1");
    }

    return dtaObjectByKey.get(createKey(clazz, id));
  }

  // --------------------------------------------------------------------------------

  public IDTO del(IDTO delDO) {
    if (delDO == null) {
      return null;
    }

    dtaObjectByKey.remove(delDO);

    delObject.add(delDO);

    return delDO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDelByKey(IDTO getDO) {
    if (delObject.contains(getDO)) {
      return getDO;
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public String createKey(Class<?> clazz, Object id) {
    clazz = CGLIBUtil.getNotEnhancedClass(clazz);

    return clazz + "#" + id;
  }

  // --------------------------------------------------------------------------------

  public String createKey(IDTO dataTransfObject) throws Exception {

    DTOMetadataUtil dtoMetadataUtil = //
    new DTOMetadataUtil(dataTransfObject.getClass());

    ColumnDescriptor pkColumnDescriptor = //
    dtoMetadataUtil.getPKColumnDescriptor();

    Object idVal = ReflectionUtil.callGet( //
        dataTransfObject, pkColumnDescriptor.getName());

    return createKey(dataTransfObject.getClass(), idVal);
  }
}
