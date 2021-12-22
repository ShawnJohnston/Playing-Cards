import java.util.ArrayList;

// This class will implement all features needed for a player.
public class Player {
    private String name = ""; // The player can set a name.
    private int maxNumCardsInHand; // Used for game purposes.
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

    // Getters
    public String getName() {
        return name;
    }
    public int getMaxNumCardsInHand() {
        return maxNumCardsInHand;
    }
    public Hand getHand() {
        return hand;
    }
    public int getChipTotal() {
        return chipTotal;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setMaxNumCardsInHand(int maxNumCardsInHand) {
        this.maxNumCardsInHand = maxNumCardsInHand;
    }
    public void setStartingChips(int startingChips) {
        this.startingChips = startingChips;
    }
    public void setHand(ArrayList<PlayingCard> cards) {
        hand.setHand(cards);
    }

    // Methods
    public void addToHand(PlayingCard card) {
        hand.addCard(card);
    }
    public void addChips(int chips) {
        chipTotal += chips;
    }
    public void subtractChips(int chips) {
        chipTotal -= chips;
    }
}
