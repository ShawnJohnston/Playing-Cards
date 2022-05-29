package group.playingcardsdemo;

import group.playingcardsdemo.cards.DeckOfCards;
import group.playingcardsdemo.cards.Shuffler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DrawAndDiscardTest extends Controller {
    private int jokerCount = 0;

    @FXML
    ImageView cardImageView1 = new ImageView();
    @FXML
    ImageView deckTopImageView = new ImageView();
    @FXML
    ImageView deckBottomImageView = new ImageView();
    @FXML
    ImageView discardTopImageView = new ImageView();
    @FXML
    ImageView discardBottomImageView = new ImageView();
    @FXML
    Label deckSizeLabel = new Label();
    @FXML
    Label discardSizeLabel = new Label();
    @FXML
    Label cardNameLabel = new Label();
    @FXML
    Button jokerIncrementButton = new Button();
    @FXML
    Button jokerDecrementButton = new Button();
    @FXML
    Label jokerCountLabel = new Label();
    @FXML
    Label promptingShuffleResetLabel;

    public DrawAndDiscardTest() {
        setInitialDeckTopY(deckTopImageView.getY());
        hand.setCapacity(1);
        super.setCurrentScene("fxml/DrawAndDiscardTest.fxml");
    }
    public void incrementJokerCount() throws FileNotFoundException {
        int jokerMax = 2;
        if (jokerCount < jokerMax) {
            jokerCount++;
        }
        adjustForJoker();
    }
    public void decrementJokerCount() throws FileNotFoundException {
        int jokerMin = 0;
        if (jokerCount > jokerMin) {
            jokerCount--;
        }
        adjustForJoker();
    }
    public void adjustForJoker() throws FileNotFoundException {
        moveAllCardsToDeck();
        resetAllCards(jokerCount);

        deckSizeLabel.setText(String.valueOf(deck.getCurrentSize()));
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
        jokerCountLabel.setText(String.valueOf(jokerCount));
        cardNameLabel.setText("");

        cardImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
    }
    public void drawFromDeck() throws IOException {
        /*
            This method will control the main logic for the test.

            1.  If the hand has a card, it will be discarded.
            2.  If the deck is empty (before draw), all cards will return to the deck and be shuffled.
            3.  A card is drawn from deck to hand.
            4.  If the deck is empty (after draw), the deck will show a green image and a prompt will display.
         */

        if (hand.getSize() > 0) {
            discardCardFromHand();
        }
        if (deck.isEmpty()) {
            moveAllCardsToDeck();
        }
        drawCardFromDeck();
        if (deck.isEmpty()) {
            deckTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
            promptingShuffleResetLabel.setText("The deck is empty. It will be shuffled.");
        }
    }
    public void discardCardFromHand() throws FileNotFoundException {
        /*
            1.  The card in the hand is added to the discard.
            2.  The card in the hand is removed from the hand.
            3.  The discard pile updates.
         */

        discard.addCard(hand.getCards().get(0));
        hand.removeCard(0);
        incrementDiscardGraphics(discardTopImageView, discardSizeLabel);
    }
    public void moveAllCardsToDeck() throws FileNotFoundException {
        /*
            This method resets the cards, shuffles them, and draws the top card from the deck.

            1.  All cards in the discard and hand are returned to the deck.
            2.  The graphics for the deck and discard are reset to their initial states.
            3.  The prompt previously displayed is cleared.
            4.  A shuffler is instantiated and shuffles the deck.
         */

        resetAllCards();
        resetDeckDiscardGraphics(deckTopImageView, discardTopImageView,discardBottomImageView, discardSizeLabel, initialDeckTopY);
        promptingShuffleResetLabel.setText("");

        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
    }
    public void drawCardFromDeck() throws FileNotFoundException {
        /*
            This method performs the tasks that must be done for drawing a card in this test.

            1.  The top card in the deck is drawn to the hand.
            2.  The name of the card is displayed.
            3.  The card count for the deck and discard are updated.
            4.   The hand's cardImageView is updated to display the drawn card.
         */

        hand.addCard(deck.drawTopCard());
        cardNameLabel.setText(hand.getCards().get(0).getName());
        deckSizeLabel.setText(String.valueOf(deck.getCurrentSize()));
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
        decrementFromDeckGraphics_RandomTest(deckTopImageView, deckSizeLabel,1);
        cardImageView1.setImage(new Image(new FileInputStream(hand.getCards().get(0).getFront())));
    }
}