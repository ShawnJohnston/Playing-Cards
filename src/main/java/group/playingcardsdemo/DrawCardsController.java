package group.playingcardsdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DrawCardsController extends Controller {
    Hand hand = new Hand();
    int jokerCount = 0;
    int jokerMin = 0;
    int jokerMax = 2;

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

    public DrawCardsController() throws IOException {
        hand.setCapacity(1);
        super.setCurrentScene("DrawCardsTest.fxml");
    }
    public void incrementJokerCount() throws FileNotFoundException {
        if (jokerCount < jokerMax) {
            jokerCount++;
        }
        adjustForJoker();
    }
    public void decrementJokerCount() throws FileNotFoundException {
        if (jokerCount > jokerMin) {
            jokerCount--;
        }
        adjustForJoker();
    }
    private void adjustForJoker() throws FileNotFoundException {
        jokerCountLabel.setText(String.valueOf(jokerCount));
        deck = new DeckOfCards(jokerCount);
        discard = new Discard();
        deckSizeLabel.setText(String.valueOf(deck.getCurrentSize()));
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
        deckTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
        hand.clear();
        cardImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
        discardTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
        discardTopImageView.setY(discardBottomImageView.getY());
        deckTopImageView.setY(deckBottomImageView.getY());
        cardNameLabel.setText("");
    }
    public void drawFromDeck(ActionEvent event) throws IOException {
        if (hand.getSize() > 0) {
            discard.addCard(hand.getCards().get(0));
            Image discardFront = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(0).getFront()));

            discardTopImageView.setImage(discardFront);
            discardTopImageView.setY(discardTopImageView.getY() - .5);
            discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
        }

        if (deck.isEmpty()) {
            deck.compileFromDiscard(discard);
            discard = new Discard();

            discardSizeLabel.setText(String.valueOf(discard.currentSize));
            deckSizeLabel.setText(String.valueOf(deck.currentSize));

            Shuffler shuffler = new Shuffler();
            shuffler.random(deck);
            promptingShuffleResetLabel.setText("");

            deckTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
            deckTopImageView.setY(deckTopImageView.getY() - (float) deck.getMaxSize()/2);

            discardTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
            discardTopImageView.setY(discardTopImageView.getY() + (float) deck.getMaxSize()/2);
            discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
        }

        hand.clear();
        hand.addCard(deck.drawTopCard());
        cardNameLabel.setText(hand.getCards().get(0).getName());
        deckTopImageView.setY(deckTopImageView.getY() + .5);
        deckSizeLabel.setText(String.valueOf(deck.currentSize));

        if (deck.currentSize == 0) {
            deckTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
            promptingShuffleResetLabel.setText("The deck is empty. It will be shuffled.");
        }

        Image front = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(0).getFront()));
        cardImageView1.setImage(front);
    }
}