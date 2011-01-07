package dao.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.annotation.Entity;
import dao.annotation.ManyToOne;
import dao.annotation.OneToMany;
import dao.annotation.PK;
import dao.annotation.SQLType;
import dao.annotation.Transient;

/**
 * @author Demián Gutierrez
 */
public class DTOMetadataUtil {

  private Class<?> clazz;

  private List<ColumnDescriptor> columnDescriptorList;

  private Entity entity;

  // --------------------------------------------------------------------------------

  public DTOMetadataUtil(Class<?> clazz) //
      throws Exception {

    this.clazz = //
    CGLIBUtil.getNotEnhancedClass(clazz);

    calcColumnDescriptorList(this.clazz);
    calcClassMetadata/*   */(this.clazz);
  }

  // --------------------------------------------------------------------------------

  private void calcColumnDescriptorList( //
      Class<?> clazz) //
      throws Exception {

    // ----------------------------------------
    // Query getters / setters individually
    // ----------------------------------------

    Map<String, ColumnDescriptor> columnDescriptorMap = //
    new HashMap<String, ColumnDescriptor>();

    Set<String> getterPropertySet = new HashSet<String>();
    Set<String> setterPropertySet = new HashSet<String>();

    Method[] methodArray = clazz.getMethods();

    for (Method method : methodArray) {
      if (!method.getName().startsWith("get") && //
          !method.getName().startsWith("set")) {
        continue;
      }

      String property = method.getName().substring(3);

      if (property.length() == 0) {
        continue;
      }

      property = //
      Character.toLowerCase(property.charAt(0)) + //
          property.substring(1);

      if (method.getName().startsWith("get")) {
        getterPropertySet.add(property);

        columnDescriptorMap.put(property, //
            new ColumnDescriptor(method.getReturnType(), property));
      } else {
        setterPropertySet.add(property);
      }
    }

    // ----------------------------------------
    // Keep only pairs of getters / setters
    // ----------------------------------------

    Iterator<Map.Entry<String, ColumnDescriptor>> itt = //
    columnDescriptorMap.entrySet().iterator();

    while (itt.hasNext()) {
      Map.Entry<String, ColumnDescriptor> entry = itt.next();

      if (!getterPropertySet.contains(entry.getKey()) || //
          !setterPropertySet.contains(entry.getKey())) {
        itt.remove();
        continue;
      }
    }

    updateAnnotations(clazz, columnDescriptorMap);

    columnDescriptorList = //
    new ArrayList<ColumnDescriptor>(columnDescriptorMap.values());
  }

  // --------------------------------------------------------------------------------

  private void updateAnnotations( //
      Class<?> clazz, //
      Map<String, ColumnDescriptor> columnDescriptorMap) //
      throws Exception {

    for (ColumnDescriptor columnDescriptor : columnDescriptorMap.values()) {
      StringBuffer strbuf = new StringBuffer();

      strbuf.append("get");
      strbuf.append(columnDescriptor.getName().substring(0, 1).toUpperCase());
      strbuf.append(columnDescriptor.getName().substring(1));

      Method method = clazz.getMethod(strbuf.toString(), new Class[0]);

      Annotation[] annotationArray = AnnotationMethodUtil.getDeclaredAnnotations( //
          clazz, method, CalcPriorityRule.INT_FIRST);

      for (int i = 0; i < annotationArray.length; i++) {
        /*   */if (annotationArray[i] instanceof ManyToOne/**/) {
          columnDescriptor.setManyToOne/**/((ManyToOne) /**/annotationArray[i]);
        } else if (annotationArray[i] instanceof OneToMany/**/) {
          columnDescriptor.setOneToMany/**/((OneToMany) /**/annotationArray[i]);
        } else if (annotationArray[i] instanceof PK/*       */) {
          columnDescriptor.setPK/*       */((PK) /*       */annotationArray[i]);
        } else if (annotationArray[i] instanceof Transient/**/) {
          columnDescriptor.setTransient/**/((Transient) /**/annotationArray[i]);
        } else if (annotationArray[i] instanceof SQLType/*  */) {
          columnDescriptor.setSQLType/*  */((SQLType) /*  */annotationArray[i]);
        }
      }
    }
  }

  // --------------------------------------------------------------------------------

  private void calcClassMetadata(Class<?> clazz) {
    Annotation[] annotationArray = AnnotationClassUtil.getDeclaredAnnotations( //
        clazz, CalcPriorityRule.INT_FIRST);

    for (int i = 0; i < annotationArray.length; i++) {
      if (annotationArray[i] instanceof Entity) {
        entity = (Entity) annotationArray[i];
      }
    }
  }

  // --------------------------------------------------------------------------------

  public ColumnDescriptor getPKColumnDescriptor() {
    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      if (columnDescriptor.getPK() != null) {
        return columnDescriptor;
      }
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public List<ColumnDescriptor> getSQLTypeNotPKColumnDescriptor() {
    List<ColumnDescriptor> ret = new ArrayList<ColumnDescriptor>();

    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      if (columnDescriptor.getPK()/*  */== null && //
          columnDescriptor.getSQLType() != null) {
        ret.add(columnDescriptor);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public List<ColumnDescriptor> getManyToOneColumnDescriptor() {
    List<ColumnDescriptor> ret = new ArrayList<ColumnDescriptor>();

    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      if (columnDescriptor.getManyToOne() != null) {
        ret.add(columnDescriptor);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public List<ColumnDescriptor> getOneToManyColumnDescriptor() {
    List<ColumnDescriptor> ret = new ArrayList<ColumnDescriptor>();

    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      if (columnDescriptor.getOneToMany() != null) {
        ret.add(columnDescriptor);
      }
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  public List<ColumnDescriptor> getAllTableColumnDescriptor() {
    List<ColumnDescriptor> ret = new ArrayList<ColumnDescriptor>();

    ret.add(getPKColumnDescriptor());

    ret.addAll(getSQLTypeNotPKColumnDescriptor());
    ret.addAll(getManyToOneColumnDescriptor());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public List<ColumnDescriptor> getColumnDescriptorList() {
    return columnDescriptorList;
  }

  // --------------------------------------------------------------------------------

  public ColumnDescriptor getColumnDescriptorByName(String name) {
    for (ColumnDescriptor columnDescriptor : columnDescriptorList) {
      if (name.equals(columnDescriptor.getName())) {
        return columnDescriptor;
      }
    }

    return null;
  }

  // --------------------------------------------------------------------------------

  public String getTableName() {
    if (entity != null) {
      return entity.value();
    }

    return clazz.getSimpleName();
  }
}
