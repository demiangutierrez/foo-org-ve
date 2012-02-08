package org.cyrano.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.cyrano.objects.Card;
import org.cyrano.objects.CardEnums.CardNumb;
import org.cyrano.objects.CardEnums.CardSuit;
import org.cyrano.util.misc.Hwh;
import org.cyrano.util.misc.ImageCache;

/**
 * @author Demián Gutierrez
 */
public class CardLoader {

  private static final String BACK_FILENAME = //
  "backs/_" + Settings.CARD_SIZ + //
      "/back-" + Settings.BACK_COL + "-" + Settings.CARD_SIZ + "-" + Settings.BACK_VAR + ".png";

  // --------------------------------------------------------------------------------

  private List<Card> deck = new ArrayList<Card>();

  // --------------------------------------------------------------------------------

  private String getSuitBaseName(int suit) {
    CardSuit cardSuit = CardSuit.values()[suit];
    return cardSuit.toString();
  }

  // --------------------------------------------------------------------------------

  private String getNumbBaseName(int numb) {
    CardNumb cardNumb = CardNumb.intValToCardNumb(numb);
    return cardNumb.toString();
  }

  // --------------------------------------------------------------------------------

  public void load() throws Exception {
    for (int suit = 0; suit < 4; suit++) {
      for (int numb = 1; numb < 14; numb++) {
        StringBuffer strbuf = new StringBuffer();

        strbuf.append("cards/_");
        strbuf.append(Settings.CARD_SIZ);
        strbuf.append("/");
        strbuf.append(getSuitBaseName(suit));
        strbuf.append("-");
        strbuf.append(getNumbBaseName(numb));
        strbuf.append("-" + Settings.CARD_SIZ + ".png");

        BufferedImage forebimg = ImageCache.getInstance().getImage(strbuf.toString());
        BufferedImage backbimg = ImageCache.getInstance().getImage(BACK_FILENAME);

        if (Hwh.getW(forebimg) != Settings.CARD_W || //
            Hwh.getH(forebimg) != Settings.CARD_H || //
            Hwh.getW(backbimg) != Settings.CARD_W || //
            Hwh.getH(backbimg) != Settings.CARD_H) {
          throw new IllegalArgumentException("forebimg|backbimg : " + //
              strbuf.toString() + ";" + BACK_FILENAME);
        }

        Card card = new Card( //
            forebimg, //
            backbimg, //
            CardSuit.values()[suit], //
            CardNumb.intValToCardNumb(numb));

        deck.add(card);
      }
    }
  }

  // --------------------------------------------------------------------------------

  public void shuffle() {
    List<Card> aux = deck;

    deck = new ArrayList<Card>();

    while (aux.size() > 0) {
      int index = (int) Math.floor(Math.random() * aux.size());
      deck.add(aux.remove(index));
    }
  }

  // --------------------------------------------------------------------------------

  public List<Card> getDeck() {
    return deck;
  }

  // --------------------------------------------------------------------------------

  public Card getCard(CardSuit cardSuit, CardNumb cardNumb) {
    for (Card card : deck) {
      if (card.getCardSuit() == cardSuit && //
          card.getCardNumb() == cardNumb) {
        return card;
      }
    }

    return null;
  }
}
