package org.cyrano.objects;

/**
 * @author Demián Gutierrez
 */
public interface CardEnums {

  public enum CardSuit {
    HEA, DIA, CLU, SPA
  }

  // --------------------------------------------------------------------------------

  public enum CardNumb {
    N0A, N02, N03, N04, N05, N06, N07, N08, N09, N10, N0J, N0Q, N0K;

    // ----------------------------------------

    public static CardNumb intValToCardNumb(int val) {
      if (val < 1 || val > 14) {
        throw new IllegalArgumentException("val < 1 || val > 14");
      }

      return values()[val - 1];
    }

    // ----------------------------------------

    public static int cardNumbValToInt(CardNumb val) {
      for (int i = 0; i < values().length; i++) {
        if (values()[i] == val) {
          return i;
        }
      }

      throw new IllegalStateException();
    }
  }

  // --------------------------------------------------------------------------------

  public enum CardColor {
    RED, BLK;

    static public CardColor getCardColor(CardSuit cardSuit) {
      switch (cardSuit) {
        case HEA :
        case DIA :
          return RED;
        case CLU :
        case SPA :
          return BLK;
      }

      throw new IllegalArgumentException();
    }
  }

  // --------------------------------------------------------------------------------

  public enum CardFace {
    UP, DW
  }
}
