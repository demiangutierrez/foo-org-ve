package dao.reflection;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Demián Gutierrez
 */
public class CGLIBUtil {

  private CGLIBUtil() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static Class<?> getNotEnhancedClass(Class<?> cls) {
    if (Enhancer.isEnhanced(cls)) {
      return getNotEnhancedClass(cls.getSuperclass());
    }

    return cls;
  }

  // --------------------------------------------------------------------------------

  public static Class<?> getNotEnhancedClass(Object obj) {
    return getNotEnhancedClass(obj.getClass());
  }
}
