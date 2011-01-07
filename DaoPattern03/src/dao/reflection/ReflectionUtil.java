package dao.reflection;

import java.lang.reflect.Method;

/**
 * @author Demián Gutierrez
 */
public class ReflectionUtil {

  private ReflectionUtil() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public static String getPropertyName(String methodName) {
    if (!methodName.startsWith("get") && //
        !methodName.startsWith("set")) {
      throw new IllegalArgumentException(methodName);
    }

    methodName = methodName.substring(3);
    methodName = methodName.substring(0, 1).toLowerCase() + //
        methodName.substring(1);

    return methodName;
  }

  // --------------------------------------------------------------------------------

  public static String getGetterName(String property) {
    property = //
    Character.toUpperCase(property.charAt(0)) + //
        property.substring(1);
    return "get" + property;
  }

  // --------------------------------------------------------------------------------

  public static String getSetterName(String property) {
    property = //
    Character.toUpperCase(property.charAt(0)) + //
        property.substring(1);
    return "set" + property;
  }

  // --------------------------------------------------------------------------------

  public static Object/**/callGet(Object thiz, String property) //
      throws Exception {

    Class<?> clazzThi = thiz.getClass();

    Method getMethod = clazzThi.getMethod(getGetterName(property), new Class<?>[]{});
    return getMethod.invoke(thiz, new Object[0]);
  }

  // --------------------------------------------------------------------------------

  public static void/*  */callSet(Object thiz, String property, //
      Object value) //
      throws Exception {

    Class<?> clazzThi = thiz.getClass();

    Method getMethod = clazzThi.getMethod(getGetterName(property), new Class<?>[]{});
    Class<?> clazzRet = getMethod.getReturnType();

    Method setMethod = clazzThi.getMethod(getSetterName(property), new Class<?>[]{clazzRet});
    setMethod.invoke(thiz, new Object[]{value});
  }
}
