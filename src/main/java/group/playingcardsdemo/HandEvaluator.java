package group.playingcardsdemo;

import java.util.ArrayList;
import java.util.Arrays;

public class HandEvaluator {
    private final boolean[] RANKDATA = new boolean[Global.STANDARDPOKERRANKS.length];
    private Player player;
    private Hand hand;
    private rankState handRank = rankState.None;

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
        RANKDATA[0] = true;
        hand.sortHand();
        checkForWheel();

        pairsList = new ArrayList<>();
        tripsList = new ArrayList<>();
        fullHouseList = new ArrayList<>();
        hasMultiples();
        determineRank();
    }

    public Player getPlayer() {
        return player;
    }
    public Hand getHand() {
        return hand;
    }
    public rankState getHandRank() {
        return handRank;
    }
    public boolean[] getRankData() {
        return RANKDATA;
    }
    public ArrayList<String> getPairs() {
        return pairsList;
    }
    public ArrayList<String> getTrips() {
        return tripsList;
    }
    public String getQuadsValue() {
        return quadsValue;
    }
    public ArrayList<String> getFullHouse() {
        return fullHouseList;
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
        if (hand.getCards().get(hand.getCards().size() - 1).getValue().equals("Ace")) {
            for (int i = 0; i < Global.straightFlushSize - 1; i++) {
                if (hand.getCards().get(i).getValue().equals(Global.VALUES[i])) {
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
        PlayingCard tempCard = hand.getCards().get(hand.getCards().size() - 1);
        hand.removeCard(hand.getCards().size() - 1);
        hand.addCard(0, tempCard);
    }

    public Boolean isAPair() {
        return pairsList.size() == 1;
    }
    public Boolean isATwoPair() {
        return pairsList.size() == 2;
    }
    public Boolean isATrips() {
        return !tripsList.isEmpty();
    }
    public Boolean isAStraight() {
        // This flow of logic is for game modes where a straight requires 5 consecutive values.
        if (Global.straightFlushSize == 5) {
            boolean handContainsFiveOrTen = false; // All 5 card straights contain either a "5" or "10". Otherwise, it's ruled out.
            if (pairsList.size() > 0 || tripsList.size() > 0 || !(quadsValue == null)) {
                return false;
            }
            for (PlayingCard card: hand.getCards()) {
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
                    if (hand.getCards().get(0).getValue().equals(values[i])) {
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
                    if (hand.getCards().get(handIndex).getValue().equals(values[i])) {
                        // The string value at this playerHand index matches the string value at the values array index i.
                        straightCount++;
                    }
                    else {
                        straightCount = 0;
                    }
                    handIndex++;
                    if (straightCount == Global.straightFlushSize) {
                        RANKDATA[4] = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Boolean isAFlush() {
        for (int i = 0; i < hand.getSuitData().length - 1; i++) {
            if (hand.getSuitData()[i] >= Global.straightFlushSize) {
                RANKDATA[5] = true;
                return true;
            }
        }
        return false;
    }
    public Boolean isAFullHouse() {
        return fullHouseList.size() >= 2;
    }
    public Boolean isAQuads() {
        return quadsValue != null;
    }
    public Boolean isAStraightFlush() {
        if (Global.straightFlushSize == 5 && isAStraight() && isAFlush()) {
            RANKDATA[8] = true;
            return true;
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (isAStraightFlush()) {
            if (hand.getCards().get(hand.getCards().size() - 1).getValue().equals("Ace") &&
                    hand.getCards().get(0).getValue().equals("10")) {
                RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }

    private void hasMultiples() {
        for (int i = hand.getValueData().length - 1; i >= 0; i--) {
            switch (hand.getValueData()[i]) {
                case 2:
                    if (pairsList.size() == 1) {
                        RANKDATA[1] = true;
                    }
                    else if (pairsList.size() == 2) {
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
                    RANKDATA[7] = true;
                    break;
            }
        }
        if (tripsList.size() >= 1 && pairsList.size() >= 1) {
            fullHouseList.add(tripsList.get(0));
            fullHouseList.add(pairsList.get(0));
            RANKDATA[6] = true;
        }
    }
    private void determineRank() {
        if (isARoyalFlush()) {
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[9]);
        }
        else if (isAStraightFlush()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[8]);
        }
        else if (isAQuads()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[7]);
        }
        else if (isAFullHouse()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[6]);
        }
        else if (isAFlush()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[5]);
        }
        else if (isAStraight()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[4]);
        }
        else if (isATrips()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[3]);
        }
        else if (isATwoPair()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[2]);
        }
        else if (isAPair()){
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[1]);
        }
        else {
            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[0]);
        }
    }
}

enum rankState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}