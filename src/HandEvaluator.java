import java.util.ArrayList;
import java.util.Arrays;

public class HandEvaluator {
    private final boolean[] RANKDATA = new boolean[Global.STANDARDPOKERRANKS.length];
    private Player player;
    private Hand hand;
    private rankState handRank = rankState.HighCard;

    private ArrayList<String> pairsList;
    private ArrayList<String> tripsList;
    private ArrayList<String> fullHouseList;
    private String quadsValue = null;

    public HandEvaluator() {
    }
    public HandEvaluator(Player player, Hand hand) {
        this.player = player;
        this.hand = hand;
        Arrays.fill(RANKDATA, false);
        this.RANKDATA[0] = true;
        this.hand.sortHand();
        checkForWheel();

        this.pairsList = new ArrayList<>();
        this.tripsList = new ArrayList<>();
        this.fullHouseList = new ArrayList<>();
        hasMultiples();
        determineRank();
        System.out.println("Rank Data: " + Arrays.toString(this.RANKDATA));
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
    public ArrayList<String> getPairs() {
        return this.pairsList;
    }
    public ArrayList<String> getTrips() {
        return this.tripsList;
    }
    public String getQuadsValue() {
        return this.quadsValue;
    }
    public ArrayList<String> getFullHouse() {
        return this.fullHouseList;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setStraightFlushSize(int size) {
        Global.straightFlushSize = size;
    }

    private void checkForWheel() {
        int counter = 1;
        if (this.hand.getCards().get(this.hand.getCards().size() - 1).getValue().equals("Ace")) {
            for (int i = 0; i < Global.straightFlushSize - 1; i++) {
                if (this.hand.getCards().get(i).getValue().equals(Global.VALUES[i])) {
                    counter++;
                }
                else break;
            }
        }
        if (counter == Global.straightFlushSize) {
            adjustForWheel();
        }
    }
    private void adjustForWheel() {
        PlayingCard tempCard = this.hand.getCards().get(this.hand.getCards().size() - 1);
        this.hand.removeCard(this.hand.getCards().size() - 1);
        this.hand.addCard(0, tempCard);
    }

    public Boolean isAPair() {
        return this.pairsList.size() == 1;
    }
    public Boolean isATwoPair() {
        return this.pairsList.size() == 2;
    }
    public Boolean isATrips() {
        return !tripsList.isEmpty();
    }
    public Boolean isAStraight() {
        // This flow of logic is for game modes where a straight requires 5 consecutive values.
        if (Global.straightFlushSize == 5) {
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
                for (int i = indexPoint; i < (indexPoint + Global.straightFlushSize); i++) {
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
                    if (straightCount == Global.straightFlushSize) {
                        this.RANKDATA[4] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean isAFlush() {
        if (!this.pairsList.isEmpty() || !this.tripsList.isEmpty() || !(this.quadsValue == null)) {
            return false;
        }

        for (int i = 0; i < this.hand.getSuitData().length - 1; i++) {
            if (this.hand.getSuitData()[i] >= Global.straightFlushSize) {
                this.RANKDATA[5] = true;
                return true;
            }
        }
        return false;
    }
    public Boolean isAFullHouse() {
        return this.fullHouseList.size() >= 2;
    }
    public Boolean isAQuads() {
        return this.quadsValue != null;
    }
    public Boolean isAStraightFlush() {
        if (Global.straightFlushSize == 5 && this.isAStraight() && this.isAFlush()) {
            this.RANKDATA[8] = true;
            return true;
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (this.isAStraightFlush()) {
            if (this.hand.getCards().get(this.hand.getCards().size() - 1).getValue().equals("Ace") &&
                    this.hand.getCards().get(0).getValue().equals("10")) {
                this.RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }

    private void hasMultiples() {
        for (int i = this.hand.getValueData().length - 1; i >= 0; i--) {
            switch (this.hand.getValueData()[i]) {
                case 2:
                    if (this.pairsList.size() == 1) {
                        RANKDATA[1] = true;
                    }
                    else if (this.pairsList.size() == 2) {
                        RANKDATA[2] = true;
                    }
                    pairsList.add(Global.VALUES[i]);
                    break;
                case 3:
                    tripsList.add(Global.VALUES[i]);
                    RANKDATA[3] = true;
                    break;
                case 4:
                    quadsValue = Global.VALUES[i];
                    this.RANKDATA[7] = true;
                    break;
            }
        }
        if (this.tripsList.size() >= 1 && this.pairsList.size() >= 1) {
            this.fullHouseList.add(this.tripsList.get(0));
            this.fullHouseList.add(this.pairsList.get(0));
            this.RANKDATA[6] = true;
        }
    }
    private void determineRank() {
        if (this.isARoyalFlush()) {
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[9]);
        }
        else if (this.isAStraightFlush()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[8]);
        }
        else if (this.isAQuads()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[7]);
        }
        else if (this.isAFullHouse()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[6]);
        }
        else if (this.isAFlush()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[5]);
        }
        else if (this.isAStraight()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[4]);
        }
        else if (this.isATrips()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[3]);
        }
        else if (this.isATwoPair()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[2]);
        }
        else if (this.isAPair()){
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[1]);
        }
        else {
            this.handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[0]);
        }
    }
}

enum rankState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}