package dao.base.impl;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import dao.base.api.IDTO;
import dao.reflection.ColumnDescriptor;
import dao.reflection.DTOMetadataUtil;
import dao.reflection.ReflectionUtil;

/**
 * @author Demián Gutierrez
 */
public class LazyLoadProxy implements MethodInterceptor {

  private static LazyLoadProxy instance = new LazyLoadProxy();

  // --------------------------------------------------------------------------------

  public LazyLoadProxy() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static Object newInstance(Class<?> clazz) {
    try {
      Enhancer enhancer = new Enhancer();

      enhancer.setSuperclass/*   */(clazz);
      enhancer.setCallback/*  */(instance);

      return enhancer.create();
    } catch (Throwable e) {
      throw new Error(e.getMessage());
    }
  }

  // --------------------------------------------------------------------------------

  @Override
  public Object intercept( //
      Object obj, Method method, Object[] args, MethodProxy proxy) //
      throws Throwable {

    if (!method.getName().startsWith("get")) {
      return proxy.invokeSuper(obj, args);
    }

    // ----------------------------------------

    if (IDTO.class.isAssignableFrom(method.getReturnType())) {
      loadDto(obj, method, args, proxy);
    }

    if (List.class.isAssignableFrom(method.getReturnType())) {
      loadLst(obj, method, args, proxy);
    }

    // ----------------------------------------

    return proxy.invokeSuper(obj, args);
  }

  // --------------------------------------------------------------------------------

  protected void loadDto( //
      Object obj, Method method, Object[] args, MethodProxy proxy) //
      throws Throwable {

    IDTO currDTO = (BaseDTO) proxy.invokeSuper(obj, args);

    DTOMetadataUtil dtoMetadataUtil = new DTOMetadataUtil(obj.getClass());

    ColumnDescriptor srcColumnDescriptor = //
    dtoMetadataUtil.getColumnDescriptorByName( //
        ReflectionUtil.getPropertyName(method.getName()));

    RelationHandler relationHandler = new RelationHandler();
    relationHandler.loadManyToOne((IDTO) obj, srcColumnDescriptor, currDTO);
  }

  // --------------------------------------------------------------------------------

  protected void loadLst( //
      Object obj, Method method, Object[] args, MethodProxy proxy) //
      throws Throwable {

    List<?> currList = (List<?>) proxy.invokeSuper(obj, args);

    DTOMetadataUtil dtoMetadataUtil = new DTOMetadataUtil(obj.getClass());

    ColumnDescriptor srcColumnDescriptor = //
    dtoMetadataUtil.getColumnDescriptorByName( //
        ReflectionUtil.getPropertyName(method.getName()));

    RelationHandler relationHandler = new RelationHandler();
    relationHandler.loadOneToMany((IDTO) obj, srcColumnDescriptor, currList);
  }
}
