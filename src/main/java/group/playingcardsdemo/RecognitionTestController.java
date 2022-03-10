package group.playingcardsdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class RecognitionTestController {
    private Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Discard discard = new Discard();
    Shuffler shuffler = new Shuffler();
    Hand hand = new Hand();
    TestState state = TestState.None;

    @FXML
    Button resetButton;
    @FXML
    Button mainMenuButton;
    @FXML
    AnchorPane pane;

    @FXML
    ImageView cardImageView1;
    @FXML
    ImageView cardImageView2;
    @FXML
    ImageView cardImageView3;
    @FXML
    ImageView cardImageView4;
    @FXML
    ImageView cardImageView5;
    @FXML
    ImageView cardImageView6;
    @FXML
    ImageView cardImageView7;

    Image[] cardFronts;
    ImageView[] cards;

    public RecognitionTestController() throws IOException {
    }

    public void initializeController(ActionEvent event) throws IOException {

        initializeImageViews();
        testSceneBuilder(event);
    }
    public void updateController(ActionEvent event, Image[] shuffledCardFronts) throws IOException {
        testSceneBuilder(event);
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

    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();
        RecognitionTestController controller = loader.getController();
        controller.initializeController(event);
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
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
    public void switchToShufflingTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
        root = loader.load();

        ShufflingTestController controller = loader.getController();
        controller.initializeController(event);
    }
    private void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        stage.show();
    }
    private void sceneBuilder(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(css);
        stage.show();
    }
}

enum TestState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush, Random, FromDeck
}