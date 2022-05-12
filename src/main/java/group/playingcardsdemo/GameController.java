package group.playingcardsdemo;

import group.playingcardsdemo.PayoutSheet;
import group.playingcardsdemo.Player;
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
        for (int i = 0; i < PayoutSheet.payoutSheetUTH.length; i++) {
            System.out.println(Arrays.toString(PayoutSheet.payoutSheetUTH[i]));
        }

        payout00Label.setText(PayoutSheet.payoutSheetUTH[0][0]);
        payout01Label.setText(PayoutSheet.payoutSheetUTH[0][1]);
        payout02Label.setText(PayoutSheet.payoutSheetUTH[0][2]);
        payout10Label.setText(PayoutSheet.payoutSheetUTH[1][0]);
        payout11Label.setText(PayoutSheet.payoutSheetUTH[1][1]);
        payout12Label.setText(PayoutSheet.payoutSheetUTH[1][2]);
        payout20Label.setText(PayoutSheet.payoutSheetUTH[2][0]);
        payout21Label.setText(PayoutSheet.payoutSheetUTH[2][1]);
        payout22Label.setText(PayoutSheet.payoutSheetUTH[2][2]);
        payout30Label.setText(PayoutSheet.payoutSheetUTH[3][0]);
        payout31Label.setText(PayoutSheet.payoutSheetUTH[3][1]);
        payout32Label.setText(PayoutSheet.payoutSheetUTH[3][2]);
        payout40Label.setText(PayoutSheet.payoutSheetUTH[4][0]);
        payout41Label.setText(PayoutSheet.payoutSheetUTH[4][1]);
        payout42Label.setText(PayoutSheet.payoutSheetUTH[4][2]);
        payout50Label.setText(PayoutSheet.payoutSheetUTH[5][0]);
        payout51Label.setText(PayoutSheet.payoutSheetUTH[5][1]);
        payout52Label.setText(PayoutSheet.payoutSheetUTH[5][2]);
        payout60Label.setText(PayoutSheet.payoutSheetUTH[6][0]);
        payout61Label.setText(PayoutSheet.payoutSheetUTH[6][1]);
        payout62Label.setText(PayoutSheet.payoutSheetUTH[6][2]);
        payout70Label.setText(PayoutSheet.payoutSheetUTH[7][0]);
        payout71Label.setText(PayoutSheet.payoutSheetUTH[7][1]);
        payout72Label.setText(PayoutSheet.payoutSheetUTH[7][2]);
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
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
