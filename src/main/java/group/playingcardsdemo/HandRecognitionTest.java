package group.playingcardsdemo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HandRecognitionTest extends Controller implements Initializable {
    private final int boardSize = 7;
    private TestState testState = TestState.Random;
    private String sliderPrimaryState = PlayingCard.VALUES[0],
                    sliderSecondaryState = PlayingCard.VALUES[0];
    private Image[] cardFronts;

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

    //  Test State
    @FXML
    ChoiceBox<String> testStateChoiceBox = new ChoiceBox<>();
    @FXML
    Slider stateSliderPrimary;
    @FXML
    Slider stateSliderSecondary;
    @FXML
    Label statePrimaryLabel;
    @FXML
    Label stateSecondaryLabel;

    //  Card ImageViews
    @FXML
    ImageView cardImageView1 = new ImageView();
    @FXML
    ImageView cardImageView2 = new ImageView();
    @FXML
    ImageView cardImageView3 = new ImageView();
    @FXML
    ImageView cardImageView4 = new ImageView();
    @FXML
    ImageView cardImageView5 = new ImageView();
    @FXML
    ImageView cardImageView6 = new ImageView();
    @FXML
    ImageView cardImageView7 = new ImageView();

    public HandRecognitionTest() {
        super();
        setInitialDeckTopY(deckTopImageView.getY());
        hand.setCapacity(boardSize);
        cardFronts = new Image[boardSize];
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
        if (!(hand.getSize() > boardSize)) {
            for (int i = 0; i < hand.getSize(); i++) {
                cardFronts[i] = new Image(new FileInputStream(hand.getCards().get(i).getFront()));
                if (i >= boardSize) {
                    break;
                }
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

        if (hand.getSize() == 0) {
            cardImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView2.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView3.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView4.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView5.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView6.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            cardImageView7.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
        }
        else {
            cardImageView1.setImage(cardFronts[0]);
            cardImageView2.setImage(cardFronts[1]);
            cardImageView3.setImage(cardFronts[2]);
            cardImageView4.setImage(cardFronts[3]);
            cardImageView5.setImage(cardFronts[4]);
            if (hand.getSize() >= 6) {
                cardImageView6.setImage(cardFronts[5]);
            }
            else {
                cardImageView6.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            }
            if (hand.getSize() >= 7) {
                cardImageView7.setImage(cardFronts[6]);
            }
            else {
                cardImageView7.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            }
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
    public void toSort(ActionEvent event) throws FileNotFoundException {
        /*
            This method will sort the 7-card board.
         */

        hand.sortHandByValue();

        setCardFronts();
        updateCardImageViews();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateSliderPrimary.valueProperty().addListener(new ChangeListener<Number>() {
            @SneakyThrows
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (testState.equals(TestState.Random)) {
                    stateSliderPrimary.setValue(1);
                    sliderPrimaryState = "None";
                    statePrimaryLabel.setText("-");
                }
                else {
                    sliderPrimaryState = setSliderState(stateSliderPrimary, statePrimaryLabel);
                    statePrimaryLabel.setText(sliderPrimaryState);
                    runTestState();
                }
            }
        });
        stateSliderSecondary.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (testState.equals(TestState.TwoPair) || testState.equals(TestState.FullHouse) ) {
                    sliderSecondaryState = setSliderState(stateSliderSecondary, stateSecondaryLabel);
                }
                else {
                    stateSliderSecondary.setValue(1);
                    sliderSecondaryState = "None";
                    stateSecondaryLabel.setText("-");
                }
            }
        });
        testStateChoiceBox.getItems().addAll(
                TestState.Random.toString(),
                TestState.Pair.toString(),
                TestState.TwoPair.toString(),
                TestState.Trips.toString(),
                TestState.Straight.toString(),
                TestState.Flush.toString(),
                TestState.FullHouse.toString(),
                TestState.Quads.toString(),
                TestState.StraightFlush.toString(),
                TestState.RoyalFlush.toString()
        );
        testStateChoiceBox.setOnAction(this::setTestState);
    }
    @SneakyThrows
    private void setTestState(ActionEvent event) {
        resetSliders();
        testState = TestState.valueOf(testStateChoiceBox.getValue());
        switch (testState) {
            case Random, RoyalFlush -> {
                statePrimaryLabel.setText("-");
            }
            case Straight, Flush, StraightFlush -> {
                statePrimaryLabel.setText("5");
                stateSliderPrimary.setValue(4);
            }
            case TwoPair, FullHouse -> {
                statePrimaryLabel.setText("3");
                stateSliderPrimary.setValue(2);

                stateSecondaryLabel.setText("2");
            }
            default -> {
                statePrimaryLabel.setText("2");
                stateSliderPrimary.setValue(1);
            }
        }
        updateCardImageViews();
        if (testState.equals(TestState.RoyalFlush) || testState.equals(TestState.Flush)) {
            runTestState();
        }
    }
    public String setSliderState(Slider slider, Label label) {
        label.setText(PlayingCard.VALUES[(int) slider.getValue() - 1]);
        return PlayingCard.VALUES[(int) slider.getValue() - 1];
    }
    public void resetSliders() {
        stateSliderPrimary.setValue(1);
        sliderPrimaryState = "None";
        statePrimaryLabel.setText("-");

        stateSliderSecondary.setValue(1);
        sliderSecondaryState = "None";
        stateSecondaryLabel.setText("-");
    }

    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void runTestState() throws FileNotFoundException {
        resetAllCards();
        updateCardImageViews();

        switch (testState) {
            case Random:
                shuffler.random(deck);
                break;
            case Pair:
                testPair();
                break;
            case TwoPair:
                if (stateSliderPrimary.getValue() < 2) {
                    stateSliderPrimary.setValue(2);
                }
                testTwoPair();
                break;
            case Trips:
                testTrips();
                break;
            case Straight:
                if (stateSliderPrimary.getValue() < 4) {
                    stateSliderPrimary.setValue(4);
                }
                testStraight();
                break;
            case Flush:
                if (stateSliderPrimary.getValue() < 5) {
                    stateSliderPrimary.setValue(5);
                }
                testFlush();
                break;
            case FullHouse:
                testFullHouse();
                break;
            case Quads:
                testQuads();
                break;
            case StraightFlush:
                if (stateSliderPrimary.getValue() < 4) {
                    stateSliderPrimary.setValue(4);
                }
                testStraightFlush();
                break;
            case RoyalFlush:
                testRoyalFlush();
        }
    }
    public void testPair() throws FileNotFoundException {
        for (int i = 0; i < 2; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard("2", "Hearts" );
        PlayingCard card1 = new PlayingCard("3", "Diamonds" );
        PlayingCard card2 = new PlayingCard("4", "Clubs" );
        hand.addCard(card);
        hand.addCard(card1);
        hand.addCard(card2);
        testCaseTemplate();
    }
    public void testTwoPair() throws FileNotFoundException {
        for (int i = 0; i < 2; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        for (int i = 0; i < 2; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderSecondary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard("Ace", "Spades" );
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testTrips() throws FileNotFoundException {
        for (int i = 0; i < 3; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard("Ace", "Hearts" );
        PlayingCard card1 = new PlayingCard("King", "Hearts" );
        hand.addCard(card);
        hand.addCard(card1);

        testCaseTemplate();
    }
    public void testStraight() throws FileNotFoundException {
        for (int i = 1; i < HandEvaluator.straightFlushSize; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue())], "Hearts");
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize - 1; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue() - 5)], "Spades");
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testFullHouse() throws FileNotFoundException {
        Hand full = new Hand();
        for (int i = 0; i < 3; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            full.addCard(card);
        }
        Hand of = new Hand();
        for (int i = 0; i < 2; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES[(int) (stateSliderSecondary.getValue() - 1)], PlayingCard.SUITS[i] );
            of.addCard(card);
        }
        for (PlayingCard card: full.getCards()) {
            hand.addCard(card);
        }
        for (PlayingCard card: of.getCards()) {
            hand.addCard(card);
        }
        testCaseTemplate();
    }
    public void testQuads() throws FileNotFoundException {
        for (int i = 0; i < 4; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard("Ace", "Spades");
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testStraightFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }

        testCaseTemplate();
    }
    public void testRoyalFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUES_INDEX[PlayingCard.VALUES_INDEX.length - 2 - i], "Spades");
            hand.addCard(card);
        }
        testCaseTemplate();
    }
    private void testCaseTemplate() throws FileNotFoundException {
        deck = new DeckOfCards();
        deckSizeLabel.setText(String.valueOf(deck.currentSize));

        HandEvaluator evaluator = new HandEvaluator(hand);
        setCardFronts();
        updateCardImageViews();
        updateFiveCardHandLabels(evaluator);
    }
    public void drawRandomHandFromDeck() throws FileNotFoundException {
        if (deck.currentSize == deck.maxSize) {
            shuffler.random(deck);
        }
        else {
            discardHand();
        }
        hand = new Hand();

        if (deck.getCurrentSize() < boardSize || deck.isEmpty()) {
            resetAllCards();
            resetDeckDiscardGraphics(deckTopImageView, discardTopImageView, discardBottomImageView, discardSizeLabel, initialDeckTopY);
            shuffler.random(deck);
        }
        drawCards(7);
        setCardFronts();
        updateCardImageViews();

        decrementFromDeckGraphics_RandomTest(deckTopImageView, deckSizeLabel, boardSize);
        HandEvaluator evaluator = new HandEvaluator(hand);
        hand = evaluator.getFiveCardHand();
        hand.addCard(evaluator.getFullHand().getCards().get(0));
        hand.addCard(evaluator.getFullHand().getCards().get(1));

        updateFiveCardHandLabels(evaluator);
    }

    private void discardHand() throws FileNotFoundException {
        /*
            In this method, each card in the hand is added to the discard pile.
         */

        if (hand.getSize() > 7) {
            System.out.println(Arrays.toString(hand.getValueData()));
        }

        for (int i = 0; i < hand.getSize(); i++) {
            discard.addCard(hand.getCards().get(i));
            incrementDiscardGraphics(discardTopImageView, discardSizeLabel);
        }
    }
    private void drawCards(int numberToDraw) {
        /*
            In this method, the 'numberToDraw' parameter determines how many cards to add to the hand.
         */
        for (int i = 0; i < numberToDraw; i++) {
            hand.addCard(deck.drawTopCard());
            if (hand.getSize() >= boardSize) {
                break;
            }
        }
    }
}

enum TestState {
    Random, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}