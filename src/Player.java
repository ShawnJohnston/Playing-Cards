import java.util.ArrayList;

public class Player {
    private static int playerNumber = 0;
    private int playerID;
    private static ArrayList<Integer> playerList = new ArrayList<>();
    private int maxNumCardsInHand;
    private ArrayList<PlayingCard> hand;
    private int startingChips;
    private int chipTotal;

    public Player() {
        playerNumber++;
        this.playerID = playerNumber;
        playerList.add(playerID);
    }

    public static int getPlayerNumber() {
        return playerNumber;
    }
    public int getPlayerID() {
        return playerID;
    }
    public static ArrayList<Integer> getPlayerList() {
        return playerList;
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

    public static void setPlayerList(ArrayList<Integer> playerList) {
        Player.playerList = playerList;
    }
    public void setMaxNumCardsInHand(int maxNumCardsInHand) {
        this.maxNumCardsInHand = maxNumCardsInHand;
    }
    public void addToHand(PlayingCard card) {
        this.hand.add(card);
    }
    public void setStartingChips(int startingChips) {
        this.startingChips = startingChips;
    }
}
