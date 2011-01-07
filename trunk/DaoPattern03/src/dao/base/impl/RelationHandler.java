package dao.base.impl;

import java.util.List;

import dao.annotation.ManyToOne;
import dao.annotation.OneToMany;
import dao.base.api.FactoryDAO;
import dao.base.api.IDAO;
import dao.base.api.IDTO;
import dao.reflection.ColumnDescriptor;
import dao.reflection.DTOMetadataUtil;
import dao.reflection.ReflectionUtil;

public class RelationHandler {

  public RelationHandler() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private IDAO getOtherSideDAO(IDTO srcDTO, OneToMany oneToMany) //
      throws Exception {

    Class<?> dtoClass = oneToMany.dtoClass();
    FactoryDAO factoryDAO = srcDTO.getFactoryDAO();
    return factoryDAO.getDAO(dtoClass, srcDTO.getConnectionBean());
  }

  // --------------------------------------------------------------------------------

  private IDAO getOtherSideDAO(IDTO srcDTO, ManyToOne manyToOne) //
      throws Exception {

    Class<?> dtoClass = manyToOne.dtoClass();
    FactoryDAO factoryDAO = srcDTO.getFactoryDAO();
    return factoryDAO.getDAO(dtoClass, srcDTO.getConnectionBean());
  }

  // --------------------------------------------------------------------------------

  public void loadOneToMany(IDTO srcDTO, ColumnDescriptor srcColumnDescriptor, Object curVal) //
      throws Exception {

    if (curVal == null) {
      return;
    }

    if (!(curVal instanceof LazyList<?>)) {
      return;
    }

    LazyList<IDTO> lazyList = (LazyList<IDTO>) curVal;

    if (lazyList.getLoaded()) {
      return;
    }

    DTOMetadataUtil srcDtoMetadataUtil = new DTOMetadataUtil(srcDTO.getClass());
    ColumnDescriptor srcPkColumnDescriptor = srcDtoMetadataUtil.getPKColumnDescriptor();

    IDAO tgtDAO = getOtherSideDAO(srcDTO, srcColumnDescriptor.getOneToMany());

    List<IDTO> list = tgtDAO.listBy( //
        srcColumnDescriptor.getOneToMany().mappedBy(), //
        ReflectionUtil.callGet(srcDTO, srcPkColumnDescriptor.getName()));

    lazyList.addAll(list);
    lazyList.setLoaded(true);

    ReflectionUtil.callSet(srcDTO, srcColumnDescriptor.getName(), lazyList);
  }

  // --------------------------------------------------------------------------------

  public void loadManyToOne(IDTO srcDTO, ColumnDescriptor srcColumnDescriptor, Object curVal) //
      throws Exception {

    if (curVal == null) {
      return;
    }

    if (!(curVal instanceof BaseDTO)) {
      return;
    }

    BaseDTO baseDTO = (BaseDTO) curVal;

    if (baseDTO.getLoaded()) {
      return;
    }

    IDAO tgtDAO = getOtherSideDAO(srcDTO, srcColumnDescriptor.getManyToOne());

    DTOMetadataUtil tgtDtoMetadataUtil = new DTOMetadataUtil(baseDTO.getClass());
    ColumnDescriptor tgtPkColumnDescriptor = tgtDtoMetadataUtil.getPKColumnDescriptor();

    IDTO tgtDTO = tgtDAO.loadById( //
        ReflectionUtil.callGet(baseDTO, tgtPkColumnDescriptor.getName()));

    ReflectionUtil.callSet(srcDTO, srcColumnDescriptor.getName(), tgtDTO);
  }
}
