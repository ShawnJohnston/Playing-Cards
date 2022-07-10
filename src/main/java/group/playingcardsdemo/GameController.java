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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class GameController {
    private Parent root;
    private final String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
    private static Player player;

    @FXML
    Label nameLabel;
    @FXML
    Label chipCountLabel;
    @FXML
    ImageView backgroundImageView;
    Image backgroundImage;
    @FXML
    ImageView deckTopImageView = new ImageView();
    @FXML
    ImageView avatarImageView = new ImageView();

    @FXML
    Label payout00Label = new Label();
    @FXML
    Label payout01Label = new Label();
    @FXML
    Label payout02Label = new Label();
    @FXML
    Label payout10Label = new Label();
    @FXML
    Label payout11Label = new Label();
    @FXML
    Label payout12Label = new Label();
    @FXML
    Label payout20Label = new Label();
    @FXML
    Label payout21Label = new Label();
    @FXML
    Label payout22Label = new Label();
    @FXML
    Label payout30Label = new Label();
    @FXML
    Label payout31Label = new Label();
    @FXML
    Label payout32Label = new Label();
    @FXML
    Label payout40Label = new Label();
    @FXML
    Label payout41Label = new Label();
    @FXML
    Label payout42Label = new Label();
    @FXML
    Label payout50Label = new Label();
    @FXML
    Label payout51Label = new Label();
    @FXML
    Label payout52Label = new Label();
    @FXML
    Label payout60Label = new Label();
    @FXML
    Label payout61Label = new Label();
    @FXML
    Label payout62Label = new Label();
    @FXML
    Label payout70Label = new Label();
    @FXML
    Label payout71Label = new Label();
    @FXML
    Label payout72Label = new Label();

    public GameController() {
    }

    public static void setPlayer(Player _player) {
        player = _player;
    }

    public void setCardBack(Image image) {
        this.deckTopImageView.setImage(image);
    }
    public void setAvatar(Image image) {
        this.avatarImageView.setImage(image);
    }
    public void displayPayouts() {

    }
    public void displayName(String name) {
        nameLabel.setText(name);
    }
    public void displayChipCount(String chipCount) {
        chipCountLabel.setText(chipCount);
    }
    public void displayBackground() {
        backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("redFelt-2x.jpg")));
        backgroundImageView.setImage(backgroundImage);
    }
    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/MainMenu.fxml")));
        sceneBuilder(event);
    }
    private void sceneBuilder(ActionEvent event) {
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
