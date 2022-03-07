package group.playingcardsdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController {
    @FXML
    Label nameLabel;
    @FXML
    Label chipCountLabel;
    @FXML
    ImageView backgroundImageView;
    Image backgroundImage;

    public void displayName(String name) {
        nameLabel.setText(name);
    }
    public void displayChipCount(String chipCount) {
        chipCountLabel.setText(chipCount);
    }
    public void displayBackground() {
        backgroundImage = new Image(getClass().getResourceAsStream("redfelt-2x.jpg"));
        backgroundImageView.setImage(backgroundImage);
    }
}
