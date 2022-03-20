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
import java.io.FileNotFoundException;
import java.io.IOException;

public class DebugController {
    Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Player player = new Player();
    Hand hand = new Hand();
    int handCapacity = 7;

    @FXML
    AnchorPane pane;
    @FXML
    Label handRankLabel = new Label();
    @FXML
    Label deckSizeLabel = new Label();
    @FXML
    Label discardSizeLabel = new Label();
    @FXML
    Label promptingShuffleResetLabel = new Label();
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

    Image[] cardFronts;

    public DebugController() {
        hand.setCapacity(handCapacity);
        cardFronts = new Image[handCapacity];
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
    }

    public void drawFromDeck() throws FileNotFoundException {
        for (int i = 0; i < hand.getSize(); i++) {
            discard.addCard(hand.getCards().get(i));
            discardTopImageView.setY(discardTopImageView.getY() - .5);
            Image discardImage = new Image((new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + discard.getCards().get(discard.getCurrentSize() - 1).getFront())));
            discardTopImageView.setImage(discardImage);
            discardSizeLabel.setText(String.valueOf(discard.currentSize));
        }
        hand.clear();

        if (deck.getCurrentSize() < handCapacity || deck.isEmpty()) {
            hand.clear();
            deck.compileFromDiscard(discard);
            discard = new Discard();

            Shuffler shuffler = new Shuffler();
            shuffler.handShuffle(deck);
            //promptingShuffleResetLabel.setText("");

            deckTopImageView.setImage(new Image(new FileInputStream
                    ("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
            deckTopImageView.setY(deckTopImageView.getY() - (float) deck.getMaxSize()/2);

            discardTopImageView.setImage(new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
            discardTopImageView.setY(discardBottomImageView.getY());
            discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
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


        deckTopImageView.setY(deckTopImageView.getY() + (.5 * handCapacity));
        deckSizeLabel.setText(String.valueOf(deck.currentSize));

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        handRankLabel.setText(String.valueOf(evaluator.getHandRank()));
    }
    private void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Debug.fxml"));
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
        root = FXMLLoader.load(getClass().getResource("Debug.fxml"));
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
