package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class Player {
    private String name = ""; // The player can set a name.
    //private int maxNumCardsInHand; // Used for game purposes.
    private Hand pocket = new Hand();
    private Hand hand = new Hand(); // The player's hand of cards.
    private int startingChips; // Amount of Chips the player starts a game session with.
    private int chipTotal; // The player's total number of chips.

    // Constructors
    public Player() {
    }
    public Player(String playerName) {
        this.name = playerName;
    }
    public Player(String playerName, int startingChips) {
        this.name = playerName;
        this.startingChips = startingChips;
    }
    public void setHand(ArrayList<PlayingCard> cards) {
        hand.setHand(cards);
    }
    public void setPocket(ArrayList<PlayingCard> cards) {
        pocket.setHand(cards);
    }
    // Methods
    public void addToHand(PlayingCard card) {
        hand.addCard(card);
    }
    public void addToPocket(PlayingCard card) {
        pocket.addCard(card);
    }
    public void addChips(int chips) {
        chipTotal += chips;
    }
    public void subtractChips(int chips) {
        chipTotal -= chips;
    }
}
