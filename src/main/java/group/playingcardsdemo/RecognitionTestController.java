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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecognitionTestController implements Initializable {
    private Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Shuffler shuffler = new Shuffler();
    Hand hand = new Hand();
    int handCapacity = 7;
    TestState testState = TestState.None;
    SliderState sliderPrimaryState = SliderState.Ace;
    SliderState sliderSecondaryState = SliderState.Ace;
    Boolean toSort = false;

    @FXML
    AnchorPane pane;
    @FXML
    Button resetButton;
    @FXML
    Button mainMenuButton;
    @FXML
    CheckBox sortHandCheckBox;
    @FXML
    ChoiceBox<String> rankChoiceBox = new ChoiceBox<>();
    @FXML
    Slider stateSliderPrimary;
    @FXML
    Slider stateSliderSecondary;
    @FXML
    Label statePrimaryLabel;
    @FXML
    Label stateSecondaryLabel;
    @FXML
    Label handCapacityActualLabel;
    @FXML
    Button capacityDecrementButton;
    @FXML
    Button capacityIncrementButton;

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

    Image[] cardFronts = new Image[handCapacity];
    ImageView[] cards = new ImageView[handCapacity];

    public RecognitionTestController() throws IOException {
    }

    private void initializeImageViews() {
        cards[0] = cardImageView1;
        cards[1] = cardImageView2;
        cards[2] = cardImageView3;
        cards[3] = cardImageView4;
        cards[4] = cardImageView5;
        cards[5] = cardImageView6;
        cards[6] = cardImageView7;
    }
    public void initializeController(ActionEvent event) throws IOException {
        rankChoiceBox.setValue("");
        initializeImageViews();
        testSceneBuilder(event);
    }

    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();
        RecognitionTestController controller = loader.getController();
        controller.initializeController(event);
    }
    public void changeSortHandCheckBox(ActionEvent event) {
        toSort = sortHandCheckBox.isSelected();
        if (toSort) {
            hand.sortHand();
        }
    }
    public void toSortHand() {
        if (toSort) {
            hand.sortHand();
        }
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
    }
    public void switchToShufflingTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
        root = loader.load();

        ShufflingTestController controller = loader.getController();
        controller.initializeController(event);
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
        rankChoiceBox.getItems().addAll(
                TestState.None.toString(),
                TestState.HighCard.toString(),
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
        rankChoiceBox.setOnAction(this::setTestState);
    }
    private void setTestState(ActionEvent event) {
        resetSliders();

        testState = TestState.valueOf(rankChoiceBox.getValue());
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
    public void setHandCapacity(int num) {
        handCapacity = num;
    }
    public void onClickStartButton(ActionEvent event) throws IOException {
        switch (testState.toString()) {
            case "None" -> {

            }
            case "HighCard" -> {

            }
            case "Pair" -> {

            }
            case "TwoPair" -> {

            }
            case "Trips" -> {

            }
            case "Straight" -> {

            }
            case "Flush" -> {

            }
            case "FullHouse" -> {

            }
            case "Quads" -> {

            }
            case "StraightFlush" -> {

            }
            case "RoyalFlush" -> {

            }
            case "Random" -> {

            }
            case "FromDeck" -> {
                shuffler.random(deck);

                //hand.discardAll(discard);
                hand.setCapacity(handCapacity);
                cards = new ImageView[handCapacity];
                for (int i = 0; i < handCapacity; i++) {
                    cardFronts[i] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + deck.getCards().get(i).getFront()));
                    cards[i].setImage(cardFronts[i]);
                    System.out.println("asdf - " + deck.getCards().get(i).getFront());
                    PlayingCard topCard = deck.draw();
                    hand.addCard(topCard);
                }
            }
        }
        updateScene(event);
    }
    //public void incrementHandCapacity(ActionEvent event) {
    //    if (handCapacity < handCapacityUpperBound) {
    //        handCapacity++;
    //        hand.setCapacity(handCapacity);
    //        handCapacityActualLabel.setText(String.valueOf(handCapacity));
    //    }
    //}
    //public void decrementHandCapacity(ActionEvent event) {
    //
    //    if (handCapacity > handCapacityLowerBound) {
    //        handCapacity--;
    //        hand.setCapacity(handCapacity);
    //        handCapacityActualLabel.setText(String.valueOf(handCapacity));
    //    }
    //}

    public void updateController(ActionEvent event, Image[] cardFronts) throws IOException {
        this.cardFronts = cardFronts;
        this.cards = Global.cardImageViews;

        for (int i = 0; i < handCapacity; i++) {
            cards[i].setImage(cardFronts[i]);
            this.pane.getChildren().add(this.cards[i]);
        }

        testSceneBuilder(event);
    }
    public void updateScene(ActionEvent event) throws IOException  {
        Global.initializeCardImages(deck);

        for (int i = 0; i < deck.getMaxSize(); i++) {
            cardFronts[i] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + deck.getCards().get(i).getFront()));
            System.out.println(deck.getCards().get(i).getName());
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();

        RecognitionTestController controller = loader.getController();
        controller.updateController(event, cardFronts);
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