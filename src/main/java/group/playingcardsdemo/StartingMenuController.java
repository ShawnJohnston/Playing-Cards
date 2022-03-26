package group.playingcardsdemo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartingMenuController implements Initializable {
    private Parent root;
    private final String css = this.getClass().getResource("style.css").toExternalForm();
    public int chipCount;
    public String room = "Default";

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

    public void exitApplication(ActionEvent event) {
        Platform.exit();
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

        String name = nameTextField.getText();
        Global.playerName = name;

        Global.startingChips = (int) slider.getValue();
        Global.chipCount = (int) slider.getValue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        gameController.displayName(name);
        gameController.displayChipCount(chipCountLabel.getText());
        gameController.displayPayouts();

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
        gameModeChoiceBox.getItems().addAll(Global.GAMES);
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