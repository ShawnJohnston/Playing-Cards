package group.playingcardsdemo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DrawCardsController extends Node {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Hand hand = new Hand();
    int jokerCount = 0;
    int jokerMin = 0;
    int jokerMax = 2;

    @FXML
    AnchorPane pane;
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
    }
    private void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DrawCardsTest.fxml"));
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
        root = FXMLLoader.load(getClass().getResource("DrawCardsTest.fxml"));
        sceneBuilder(event);
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
    }
    public void switchToHandRecognition(ActionEvent event) throws IOException {
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
            discardSizeLabel.setText(String.valueOf(discard.currentSize));
        }

        if (deck.isEmpty()) {
            deck.compileFromDiscard(discard);
            discard = new Discard();

            Shuffler shuffler = new Shuffler();
            shuffler.random(deck);
            promptingShuffleResetLabel.setText("");

            deckTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
            deckTopImageView.setY(deckTopImageView.getY() - (float) deck.getMaxSize()/2);

            discardTopImageView.setImage(new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
            discardTopImageView.setY(discardTopImageView.getY() + (float) deck.getMaxSize()/2);
            discardSizeLabel.setText(String.valueOf(discard.currentSize));
        }

        hand.clear();
        hand.addCard(deck.draw());
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