package group.playingcardsdemo;

import group.playingcardsdemo.cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HandComparisonTest extends Controller {
    Player player1 = new Player();
    Player player2 = new Player();
    Hand hand1 = new Hand();
    Hand hand2 = new Hand();
    int handCapacity = 5;

    @FXML
    AnchorPane pane;
    @FXML
    Label p1HandRankLabel = new Label();
    @FXML
    Label p2HandRankLabel = new Label();
    @FXML
    Label outcomeLabel = new Label();
    @FXML
    Button sortButton = new Button();

    @FXML
    ImageView p1CardImageView1 = new ImageView();
    @FXML
    ImageView p1CardImageView2 = new ImageView();
    @FXML
    ImageView p1CardImageView3 = new ImageView();
    @FXML
    ImageView p1CardImageView4 = new ImageView();
    @FXML
    ImageView p1CardImageView5 = new ImageView();
    @FXML
    ImageView p2CardImageView1 = new ImageView();
    @FXML
    ImageView p2CardImageView2 = new ImageView();
    @FXML
    ImageView p2CardImageView3 = new ImageView();
    @FXML
    ImageView p2CardImageView4 = new ImageView();
    @FXML
    ImageView p2CardImageView5 = new ImageView();


    @FXML
    Label p1Card1Label = new Label();
    @FXML
    Label p1Card2Label = new Label();
    @FXML
    Label p1Card3Label = new Label();
    @FXML
    Label p1Card4Label = new Label();
    @FXML
    Label p1Card5Label = new Label();

    @FXML
    Label p2Card1Label = new Label();
    @FXML
    Label p2Card2Label = new Label();
    @FXML
    Label p2Card3Label = new Label();
    @FXML
    Label p2Card4Label = new Label();
    @FXML
    Label p2Card5Label = new Label();

    Image[] cardFrontsHand1;
    Image[] cardFrontsHand2;

    public HandComparisonTest() {
        super();
        hand1.setCapacity(handCapacity);
        hand2.setCapacity(handCapacity);
        cardFrontsHand1 = new Image[handCapacity];
        cardFrontsHand2 = new Image[handCapacity];
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
    }

    public void drawFromDeck() throws FileNotFoundException {
        p1HandRankLabel.setText("");
        p2HandRankLabel.setText("");
        for (int i = 0; i < hand1.getSize(); i++) {
            discard.addCard(hand1.getCards().get(i));
        }
        for (int i = 0; i < hand2.getSize(); i++) {
            discard.addCard(hand2.getCards().get(i));
        }
        hand1.clear();
        hand2.clear();

        if (deck.getCurrentSize() < (handCapacity * 2) || deck.isEmpty()) {
            deck.compileFromDiscard(discard);
            discard = new Discard();

            Shuffler shuffler = new Shuffler();
            shuffler.handShuffle(deck);
        }
        deck = new DeckOfCards();
        Shuffler shuffler = new Shuffler();
        shuffler.handShuffle(deck);

        for (int i = 0; i < handCapacity; i++) {
            hand1.addCard(deck.drawTopCard());
            cardFrontsHand1[i] = new Image(new FileInputStream(hand1.getCards().get(i).getFront()));

            hand2.addCard(deck.drawTopCard());
            cardFrontsHand2[i] = new Image(new FileInputStream(hand2.getCards().get(i).getFront()));
        }

        p1CardImageView1.setImage(cardFrontsHand1[0]);
        p1CardImageView2.setImage(cardFrontsHand1[1]);
        p1CardImageView3.setImage(cardFrontsHand1[2]);
        p1CardImageView4.setImage(cardFrontsHand1[3]);
        p1CardImageView5.setImage(cardFrontsHand1[4]);

        p2CardImageView1.setImage(cardFrontsHand2[0]);
        p2CardImageView2.setImage(cardFrontsHand2[1]);
        p2CardImageView3.setImage(cardFrontsHand2[2]);
        p2CardImageView4.setImage(cardFrontsHand2[3]);
        p2CardImageView5.setImage(cardFrontsHand2[4]);

        HandEvaluator p1Evaluator = new HandEvaluator(hand1);
        HandEvaluator p2Evaluator = new HandEvaluator(hand2);
        p1HandRankLabel.setText(String.valueOf(p1Evaluator.getHandRank()));
        p2HandRankLabel.setText(String.valueOf(p2Evaluator.getHandRank()));

        p1Card1Label.setText(p1Evaluator.getGameFittedHand().getCards().get(0).getName());
        p1Card2Label.setText(p1Evaluator.getGameFittedHand().getCards().get(1).getName());
        p1Card3Label.setText(p1Evaluator.getGameFittedHand().getCards().get(2).getName());
        p1Card4Label.setText(p1Evaluator.getGameFittedHand().getCards().get(3).getName());
        p1Card5Label.setText(p1Evaluator.getGameFittedHand().getCards().get(4).getName());

        p2Card1Label.setText(p2Evaluator.getGameFittedHand().getCards().get(0).getName());
        p2Card2Label.setText(p2Evaluator.getGameFittedHand().getCards().get(1).getName());
        p2Card3Label.setText(p2Evaluator.getGameFittedHand().getCards().get(2).getName());
        p2Card4Label.setText(p2Evaluator.getGameFittedHand().getCards().get(3).getName());
        p2Card5Label.setText(p2Evaluator.getGameFittedHand().getCards().get(4).getName());

        GameOutcome outcome = new GameOutcome(p1Evaluator, p2Evaluator);
        outcomeLabel.setText(outcome.getWinner());
    }

    public void toSort(ActionEvent event) throws FileNotFoundException {
        hand1.sortHandByValue();

        for (int i = 0; i < handCapacity; i++) {
            cardFrontsHand1[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand1.getCards().get(i).getFront()));
        }
        p1CardImageView1.setImage(cardFrontsHand1[0]);
        p1CardImageView2.setImage(cardFrontsHand1[1]);
        p1CardImageView3.setImage(cardFrontsHand1[2]);
        p1CardImageView4.setImage(cardFrontsHand1[3]);
        p1CardImageView5.setImage(cardFrontsHand1[4]);

        hand2.sortHandByValue();

        for (int i = 0; i < handCapacity; i++) {
            cardFrontsHand2[i] = new Image(new FileInputStream(
                    "src/main/resources/group/playingcardsdemo/Card_Fronts/" + hand2.getCards().get(i).getFront()));
        }
        p2CardImageView1.setImage(cardFrontsHand2[0]);
        p2CardImageView2.setImage(cardFrontsHand2[1]);
        p2CardImageView3.setImage(cardFrontsHand2[2]);
        p2CardImageView4.setImage(cardFrontsHand2[3]);
        p2CardImageView5.setImage(cardFrontsHand2[4]);
    }
}

