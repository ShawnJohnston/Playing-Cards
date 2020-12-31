import java.util.ArrayList;

// This class will implement all features needed for a player.
public class Player {
    private String name = ""; // The player can set a name.
    private static int playerNumber = 0; // Used to count total number of players in a game session.
    private int playerID; // The playerNumber is stored here. That number is an unique ID unique to that player instance.
    private static ArrayList<Integer> playerList = new ArrayList<>(); // List of all players in the session.
    private int minNumCardsInHand; // Used for game purposes.
    private int maxNumCardsInHand; // Used for game purposes.
    private ArrayList<PlayingCard> hand; // The player's hand of cards.
    private int startingChips; // Amount of Chips the player starts a game session with.
    private int chipTotal; // The player's total number of chips.

    // Constructor
    public Player() {
        playerNumber++; // The playerNumber increments.
        this.playerID = playerNumber; // The player's ID is set to the current player number.
        playerList.add(playerID); // The player is added to the playerList by ID.
    }

    // Getters
    public static int getPlayerNumber() {
        return playerNumber;
    }
    public int getPlayerID() {
        return playerID;
    }
    public static ArrayList<Integer> getPlayerList() {
        return playerList;
    }
    public int getMinNumCardsInHand() {
        return minNumCardsInHand;
    }
    public int getMaxNumCardsInHand() {
        return maxNumCardsInHand;
    }
    public ArrayList<PlayingCard> getHand() {
        return this.hand;
    }
    public int getChipTotal() {
        return this.chipTotal;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public static void setPlayerList(ArrayList<Integer> playerList) {
        Player.playerList = playerList;
    }
    public void setMinNumCardsInHand(int minNumCardsInHand) {
        this.minNumCardsInHand = minNumCardsInHand;
    }
    public void setMaxNumCardsInHand(int maxNumCardsInHand) {
        this.maxNumCardsInHand = maxNumCardsInHand;
    }
    public void setStartingChips(int startingChips) {
        this.startingChips = startingChips;
    }

    // Methods
    public void addToHand(PlayingCard card) {
        this.hand.add(card);
    }
}
