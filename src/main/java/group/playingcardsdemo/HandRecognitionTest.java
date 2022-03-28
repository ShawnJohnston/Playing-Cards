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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HandRecognitionTest implements Initializable {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    Shuffler shuffler = new Shuffler();
    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Player player = new Player();
    Hand hand = new Hand();
    int handCapacity = 7;
    TestState testState = TestState.None;
    SliderState sliderPrimaryState = SliderState.Ace;
    SliderState sliderSecondaryState = SliderState.Ace;

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

    Image[] cardFronts;
    float initialDeckTopY = (float) deckTopImageView.getY();

    public HandRecognitionTest() {
        hand.setCapacity(handCapacity);
        cardFronts = new Image[handCapacity];
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
    }


    public void drawFromDeck() throws FileNotFoundException {
        for (int i = 0; i < hand.getSize(); i++) {
            discard.addCard(hand.getCards().get(i));
            adjustDiscardGraphics();
        }
        hand = new Hand();

        if (deck.getCurrentSize() < handCapacity || deck.isEmpty()) {
            deck = new DeckOfCards();
            discard = new Discard();

            shuffler.random(deck);
            resetDeckDiscardGraphics();
        }
        for (int i = 0; i < handCapacity; i++) {
            hand.addCard(deck.draw());
            cardFronts[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(i).getFront()));
        }
        cardImageView1.setImage(cardFronts[0]);
        cardImageView2.setImage(cardFronts[1]);
        cardImageView3.setImage(cardFronts[2]);
        cardImageView4.setImage(cardFronts[3]);
        cardImageView5.setImage(cardFronts[4]);
        cardImageView6.setImage(cardFronts[5]);
        cardImageView7.setImage(cardFronts[6]);

        adjustDeckGraphics();
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        handRankLabel.setText(String.valueOf(evaluator.getHandRank()));

        card1Label.setText(evaluator.getFiveCardHand().getCards().get(0).getName());
        card2Label.setText(evaluator.getFiveCardHand().getCards().get(1).getName());
        card3Label.setText(evaluator.getFiveCardHand().getCards().get(2).getName());
        card4Label.setText(evaluator.getFiveCardHand().getCards().get(3).getName());
        card5Label.setText(evaluator.getFiveCardHand().getCards().get(4).getName());
    }
    private void adjustDeckGraphics() {
        deckTopImageView.setY(deckTopImageView.getY() + (float) handCapacity / 2);
        deckSizeLabel.setText(String.valueOf(deck.currentSize));
    }
    private void adjustDiscardGraphics() throws FileNotFoundException {
        discardTopImageView.setY(discardTopImageView.getY() - .5);
        Image discardImage = new Image((new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Fronts/" + discard.getCards().get(discard.getCurrentSize() - 1).getFront())));
        discardTopImageView.setImage(discardImage);
        discardSizeLabel.setText(String.valueOf(discard.currentSize));
    }
    private void updateCardImageViews() {
        cardImageView1.setImage(cardFronts[0]);
        cardImageView2.setImage(cardFronts[1]);
        cardImageView3.setImage(cardFronts[2]);
        cardImageView4.setImage(cardFronts[3]);
        cardImageView5.setImage(cardFronts[4]);
        cardImageView6.setImage(cardFronts[5]);
        cardImageView7.setImage(cardFronts[6]);
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
        //cardImageView6.setImage(cardFronts[5]);
        //cardImageView7.setImage(cardFronts[6]);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateSliderPrimary.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (testState.equals(TestState.None) || testState.equals(TestState.FromDeck) || testState.equals(TestState.Random)) {
                    stateSliderPrimary.setValue(1);
                    sliderPrimaryState = SliderState.Ace;
                    statePrimaryLabel.setText("-");
                }
                else {
                    setSliderState(stateSliderPrimary, sliderPrimaryState, statePrimaryLabel);
                }
            }
        });
        stateSliderSecondary.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (testState.equals(TestState.TwoPair) || testState.equals(TestState.FullHouse) ) {
                    setSliderState(stateSliderSecondary, sliderSecondaryState, stateSecondaryLabel);
                }
                else {
                    stateSliderSecondary.setValue(1);
                    sliderSecondaryState = SliderState.Ace;
                    stateSecondaryLabel.setText("-");
                }
            }
        });
        testStateChoiceBox.getItems().addAll(
                TestState.None.toString(),
                TestState.Pair.toString(),
                TestState.TwoPair.toString(),
                TestState.Trips.toString(),
                TestState.Straight.toString(),
                TestState.Flush.toString(),
                TestState.FullHouse.toString(),
                TestState.Quads.toString(),
                TestState.StraightFlush.toString(),
                TestState.RoyalFlush.toString(),
                TestState.FromDeck.toString(),
                TestState.Random.toString()
        );
        testStateChoiceBox.setOnAction(this::setTestState);
    }
    private void setTestState(ActionEvent event) {
        resetSliders();
        testState = TestState.valueOf(testStateChoiceBox.getValue());

        switch (testState.toString()) {
            case "Pair" -> {
            }
            case "TwoPair" -> {
            }
            case "Trips" -> {
            }
        }
    }
    public void setSliderState(Slider slider, SliderState sliderState, Label label) {
        switch ((int) slider.getValue()) {
            case 1 -> {
                sliderState = SliderState.Ace;
                label.setText("Ace");
            }
            case 2 -> {
                sliderState = SliderState.Two;
                label.setText("2");
            }
            case 3 -> {
                sliderState = SliderState.Three;
                label.setText("3");
            }
            case 4 -> {
                sliderState = SliderState.Four;
                label.setText("4");
            }
            case 5 -> {
                sliderState = SliderState.Five;
                label.setText("5");
            }
            case 6 -> {
                sliderState = SliderState.Six;
                label.setText("6");
            }
            case 7 -> {
                sliderState = SliderState.Seven;
                label.setText("7");
            }
            case 8 -> {
                sliderState = SliderState.Eight;
                label.setText("8");
            }
            case 9 -> {
                sliderState = SliderState.Nine;
                label.setText("9");
            }
            case 10 -> {
                sliderState = SliderState.Ten;
                label.setText("Ten");
            }
            case 11 -> {
                sliderState = SliderState.Jack;
                label.setText("Jack");
            }
            case 12 -> {
                sliderState = SliderState.Queen;
                label.setText("Queen");
            }
            case 13 -> {
                sliderState = SliderState.King;
                label.setText("King");
            }
            default -> {
            }
        }
    }
    public void resetSliders() {
        stateSliderPrimary.setValue(1);
        sliderPrimaryState = SliderState.None;
        statePrimaryLabel.setText("-");

        stateSliderSecondary.setValue(1);
        sliderSecondaryState = SliderState.None;
        stateSecondaryLabel.setText("-");
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
    private void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(pane);
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
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush, Random, FromDeck
}
enum SliderState {
    None, Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
}
