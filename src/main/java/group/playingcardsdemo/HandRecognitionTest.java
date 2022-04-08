package group.playingcardsdemo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HandRecognitionTest extends Controller implements Initializable {

    private Shuffler shuffler = new Shuffler();
    private DeckOfCards deck = new DeckOfCards();
    private Discard discard = new Discard();
    private Player player = new Player();
    private Hand hand = new Hand();
    private final int boardSize = 7;
    private TestState testState = TestState.Random;
    private final String[] sliderStates = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private String sliderPrimaryState = sliderStates[0];
    private String sliderSecondaryState = sliderStates[0];

    @FXML
    AnchorPane pane;
    @FXML
    Label handRankLabel = new Label();
    @FXML
    Label deckSizeLabel = new Label();
    @FXML
    Label discardSizeLabel = new Label();
    @FXML
    Button sortButton = new Button();
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
    @FXML
    ImageView deckTopImageView = new ImageView();
    @FXML
    ImageView deckBottomImageView = new ImageView();
    @FXML
    ImageView discardTopImageView = new ImageView();
    @FXML
    ImageView discardBottomImageView = new ImageView();

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

    @FXML
    Button randomDrawButton = new Button();

    Image[] cardFronts;
    float initialDeckTopY = (float) deckTopImageView.getY();

    public HandRecognitionTest() {
        hand.setCapacity(boardSize);
        cardFronts = new Image[boardSize];
    }
    private void setCardFronts() throws FileNotFoundException {
        if (!(hand.getSize() >= boardSize)) {
            for (int i = 0; i < hand.getSize(); i++) {
                cardFronts[i] = new Image(new FileInputStream(
                        "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(i).getFront()));
                if (i >= boardSize) {
                    break;
                }
            }
        }
    }
    private void updateCardImageViews() throws FileNotFoundException {
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
        handRankLabel.setText(String.valueOf (evaluator.getHandRank()));
        card1Label.setText(evaluator.getFiveCardHand().getCards().get(0).getName());
        card2Label.setText(evaluator.getFiveCardHand().getCards().get(1).getName());
        card3Label.setText(evaluator.getFiveCardHand().getCards().get(2).getName());
        card4Label.setText(evaluator.getFiveCardHand().getCards().get(3).getName());
        card5Label.setText(evaluator.getFiveCardHand().getCards().get(4).getName());
    }
    public void toSort(ActionEvent event) throws FileNotFoundException {
        //hand.sortHand();
//
        //for (int i = 0; i < handCapacity; i++) {
        //    cardFronts[i] = new Image(new FileInputStream(
        //            "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(i).getFront()));
        //}
        //cardImageView1.setImage(cardFronts[0]);
        //cardImageView2.setImage(cardFronts[1]);
        //cardImageView3.setImage(cardFronts[2]);
        //cardImageView4.setImage(cardFronts[3]);
        //cardImageView5.setImage(cardFronts[4]);
        //cardImageView6.setImage(cardFronts[ 5]);
        //cardImageView7.setImage(cardFronts[6]);
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
        label.setText(sliderStates[(int) slider.getValue() - 1]);
        return sliderStates[(int) slider.getValue() - 1];
    }
    public void resetSliders() {
        stateSliderPrimary.setValue(1);
        sliderPrimaryState = "None";
        statePrimaryLabel.setText("-");

        stateSliderSecondary.setValue(1);
        sliderSecondaryState = "None";
        stateSecondaryLabel.setText("-");
    }

    public void runTestState() throws FileNotFoundException {
        deck = new DeckOfCards();
        discard = new Discard();
        hand = new Hand();

        resetDeckDiscardGraphics();
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
            PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue())], "Hearts");
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize - 1; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue() - 5)], "Spades");
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
            PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue() - 1)], PlayingCard.SUITS[i] );
            hand.addCard(card);
        }
        PlayingCard card = new PlayingCard("Ace", "Spades");
        hand.addCard(card);
        testCaseTemplate();
    }
    public void testStraightFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[(int) (stateSliderPrimary.getValue() - i)], "Spades" );
            hand.addCard(card);
        }

        testCaseTemplate();
    }
    public void testRoyalFlush() throws FileNotFoundException {
        for (int i = 0; i < HandEvaluator.straightFlushSize; i++) {
            PlayingCard card = new PlayingCard(PlayingCard.VALUESHIERARCHY[PlayingCard.VALUESHIERARCHY.length - 2 - i], "Spades");
            hand.addCard(card);
        }
        testCaseTemplate();
    }
    private void testCaseTemplate() throws FileNotFoundException {
        deck = new DeckOfCards();
        deckSizeLabel.setText(String.valueOf(deck.currentSize));

        HandEvaluator evaluator = new HandEvaluator(new Player(), hand);
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
            resetDeck();
        }
        drawCards(7);
        updateCardImageViews();

        decrementFromDeckGraphics_RandomTest();
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        hand = evaluator.getFiveCardHand();
        hand.addCard(evaluator.getRawHand().getCards().get(0));
        hand.addCard(evaluator.getRawHand().getCards().get(1));

        updateFiveCardHandLabels(evaluator);
    }

    private void discardHand() throws FileNotFoundException {
        for (int i = 0; i < hand.getSize(); i++) {
            discard.addCard(hand.getCards().get(i));
            incrementDiscardGraphics();
        }
    }
    private void drawCards(int numberToDraw) throws FileNotFoundException {
        int boardIndex = hand.getSize();
        for (int i = 0; i < numberToDraw; i++) {
            hand.addCard(deck.draw());
            cardFronts[boardIndex + i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(boardIndex + i).getFront()));
            if (hand.getSize() >= boardSize) {
                break;
            }
        }
    }
    private void resetDeck() throws FileNotFoundException {
        deck = new DeckOfCards();
        discard = new Discard();

        shuffler.random(deck);
        resetDeckDiscardGraphics();
    }
    private void decrementFromDeckGraphics_RandomTest() {
        deckTopImageView.setY(deckTopImageView.getY() + (float) boardSize / 2);
        deckSizeLabel.setText(String.valueOf(deck.currentSize));
    }
    private void incrementDiscardGraphics() throws FileNotFoundException {
        discardTopImageView.setY(discardTopImageView.getY() - .5);
        Image discardImage = new Image((new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Fronts/" + discard.getCards().get(discard.getCurrentSize() - 1).getFront())));
        discardTopImageView.setImage(discardImage);
        discardSizeLabel.setText(String.valueOf(discard.currentSize));
    }
    private void resetDeckDiscardGraphics() throws FileNotFoundException {
        deckTopImageView.setImage(new Image(new FileInputStream
                ("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
        deckTopImageView.setY(initialDeckTopY);

        discardTopImageView.setImage(new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
        discardTopImageView.setY(discardBottomImageView.getY());
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
    }
}

enum TestState {
    Random, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}