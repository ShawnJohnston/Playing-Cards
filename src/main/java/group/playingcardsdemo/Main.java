package group.playingcardsdemo;

import group.playingcardsdemo.PlayingCards.HandEvaluator;
import group.playingcardsdemo.PlayingCards.Values;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Values.initializeValueMap();
        Values.initializeIndexMap();
        HandEvaluator.initializePokerRanks();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/TitleScreen.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ESCAPE -> {
                        Platform.exit();
                    }
                }
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}