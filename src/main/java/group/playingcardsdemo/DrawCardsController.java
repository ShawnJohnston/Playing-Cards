package group.playingcardsdemo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class DrawCardsController extends Node {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards(2);
    Discard discard = new Discard();
    Hand hand = new Hand();

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


    public DrawCardsController() throws IOException {
        hand.setCapacity(1);
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
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

    public void update(ActionEvent event) throws IOException {
        if (hand.getSize() > 0) {
            discard.addCard(hand.getCards().get(0));
            Image discardFront = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(0).getFront()));

            discardTopImageView.setImage(discardFront);
            discardTopImageView.setY(discardTopImageView.getY() - .5);
            discardSizeLabel.setText(String.valueOf(discard.currentSize));
        }

        if (deck.isEmpty()) {
            Shuffler shuffler = new Shuffler();
            deck.compileFromDiscard(discard);
            discard = new Discard();
            shuffler.random(deck);

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
        }

        Image front = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand.getCards().get(0).getFront()));
        cardImageView1.setImage(front);
    }
}