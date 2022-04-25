 package group.playingcardsdemo;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor

public class HandEvaluator {
    public static final String[] STANDARDPOKERRANKS = {"HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    public static int straightFlushSize = 5;
    private final boolean[] RANKDATA = new boolean[STANDARDPOKERRANKS.length];
    private Player player;
    private Hand fullHand;
    private Hand fiveCardHand = new Hand();
    private rankState handRank = rankState.None;
    protected static HashMap<String, Integer> pokerRanks = new HashMap<>();

    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private ArrayList<String> fullHouseList = new ArrayList<>();
    private String quadsValue = null;
    private String straightValue = null;
    private int topOfStraight = -1;
    private String flushValue = null;

    public HandEvaluator(Hand fullHand) {
        this.fullHand = fullHand;
        Arrays.fill(RANKDATA, false);
        RANKDATA[0] = true;
        fullHand.sortHandByValue();
        checkForWheel();

        pairsList = new ArrayList<>();
        tripsList = new ArrayList<>();
        fullHouseList = new ArrayList<>();
        hasMultiples();
        determineRank();
    }

    private void checkForWheel() {
        /*
            This method will read the card values in the hand to see if it contains "the wheel", which means a 5-high
            straight (Ace-2-3-4-5). If so, it will override the conventional procedures or recognizing the straight,
            since the Ace is read as high by default.

            1.  Initialize a counter that starts at 1.
            2.  Conditional: If the hand is large enough to make a straight (5 cards in most variations) and the hand
                contains an Ace, it will run a loop to check for the other cards.
            3.  For Loop: From 0 <= i < 5, 'i' will plug into the static PlayingCard class 'VALUES' array, which will be
                used to determine if the value at that index exists in the hand.
            4.  If/Else: If the hand contains the value queried, the counter will increment, else the loop breaks;
            5.  If the counter equals the straight length requirement, adjustForWheel() will override the hand.
         */

        int counter = 1;
        if (fullHand.getSize() >= straightFlushSize && fullHand.containsCardValue("Ace")) {
            for (int i = 0; i < fullHand.getSize() - 1; i++) {
                if (fullHand.containsCardValue(PlayingCard.VALUES[i])) {
                    counter++;
                }
                else break;
            }
        }
        if (counter == straightFlushSize) {
            adjustForWheel();
        }
    }
    private void adjustForWheel() {
        /*
            This method is for when the hand is determined to be the "wheel" straight. It will rearrange the hand so
            that the Ace is in front.

            1.  Create temporary variable of the Ace card.
            2.  Delete the Ace card from the hand.
            3.  Insert the temporary Ace at index 0.
         */

        PlayingCard tempCard = fullHand.getCards().get(fullHand.getCards().size() - 1);
        fullHand.removeCard(fullHand.getCards().size() - 1);
        fullHand.addCard(0, tempCard);
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
    //    if (straightFlushSize == 5) {
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
    //            for (int i = indexPoint; i < (indexPoint + straightFlushSize); i++) {
    //                // This if-statement will compare the string value at current playerHand index to the values array at index i.
    //                if (hand.getCards().get(handIndex).getValue().equals(values[i])) {
    //                    // The string value at this playerHand index matches the string value at the values array index i.
    //                    straightCount++;
    //                }
    //                else {
    //                    straightCount = 0;
    //                }
    //                handIndex++;
    //                if (straightCount == straightFlushSize) {
    //                    RANKDATA[4] = true;
    //                    return true;
    //                }
    //            }
    //        }
    //    }
    //    return false;
    //}
    public Boolean isAStraight() {
        int counter = 0;
        
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = PlayingCard.VALUES_INDEX.length - 2; i > 0 ; i--) {
            if (counter >= 5) {
                break;
            }
            if (fullHand.getValueData()[i] > 0 && fullHand.getValueData()[i - 1] > 0) {
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
                for (int i = fullHand.getSize() - 1; i >= 0 ; i--) {
                    if (fullHand.getCards().get(i).getValue().equals(PlayingCard.VALUES_INDEX[index])) {
                        fiveCardHand.addCard(fullHand.getCards().get(i));
                        break;
                    }
                }
            }
            RANKDATA[4] = true;
            straightValue = fiveCardHand.getCards().get(0).getValue();
            return true;
        }
        else if (fullHand.getSize() >= 5 && fullHand.getCards().get(0).getValue().equals("Ace") &&
                fullHand.getCards().get(1).getValue().equals("2") &&
                fullHand.getCards().get(2).getValue().equals("3") &&
                fullHand.getCards().get(3).getValue().equals("4") &&
                fullHand.getCards().get(4).getValue().equals("5")) {
            fiveCardHand.addCard(fullHand.getCards().get(0));
            fiveCardHand.addCard(fullHand.getCards().get(1));
            fiveCardHand.addCard(fullHand.getCards().get(2));
            fiveCardHand.addCard(fullHand.getCards().get(3));
            fiveCardHand.addCard(fullHand.getCards().get(4));

            RANKDATA[4] = true;
            straightValue = "5";
            return true;
        }
        return false;
    }
    public Boolean isAFlush() {
        for (int i = 0; i < fullHand.getSuitData().length - 1; i++) {
            if (fullHand.getSuitData()[i] >= 5) {
                RANKDATA[5] = true;
                flushValue = PlayingCard.SUITS[i];

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
        if (this.isAFlush()) {
            for (int j = fullHand.getSize() - 1; j >= 0; j--) {
                if (fullHand.getCards().get(j).getSuit().equals(flushValue)) {
                    fiveCardHand.addCard(fullHand.getCards().get(j));
                }
            }
            int counter = 1;
            int straightFlushTop = 0;
            for (int i = fiveCardHand.getValueData().length - 1; i > 0 ; i--) {
                if (fiveCardHand.getValueData()[i] == 1 &&fiveCardHand.getValueData()[i - 1] == 1) {
                    straightFlushTop = i;
                    counter++;
                }
                else {
                    counter = 1;
                }
                if (counter == 5) {
                    RANKDATA[8] = true;
                    counter = 0;
                    Hand tempHand = new Hand();
                    for (int j = fiveCardHand.getSize() - 1; j >= 0 ; j--) {
                        if (fiveCardHand.getCards().get(j).getValue().equals(PlayingCard.VALUES_INDEX[straightFlushTop - counter])) {
                            tempHand.addCard(fiveCardHand.getCards().get(j));
                        }
                        if (tempHand.getSize() >= 5) {
                            fiveCardHand = new Hand();
                            for (int k = 0; k < 5; k++) {
                                fiveCardHand.addCard(tempHand.getCards().get(k));
                            }
                            break;
                        }
                    }
                    return true;
                }
            }
        }
        //if (straightFlushSize == 5 && isAFlush()) {
        //    for (int i = rawHand.getSize() - 1; i >= 0 ; i--) {
        //        if (rawHand.getCards().get(i).getSuit().equals(flushValue)) {
        //            fiveCardHand.addCard(rawHand.getCards().get(i));
        //        }
        //    }
        //    HandEvaluator evaluator = new HandEvaluator(player, fiveCardHand);
        //    if (evaluator.isAStraight_ByValueData()) {
        //        RANKDATA[8] = true;
        //        return true;
        //    }
        //}
        return false;
    }
    public Boolean isARoyalFlush() {
        if (isAStraightFlush()) {
            if (fiveCardHand.getCards().get(0).getValue().equals("Ace")) {
                RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }

    private void moveCardFromFullHandToFiveCardHand(String value) {
        for (int i = fullHand.getSize() - 1; i  >= 0; i--) {
            if (fullHand.getCards().get(i).getValue().equals(value)) {
                fiveCardHand.addCard(fullHand.getCards().get(i));
                fullHand.removeCard(i);
            }
        }
    }
    private void moveKickersFromFullHandToFiveCardHand() {
        if (fiveCardHand.getSize() < 5) {
            for (int i = fullHand.getSize() - 1; i >= 0; i--) {
                fiveCardHand.addCard(fullHand.getCards().get(i));
                fullHand.removeCard(i);
                if (fiveCardHand.getSize() == 5) {
                    break;
                }
            }
        }
    }
    private void hasMultiples() {
        /*
            This method will read the number of times each card value appears in the hand and make setting changes
            for any existing pairs, trips, and/or full houses.

            1.  For Loop: Descending loop from (value data length - 1) >= i >= 0.
                - If the count at that index is 2, the card value will be added to the 'pairsList' list.
                - If 3, the card value will be added to the 'tripsList' list.
                - If 4, the 'quadsValue' variable will have the card value assigned to it.
                - In any case, the 'RANKDATA' array will update for all applicable results.
            2.  If there is 2+ pairs in 'pairsList', 'RANKDATA' will set true for Two Pairs.
            3.  If both 'pairsList' and 'tripsList' contain at least one entry, the first items in each list will be
                added to 'fullHouseList', and 'RANKDATA' will set true for Full House.
         */

        for (int i = fullHand.getValueData().length - 1; i >= 0; i--) {
            switch (fullHand.getValueData()[i]) {
                case 2 -> {
                    if (pairsList.size() >= 1) {
                        RANKDATA[1] = true;
                    }
                    pairsList.add(PlayingCard.VALUES[i]);
                }
                case 3 -> {
                    tripsList.add(PlayingCard.VALUES[i]);
                    RANKDATA[3] = true;
                }
                case 4 -> {
                    quadsValue = PlayingCard.VALUES[i];
                    RANKDATA[7] = true;
                }
            }
        }
        if (pairsList.size() >= 2) {
            RANKDATA[2] = true;
        }
        if (tripsList.size() >= 1 && pairsList.size() >= 1) {
            fullHouseList.add(tripsList.get(0));
            fullHouseList.add(pairsList.get(0));
            RANKDATA[6] = true;
        }
    }
    private void determineRank() {
        if (isARoyalFlush()) {
            handRank = rankState.valueOf(STANDARDPOKERRANKS[9]);
        }
        else if (isAStraightFlush()){
            handRank = rankState.valueOf(STANDARDPOKERRANKS[8]);
        }
        else if (isAQuads()){
            moveCardFromFullHandToFiveCardHand(quadsValue);
            moveKickersFromFullHandToFiveCardHand();

            handRank = rankState.valueOf(STANDARDPOKERRANKS[7]);
        }
        else if (isAFullHouse()){
            moveCardFromFullHandToFiveCardHand(fullHouseList.get(0));
            moveCardFromFullHandToFiveCardHand(fullHouseList.get(1));

            handRank = rankState.valueOf(STANDARDPOKERRANKS[6]);
        }
        else if (isAFlush()){
            for (int i = PlayingCard.VALUES_INDEX.length - 1; i >= 0 ; i--) {
                for (int j = fullHand.getSize() - 1; j >= 0; j--) {
                    if (fullHand.getCards().get(j).getSuit().equals(flushValue)) {
                        fiveCardHand.addCard(fullHand.getCards().get(j));
                    }
                    if (fiveCardHand.getSize() == 5) {
                        break;
                    }
                }
                if (fiveCardHand.getSize() == 5) {
                    break;
                }
            }

            handRank = rankState.valueOf(STANDARDPOKERRANKS[5]);
        }
        else if (isAStraight()){

            handRank = rankState.valueOf(STANDARDPOKERRANKS[4]);
        }
        else if (isATrips()){
            if (tripsList.size() >= 1) {
                moveCardFromFullHandToFiveCardHand(tripsList.get(0));
                while (fiveCardHand.getSize() < 5) {
                    moveKickersFromFullHandToFiveCardHand();
                }
            }
            
            

            handRank = rankState.valueOf(STANDARDPOKERRANKS[3]);
        }
        else if (isATwoPair()){
            if (pairsList.size() >= 2) {
                moveCardFromFullHandToFiveCardHand(pairsList.get(0));
                moveCardFromFullHandToFiveCardHand(pairsList.get(1));

                moveKickersFromFullHandToFiveCardHand();
            }

            handRank = rankState.valueOf(STANDARDPOKERRANKS[2]);
        }
        else if (isAPair()){
            moveCardFromFullHandToFiveCardHand(pairsList.get(0));
            while (fiveCardHand.getSize() < 5) {
                moveKickersFromFullHandToFiveCardHand();
            }
            handRank = rankState.valueOf(STANDARDPOKERRANKS[1]);
        }
        else {
            moveKickersFromFullHandToFiveCardHand();

            handRank = rankState.valueOf(STANDARDPOKERRANKS[0]);

        }
        deleteExcessCards();
    }
    public void deleteExcessCards() {
        if (fiveCardHand.getSize() > 5) {
            for (int i = 5; i < fiveCardHand.getSize(); i++) {
                fiveCardHand.removeCard(i);
            }
        }
    }
    public static void initializePokerRanks() {
        HandEvaluator.pokerRanks.put("HighCard", 0);
        HandEvaluator.pokerRanks.put("Pair", 1);
        HandEvaluator.pokerRanks.put("TwoPair", 2);
        HandEvaluator.pokerRanks.put("Trips", 3);
        HandEvaluator.pokerRanks.put("Straight", 4);
        HandEvaluator.pokerRanks.put("Flush", 5);
        HandEvaluator.pokerRanks.put("FullHouse", 6);
        HandEvaluator.pokerRanks.put("Quads", 8);
        HandEvaluator.pokerRanks.put("StraightFlush", 9);
        HandEvaluator.pokerRanks.put("RoyalFlush", 10);
    }
}

enum rankState {
    None, HighCard, Pair, TwoPair, Trips, Straight, Flush, FullHouse, Quads, StraightFlush, RoyalFlush
}