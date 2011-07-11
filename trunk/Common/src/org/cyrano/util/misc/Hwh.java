package org.cyrano.util.misc;

import java.lang.reflect.Method;

public class Hwh {

  public static int getW(Object obj) {
    Class<?> clazz = obj.getClass();

    try {
      Method method = clazz.getMethod("getWidth", new Class<?>[0]);
      return ((Number) method.invoke(obj, new Object[0])).intValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static int getH(Object obj) {
    Class<?> clazz = obj.getClass();

    try {
      Method method = clazz.getMethod("getHeight", new Class<?>[0]);
      return ((Number) method.invoke(obj, new Object[0])).intValue();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
