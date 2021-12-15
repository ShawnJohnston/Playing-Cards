import java.util.ArrayList;
import java.util.Arrays;

public class HandEvaluator {
    private static String[] standardPokerRanks = {"HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    private final boolean[] RANKDATA = new boolean[standardPokerRanks.length];
    private Player player;
    private Hand hand;
    private int handSize;
    private int straightFlushSize = 5;
    private rankState handRank = rankState.None;


    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private String quadsValue = null;

    public HandEvaluator() {
    }
    public HandEvaluator(Player player, Hand hand) {
        this.player = player;
        this.hand = hand;
        this.handSize = hand.getCards().size();
        Arrays.fill(RANKDATA, false);
        this.RANKDATA[0] = true;
        this.hand.sortHand();
        checkForWheel();
    }

    public Player getPlayer() {
        return this.player;
    }
    public Hand getHand() {
        return this.hand;
    }
    public rankState getHandRank() {
        return handRank;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setHandSize(int size) {
        this.handSize = size;
    }
    public void setStraightFlushSize(int size) {
        this.straightFlushSize = size;
    }

    private void checkForWheel() {
        if (this.hand.getCards().get(0).getValue().equals("2") &&
            this.hand.getCards().get(1).getValue().equals("3") &&
            this.hand.getCards().get(2).getValue().equals("4") &&
            this.hand.getCards().get(3).getValue().equals("5") &&
            this.hand.getCards().get(4).getValue().equals("Ace")) {
            adjustForWheel();
        }
    }
    private void adjustForWheel() {
        PlayingCard tempCard = this.hand.getCards().get(this.hand.getCards().size() - 1);
        this.hand.removeCard(this.hand.getCards().size() - 1);
        this.hand.addCard(0, tempCard);
    }

    public Boolean isAFlush() {
        if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
            return false;
        }

        System.out.println(Arrays.toString(this.hand.getSuitData()));
        for (int i = 0; i < this.hand.getSuitData().length - 1; i++) {
            if (this.hand.getSuitData()[i] >= straightFlushSize) {
                this.handRank = rankState.Flush;
                this.RANKDATA[5] = true;
                return true;
            }
        }
        //for (int counter: this.hand.getSuitData()) {
        //    if (counter >= 5) {
        //        this.handRank = rankState.Flush;
        //        this.RANKDATA[5] = true;
        //        return true;
        //    }
        //}
        return false;
    }
    public Boolean isAStraight() {
        // This flow of logic is for game modes where a straight requires 5 consecutive values.
        if (this.straightFlushSize == 5) {
            boolean handContainsFiveOrTen = false; // All 5 card straights contain either a "5" or "10". Otherwise, it's ruled out.
            if (this.pairsList.size() > 0 || this.tripsList.size() > 0 || !(this.quadsValue == null)) {
                return false;
            }
            for (PlayingCard card: this.hand.getCards()) {
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
                    if (this.hand.getCards().get(0).getValue().equals(values[i])) {
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
                for (int i = indexPoint; i < (indexPoint + this.straightFlushSize); i++) {
                    // This if-statement will compare the string value at current playerHand index to the values array at index i.
                    if (this.hand.getCards().get(handIndex).getValue().equals(values[i])) {
                        // The string value at this playerHand index matches the string value at the values array index i.
                        straightCount++;
                        handIndex++;
                    }
                    else {
                        straightCount = 0;
                        handIndex++;
                    }
                    if (straightCount == this.straightFlushSize) {
                        this.handRank = rankState.Straight;
                        this.RANKDATA[4] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean isAStraightFlush() {
        if (this.straightFlushSize == 5 && this.isAStraight() && this.isAFlush()) {
            this.handRank = rankState.StraightFlush;
            this.RANKDATA[8] = true;
            return true;
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (this.isAStraightFlush()) {
            if (this.hand.getCards().get(this.hand.getCards().size() - 1).getValue().equals("Ace")) {
                this.handRank = rankState.RoyalFlush;
                this.RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }
    public void hasMultiples() {
        for (int i = this.hand.getValueData().length - 1; i >= 0; i--) {
            System.out.println(this.hand.getValueData()[i]);
            switch (this.hand.getValueData()[i]) {
                case 2 -> pairsList.add(Hand.VALUES[i]);
                case 3 -> tripsList.add(Hand.SUITS[i]);
                case 4 -> quadsValue = Hand.VALUES[i];
            }
        }
    }
}

enum rankState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}