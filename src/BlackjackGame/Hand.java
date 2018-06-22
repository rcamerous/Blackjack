package BlackjackGame;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import BlackjackGame.Card.Rank;

/**
 * Robert Cameron
 */
public class Hand {

    private ObservableList<Node> cards;
    private SimpleIntegerProperty cardValue = new SimpleIntegerProperty(0);

    private int aces = 0;

    public Hand(ObservableList<Node> cards) {  //cards assigned to list
        this.cards = cards;
    }

    public void takeCard(Card card) {  //player takes card from deck
        cards.add(card);

        if (card.rank == Rank.ACE) {
            aces++;
        }

        if (cardValue.get() + card.value > 21 && aces > 0) {     //check is value is > 21 and if there is an ace
            cardValue.set(cardValue.get() + card.value - 10);    //if true then count ace as '1' not '11'
            aces--;
        }
        else {
            cardValue.set(cardValue.get() + card.value);
        }
    }

    public void reset() {
        cards.clear();
        cardValue.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty valueProperty() {
        return cardValue;
    }
}