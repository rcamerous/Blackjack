package BlackjackGame;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *Robert Cameron
 */
public class Card extends Parent {

    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 140;

    enum Suit {
        DIAMONDS, HEARTS, SPADES, CLUBS;  //enumerate suit constants

        final Image cardSuit;

        Suit() {
            this.cardSuit = new Image(Card.class.getResourceAsStream("pictures/".concat(name().toLowerCase()).concat(".jpg")),
                    32, 32, true, true); //assign suit to correct image
        }
    }

    enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);  //enumerate rank values

        final int cardValue;
        Rank(int value) {
            this.cardValue = value;  //assign rank to card
        }

        String displayName() {
            return ordinal() < 9 ? String.valueOf(cardValue) : name().substring(0, 1);  //set value or card to number or the first letter
        }
    }

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.cardValue;

        Rectangle rect = new Rectangle(CARD_WIDTH, CARD_HEIGHT);  //assign card size
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setFill(Color.WHITE);

        Text text1 = new Text(rank.displayName());  //determine card text for rank
        text1.setFont(Font.font(18));
        text1.setX(CARD_WIDTH - text1.getLayoutBounds().getWidth() - 10);
        text1.setY(text1.getLayoutBounds().getHeight());

        Text text2 = new Text(text1.getText());   //get assigned rank and text for card
        text2.setFont(Font.font(18));
        text2.setX(10);
        text2.setY(CARD_HEIGHT - 10);

        ImageView view = new ImageView(suit.cardSuit);  //intialize suit image for card
        view.setRotate(180);
        view.setX(CARD_WIDTH - 32);
        view.setY(CARD_HEIGHT - 32);

        getChildren().addAll(rect, new ImageView(suit.cardSuit), view, text1, text2); //implement all elements
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }
}





