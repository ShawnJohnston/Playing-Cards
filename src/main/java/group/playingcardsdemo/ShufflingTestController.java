package group.playingcardsdemo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class ShufflingTestController {
    private Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();

    DeckOfCards deck = new DeckOfCards();
    Shuffler shuffler = new Shuffler();

    @FXML
    Button handShuffleButton;
    @FXML
    Button computerShuffleButton;
    @FXML
    Button resetButton;
    @FXML
    Button mainMenuButton;
    @FXML
    Button handRecognitionButton;
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
    @FXML
    ImageView cardImageView8;
    @FXML
    ImageView cardImageView9;
    @FXML
    ImageView cardImageView10;
    @FXML
    ImageView cardImageView11;
    @FXML
    ImageView cardImageView12;
    @FXML
    ImageView cardImageView13;
    @FXML
    ImageView cardImageView14;
    @FXML
    ImageView cardImageView15;
    @FXML
    ImageView cardImageView16;
    @FXML
    ImageView cardImageView17;
    @FXML
    ImageView cardImageView18;
    @FXML
    ImageView cardImageView19;
    @FXML
    ImageView cardImageView20;
    @FXML
    ImageView cardImageView21;
    @FXML
    ImageView cardImageView22;
    @FXML
    ImageView cardImageView23;
    @FXML
    ImageView cardImageView24;
    @FXML
    ImageView cardImageView25;
    @FXML
    ImageView cardImageView26;
    @FXML
    ImageView cardImageView27;
    @FXML
    ImageView cardImageView28;
    @FXML
    ImageView cardImageView29;
    @FXML
    ImageView cardImageView30;
    @FXML
    ImageView cardImageView31;
    @FXML
    ImageView cardImageView32;
    @FXML
    ImageView cardImageView33;
    @FXML
    ImageView cardImageView34;
    @FXML
    ImageView cardImageView35;
    @FXML
    ImageView cardImageView36;
    @FXML
    ImageView cardImageView37;
    @FXML
    ImageView cardImageView38;
    @FXML
    ImageView cardImageView39;
    @FXML
    ImageView cardImageView40;
    @FXML
    ImageView cardImageView41;
    @FXML
    ImageView cardImageView42;
    @FXML
    ImageView cardImageView43;
    @FXML
    ImageView cardImageView44;
    @FXML
    ImageView cardImageView45;
    @FXML
    ImageView cardImageView46;
    @FXML
    ImageView cardImageView47;
    @FXML
    ImageView cardImageView48;
    @FXML
    ImageView cardImageView49;
    @FXML
    ImageView cardImageView50;
    @FXML
    ImageView cardImageView51;
    @FXML
    ImageView cardImageView52;
    @FXML
    ImageView cardImageView53;
    @FXML
    ImageView cardImageView54;

    Image[] cardFronts;
    ImageView[] cards;

    public ShufflingTestController() throws IOException {
    }

    public void initializeController(ActionEvent event) throws IOException {
        cardFronts = Global.cardImages;
        cards = Global.cardImageViews;

        Global.initializeCardImages();
        initializeImageViews();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                int index = (13*i) + j;
                cards[index].setImage(cardFronts[index]);
                cards[index].setFitWidth(105);
                cards[index].setFitHeight(150);
                if (j > 0) {
                    cards[index].setX(cards[index - 1].getX());
                }
                else {
                    cards[index].setX(60);
                }
                cards[index].setY(60 + 150*i);

                pane.getChildren().add(cards[index]);
            }
        }
        testSceneBuilder(event);
    }
    public void updateController(ActionEvent event, Image[] shuffledCardFronts) throws IOException {
        this.cardFronts = shuffledCardFronts;
        this.cards = Global.cardImageViews;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                int index = (13*i) + j;
                cards[index].setImage(cardFronts[index]);
                cards[index].setFitWidth(105);
                cards[index].setFitHeight(150);
                if (j > 0) {
                    cards[index].setX(cards[index - 1].getX());
                }
                else {
                    cards[index].setX(60);
                }
                cards[index].setY(60 + 150*i);

                pane.getChildren().add(cards[index]);
            }
        }
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
        cards[7] = cardImageView8;
        cards[8] = cardImageView9;
        cards[9] = cardImageView10;
        cards[10] = cardImageView11;
        cards[11] = cardImageView12;
        cards[12] = cardImageView13;
        cards[13] = cardImageView14;
        cards[14] = cardImageView15;
        cards[15] = cardImageView16;
        cards[16] = cardImageView17;
        cards[17] = cardImageView18;
        cards[18] = cardImageView19;
        cards[19] = cardImageView20;
        cards[20] = cardImageView21;
        cards[21] = cardImageView22;
        cards[22] = cardImageView23;
        cards[23] = cardImageView24;
        cards[24] = cardImageView25;
        cards[25] = cardImageView26;
        cards[26] = cardImageView27;
        cards[27] = cardImageView28;
        cards[28] = cardImageView29;
        cards[29] = cardImageView30;
        cards[30] = cardImageView31;
        cards[31] = cardImageView32;
        cards[32] = cardImageView33;
        cards[33] = cardImageView34;
        cards[34] = cardImageView35;
        cards[35] = cardImageView36;
        cards[36] = cardImageView37;
        cards[37] = cardImageView38;
        cards[38] = cardImageView39;
        cards[39] = cardImageView40;
        cards[40] = cardImageView41;
        cards[41] = cardImageView42;
        cards[42] = cardImageView43;
        cards[43] = cardImageView44;
        cards[44] = cardImageView45;
        cards[45] = cardImageView46;
        cards[46] = cardImageView47;
        cards[47] = cardImageView48;
        cards[48] = cardImageView49;
        cards[49] = cardImageView50;
        cards[50] = cardImageView51;
        cards[51] = cardImageView52;
        cards[52] = cardImageView53;
        cards[53] = cardImageView54;
    }

    public void toReset(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
        root = loader.load();
        ShufflingTestController controller = loader.getController();
        controller.initializeController(event);
    }
    public void toHandShuffle(ActionEvent event) throws IOException {
        shuffler.handShuffle(deck);
        updateScene(event);
    }
    public void toComputerShuffle(ActionEvent event) throws IOException {
        shuffler.random(deck);
        updateScene(event);
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
    }
    public void switchToHandRecognition(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HandRecognitionTest.fxml"));
        sceneBuilder(event);
    }
    public void switchToDrawCardsTest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("DrawCardsTest.fxml"));
        sceneBuilder(event);
    }
    public void updateScene(ActionEvent event) throws IOException  {
        Global.initializeCardImages(deck);

        for (int i = 0; i < deck.getMaxSize(); i++) {
            cardFronts[i] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + deck.getCards().get(i).getFront()));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
        root = loader.load();

        ShufflingTestController controller = loader.getController();
        controller.updateController(event, cardFronts);
    }
    private void testSceneBuilder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
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