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

public class HandRecognitionTest implements Initializable {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

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
    @FXML
    Button controlledDrawButton = new Button();

    Image[] cardFronts;
    ImageView[] imageViews = new ImageView[] {cardImageView1, cardImageView2, cardImageView3, cardImageView4, cardImageView5, cardImageView6, cardImageView7};
    float initialDeckTopY = (float) deckTopImageView.getY();

    public HandRecognitionTest() {
        hand.setCapacity(boardSize);
        cardFronts = new Image[boardSize];
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
            cardImageView6.setImage(cardFronts[5]);
            cardImageView7.setImage(cardFronts[6]);
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
        if (!testState.equals(TestState.Random)) {
            randomDrawButton.setDisable(true);
            randomDrawButton.setVisible(false);

            controlledDrawButton.setDisable(false);
            controlledDrawButton.setVisible(true);
        }
        else {
            randomDrawButton.setDisable(false);
            randomDrawButton.setVisible(true);

            controlledDrawButton.setDisable(true);
            controlledDrawButton.setVisible(false);
        }

        switch (testState) {
            case Random, RoyalFlush -> {
                statePrimaryLabel.setText("-");
            }
            default -> {
                statePrimaryLabel.setText("2");
            }
        }
        switch (testState) {
            case TwoPair, FullHouse -> {
                stateSecondaryLabel.setText("2");
            }
        }
        updateCardImageViews();
        setTestState();
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

    public void setTestState() throws FileNotFoundException {
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
                testTwoPair();
                break;
            case Trips:
                testTrips();
                break;
            case Straight:
                testStraight();
                break;
            case Flush:
                testFlush();
                break;
            case FullHouse:
                testFullHouse();
                break;
            case Quads:
                testQuads();
                break;
            case StraightFlush:
                testStraightFlush();
                break;
            case RoyalFlush:
                testRoyalFlush();
        }
    }
    public void testPair() {}
    public void testTwoPair() {}
    public void testTrips() {}
    public void testStraight() {}
    public void testFlush() {}
    public void testFullHouse() {}
    public void testQuads() {}
    public void testStraightFlush() {

    }
    public void testRoyalFlush() throws FileNotFoundException {
        deck = new DeckOfCards();
        hand = new Hand();
        hand.addCard(deck.draw(12));
        hand.addCard(deck.draw(11));
        hand.addCard(deck.draw(10));
        hand.addCard(deck.draw(9));
        hand.addCard(deck.draw(0));
        shuffler.random(deck);
        deckSizeLabel.setText(String.valueOf(deck.currentSize));

        HandEvaluator evaluator = new HandEvaluator(new Player(), hand);
        setCardFronts();
        updateCardImageViews();
        cardImageView6.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
        cardImageView7.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
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
    public void DrawUnderControl() throws FileNotFoundException {
        int toDraw = numberOfCardsToDrawByRank();
        if (hand.getSize() > boardSize - toDraw) {
            discardCards(toDraw);
        }

        if (deck.getCurrentSize() < toDraw || deck.isEmpty()) {
            resetDeck();
            testRoyalFlush();
            resetDeckDiscardGraphics();
        }
        else {
            drawCards(toDraw);
            for (int i = 0; i < toDraw; i++) {
                decrementFromDeckGraphics();
            }
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            hand = evaluator.getFiveCardHand();
            updateCardImageViews();
            updateFiveCardHandLabels(evaluator);
        }
    }
    private void setCardFronts() throws FileNotFoundException {
        for (int i = 0; i < hand.getSize(); i++) {
            cardFronts[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(i).getFront()));
        }
    }
    private int numberOfCardsToDrawByRank() {
        switch (testState) {
            case Pair -> {
                return 5;
            }
            case TwoPair, Quads -> {
                return 1;
            }
            case FullHouse, Straight, Flush, StraightFlush, RoyalFlush -> {
                return 2;
            }
            case Trips -> {
                return 3;
            }
        }
        return 0;
    }
    private void discardCards(int toDiscard) throws FileNotFoundException {
        int counter = toDiscard;
        for (int i = 0; i < toDiscard; i++) {
            decrementFromDeckGraphics();
        }
        while (counter > 0) {
            discard.addCard(hand.getCards().get(hand.getSize() - 1));
            counter--;
        }

        if (toDiscard > 0) {
            cardImageView7.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView6.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView5.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView4.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView3.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView2.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
            toDiscard--;
        }
        if (toDiscard > 0) {
            cardImageView1.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/none.png")));
        }
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
    private void decrementFromDeckGraphics() {
        deckTopImageView.setY(deckTopImageView.getY() + .5);
        deckSizeLabel.setText(String.valueOf(deck.currentSize));
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

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
    }
    public void switchToHandRecognitionTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();
        sceneBuilder(event);

    }
    public void switchToHandComparisonTest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HandComparisonTest.fxml"));
        sceneBuilder(event);
    }
    public void switchToDrawCardsTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DrawCardsTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void toReset(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HandRecognitionTest.fxml"));
        sceneBuilder(event);
    }
    private void sceneBuilder(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE -> {
                    Platform.exit();
                }
            }
        });
        stage.show();
    }
}

enum TestState {
    Random, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}