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

        checkForMultiples();
        if (isAFlush()) {
            this.rank = "Flush";
        }
        if (isAStraight()) {
            this.rank = "Straight";
        }
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
    // Setters
    public static void setGameMode(String gameMode) {
        HandEvaluator.gameMode = gameMode;
    }

    // Methods
    public ArrayList<PlayingCard> sortHand(ArrayList<PlayingCard> hand) {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>();
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String value : values) {
            for (PlayingCard playingCard : hand) {
                if (value.equals(playingCard.getValue())) {
                    sortedHand.add(playingCard);
                    hand.remove(playingCard);
                }
            }
        }
        return sortedHand;
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
            if (card.getValue().equals("Five") || card.getValue().equals("Ten")) {
                handContainsFiveOrTen = true;
                break;
            }
        }
        if (handContainsFiveOrTen = false) {
            return false;
        }
        else {
            int straightCondition = this.playerHand.size();
            ArrayList<PlayingCard> sortedHand = new ArrayList<>();
            String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

            for (String value : values) {
                for (PlayingCard playingCard : this.playerHand) {
                    if (value.equals(playingCard.getValue())) {
                        sortedHand.add(playingCard);
                        this.playerHand.remove(playingCard);
                    }
                }
            }
            return straightCondition == countToStraight(sortedHand, values);
        }
    }
    public int countToStraight(ArrayList<PlayingCard> sortedHand, String[] values) {
        int straightCount = 1;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < sortedHand.size(); j++) {
                if (straightCount == 5) {
                    break;
                }
                if (sortedHand.get(j).getValue().equals(values[i])) {
                    if (sortedHand.get(j + 1).getValue().equals(values[i + 1])) {
                        straightCount++;
                    }
                }
            }
        }
        return straightCount;
    }
}