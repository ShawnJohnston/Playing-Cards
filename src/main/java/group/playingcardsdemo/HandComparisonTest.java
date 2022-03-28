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
import java.util.Arrays;
import java.util.ResourceBundle;

public class HandComparisonTest {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Player player1 = new Player();
    Player player2 = new Player();
    Hand hand1 = new Hand();
    Hand hand2 = new Hand();
    int handCapacity = 5;

    @FXML
    AnchorPane pane;
    @FXML
    Label p1HandRankLabel = new Label();
    @FXML
    Label p2HandRankLabel = new Label();
    @FXML
    Label outcomeLabel = new Label();
    @FXML
    Button sortButton = new Button();

    @FXML
    ImageView p1CardImageView1 = new ImageView();
    @FXML
    ImageView p1CardImageView2 = new ImageView();
    @FXML
    ImageView p1CardImageView3 = new ImageView();
    @FXML
    ImageView p1CardImageView4 = new ImageView();
    @FXML
    ImageView p1CardImageView5 = new ImageView();
    @FXML
    ImageView p2CardImageView1 = new ImageView();
    @FXML
    ImageView p2CardImageView2 = new ImageView();
    @FXML
    ImageView p2CardImageView3 = new ImageView();
    @FXML
    ImageView p2CardImageView4 = new ImageView();
    @FXML
    ImageView p2CardImageView5 = new ImageView();


    @FXML
    Label p1Card1Label = new Label();
    @FXML
    Label p1Card2Label = new Label();
    @FXML
    Label p1Card3Label = new Label();
    @FXML
    Label p1Card4Label = new Label();
    @FXML
    Label p1Card5Label = new Label();

    @FXML
    Label p2Card1Label = new Label();
    @FXML
    Label p2Card2Label = new Label();
    @FXML
    Label p2Card3Label = new Label();
    @FXML
    Label p2Card4Label = new Label();
    @FXML
    Label p2Card5Label = new Label();

    Image[] cardFrontsHand1;
    Image[] cardFrontsHand2;

    public HandComparisonTest() {
        hand1.setCapacity(handCapacity);
        hand2.setCapacity(handCapacity);
        cardFrontsHand1 = new Image[handCapacity];
        cardFrontsHand2 = new Image[handCapacity];
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
    }

    public void drawFromDeck() throws FileNotFoundException {
        p1HandRankLabel.setText("");
        p2HandRankLabel.setText("");
        for (int i = 0; i < hand1.getSize(); i++) {
            discard.addCard(hand1.getCards().get(i));
        }
        for (int i = 0; i < hand2.getSize(); i++) {
            discard.addCard(hand2.getCards().get(i));
        }
        hand1.clear();
        hand2.clear();

        if (deck.getCurrentSize() < (handCapacity * 2) || deck.isEmpty()) {
            deck.compileFromDiscard(discard);
            discard = new Discard();

            Shuffler shuffler = new Shuffler();
            shuffler.handShuffle(deck);
        }
        deck = new DeckOfCards();
        Shuffler shuffler = new Shuffler();
        shuffler.handShuffle(deck);

        for (int i = 0; i < handCapacity; i++) {
            hand1.addCard(deck.draw());
            cardFrontsHand1[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand1.getCards().get(i).getFront()));

            hand2.addCard(deck.draw());
            cardFrontsHand2[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand2.getCards().get(i).getFront()));
        }

        p1CardImageView1.setImage(cardFrontsHand1[0]);
        p1CardImageView2.setImage(cardFrontsHand1[1]);
        p1CardImageView3.setImage(cardFrontsHand1[2]);
        p1CardImageView4.setImage(cardFrontsHand1[3]);
        p1CardImageView5.setImage(cardFrontsHand1[4]);

        p2CardImageView1.setImage(cardFrontsHand2[0]);
        p2CardImageView2.setImage(cardFrontsHand2[1]);
        p2CardImageView3.setImage(cardFrontsHand2[2]);
        p2CardImageView4.setImage(cardFrontsHand2[3]);
        p2CardImageView5.setImage(cardFrontsHand2[4]);

        HandEvaluator p1Evaluator = new HandEvaluator(player1, hand1);
        HandEvaluator p2Evaluator = new HandEvaluator(player2, hand2);
        p1HandRankLabel.setText(String.valueOf(p1Evaluator.getHandRank()));
        p2HandRankLabel.setText(String.valueOf(p2Evaluator.getHandRank()));

        p1Card1Label.setText(p1Evaluator.getFiveCardHand().getCards().get(0).getName());
        p1Card2Label.setText(p1Evaluator.getFiveCardHand().getCards().get(1).getName());
        p1Card3Label.setText(p1Evaluator.getFiveCardHand().getCards().get(2).getName());
        p1Card4Label.setText(p1Evaluator.getFiveCardHand().getCards().get(3).getName());
        p1Card5Label.setText(p1Evaluator.getFiveCardHand().getCards().get(4).getName());

        p2Card1Label.setText(p2Evaluator.getFiveCardHand().getCards().get(0).getName());
        p2Card2Label.setText(p2Evaluator.getFiveCardHand().getCards().get(1).getName());
        p2Card3Label.setText(p2Evaluator.getFiveCardHand().getCards().get(2).getName());
        p2Card4Label.setText(p2Evaluator.getFiveCardHand().getCards().get(3).getName());
        p2Card5Label.setText(p2Evaluator.getFiveCardHand().getCards().get(4).getName());

        GameOutcome outcome = new GameOutcome(p1Evaluator, p2Evaluator);
        outcomeLabel.setText(outcome.getWinner());
    }

    public void toSort(ActionEvent event) throws FileNotFoundException {
        hand1.sortHand();

        for (int i = 0; i < handCapacity; i++) {
            cardFrontsHand1[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand1.getCards().get(i).getFront()));
        }
        p1CardImageView1.setImage(cardFrontsHand1[0]);
        p1CardImageView2.setImage(cardFrontsHand1[1]);
        p1CardImageView3.setImage(cardFrontsHand1[2]);
        p1CardImageView4.setImage(cardFrontsHand1[3]);
        p1CardImageView5.setImage(cardFrontsHand1[4]);

        hand2.sortHand();

        for (int i = 0; i < handCapacity; i++) {
            cardFrontsHand2[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand2.getCards().get(i).getFront()));
        }
        p2CardImageView1.setImage(cardFrontsHand2[0]);
        p2CardImageView2.setImage(cardFrontsHand2[1]);
        p2CardImageView3.setImage(cardFrontsHand2[2]);
        p2CardImageView4.setImage(cardFrontsHand2[3]);
        p2CardImageView5.setImage(cardFrontsHand2[4]);
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

