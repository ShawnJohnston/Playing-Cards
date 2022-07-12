package group.playingcardsdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class UTHGameController extends GameController {
    private int pocketBetAmount;
    private int tripsBetAmount;
    private int anteBetAmount;
    private int blindBetAmount;
    private int playBetAmount;

    //  Buttons
    @FXML
    Button foldButton;
    @FXML
    Button checkButton;
    @FXML
    Button betButton;

    //  Betting Spots
    @FXML
    Circle pocketBet;
    @FXML
    Circle tripsBet;
    @FXML
    Circle anteBet;


    public void toFold() {

    }
    public void toCheck() {

    }
    public void toBet() {

    }
    public void placeAnteBet() {
    }
    public void placeTripsBet() {

    }
    public void placePocketBet() {

    }
    public void startGame(ActionEvent event) {

    }
}
