package state.machine.example;

public class Animo {

  // --------------------------------------------------------------------------------
  // Constantes que representan los eventos
  // --------------------------------------------------------------------------------

  public static final int EVT_BUENA_NOTICIA = 0;
  public static final int EVT_MALA_NOTICIA = 1;
  public static final int EVT_TIEMPO = 2;
  public static final int EVT_NOTICIA_DEVASTADORA = 3;
  public static final int EVT_ANTI_DEPRESIVOS = 4;
  public static final int EVT_BUSCA_AYUDA = 5;
  public static final int EVT_EVALUACION_FAVORABLE = 6;
  public static final int EVT_EVALUACION_DESFAVORABLE = 7;

  // --------------------------------------------------------------------------------
  // Constantes que representan los estados
  // --------------------------------------------------------------------------------

  public static final int ST_NORMAL = 0;
  public static final int ST_CONTENTO = 1;
  public static final int ST_TRISTE = 2;
  public static final int ST_DEPRIMIDO = 3;
  public static final int ST_MENOS_DEPRIMIDO = 4;
  public static final int ST_MUCHO_MENOS_DEPRIMIDO = 5;
  public static final int ST_MUERTO = 6;
  public static final int ST_EN_TRATAMIENTO = 7;

  // --------------------------------------------------------------------------------
  // Atributo que representa el estado actual
  // --------------------------------------------------------------------------------

  private int estado = ST_NORMAL;

  // --------------------------------------------------------------------------------

  public Animo() {
    // Empty
  }

  public Animo(int estado) {
    this.estado = estado;
  }

  // --------------------------------------------------------------------------------

  public int getEstado() {
    return estado;
  }

  // --------------------------------------------------------------------------------
  // Método para disparar eventos (cambiar el estado) segun las reglas de la
  // maquina de estados
  // --------------------------------------------------------------------------------

  public void disparaEvento(int evento) {
    switch (estado) {
      case ST_NORMAL :
        disparaEventoStNormal(evento);
        break;
      case ST_CONTENTO :
        disparaEventoStContento(evento);
        break;
      case ST_TRISTE :
        disparaEventoStTriste(evento);
        break;
      case ST_DEPRIMIDO :
        disparaEventoStDeprimido(evento);
        break;
      case ST_MENOS_DEPRIMIDO :
        disparaEventoStMenosDeprimido(evento);
        break;
      case ST_MUCHO_MENOS_DEPRIMIDO :
        disparaEventoStMuchoMenosDeprimido(evento);
        break;
      case ST_MUERTO :
        disparaEventoStMuerto(evento);
        break;
      case ST_EN_TRATAMIENTO :
        disparaEventoStEnTratamiento(evento);
      default :
        throw new IllegalStateException("Estado: " + Integer.toString(estado));
    }
  }

  // --------------------------------------------------------------------------------
  // Metodos que cambian el estado segun el evento en un estado particular y segun 
  // las reglas de la maquina de estados (estos metodos son internos y no estan
  // pensados para invocarse por el cliente
  // --------------------------------------------------------------------------------

  private void disparaEventoStNormal(int evento) {
    switch (evento) {
      case EVT_BUENA_NOTICIA :
        estado = ST_CONTENTO;
        break;
      case EVT_MALA_NOTICIA :
        estado = ST_TRISTE;
        break;
      case EVT_NOTICIA_DEVASTADORA :
        estado = ST_DEPRIMIDO;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStContento(int evento) {
    switch (evento) {
      case EVT_BUENA_NOTICIA :
        estado = ST_CONTENTO;
        break;
      case EVT_TIEMPO :
        estado = ST_NORMAL;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStTriste(int evento) {
    switch (evento) {
      case EVT_MALA_NOTICIA :
        estado = ST_DEPRIMIDO;
        break;
      case EVT_TIEMPO :
        estado = ST_NORMAL;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStDeprimido(int evento) {
    switch (evento) {
      case EVT_BUSCA_AYUDA :
        estado = ST_EN_TRATAMIENTO;
        break;
      case EVT_ANTI_DEPRESIVOS :
        estado = ST_MENOS_DEPRIMIDO;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStMenosDeprimido(int evento) {
    switch (evento) {
      case EVT_ANTI_DEPRESIVOS :
        estado = ST_MUCHO_MENOS_DEPRIMIDO;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStMuchoMenosDeprimido(int evento) {
    switch (evento) {
      case EVT_ANTI_DEPRESIVOS :
        estado = ST_MUERTO;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }

  private void disparaEventoStMuerto(int evento) {
    // ------------------------------------------
    // No hay posible solucion a estar muerto ;-)
    // ------------------------------------------
    throw new IllegalStateException( //
        "Evento: " + Integer.toString(evento) + //
            ", estado: " + Integer.toString(estado));
  }

  private void disparaEventoStEnTratamiento(int evento) {
    switch (evento) {
      case EVT_EVALUACION_FAVORABLE :
        estado = ST_NORMAL;
        break;
      case EVT_EVALUACION_DESFAVORABLE :
        estado = ST_EN_TRATAMIENTO;
        break;
      default :
        throw new IllegalStateException( //
            "Evento: " + Integer.toString(evento) + //
                ", estado: " + Integer.toString(estado));
    }
  }
}
