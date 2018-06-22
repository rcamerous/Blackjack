package BlackjackGame;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *Robert Cameron
 */
public class Blackjack extends Application {

    private Deck deck = new Deck();
    private Hand dealer, player;
    private Text message = new Text();
    private Text message2 = new Text();
    private int gamesPlayed;

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);  //boolean property for buttons

    private HBox dealerCards = new HBox(20);  //value of cards and spacing between them
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        dealer = new Hand(dealerCards.getChildren());  //add card from list to GUI
        player = new Hand(playerCards.getChildren());

        Pane root = new Pane();  //create panes
        root.setPrefSize(800, 600);

        Region background = new Region();  //initialize background
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        HBox rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle leftRect = new Rectangle(550, 560); //left rectangle size
        leftRect.setArcWidth(50);
        leftRect.setArcHeight(50);
        leftRect.setFill(Color.GREEN);  //set left pane green
        Rectangle rightRect = new Rectangle(230, 560); //right rectangle size
        rightRect.setArcWidth(50);
        rightRect.setArcHeight(50);
        rightRect.setFill(Color.RED); //set right pane red

        //Left vertical box
        VBox leftVertBox = new VBox(40);
        leftVertBox.setAlignment(Pos.TOP_CENTER);

        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");

        leftVertBox.getChildren().addAll(dealerScore, dealerCards, message, message2, playerCards, playerScore); //set elements for left vertical box

        //Right vertical box

        VBox rightVertBox = new VBox(20);
        rightVertBox.setAlignment(Pos.CENTER);

        Button play = new Button("PLAY");
        Button hit = new Button("HIT");
        Button stand = new Button("STAND");

        HBox buttonsHBox = new HBox(15, hit, stand);
        buttonsHBox.setAlignment(Pos.CENTER);

        rightVertBox.getChildren().addAll(play, buttonsHBox);  //implement buttons on right vertical box

        // Add stacks to root layout

        rootLayout.getChildren().addAll(new StackPane(leftRect, leftVertBox), new StackPane(rightRect, rightVertBox));
        root.getChildren().addAll(background, rootLayout);

        // Bind the properties

        play.disableProperty().bind(playable);
        hit.disableProperty().bind(playable.not());
        stand.disableProperty().bind(playable.not());

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

        player.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
                endGame();
               // message2.setText("Player has won " + count[0] + " games and dealer has won " + count[1] + " games.");
               incrementGamesPlayed();
               message2.setText("You have played " + gamesPlayed + " games.");
            }
        });

        dealer.valueProperty().addListener((obs, old, newValue) -> {
            if (newValue.intValue() >= 21) {
               endGame();
              // message2.setText("Player has won " + count[0] + " games and dealer has won " + count[1] + " games.");
               incrementGamesPlayed();
               message2.setText("You have played " + gamesPlayed + " games.");
            }
        });

        // Initialize buttons

        play.setOnAction(event -> {
            startNewGame();
        });

        hit.setOnAction(event -> {
            player.takeCard(deck.drawCard());
        });

        stand.setOnAction(event -> {
            while (dealer.valueProperty().get() < 17) { //dealer will take cards as long as value is < 17
                dealer.takeCard(deck.drawCard());
            }

            endGame();
            //message2.setText("Player has won " + count[0] + " games and dealer has won " + count[1] + " games.");
            incrementGamesPlayed();
            message2.setText("You have played " + gamesPlayed + " games.");
        });

        return root;
    }

    private void startNewGame() {  //refresh game
        playable.set(true);
        message.setText("");
        message2.setText("");
        deck.refill();

        dealer.reset();
        player.reset();

        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
    }

    private void endGame() { //end the game and determine the winner
        playable.set(false);

        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        //int count = 0;
        //int playerCount = 0;
        //int dealerCount = 0;
        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

        
        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
                || (dealerValue < 21 && dealerValue > playerValue)) {
            winner = "DEALER";
            //dealerCount = countGames(count);
            //++dealerCount;
            //dealerCount += dealerCount;
        }
        else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
            winner = "PLAYER";
            //playerCount = countGames(count);
            //++playerCount;
            //playerCount += playerCount;
        }

        message.setText(winner + " WON");
        //return new int[] {playerCount, dealerCount};
        //message2.setText("Player has won " + playerCount + " games and dealer has won " + dealerCount + " games.");
    }
    
    public void incrementGamesPlayed() { //add the games played
        this.gamesPlayed++;
    }
    
    //private int countGames(int count) {
    	
    	//count = 0;
    	//++count;
    	//return count;
    			
    //}

    @Override
    public void start(Stage primaryStage) throws Exception {  //the main size of GUI window is created
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(true);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}