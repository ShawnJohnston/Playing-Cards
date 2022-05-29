package group.playingcardsdemo;

import group.playingcardsdemo.cards.DeckOfCards;
import group.playingcardsdemo.cards.Discard;
import group.playingcardsdemo.cards.Hand;
import group.playingcardsdemo.cards.Shuffler;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

public abstract class Controller {
    protected Parent root;
    protected Pane pane;
    protected String currentScene;
    protected DeckOfCards deck = new DeckOfCards();
    protected Discard discard = new Discard();
    protected Hand hand = new Hand();
    protected Shuffler shuffler = new Shuffler();
    protected float initialDeckTopY;

    protected final String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();

    protected void setInitialDeckTopY(double y) {
        initialDeckTopY = (float) y;
    }

    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(currentScene));
        root = loader.load();
        sceneBuilder(event);
    }
    public void exitApplication() {
        Platform.exit();
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));
        sceneBuilder(event);
    }
    public void switchToGameSetup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameSetup.fxml"));
        sceneBuilder(event);
    }
    public void switchToShufflingTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ShufflingTest.fxml"));
        root = loader.load();

        ShufflingTestController controller = loader.getController();
        controller.initializeController(event);
    }
    public void switchToHandRecognitionTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HandRecognitionTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToHandComparisonTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HandComparisonTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToDrawCardsTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/DrawAndDiscardTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Settings.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToUTHBoardTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UTHBoardTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    protected void sceneBuilder(ActionEvent event) {
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
    protected void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ShufflingTest.fxml"));
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
    protected void resetAllCards() {
        /*
            In this method, the deck and discard objects are reinitialize, the deck is shuffled, and the visuals for
            the deck and discard pile are reset.
         */

        deck = new DeckOfCards();
        discard = new Discard();
        hand = new Hand();
    }
    protected void resetAllCards(int jokerCount) {
        /*
            In this method, the deck and discard objects are reinitialize, the deck is shuffled, and the visuals for
            the deck and discard pile are reset.
         */

        deck = new DeckOfCards(jokerCount);
        discard = new Discard();
        hand = new Hand();
    }

    protected void decrementFromDeckGraphics_RandomTest(ImageView deckTopImageView, Label deckSizeLabel, int numberOfCards) {
        /*
            In this method, the graphics for the deck of cards is adjusted for the drawing of a card.

            1. The ImageView representing the top of the deck moves -0.5 pixels in the y-axis per board slot from its
               current position.
            2. The label that displays the number of cards in the deck is updated accordingly.
         */

        deckTopImageView.setY(deckTopImageView.getY() + (float) numberOfCards / 2);
        deckSizeLabel.setText(String.valueOf(deck.getCurrentSize()));
    }
    protected void incrementDiscardGraphics(ImageView discardTopImageView, Label discardSizeLabel) throws FileNotFoundException {
        /*
            In this method, the graphics for the deck of cards is adjusted for the drawing of a card.

            1. The ImageView representing the top of the discard moves +0.5 pixels in the y-axis per board slot from its
               current position.
            2. The label that displays the number of cards in the discard is updated accordingly
            3. The discard image is changed to be the last card on the board previously.
         */
        discardTopImageView.setY(discardTopImageView.getY() - .5);
        Image discardImage = new Image((new FileInputStream(discard.getCards().get(discard.getCurrentSize() - 1).getFront())));
        discardTopImageView.setImage(discardImage);
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
    }
    protected void resetDeckDiscardGraphics(ImageView deckTopImageView, ImageView discardTopImageView, ImageView discardBottomImageView, Label discardSizeLabel, float initialDeckTopY) throws FileNotFoundException {
        /*
            In this method, the graphics for both the deck and discard are reset to their initial state.
         */

        deckTopImageView.setImage(new Image(new FileInputStream
                ("src/main/resources/group/playingcardsdemo/Card_Backs/red.png")));
        deckTopImageView.setY(initialDeckTopY);

        discardTopImageView.setImage(new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Fronts/none.png")));
        discardTopImageView.setY(discardBottomImageView.getY());
        discardSizeLabel.setText(String.valueOf(discard.getCurrentSize()));
    }
}
