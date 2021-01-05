import java.util.ArrayList;

public class HandEvaluator {
    private static String gameMode;
    private static String[] standardPokerRanks = {"High Card", "Pair", "Two Pair", "Trips", "Straight", "Flush",
            "Full House", "Quads", "Straight Flush", "Royal Flush"};
    private Player player;
    private String rank;
    private ArrayList<PlayingCard> playerHand;
    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private String quadsValue = null;

    // Constructor
    public HandEvaluator(Player player, ArrayList<PlayingCard> hand) {
        this.player = player;
        this.playerHand = sortHand(hand);
    }

    // Getters
    public static String getGameMode() {
        return gameMode;
    }
    public String getRank() {
        return this.rank;
    }
    public Player getPlayer() {
        return this.player;
    }
    public ArrayList<PlayingCard> getHand() {
        return this.playerHand;
    }

    // Setters
    public static void setGameMode(String gameMode) {
        HandEvaluator.gameMode = gameMode;
    }

    // Methods
    public ArrayList<PlayingCard> sortHand(ArrayList<PlayingCard> hand) {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>();
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int cardsSorted = 0;

        for (int i = 0; i < values.length; i++) {
            if (cardsSorted == hand.size()) {
                break;
            }
            for (PlayingCard card: hand) {
                if (card.getValue().equals(values[i])) {
                    sortedHand.add(card);
                    cardsSorted++;
                }
            }
        }

        return sortedHand;
    }
    private boolean checkForWheel() {
        if (this.playerHand.get(0).getValue().equals("2") &&
                this.playerHand.get(1).getValue().equals("3") &&
                this.playerHand.get(2).getValue().equals("4") &&
                this.playerHand.get(3).getValue().equals("5") &&
                this.playerHand.get(4).getValue().equals("Ace")) {
            this.playerHand.add(0, this.playerHand.get(4));
            this.playerHand.remove(5);
            return true;
        }
        return false;
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
            this.rank = "Quads";
        }
        else if (this.tripsList.size() == 1 && this.pairsList.size() == 1) {
            this.rank = "Full House";
        }
        else if (this.tripsList.size() == 1) {
            this.rank = "Trips";
        }
        else if (this.pairsList.size() == 2) {
            this.rank = "Two Pair";
        }
        else if (this.pairsList.size() == 1) {
            this.rank = "Pair";
        }
        else {
            this.rank = "High Card";
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
        if (spadesCounter >= 5) {
            return true;
        }
        else if (heartsCounter >= 5) {
            return true;
        }
        else if (clubsCounter >= 5) {
            return true;
        }
        else if (diamondsCounter >= 5) {
            return true;
        }
        return false;
    }
    public Boolean isAStraight() {
        boolean handContainsFiveOrTen = false;
        if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
            return false;
        }
        for (PlayingCard card: this.playerHand) {
            if (card.getValue().equals("5") || card.getValue().equals("10")) {
                handContainsFiveOrTen = true;
                break;
            }
        }
        if (!handContainsFiveOrTen) {
            return false;
        }
        else {
            int straightLength = 1;
            String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
            int indexPoint = 0;

            for (int i = 0; i < values.length; i++) {
                if (this.playerHand.get(0).getValue().equals(values[i])) {
                    indexPoint = i;
                }
            }

            int j = 0;
            for (int i = indexPoint; i < indexPoint + this.playerHand.size(); i++) {
                if (this.playerHand.get(j).getValue().equals(values[i])) {
                    straightLength++;
                    j++;
                    if (straightLength == 5) {
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