package stacktrace;

public class Util {

  public static Class<?> getDeclaringClazz() {
    StackTraceElement[] steArray = Thread.currentThread().getStackTrace();

    try {
      return Class.forName(steArray[2].getClassName());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e); // -> never
    }
  }
}
