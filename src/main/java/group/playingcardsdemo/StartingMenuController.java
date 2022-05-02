package group.playingcardsdemo;

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
import java.util.HashMap;
import java.util.ResourceBundle;

public class StartingMenuController extends Controller implements Initializable {
    public int chipCount;
    public String room;
    public Image cardBackImage;
    public Image avatarImage;
    public String currentFeltColor;
    public String currentCardBack;
    public String currentAvatar;
    private HashMap<String, Integer> cardBackMap;
    private HashMap<String, Integer> avatarMap;
    private final String[] cardBacks = {
            "red", "red2", "blue", "blue2", "abstract", "abstract_clouds",
            "abstract_scene", "astronaut", "cars", "castle", "fish", "frog"};
    private final String[] avatars = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

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
        currentCardBack = "red";
        currentAvatar = avatars[0];
        cardBackImage = new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Backs/" + cardBacks[0] + ".png"));
        avatarImage = new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Avatars/" + avatars[0] + ".png"));
        initializeCardBackMap();
        initializeAvatarMap();
    }

    public void toggleAvatarLeft() throws FileNotFoundException {
        /*
            On clicking the arrow button left of the Avatar image, the Avatar will toggle leftward in the containing
            avatar array.
            
            1.  Initialize buffer array container.
            2.  If the avatar is already set to index 0, the new avatar will be set to the rightmost index.
            3.  Else, use a for loop to retrieve the avatar at the next leftward index.
            4.  Assign the buffer container to the actual avatar container.
            4.  Concatenate the file path to the avatar image and set it to the avatar ImageView.
         */
        
        String newAvatar;
        if (currentAvatar.equals(avatars[0])) {
            newAvatar = avatars[avatars.length - 1];
        }
        else {
            newAvatar = avatars[avatarMap.get(currentAvatar) - 1];
        }
        currentAvatar = newAvatar;

        avatarImage = new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Avatars/" + currentAvatar + ".png"));
        avatarImageView.setImage(avatarImage);
    }
    public void toggleAvatarRight() throws FileNotFoundException {
        /*
            On clicking the arrow button right of the Avatar image, the Avatar will toggle rightward in the containing
            avatar array.
            
            1.  Initialize buffer array container.
            2.  If the avatar is already set to index 0, the new avatar will be set to the leftmost index.
            3.  Else, use a for loop to retrieve the avatar at the next rightward index.
            4.  Assign the buffer container to the actual avatar container.
            4.  Concatenate the file path to the avatar image and set it to the avatar ImageView.
         */
        
        String newAvatar;
        if (currentAvatar.equals(avatars[avatars.length - 1])) {
            newAvatar = avatars[0];
        }
        else {
            newAvatar = avatars[avatarMap.get(currentAvatar) + 1];
        }
        currentAvatar = newAvatar;

        avatarImage = new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Avatars/" + currentAvatar + ".png"));
        avatarImageView.setImage(avatarImage);
    }
    public void toggleFeltLeft() throws FileNotFoundException {
        /*
            This method will toggle between felt selections.
         */

        String path = "src/main/resources/group/playingcardsdemo/";
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

        String path = "src/main/resources/group/playingcardsdemo/";
        if (currentFeltColor.equals("Red")) {
            currentFeltColor = "Green";
        }
        else if (currentFeltColor.equals("Green")) {
            currentFeltColor = "Red";
        }
        feltImageView.setImage(new Image(new FileInputStream(path + currentFeltColor.toLowerCase() + "felt.jpg")));
    }
    public void toggleCardBackLeft() throws FileNotFoundException {
        /*
            This method will cycle leftward through the cardBack container from the current one.

            1.  Initialize buffer card path.
            2.  If the current cardBack is at index zero, roll the index to the rightmost index of the container.
            3.  Else, the image at the leftward index is assigned to the buffer container using the index of the current
                cardBack as a key in a hashmap and subtracting 1.
         */

        String newCardBack;
        if (currentCardBack.equals(cardBacks[0])) {
            newCardBack = cardBacks[cardBacks.length - 1];
        }
        else {
            newCardBack = cardBacks[cardBackMap.get(currentCardBack) - 1];
        }
        currentCardBack = newCardBack;
        cardBackImageView.setImage(new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Backs/" + currentCardBack + ".png")));
    }
    public void toggleCardBackRight() throws FileNotFoundException {
        /*
            This method will cycle rightward through the cardBack container from the current one.

            1.  Initialize buffer card path.
            2.  If the current cardBack is at index zero, roll the index to the leftmost index of the container.
            3.  Else, the image at the rightward index is assigned to the buffer container using the index of the current
                cardBack as a key in a hashmap and subtracting 1.
         */

        String newCardBack;
        if (currentCardBack.equals(cardBacks[cardBacks.length - 1])) {
            newCardBack = cardBacks[0];
        }
        else {
            newCardBack = cardBacks[cardBackMap.get(currentCardBack) + 1];
        }
        currentCardBack = newCardBack;
        cardBackImageView.setImage(new Image(new FileInputStream(
                "src/main/resources/group/playingcardsdemo/Card_Backs/" + currentCardBack + ".png")));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        GameController.setPlayer(player);
        gameController.setAvatar(avatarImage);
        gameController.displayName(player.getName());
        gameController.displayChipCount(chipCountLabel.getText());
        gameController.displayPayouts();
        gameController.setCardBack(cardBackImage);

        super.setRoot(root);
        sceneBuilder(event);
    }

    public void assignPayoutSheet() {
        FileManager fileManager = new FileManager();

        switch (gameLabel.getText()) {
            case "UTH":
                fileManager.inputPayoutSheet_UTH(room);
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
    
    public void initializeCardBackMap() {
        cardBackMap = new HashMap<>();
        for (int i = 0; i < cardBacks.length; i++) {
            cardBackMap.put(cardBacks[i], i);
        }
    }
    public void initializeAvatarMap() {
        avatarMap = new HashMap<>();
        for (int i = 0; i < avatars.length; i++) {
            avatarMap.put(avatars[i], i);
        }
    }
    public void setGameLabel(ActionEvent event) {
        String text = gameModeChoiceBox.getValue();
        gameLabel.setText(text);
    }
    public void setRoom(ActionEvent event) {
        room = roomChoiceBox.getValue();
    }
}