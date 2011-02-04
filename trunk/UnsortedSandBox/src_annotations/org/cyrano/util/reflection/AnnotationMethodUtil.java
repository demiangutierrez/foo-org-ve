package org.cyrano.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Demián Gutierrez
 */
public class AnnotationMethodUtil {

  protected Map<Class<?>, AnnotationBean> annotationBeanByClassMap = //
  new LinkedHashMap<Class<?>, AnnotationBean>();

  // --------------------------------------------------------------------------------

  public AnnotationMethodUtil() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void calcDeclaredAnnotations( //
      Class<?> cls, Method met, CalcPriorityRule calcPriorityRule) {
    annotationBeanByClassMap.clear();

    calcDeclaredAnnotations(cls, met, met, calcPriorityRule);
  }

  // --------------------------------------------------------------------------------

  protected void calcDeclaredAnnotations( //
      Class<?> curCls, Method curMet, Method basMet, CalcPriorityRule calcPriorityRule) {

    // ----------------------------------------
    // Go for current method
    // ----------------------------------------

    if (curMet != null) {
      Annotation[] annotationArray = curMet.getDeclaredAnnotations();

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
    }

    switch (calcPriorityRule) {
      case SUP_FIRST :
        calcSupDeclaredAnnotations(curCls, curMet, basMet, calcPriorityRule);
        calcIntDeclaredAnnotations(curCls, curMet, basMet, calcPriorityRule);
        break;

      case INT_FIRST :
        calcIntDeclaredAnnotations(curCls, curMet, basMet, calcPriorityRule);
        calcSupDeclaredAnnotations(curCls, curMet, basMet, calcPriorityRule);
        break;

      default :
        throw new IllegalArgumentException( //
            calcPriorityRule.toString());
    }
  }

  // --------------------------------------------------------------------------------

  protected void calcSupDeclaredAnnotations( //
      Class<?> curCls, Method curMet, Method basMet, CalcPriorityRule calcPriorityRule) {

    // ----------------------------------------
    // Go for superclass
    // ----------------------------------------

    Class<?> supCls = curCls.getSuperclass();

    if (supCls != null) {
      Method supMet = null;

      try {
        supMet = supCls.getMethod( //
            basMet.getName(), basMet.getParameterTypes());
      } catch (NoSuchMethodException e) {
        // Empty: supMet will be null
      }

      calcDeclaredAnnotations(supCls, supMet, basMet, calcPriorityRule);
    }
  }

  // --------------------------------------------------------------------------------

  protected void calcIntDeclaredAnnotations( //
      Class<?> curCls, Method curMet, Method basMet, CalcPriorityRule calcPriorityRule) {

    // ----------------------------------------
    // Go for interfaces
    // ----------------------------------------

    Class<?>[] interfaceArray = curCls.getInterfaces();

    for (int i = 0; i < interfaceArray.length; i++) {
      Method supMet = null;

      try {
        supMet = interfaceArray[i].getMethod( //
            basMet.getName(), basMet.getParameterTypes());
      } catch (NoSuchMethodException e) {
        // Empty: supMet will be null
      }

      calcDeclaredAnnotations(interfaceArray[i], supMet, basMet, calcPriorityRule);
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
      Class<?> cls, Method met, CalcPriorityRule calcPriorityRule) {
    AnnotationMethodUtil annotationUtil = new AnnotationMethodUtil();
    annotationUtil.calcDeclaredAnnotations(cls, met, calcPriorityRule);

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
