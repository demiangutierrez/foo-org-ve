package eventos.rojoverde;
import java.util.EventListener;

public interface RojoVerdeListener extends EventListener {

  public static final int METHOD_ROJO = 0;
  public static final int METHOD_VERDE = 1;

  public void rojoPerformed(RojoVerdeEvent evt);

  public void verdePerformed(RojoVerdeEvent evt);
}
