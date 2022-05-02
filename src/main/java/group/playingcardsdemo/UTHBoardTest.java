package group.playingcardsdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class UTHBoardTest extends Controller {
    private Hand board;
    private Pocket pocket;
    private final int boardSize = 5;
    private Image[] boardFronts;
    private Image[] pocketFronts;

    @FXML
    AnchorPane pane;

    //  Hand Labels
    @FXML
    Label handRankLabel = new Label();
    @FXML
    Label card1Label = new Label();
    @FXML
    Label card2Label = new Label();
    @FXML
    Label card3Label = new Label();
    @FXML
    Label card4Label = new Label();
    @FXML
    Label card5Label = new Label();

    //  Deck & Discard Graphics
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

    //  Buttons
    @FXML
    Button sortButton = new Button();
    @FXML
    Button randomDrawButton = new Button();
    @FXML
    Button boardDrawButton = new Button();
    @FXML
    Button pocketDrawButton = new Button();

    //  Card ImageViews
    @FXML
    ImageView boardImageView1 = new ImageView();
    @FXML
    ImageView boardImageView2 = new ImageView();
    @FXML
    ImageView boardImageView3 = new ImageView();
    @FXML
    ImageView boardImageView4 = new ImageView();
    @FXML
    ImageView boardImageView5 = new ImageView();
    @FXML
    ImageView pocketImageView1 = new ImageView();
    @FXML
    ImageView pocketImageView2 = new ImageView();

    public UTHBoardTest() {
        super();
        board = new Hand();
        pocket = new Pocket();
        setInitialDeckTopY(deckTopImageView.getY());
        hand.setCapacity(HandEvaluator.straightFlushSize);
        boardFronts = new Image[boardSize];
        pocketFronts = new Image[pocket.getCapacity()];
    }
    public void toSort() throws FileNotFoundException {
        /*
            This method will sort the 7-card board.
         */

        board.sortHandByValue();
        pocket.sortHandByValue();
        setCardFronts();
        updateCardImageViews();
    }
    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UTHBoardTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }

    private void setCardFronts() throws FileNotFoundException {
        /*
            In this method, the links for each image file corresponding to the cards in the hand will be used to
            assigned to create an image datatype. Each image will be stored in an array that is later used to set
            ImageView containers, allowing the cards to display in the scene.

             1. If statement: If the hand size is less than the 'boardSize' integer value, run the loop that allow each
                card's respective image to be stored in the 'cardFronts' array.
             2. For Loop: Runs until 'i' reaches the value matching the hand size. 'cardFronts' was initialized to be
                sized by the 'boardSize' variable. Each index of 'cardFronts' will be assigned the url to the card image
                corresponding to the card at hand index 'i'. Break when i equals or exceeds 'boardSize' value.
         */
        for (int i = 0; i < board.getSize(); i++) {
            boardFronts[i] = new Image(new FileInputStream(board.getCards().get(i).getFront()));
            if (i >= boardSize) {
                break;
            }
        }
        for (int i = 0; i < pocket.getSize(); i++) {
            pocketFronts[i] = new Image(new FileInputStream(pocket.getCards().get(i).getFront()));
            if (i >= pocket.getSize()) {
                break;
            }
        }
    }
    private void updateCardImageViews() throws FileNotFoundException {
        /*
            This method will determine what will be displayed in the ImageView containers for the 7-card board.

            1. If the hand is empty, each ImageView will display a vomit green card image.
            2. else, the first 5 ImageViews will be set to the first 5 cardFronts. If the hand size is 6 or 7, those
                ImageViews will display the additional cardFronts as well.
         */

        if (board.getSize() == 0) {
            boardImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            boardImageView2.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            boardImageView3.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            boardImageView4.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            boardImageView5.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            pocketImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            pocketImageView2.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
        }
        else {
            boardImageView1.setImage(boardFronts[0]);
            boardImageView2.setImage(boardFronts[1]);
            boardImageView3.setImage(boardFronts[2]);
            boardImageView4.setImage(boardFronts[3]);
            boardImageView5.setImage(boardFronts[4]);
            pocketImageView1.setImage(pocketFronts[0]);
            pocketImageView2.setImage(pocketFronts[1]);
        }
    }
    private void updateFiveCardHandLabels(HandEvaluator evaluator) {
        /*
            This method assigns displays the hand's best poker rank, as well as the 5 cards that it consists of.
         */

        handRankLabel.setText(String.valueOf (evaluator.getHandRank()));
        card1Label.setText(evaluator.getFiveCardHand().getCards().get(0).getName());
        card2Label.setText(evaluator.getFiveCardHand().getCards().get(1).getName());
        card3Label.setText(evaluator.getFiveCardHand().getCards().get(2).getName());
        card4Label.setText(evaluator.getFiveCardHand().getCards().get(3).getName());
        card5Label.setText(evaluator.getFiveCardHand().getCards().get(4).getName());
    }

    public void drawRandomHandFromDeck() throws FileNotFoundException {
        if (deck.currentSize == deck.maxSize) {
            shuffler.random(deck);
        }
        else {
            discardBoard();
            discardPocket();
        }
        board = new Hand();
        pocket = new Pocket();
        hand = new Hand();

        if (deck.getCurrentSize() < (boardSize + pocket.getSize()) || deck.isEmpty()) {
            resetAllCards();
            resetDeckDiscardGraphics(deckTopImageView, discardTopImageView, discardBottomImageView, discardSizeLabel, initialDeckTopY);
            shuffler.random(deck);
        }
        drawBoardCards(boardSize);
        drawPocketCards();
        setCardFronts();
        updateCardImageViews();

        decrementFromDeckGraphics_RandomTest(deckTopImageView, deckSizeLabel, boardSize);
        hand.setCards(board.getCards());
        hand.addCard(pocket.getCards().get(0));
        hand.addCard(pocket.getCards().get(1));
        HandEvaluator evaluator = new HandEvaluator(hand);
        hand = evaluator.getFiveCardHand();

        updateFiveCardHandLabels(evaluator);
    }

    public void drawRandomBoardFromDeck() throws FileNotFoundException {
        if (deck.currentSize == deck.maxSize) {
            shuffler.random(deck);
        }
        else {
            discardBoard();
        }
        board = new Hand();
        hand = new Hand();

        if (deck.getCurrentSize() < boardSize || deck.isEmpty()) {
            resetAllCards();
            resetDeckDiscardGraphics(deckTopImageView, discardTopImageView, discardBottomImageView, discardSizeLabel, initialDeckTopY);
            shuffler.random(deck);
        }
        drawBoardCards(boardSize);
        setCardFronts();
        updateCardImageViews();

        decrementFromDeckGraphics_RandomTest(deckTopImageView, deckSizeLabel, boardSize);
        hand.setCards(board.getCards());
        hand.addCard(pocket.getCards().get(0));
        hand.addCard(pocket.getCards().get(1));
        HandEvaluator evaluator = new HandEvaluator(hand);
        hand = evaluator.getFiveCardHand();

        updateFiveCardHandLabels(evaluator);
    }

    public void drawRandomPocketFromDeck() throws FileNotFoundException {
        if (deck.currentSize == deck.maxSize) {
            shuffler.random(deck);
        }
        else {
            discardPocket();
        }
        pocket = new Pocket();
        hand = new Hand();

        if (deck.getCurrentSize() < pocket.getCapacity() || deck.isEmpty()) {
            resetAllCards();
            resetDeckDiscardGraphics(deckTopImageView, discardTopImageView, discardBottomImageView, discardSizeLabel, initialDeckTopY);
            shuffler.random(deck);
        }
        drawPocketCards();
        setCardFronts();
        updateCardImageViews();

        decrementFromDeckGraphics_RandomTest(deckTopImageView, deckSizeLabel, pocket.getCapacity());
        hand.setCards(board.getCards());
        hand.addCard(pocket.getCards().get(0));
        hand.addCard(pocket.getCards().get(1));
        HandEvaluator evaluator = new HandEvaluator(hand);
        hand = evaluator.getFiveCardHand();

        updateFiveCardHandLabels(evaluator);
    }
    private void discardBoard() throws FileNotFoundException {
        /*
            In this method, each card in the hand is added to the discard pile.
         */
        for (int i = 0; i < board.getSize(); i++) {
            discard.addCard(board.getCards().get(i));
            incrementDiscardGraphics(discardTopImageView, discardSizeLabel);
        }
    }
    private void discardPocket() throws FileNotFoundException {
        /*
            In this method, each card in the hand is added to the discard pile.
         */
        for (int i = 0; i < pocket.getSize(); i++) {
            discard.addCard(pocket.getCards().get(i));
            incrementDiscardGraphics(discardTopImageView, discardSizeLabel);
        }
    }
    private void drawBoardCards(int numberToDraw) {
        /*
            In this method, the 'numberToDraw' parameter determines how many cards to add to the hand.
         */

        for (int i = 0; i < numberToDraw; i++) {
            board.addCard(deck.drawTopCard());
            if (board.getSize() >= boardSize) {
                break;
            }
        }
    }
    private void drawPocketCards() {
        /*
            In this method, the 'numberToDraw' parameter determines how many cards to add to the hand.
         */

        for (int i = 0; i < pocket.getCapacity(); i++) {
            pocket.addCard(deck.drawTopCard());
            if (pocket.getSize() >= pocket.getCapacity()) {
                break;
            }
        }
    }
}