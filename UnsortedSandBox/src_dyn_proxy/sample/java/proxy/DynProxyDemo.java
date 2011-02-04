package sample.java.proxy;
import java.lang.reflect.Method;

public class DynProxyDemo implements java.lang.reflect.InvocationHandler {

  private Object obj;

  private DynProxyDemo(Object obj) {
    this.obj = obj;
  }

  public static Object newInstance(Object obj) {
    return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
        new DynProxyDemo(obj));
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    System.err.println("Pre Call");

    Object result = method.invoke(obj, args);

    System.err.println("Pos Call");

    return result;

    //    System.err.println("Woot! " + proxy + ";" + method.getName());
    //
    //    for (Object object : args) {
    //      System.err.println(object);
    //    }
    //
    //    return null;
  }
}
