package BlackjackGame;

import BlackjackGame.Card.Rank;
import BlackjackGame.Card.Suit;

/**
 *Robert Cameron
 */
public class Deck {

    private Card[] cards = new Card[52]; //assign 52 cards for deck

    public Deck() {
        refill();
    }

    public final void refill() {  //assign all cards to an array
        int i = 0;
        for (Suit suit : Suit.values()) {  
            for (Rank rank : Rank.values()) {
                cards[i++] = new Card(suit, rank);
            }
        }
    }

    public Card drawCard() {
        Card card = null;
        while (card == null) {
            int index = (int)(Math.random()*cards.length);  //create random card
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}