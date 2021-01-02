import java.util.ArrayList;

public class HandEvaluator {
    private static String gameMode;
    private static String[] standardPokerRanks = {"High Card", "Pair", "Two Pair", "Trips", "Straight", "Flush",
                                                    "Full House", "Quads", "Straight Flush", "Royal Flush"};
    private String handRank;
    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private String quadsValue = null;
    private Boolean isHighCard = false;
    private Boolean isPair = false;
    private Boolean isTwoPair = false;
    private Boolean isTrips = false;
    private Boolean isStraight = false;
    private Boolean isFlush = false;
    private Boolean isFullHouse = false;
    private Boolean isQuads = false;
    private Boolean isStraightFlush = false;
    private Boolean isRoyalFlush = false;

    // Constructor
    public HandEvaluator(ArrayList<PlayingCard> hand) {
    }

    // Getters
    public static String getGameMode() {
        return gameMode;
    }
    // Setters
    public static void setGameMode(String gameMode) {
        HandEvaluator.gameMode = gameMode;
    }
    // Methods
    public void checkForMultiples(ArrayList<PlayingCard> hand) {
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] valueCounts = new int[values.length];
        ArrayList<String> pairsList = new ArrayList<>();
        ArrayList<String> tripsList = new ArrayList<>();
        String quadsValue = null;
        int handCount = 0;

        for (int i = 0; i < values.length; i++) {
            for (PlayingCard playingCard : hand) {
                if (playingCard.getValue().equals(values[i])) {
                    valueCounts[i] += 1;
                }
            }
        }
        for (int i = 0; i < values.length; i++) {
            if (handCount >= hand.size()) {
                break;
            }
            if (valueCounts[i] == 2) {
                pairsList.add(values[i]);
                handCount += 2;
            }
            if (valueCounts[i] == 3) {
                tripsList.add(values[i]);
                handCount += 3;
            }
            if (valueCounts[i] == 4) {
                quadsValue = values[i];
                break;
            }
        }
        if (!(quadsValue == null)) {
            this.isQuads = true;
        }
        else if (tripsList.size() == 1 && pairsList.size() == 1) {
            this.isFullHouse = true;
        }
        else if (tripsList.size() == 1) {
            this.isTrips = true;
        }
        else if (pairsList.size() == 2) {
            this.isTwoPair = true;
        }
        else if (pairsList.size() == 1) {
            this.isPair = true;
        }
    }
    public Boolean isAFlush(ArrayList<PlayingCard> hand) {
        if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
            return false;
        }

        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        int spadesCounter = 0, heartsCounter = 0, clubsCounter = 0, diamondsCounter = 0;

        for (PlayingCard card: hand) {
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
    public Boolean isAStraight(ArrayList<PlayingCard> hand) {
        boolean handContainsFiveOrTen = false;
        if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
            return false;
        }
        for (PlayingCard card: hand) {
            if (card.getValue().equals("Five") || card.getValue().equals("Ten")) {
                handContainsFiveOrTen = true;
                break;
            }
        }
        if (handContainsFiveOrTen = false) {
            return false;
        }
        else {
            int straightCondition = hand.size();
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