package group.playingcardsdemo;

import group.playingcardsdemo.FileIO.FileManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartingMenuController extends Controller implements Initializable {
    public int chipCount;
    public String room;
    public String currentFeltColor;
    private final TogglingArray<String> cardBacks = new TogglingArray<>(new String[]{
            "red", "red2", "blue", "blue2", "abstract", "abstract_clouds",
            "abstract_scene", "astronaut", "cars", "castle", "fish", "frog"},
            "src/main/resources/group/playingcardsdemo/Card_Backs/");
    private final TogglingArray<String> avatars = new TogglingArray<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9"},
            "src/main/resources/group/playingcardsdemo/Avatars/");

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
        room = "Default";
        currentFeltColor = "Red";

        cardBackImageView.setImage(new Image(new FileInputStream(
                cardBacks.getPackagePath() + cardBacks.getCurrentT() + ".png")));
        avatarImageView.setImage(new Image(new FileInputStream(
                avatars.getPackagePath() + avatars.getCurrentT() + ".png")));
    }

    public void toggleAvatarLeft() throws FileNotFoundException {
        avatars.toggleLeft();
        avatarImageView.setImage(new Image(new FileInputStream(
                avatars.getPackagePath() + avatars.getCurrentT() + ".png")));
    }
    public void toggleAvatarRight() throws FileNotFoundException {
        avatars.toggleRight();
        avatarImageView.setImage(new Image(new FileInputStream(
                avatars.getPackagePath() + avatars.getCurrentT() + ".png")));
    }
    public void toggleFeltLeft() throws FileNotFoundException {
        /*
            This method will toggle between felt selections.
         */

        String path = "src/main/resources/group/playingcardsdemo/Payouts/";
        if (currentFeltColor.equals("Red")) {
            currentFeltColor = "Green";
        }
        else if (currentFeltColor.equals("Green")) {
            currentFeltColor = "Red";
        }
        feltImageView.setImage(new Image(new FileInputStream(path + currentFeltColor.toLowerCase() + "felt.jpg")));
    }
    public void toggleFeltRight() throws FileNotFoundException {
        /*
            This method will toggle between felt selections.
         */

        String path = "src/main/resources/group/playingcardsdemo/PayoutSheets/";
        if (currentFeltColor.equals("Red")) {
            currentFeltColor = "Green";
        }
        else if (currentFeltColor.equals("Green")) {
            currentFeltColor = "Red";
        }
        feltImageView.setImage(new Image(new FileInputStream(path + currentFeltColor.toLowerCase() + "felt.jpg")));
    }
    public void toggleCardBackLeft() throws FileNotFoundException {
        cardBacks.toggleLeft();
        cardBackImageView.setImage(new Image(new FileInputStream(
                cardBacks.getPackagePath() + cardBacks.getCurrentT() + ".png")));
    }
    public void toggleCardBackRight() throws FileNotFoundException {
        cardBacks.toggleRight();
        cardBackImageView.setImage(new Image(new FileInputStream(
                cardBacks.getPackagePath() + cardBacks.getCurrentT() + ".png")));
    }

    public void startGame(ActionEvent event) throws IOException {
        /*
            This method instantiates the game controller to start the gaming session and transfers data from this class
            to the game controller.

            1.  Execute method to set payout for the given game mode.
            2.  Initialize Player object, have it store the user's name and starting chips.
            3.  Initialize loader and update root.
            4.  Initialize game controller.
            5.  Transfer game settings to game controller.
            6.  Set the root to the parent class and run method to switch scenes.
         */

        assignPayoutSheet();

        Player player = new Player(nameTextField.getText());
        player.setStartingChips((int) slider.getValue());
        player.setChipTotal((int) slider.getValue());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        GameController.setPlayer(player);
        gameController.setAvatar(new Image(new FileInputStream(
                avatars.getPackagePath() + avatars.getCurrentT() + ".png")));
        gameController.displayName(player.getName());
        gameController.displayChipCount(chipCountLabel.getText());
        gameController.displayPayouts();
        gameController.setCardBack(new Image(new FileInputStream(
                cardBacks.getPackagePath() + cardBacks.getCurrentT() + ".png")));

        super.setRoot(root);
        sceneBuilder(event);
    }

    public void assignPayoutSheet() {
        switch (gameLabel.getText()) {
            case "UTH":
                FileManager.inputPayoutSheet_UTH(room);
                break;
            case "FiveCardStud":
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
            This method will listen for changes made through the slider or choice box.

            1.  The starting chips slider will update its value and label on interaction.
            2.  The game mode choice box fills with options and executes a method to update the feedback label.
            3.  The room choice box fills with options and executes a method to update the feedback label.
         */

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