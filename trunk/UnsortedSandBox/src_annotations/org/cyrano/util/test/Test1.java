package org.cyrano.util.test;

import java.lang.reflect.Method;

import org.cyrano.util.reflection.AnnotationMethodUtil;
import org.cyrano.util.reflection.CalcPriorityRule;

public class Test1 {

  public static void main(String[] args) throws Exception {
    Class<?> clazz = ClC0.class;

    Method m = clazz.getMethod("m1", new Class[0]);

    AnnotationMethodUtil annotationMethodUtil = new AnnotationMethodUtil();

    annotationMethodUtil.calcDeclaredAnnotations(clazz, m, CalcPriorityRule.SUP_FIRST);
    annotationMethodUtil.dump();
  }
}
