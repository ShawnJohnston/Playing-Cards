import java.util.ArrayList;

public class HandEvaluator {
    private static String gameMode = "5 Card Poker";
    private static String[] standardPokerRanks = {"High Card", "Pair", "Two Pair", "Trips", "Straight", "Flush",
            "Full House", "Quads", "Straight Flush", "Royal Flush"};
    private Player player;
    private ArrayList<PlayingCard> playerHand;
    private int handSize;
    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private String quadsValue = null;
    private int straightSize = 5;

    // Constructor
    public HandEvaluator(Player player, ArrayList<PlayingCard> hand) {
        this.player = player;
        this.playerHand = hand;
        this.handSize = playerHand.size();
        sortHand(this.playerHand);
        checkForWheel(this.playerHand);
    }

    // Getters
    public static String getGameMode() {
        return gameMode;
    }
    public Player getPlayer() {
        return this.player;
    }
    public ArrayList<PlayingCard> getPlayerHand() {
        return this.playerHand;
    }
    public int getHandSize() {
        return this.handSize;
    }
    public int getStraightSize() {
        return this.straightSize;
    }

    // Setters
    public static void setGameMode(String gameMode) {
        HandEvaluator.gameMode = gameMode;
    }
    public void setPlayerHand(ArrayList<PlayingCard> hand) {
        this.playerHand = hand;
    }
    public void setHandSize(int size) {
        this.handSize = size;
    }
    public void setStraightSize(int size) {
        this.straightSize = size;
    }

    // Methods
    public void sortHand(ArrayList<PlayingCard> hand) {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>(); // Buffer hand to overwrite playerHand.
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Value order, excluding 'low Ace'.
        int cardsSorted = 0; // Counter used to break out of the following loop when the hand has been sorted.

        // This 2D loop will compare each card to each index in the values array. If they match, the card will be added
        // to the buffer array and the counter will increment.
        // The counter will break the loop when it increments to the value equal to the number of cards in the hand.
        for (String value : values) {
            for (PlayingCard card : hand) {
                // The outer loop sets the comparison value, and the inner loop cycles through each card in the hand.
                if (card.getValue().equals(value)) {
                    // The card's value matches the values array.
                    sortedHand.add(card); // The card is added to the buffer list.
                    cardsSorted++;
                }
            }
            if (cardsSorted == this.handSize) {
                break;
            }
        }
        this.playerHand = sortedHand;
    }
    private void checkForWheel(ArrayList<PlayingCard> hand) {
        if (hand.get(0).getValue().equals("2") &&
                hand.get(1).getValue().equals("3") &&
                hand.get(2).getValue().equals("4") &&
                hand.get(3).getValue().equals("5") &&
                hand.get(4).getValue().equals("Ace")) {
            adjustForWheel();
        }
    }
    private void adjustForWheel() {
        boolean isWheel = true;
        String[] values = {"2", "3", "4", "5", "Ace"};

        for (int i = 0; i < this.handSize; i++) {
            if (!this.playerHand.get(i).getValue().equals(values[i])) {
                isWheel = false;
                break;
            }
        }

        if (isWheel) {
            PlayingCard tempCard = this.playerHand.get(this.handSize - 1);
            this.playerHand.add(0, tempCard);
            this.playerHand.remove(playerHand.size() - 1);
        }
    }
    public void checkForMultiples() {
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] valueCounts = new int[values.length];
        int handCount = 0;

