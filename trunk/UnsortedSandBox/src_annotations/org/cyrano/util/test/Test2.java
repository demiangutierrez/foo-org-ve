package org.cyrano.util.test;

import org.cyrano.util.reflection.AnnotationClassUtil;
import org.cyrano.util.reflection.CalcPriorityRule;

public class Test2 {

  public static void main(String[] args) throws Exception {
    Class<?> clazz = ClC0.class;

    AnnotationClassUtil annotationClassUtil = new AnnotationClassUtil();

    annotationClassUtil.calcDeclaredAnnotations(clazz, CalcPriorityRule.SUP_FIRST);
    annotationClassUtil.dump();
  }
}
