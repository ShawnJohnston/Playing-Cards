package group.playingcardsdemo.PlayingCards;

import group.playingcardsdemo.PlayingCards.Hand;
import group.playingcardsdemo.PlayingCards.PlayingCard;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Player {
    private String name = "";
    private Hand pocket = new Hand();
    private Hand hand = new Hand();
    private int startingChips;
    private int chipTotal;

    // Constructors
    public Player(String playerName) {
        this.name = playerName;
    }
    public Player(String playerName, int startingChips) {
        this.name = playerName;
        this.startingChips = startingChips;
        this.chipTotal = startingChips;
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
