package org.cyrano.util.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Demián Gutierrez
 */
public class AnnotationClassUtil {

  protected Map<Class<?>, AnnotationBean> annotationBeanByClassMap = //
  new LinkedHashMap<Class<?>, AnnotationBean>();

  // --------------------------------------------------------------------------------

  public AnnotationClassUtil() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void calcDeclaredAnnotations( //
      Class<?> curCls, CalcPriorityRule calcPriorityRule) {

    Annotation[] annotationArray = curCls.getDeclaredAnnotations();

    for (int i = 0; i < annotationArray.length; i++) {

      // ---------------------------------------------------------------
      // Don't add if already there (use the deepest level in hierarchy)
      // ---------------------------------------------------------------

      if (!annotationBeanByClassMap.containsKey(annotationArray[i].getClass())) {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.annotation = annotationArray[i];
        annotationBean.source/* */= curCls;

        annotationBeanByClassMap.put( //
            annotationArray[i].getClass(), annotationBean);
      }
    }

    switch (calcPriorityRule) {
      case SUP_FIRST :
        calcSupDeclaredAnnotations(curCls, calcPriorityRule);
        calcIntDeclaredAnnotations(curCls, calcPriorityRule);
        break;

      case INT_FIRST :
        calcIntDeclaredAnnotations(curCls, calcPriorityRule);
        calcSupDeclaredAnnotations(curCls, calcPriorityRule);
        break;

      default :
        throw new IllegalArgumentException( //
            calcPriorityRule.toString());
    }
  }

  // --------------------------------------------------------------------------------

  protected void calcSupDeclaredAnnotations( //
      Class<?> curCls, CalcPriorityRule calcPriorityRule) {

    // ----------------------------------------
    // Go for superclass
    // ----------------------------------------

    Class<?> supCls = curCls.getSuperclass();

    if (supCls == null) {
      return;
    }

    calcDeclaredAnnotations(supCls, calcPriorityRule);
  }

  // --------------------------------------------------------------------------------

  protected void calcIntDeclaredAnnotations( //
      Class<?> curCls, CalcPriorityRule calcPriorityRule) {

    // ----------------------------------------
    // Go for interfaces
    // ----------------------------------------

    Class<?>[] interfaceArray = curCls.getInterfaces();

    for (int i = 0; i < interfaceArray.length; i++) {
      calcDeclaredAnnotations(interfaceArray[i], calcPriorityRule);
    }
  }

  // --------------------------------------------------------------------------------

  public Annotation[] getDeclaredAnnotations() {
    List<Annotation> ret = new ArrayList<Annotation>();

    for (AnnotationBean annotationBean : annotationBeanByClassMap.values()) {
      ret.add(annotationBean.annotation);
    }

    return ret.toArray(new Annotation[0]);
  }

  // --------------------------------------------------------------------------------
  // Static shortcut
  // --------------------------------------------------------------------------------

  public static Annotation[] getDeclaredAnnotations( //
      Class<?> cls, CalcPriorityRule calcPriorityRule) {
    AnnotationClassUtil annotationUtil = new AnnotationClassUtil();
    annotationUtil.calcDeclaredAnnotations(cls, calcPriorityRule);

    return annotationUtil.getDeclaredAnnotations();
  }

  // --------------------------------------------------------------------------------
  // Just for debug
  // --------------------------------------------------------------------------------

  protected static class AnnotationBean {

    public Annotation/**/annotation;
    public Class<?>/*  */source;
  }

  // --------------------------------------------------------------------------------

  public void dump() {
    for (AnnotationBean annotationBean : annotationBeanByClassMap.values()) {
      System.err.println( //
          annotationBean.annotation + ";" + annotationBean.source);
    }
  }
}
