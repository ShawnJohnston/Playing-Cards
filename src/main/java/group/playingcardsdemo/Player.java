package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class Player {
    private String name = "";
    private Hand pocket = new Hand();
    private Hand hand = new Hand();
    private int startingChips;
    private int chipTotal;

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
        hand.setCards(cards);
    }
    public void setPocket(ArrayList<PlayingCard> cards) {
        pocket.setCards(cards);
    }
    // Methods
    public void addCardToHand(PlayingCard card) {
        hand.addCard(card);
    }
    public void addCardToPocket(PlayingCard card) {
        pocket.addCard(card);
    }
    public void addChips(int chips) {
        chipTotal += chips;
    }
    public void subtractChips(int chips) {
        chipTotal -= chips;
    }
}
