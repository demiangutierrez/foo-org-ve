package org.cyrano.objects;

import org.cyrano.objects.CardEnums.CardNumb;

/**
 * @author Demián Gutierrez
 */
public class CardUtils {

  // --------------------------------------------------------------------------------

  public static boolean sameColor/**/(Card card1, Card card2) {
    return card1.getCardColor() == card2.getCardColor();
  }

  public static boolean sameSuit/* */(Card card1, Card card2) {
    return card1.getCardSuit() == card2.getCardSuit();
  }

  // --------------------------------------------------------------------------------

  public static boolean ascendant(Card cardL, Card cardH) {
    if (cardL.getCardNumb() == CardNumb.N0K) {
      return false;
    }

    return //
    cardL.getCardNumb().ordinal() + 1 == //
    cardH.getCardNumb().ordinal();
  }
}
