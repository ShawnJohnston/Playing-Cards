package group.playingcardsdemo;

import java.util.ArrayList;
import java.util.Arrays;

public class HandEvaluator {
    private final boolean[] RANKDATA = new boolean[Global.STANDARDPOKERRANKS.length];
    private Player player;
    private Hand rawHand;
    private Hand fiveCardHand = new Hand();
    private rankState handRank = rankState.None;

    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private ArrayList<String> fullHouseList = new ArrayList<>();
    private String quadsValue = null;
    private String straightValue = null;
    private int topOfStraight = -1;
    private String flushValue = null;

    public HandEvaluator() {
    }
    public HandEvaluator(Player player, Hand rawHand) {
        this.player = player;
        this.rawHand = rawHand;
        Arrays.fill(RANKDATA, false);
        RANKDATA[0] = true;
        rawHand.sortHand();
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
    public Hand getRawHand() {
        return rawHand;
    }
    public rankState getHandRank() {
        return handRank;
    }
    public boolean[] getRankData() {
        return RANKDATA;
    }
    public Hand getFiveCardHand() {
        return fiveCardHand;
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
    public String getStraightValue() {
        return straightValue;
    }
    public ArrayList<String> getFullHouse() {
        return fullHouseList;
    }

    public void setRawHand(Hand rawHand) {
        this.rawHand = rawHand;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setStraightFlushSize(int size) {
        Global.straightFlushSize = size;
    }

    private void checkForWheel() {
        int counter = 1;
        if (rawHand.getCards().get(rawHand.getCards().size() - 1).getValue().equals("Ace")) {
            for (int i = 0; i < Global.straightFlushSize - 1; i++) {
                if (rawHand.getCards().get(i).getValue().equals(Global.VALUES[i])) {
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
        PlayingCard tempCard = rawHand.getCards().get(rawHand.getCards().size() - 1);
        rawHand.removeCard(rawHand.getCards().size() - 1);
        rawHand.addCard(0, tempCard);
    }

    public Boolean isAPair() {
        if (pairsList.size() >= 1) {
            RANKDATA[1] = true;
        }
        return (pairsList.size() >= 1);
    }
    public Boolean isATwoPair() {
        RANKDATA[2] = true;
        return (pairsList.size() >= 2);
    }
    public Boolean isATrips() {
        RANKDATA[3] = true;
        return !tripsList.isEmpty();
    }
    //public Boolean isAStraight() {
    //    // This flow of logic is for game modes where a straight requires 5 consecutive values.
    //    if (Global.straightFlushSize == 5) {
    //        boolean handContainsFiveOrTen = false; // All 5 card straights contain either a "5" or "10". Otherwise, it's ruled out.
    //        if (pairsList.size() > 0 || tripsList.size() > 0 || !(quadsValue == null)) {
    //            return false;
    //        }
    //        for (PlayingCard card: hand.getCards()) {
    //            if (card.getValue().equals("5") || card.getValue().equals("10")) {
    //                handContainsFiveOrTen = true; // The hand contains "5" or "10". Evaluation of a straight can proceed.
    //                break;
    //            }
    //        }
    //        if (!handContainsFiveOrTen) {
    //            return false; // The hand does not contain a "5" or "10". A straight is not possible.
    //        }
    //        else {
    //            // The hand will be sorted by value. Then, each index of the hand will be compared to an array of values.
    //            // When the value of the first card in the hand matches an index value, a loop will be used to count value sequences.
    //            String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Reference array.
    //            int indexPoint = 0; // This integer is used to match the first card in the hand to the corresponding index of the values array.
//
    //            // This loop is used to match the first card in the hand with the corresponding value's index in the values array.
    //            // When the match occurs, the values index is stored in indexPoint, which will be used to establish where
    //            // counting begins in the next step of the process.
//
    //            for (int i = 0; i < values.length; i++) {
    //                if (hand.getCards().get(0).getValue().equals(values[i])) {
    //                    // If the first card in the hand has a value that matches the values array at index i, indexPoint
    //                    // will record the value of i.
    //                    indexPoint = i;
    //                    break;
    //                }
    //            }
//
    //            int straightCount = 0; // Used to count value sequence.
    //            int handIndex = 0; // Counter used to access the playerHand index.
    //            // This loop starts where the first card in the hand matches with the corresponding value index in the values array.
    //            // The loop spans up to (exclusively) the indexPoint value + the integer defining the size of a straight.
    //            for (int i = indexPoint; i < (indexPoint + Global.straightFlushSize); i++) {
    //                // This if-statement will compare the string value at current playerHand index to the values array at index i.
    //                if (hand.getCards().get(handIndex).getValue().equals(values[i])) {
    //                    // The string value at this playerHand index matches the string value at the values array index i.
    //                    straightCount++;
    //                }
    //                else {
    //                    straightCount = 0;
    //                }
    //                handIndex++;
    //                if (straightCount == Global.straightFlushSize) {
    //                    RANKDATA[4] = true;
    //                    return true;
    //                }
    //            }
    //        }
    //    }
    //    return false;
    //}
    public Boolean isAStraight_ByValueData() {
        int counter = 0;
        
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = Global.VALUESHIERARCHY.length - 1; i > 0 ; i--) {
            if (counter >= 5) {
                break;
            }
            if (rawHand.getValueData()[i] > 0 && rawHand.getValueData()[i - 1] > 0) {
                counter++;
                indices.add(i);
            }
            else {
                counter = 1;
                indices.clear();
                indices.add(i);

            }
        }
        if (indices.size() == 5) {
            for (Integer index: indices) {
                for (int i = rawHand.getSize() - 1; i >= 0 ; i--) {
                    if (rawHand.getCards().get(i).getValue().equals(Global.VALUESHIERARCHY[index])) {
                        fiveCardHand.addCard(rawHand.getCards().get(i));
                        break;
                    }
                }
            }
            RANKDATA[4] = true;
            return true;
        }
        //else if (rawHand.getCards().get(0).getValue().equals("Ace") &&
        //        rawHand.getCards().get(1).getValue().equals("2") &&
        //        rawHand.getCards().get(2).getValue().equals("3") &&
        //        rawHand.getCards().get(3).getValue().equals("4") &&
        //        rawHand.getCards().get(4).getValue().equals("5")) {
        //    fiveCardHand.addCard(rawHand.getCards().get(0));
        //    fiveCardHand.addCard(rawHand.getCards().get(1));
        //    fiveCardHand.addCard(rawHand.getCards().get(2));
        //    fiveCardHand.addCard(rawHand.getCards().get(3));
        //    fiveCardHand.addCard(rawHand.getCards().get(4));
//
        //    RANKDATA[4] = true;
        //    straightValue = "5";
        //    return true;
        //}
        return false;
    }
    public Boolean isAFlush() {
        for (int i = 0; i < rawHand.getSuitData().length - 1; i++) {
            if (rawHand.getSuitData()[i] >= Global.straightFlushSize) {
                RANKDATA[5] = true;
                flushValue = Global.SUITS[i];

                return true;
            }
        }
        return false;
    }
    public Boolean isAFullHouse() {
        if (fullHouseList.size() >= 2) {
            RANKDATA[6] = true;
        }
        return fullHouseList.size() >= 2;
    }
    public Boolean isAQuads() {
        RANKDATA[7] = true;
        return quadsValue != null;
    }
    public Boolean isAStraightFlush() {
        if (Global.straightFlushSize == 5 && isAFlush()) {
            for (int i = rawHand.getSize() - 1; i >= 0 ; i--) {
                if (rawHand.getCards().get(i).getSuit().equals(flushValue)) {
                    fiveCardHand.addCard(rawHand.getCards().get(i));
                }
            }
            HandEvaluator evaluator = new HandEvaluator(player, fiveCardHand);
            if (evaluator.isAStraight_ByValueData()) {
                RANKDATA[8] = true;
                return true;
            }
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (isAStraightFlush()) {
            if (fiveCardHand.getCards().get(rawHand.getCards().size() - 1).getValue().equals("Ace")) {
                RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }

    private void printHand(Hand hand) {
        for (int i = 0; i < hand.getCards().size(); i++) {
            System.out.println(hand.getCards().get(i).getName());
        }
        System.out.println();
    }
    private void moveCardFromRawHandToFiveCardHand(String value) {
        for (int i = rawHand.getSize() - 1; i  >= 0; i--) {
            if (rawHand.getCards().get(i).getValue().equals(value)) {
                fiveCardHand.addCard(rawHand.getCards().get(i));
                rawHand.removeCard(i);
            }
        }
    }
    private void moveKickersFromRawHandToFiveCardHand() {
        if (fiveCardHand.getSize() < 5) {
            for (int i = rawHand.getSize() - 1; i >= 0; i--) {
                fiveCardHand.addCard(rawHand.getCards().get(i));
                rawHand.removeCard(i);
                if (fiveCardHand.getSize() == 5) {
                    break;
                }
            }
        }
    }
    private void hasMultiples() {
        for (int i = rawHand.getValueData().length - 1; i >= 0; i--) {
            switch (rawHand.getValueData()[i]) {
                case 2 -> {
                    if (pairsList.size() >= 1) {
                        RANKDATA[1] = true;
                    } else if (pairsList.size() >= 2) {
                        RANKDATA[2] = true;
                    }
                    pairsList.add(Global.VALUES[i]);
                }
                case 3 -> {
                    tripsList.add(Global.VALUES[i]);
                    RANKDATA[3] = true;
                }
                case 4 -> {
                    quadsValue = Global.VALUES[i];
                    RANKDATA[7] = true;
                }
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
            moveCardFromRawHandToFiveCardHand(quadsValue);
            moveKickersFromRawHandToFiveCardHand();

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[7]);
        }
        else if (isAFullHouse()){
            moveCardFromRawHandToFiveCardHand(fullHouseList.get(0));
            moveCardFromRawHandToFiveCardHand(fullHouseList.get(1));

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[6]);
        }
        else if (isAFlush()){
            for (int i = Global.VALUESHIERARCHY.length - 1; i >= 0 ; i--) {
                if (rawHand.getCards().get(i).getSuit().equals(flushValue)) {
                    fiveCardHand.addCard(rawHand.getCards().get(i));
                    if (fiveCardHand.getSize() == 5) {
                        break;
                    }
                }
            }

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[5]);
        }
        else if (isAStraight_ByValueData()){

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[4]);
        }
        else if (isATrips()){
            if (tripsList.size() >= 1) {
                moveCardFromRawHandToFiveCardHand(tripsList.get(0));
                while (fiveCardHand.getSize() < 5) {
                    moveKickersFromRawHandToFiveCardHand();
                }
            }
            
            

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[3]);
        }
        else if (isATwoPair()){
            if (pairsList.size() >= 2) {
                moveCardFromRawHandToFiveCardHand(pairsList.get(0));
                moveCardFromRawHandToFiveCardHand(pairsList.get(1));

                fiveCardHand.addCard(rawHand.getCards().get(rawHand.getSize() - 1));
            }

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[2]);
        }
        else if (isAPair()){
            moveCardFromRawHandToFiveCardHand(pairsList.get(0));
            while (fiveCardHand.getSize() < 5) {
                moveKickersFromRawHandToFiveCardHand();
            }

            
            

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[1]);
        }
        else {
            moveKickersFromRawHandToFiveCardHand();

            handRank = rankState.valueOf(Global.STANDARDPOKERRANKS[0]);

        }
    }
}

enum rankState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}