        for (int i = 0; i < values.length; i++) {
            for (PlayingCard playingCard : this.playerHand) {
                if (playingCard.getValue().equals(values[i])) {
                    valueCounts[i] += 1;
                }
            }
        }
        for (int i = 0; i < values.length; i++) {
            if (handCount >= this.playerHand.size()) {
                break;
            }
            if (valueCounts[i] == 2) {
                this.pairsList.add(values[i]);
                handCount += 2;
            }
            if (valueCounts[i] == 3) {
                this.tripsList.add(values[i]);
                handCount += 3;
            }
            if (valueCounts[i] == 4) {
                this.quadsValue = values[i];
                break;
            }
        }
        if (!(this.quadsValue == null)) {

        }
        else if (this.tripsList.size() == 1 && this.pairsList.size() == 1) {

        }
        else if (this.tripsList.size() == 1) {

        }
        else if (this.pairsList.size() == 2) {

        }
        else if (this.pairsList.size() == 1) {

        }
        else {

        }
    }
    public Boolean isAFlush() {
        if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
            return false;
        }

        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        int spadesCounter = 0, heartsCounter = 0, clubsCounter = 0, diamondsCounter = 0;
        for (PlayingCard card: this.playerHand) {
            switch (card.getSuit()) {
                case "Spades":
                    spadesCounter++;
                    break;
                case "Hearts":
                    heartsCounter++;
                    break;
                case "Clubs":
                    clubsCounter++;
                case "Diamonds":
                    diamondsCounter++;
            }
        }
        int[] suitCounters =  {spadesCounter, heartsCounter, clubsCounter, diamondsCounter};
        for (int counter: suitCounters) {
            if (counter >= 5) {

                return true;
            }
        }
        return false;
    }
    public Boolean isAStraight() {
        // This flow of logic is for game modes where a straight requires 5 consecutive values.
        if (this.straightSize == 5) {
            boolean handContainsFiveOrTen = false; // All 5 card straights contain either a "5" or "10". Otherwise, it's ruled out.
            if (gameMode.equals("5 Card Poker") && this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
                return false;
            }
            for (PlayingCard card: this.playerHand) {
                if (card.getValue().equals("5") || card.getValue().equals("10")) {
                    handContainsFiveOrTen = true; // The hand contains "5" or "10". Evaluation of a straight can proceed.
                    break;
                }
            }
            if (!handContainsFiveOrTen) {
                return false; // The hand does not contain a "5" or "10". A straight is not possible.
            }
            else {
                // The hand will be sorted by value. Then, each index of the hand will be compared to an array of values.
                // When the value of the first card in the hand matches an index value, a loop will be used to count value sequences.
                String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Reference array.
                int indexPoint = 0; // This integer is used to match the first card in the hand to the corresponding index of the values array.

                // This loop is used to match the first card in the hand with the corresponding value's index in the values array.
                // When the match occurs, the values index is stored in indexPoint, which will be used to establish where
                // counting begins in the next step of the process.
                for (int i = 0; i < values.length; i++) {
                    if (this.playerHand.get(0).getValue().equals(values[i])) {
                        // If the first card in the hand has a value that matches the values array at index i, indexPoint
                        // will record the value of i.
                        indexPoint = i;
                        break;
                    }
                }

                int straightCount = 0; // Used to count value sequence.
                int handIndex = 0; // Counter used to access the playerHand index.
                // This loop starts where the first card in the hand matches with the corresponding value index in the values array.
                // The loop spans up to (exclusively) the indexPoint value + the integer defining the size of a straight.
                for (int i = indexPoint; i < (indexPoint + this.straightSize); i++) {
                    // This if-statement will compare the string value at current playerHand index to the values array at index i.
                    if (this.playerHand.get(handIndex).getValue().equals(values[i])) {
                        // The string value at this playerHand index matches the string value at the values array index i.
                        straightCount++;
                        handIndex++;
                    }
                    else {
                        straightCount = 0;
                        handIndex++;
                    }
                    if (straightCount == this.straightSize) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean isAStraightFlush() {
        if (isAStraight() && isAFlush()) {
            return true;
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (isAStraightFlush()) {
            if (this.playerHand.get(0).getValue().equals("10") &&
                this.playerHand.get(1).getValue().equals("Jack") &&
                this.playerHand.get(2).getValue().equals("Queen") &&
                this.playerHand.get(3).getValue().equals("King") &&
                this.playerHand.get(4).getValue().equals("Ace")) {
                return true;
            }
        }
        return false;
    }
}