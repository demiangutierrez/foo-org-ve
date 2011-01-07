package dao.connection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dao.base.api.IDTO;

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

  public IDTO add(IDTO addDTO) {
    if (addDTO == null) {
      return null;
    }

    IDTO curDTO = getDtaByKey(addDTO);

    if (curDTO == null) {
      dtaObjectByKey.put(createKey(addDTO), addDTO);
      curDTO = addDTO;
    }

    return curDTO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByKey(IDTO getDTO) {
    if (getDTO == null) {
      return null;
    }

    IDTO curDTO = dtaObjectByKey.get(createKey(getDTO));

    if (curDTO != null && curDTO != getDTO) {
      throw new IllegalArgumentException( //
          "curDTO != null && curDTO != getDTO : " + //
              createKey(getDTO));
    }

    return curDTO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByVal(IDTO getDTO) {
    if (dtaObjectByKey.values().contains(getDTO)) {
      return getDTO;
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDtaByKey(Class<? extends IDTO> clazz, int id) {
    if (clazz == null || id < 1) {
      throw new IllegalArgumentException("clazz == null || id < 1");
    }

    return dtaObjectByKey.get(clazz + "#" + id);
  }

  // --------------------------------------------------------------------------------

  public IDTO del(IDTO delDTO) {
    if (delDTO == null) {
      return null;
    }

    dtaObjectByKey.remove(delDTO);

    delObject.add(delDTO);

    return delDTO;
  }

  // --------------------------------------------------------------------------------

  public IDTO getDelByKey(IDTO getDTO) {
    if (delObject.contains(getDTO)) {
      return getDTO;
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public String createKey(IDTO dto) {
    return dto.getClass() + "#" + dto.getId();
  }
}
