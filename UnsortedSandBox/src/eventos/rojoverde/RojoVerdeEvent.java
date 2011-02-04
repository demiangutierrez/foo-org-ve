package eventos.rojoverde;
import java.awt.Point;
import java.util.EventObject;

public class RojoVerdeEvent extends EventObject {

  private Point coordenada;

  public RojoVerdeEvent(Object source, Point coordenada) {
    super(source);
    this.coordenada = coordenada;
  }

  public Point getCoordenada() {
    return coordenada;
  }
}
