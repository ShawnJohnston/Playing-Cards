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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartingMenuController implements Initializable {
    private Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();
    public int chipCount;
    public String room = "Default";
    public String currentFeltColor = "Red";
    private final String[] cardbacks = {"red", "red2", "blue", "blue2", "abstract", "abstract_clouds",
            "abstract_scene", "astronaut", "cars", "castle", "fish", "frog"};
    private final String[] avatars = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public String currentCardBack = "red";
    public String currentAvatar = avatars[0];
    public Image cardBackImage = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Backs/" + cardbacks[0] + ".png"));
    public Image avatarImage = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Avatars/" + avatars[0] + ".png"));

    @FXML
    TextField nameTextField;
    @FXML
    Label chipCountLabel;
    @FXML
    Slider slider = new Slider(1000, 5000, 1000);
    @FXML
    ChoiceBox<String> gameModeChoiceBox = new ChoiceBox<>();
    @FXML
    Label gameLabel = new Label();
    @FXML
    ChoiceBox<String> roomChoiceBox = new ChoiceBox<>();
    @FXML
    Button leftAvatarButton = new Button();
    @FXML
    Button rightAvatarButton = new Button();
    @FXML
    Button leftFeltButton = new Button();
    @FXML
    Button rightFeltButton = new Button();
    @FXML
    ImageView avatarImageView = new ImageView();
    @FXML
    ImageView feltImageView = new ImageView();
    @FXML
    ImageView cardBackImageView = new ImageView();

    public StartingMenuController() throws FileNotFoundException {
    }


    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }
    public void toggleAvatarLeft(ActionEvent event) throws FileNotFoundException {
        String newAvatar = null;
        if (currentAvatar.equals(avatars[0])) {
            newAvatar = avatars[avatars.length - 1];
        }
        else {
            for (int i = 0; i < avatars.length; i++) {
                if (currentAvatar.equals(avatars[i])) {
                    newAvatar = avatars[i - 1];
                    break;
                }
            }
        }
        currentAvatar = newAvatar;

        String path = "src/main/resources/group/playingcardsdemo/Avatars/";
        avatarImage = new Image(new FileInputStream(path + currentAvatar + ".png"));
        avatarImageView.setImage(avatarImage);
    }
    public void toggleAvatarRight(ActionEvent event) throws FileNotFoundException {
        String newAvatar = null;
        if (currentAvatar.equals(avatars[avatars.length - 1])) {
            newAvatar = avatars[0];
        }
        else {
            for (int i = 0; i < avatars.length; i++) {
                if (currentAvatar.equals(avatars[i])) {
                    newAvatar = avatars[i + 1];
                    break;
                }
            }
        }
        currentAvatar = newAvatar;

        String path = "src/main/resources/group/playingcardsdemo/Avatars/";
        avatarImage = new Image(new FileInputStream(path + currentAvatar + ".png"));
        avatarImageView.setImage(avatarImage);
    }
    public void toggleFeltLeft(ActionEvent event) throws FileNotFoundException {
        String path = "src/main/resources/group/playingcardsdemo/";
        if (currentFeltColor.equals("Red")) {
            currentFeltColor = "Green";
        }
        else if (currentFeltColor.equals("Green")) {
            currentFeltColor = "Red";
        }
        feltImageView.setImage(new Image(new FileInputStream(path + currentFeltColor.toLowerCase() + "felt.jpg")));
    }
    public void toggleFeltRight(ActionEvent event) throws FileNotFoundException {
        String path = "src/main/resources/group/playingcardsdemo/";
        if (currentFeltColor.equals("Red")) {
            currentFeltColor = "Green";
        }
        else if (currentFeltColor.equals("Green")) {
            currentFeltColor = "Red";
        }
        feltImageView.setImage(new Image(new FileInputStream(path + currentFeltColor.toLowerCase() + "felt.jpg")));
    }
    public void toggleCardBackLeft(ActionEvent event) throws FileNotFoundException {
        String newCardBack = null;
        if (currentCardBack.equals(cardbacks[0])) {
            newCardBack = cardbacks[cardbacks.length - 1];
        }
        else {
            for (int i = 0; i < cardbacks.length; i++) {
                if (currentCardBack.equals(cardbacks[i])) {
                    newCardBack = cardbacks[i - 1];
                    break;
                }
            }
        }
        currentCardBack = newCardBack;

        String path = "src/main/resources/group/playingcardsdemo/Card_Backs/";
        cardBackImage = new Image(new FileInputStream(path + currentCardBack + ".png"));
        cardBackImageView.setImage(cardBackImage);
    }
    public void toggleCardBackRight(ActionEvent event) throws FileNotFoundException {
        String newCardBack = null;
        if (currentCardBack.equals(cardbacks[cardbacks.length - 1])) {
            newCardBack = cardbacks[0];
        }
        else {
            for (int i = 0; i < cardbacks.length; i++) {
                if (currentCardBack.equals(cardbacks[i])) {
                    newCardBack = cardbacks[i + 1];
                    break;
                }
            }
        }
        currentCardBack = newCardBack;

        String path = "src/main/resources/group/playingcardsdemo/Card_Backs/";
        cardBackImage = new Image(new FileInputStream(path + currentCardBack + ".png"));
        cardBackImageView.setImage(cardBackImage);
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        sceneBuilder(event);
    }
    public void switchToGameSetup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameSetup.fxml"));
        sceneBuilder(event);
    }
    public void switchToShufflingTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShufflingTest.fxml"));
        root = loader.load();

        ShufflingTestController controller = loader.getController();
        controller.initializeController(event);
    }
    public void switchToHandRecognitionTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandRecognitionTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToHandComparisonTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HandComparisonTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToDrawCardsTest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DrawCardsTest.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void switchToSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        root = loader.load();
        sceneBuilder(event);
    }
    public void startGame(ActionEvent event) throws IOException {
        assignPayoutSheet();


        Player player = new Player(nameTextField.getText());
        player.setStartingChips((int) slider.getValue());
        player.setChipTotal((int) slider.getValue());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        GameController.setPlayer(player);
        gameController.setAvatar(avatarImage);
        gameController.displayName(player.getName());
        gameController.displayChipCount(chipCountLabel.getText());
        gameController.displayPayouts();
        gameController.setCardBack(cardBackImage);

        sceneBuilder(event);
    }

    public void assignPayoutSheet() {
        FileManager fileManager = new FileManager();

        switch (gameLabel.getText()) {
            case "UTH":
                fileManager.inputPayoutSheet_UTH(room);
                break;
            default:
                break;
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                chipCount = (int) slider.getValue();
                chipCountLabel.setText("$ " + chipCount);
            }
        });
        gameModeChoiceBox.getItems().addAll(GameMode.GAMES);
        gameModeChoiceBox.setOnAction(this::setGameLabel);

        ArrayList<String> roomOptions = new ArrayList<>();
        roomOptions.add("Default");
        roomOptions.add("Derby");
        roomOptions.add("Wherever");

        roomChoiceBox.getItems().addAll(roomOptions);
        roomChoiceBox.setOnAction(this::setRoom);
    }

    public void setGameLabel(ActionEvent event) {
        String text = gameModeChoiceBox.getValue();
        gameLabel.setText(text);
    }
    public void setRoom(ActionEvent event) {
        room = roomChoiceBox.getValue();
    }
}