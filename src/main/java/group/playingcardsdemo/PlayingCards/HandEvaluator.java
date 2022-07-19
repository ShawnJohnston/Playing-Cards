 package group.playingcardsdemo.PlayingCards;


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
    public static final String[] STANDARDPOKERRANKS = {
            "HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    public static short straightFlushSize = 5;
    private final boolean[] RANKDATA = new boolean[STANDARDPOKERRANKS.length];
    private Player player;
    private Hand inputHand;
    private Hand gameFittedHand = new Hand();
    private short gameHandCapacity;
    private rankState handRank = rankState.None;
    public static HashMap<String, Integer> pokerRanks = new HashMap<>();

    private ArrayList<String> pairsList = new ArrayList<>();
    private ArrayList<String> tripsList = new ArrayList<>();
    private ArrayList<String> fullHouseList = new ArrayList<>();
    private String quadsValue = null;
    private String straightValue = null;
    private short topOfStraight = -1;
    private String flushValue = null;

    public HandEvaluator(Hand inputHand) {
        this.inputHand = inputHand;
        gameHandCapacity = 5;
        constructorHelper();
    }
    public HandEvaluator(Hand inputHand, short gameHandCapacity) {
        this.inputHand = inputHand;
        this.gameHandCapacity = gameHandCapacity;
        constructorHelper();
    }
    private void constructorHelper() {
        Arrays.fill(RANKDATA, false);
        RANKDATA[0] = true;
        inputHand.sortHandByValue();
        checkForWheel();

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
        if (inputHand.getSize() >= straightFlushSize && inputHand.containsCardValue("Ace")) {
            for (int i = 0; i < inputHand.getSize() - 1; i++) {
                if (inputHand.containsCardValue(Values.VALUES[i])) {
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

        PlayingCard tempCard = inputHand.getCards().get(inputHand.getCards().size() - 1);
        inputHand.removeCard(inputHand.getCards().size() - 1);
        inputHand.addCard(0, tempCard);
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
    public Boolean isAStraight() {
        int counter = 0;

        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = Values.VALUES_INDEX.length - 2; i > 0 ; i--) {
            if (counter >= gameHandCapacity) {
                break;
            }
            if (inputHand.getValueData()[i] > 0 && inputHand.getValueData()[i - 1] > 0) {
                counter++;
                indices.add(i);
            }
            else {
                counter = 1;
                indices.clear();
                indices.add(i);

            }
        }
        if (indices.size() == gameHandCapacity) {
            for (Integer index: indices) {
                for (int i = inputHand.getSize() - 1; i >= 0 ; i--) {
                    if (inputHand.getCards().get(i).getValue().equals(Values.VALUES_INDEX[index])) {
                        gameFittedHand.addCard(inputHand.getCards().get(i));
                        break;
                    }
                }
            }
            RANKDATA[4] = true;
            straightValue = gameFittedHand.getCards().get(0).getValue();
            return true;
        }
        else if (inputHand.getSize() >= gameHandCapacity && inputHand.getCards().get(0).getValue().equals("Ace") &&
                inputHand.getCards().get(1).getValue().equals("2") &&
                inputHand.getCards().get(2).getValue().equals("3") &&
                inputHand.getCards().get(3).getValue().equals("4") &&
                inputHand.getCards().get(4).getValue().equals("5")) {
            gameFittedHand.addCard(inputHand.getCards().get(0));
            gameFittedHand.addCard(inputHand.getCards().get(1));
            gameFittedHand.addCard(inputHand.getCards().get(2));
            gameFittedHand.addCard(inputHand.getCards().get(3));
            gameFittedHand.addCard(inputHand.getCards().get(4));

            RANKDATA[4] = true;
            straightValue = String.valueOf(gameHandCapacity);
            return true;
        }
        return false;
    }
    public Boolean isAFlush() {
        for (int i = 0; i < inputHand.getSuitData().length - 1; i++) {
            if (inputHand.getSuitData()[i] >= gameHandCapacity) {
                RANKDATA[5] = true;
                flushValue = Suits.SUITS[i];

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
            Hand flushHand = new Hand();
            for (PlayingCard card: inputHand.getCards()) {
                if (card.getSuit().equals(flushValue)) {
                    flushHand.addCard(card);
                }
            }
            int counter = 0;

            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = Values.VALUES.length - 2; i > 0 ; i--) {
                if (counter >= 5) {
                    break;
                }
                if (flushHand.getValueData()[i] > 0 && flushHand.getValueData()[i - 1] > 0) {
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
                    for (int i = flushHand.getSize() - 1; i >= 0 ; i--) {
                        if (flushHand.getCards().get(i).getValue().equals(Values.VALUES_INDEX[index])) {
                            gameFittedHand.addCard(flushHand.getCards().get(i));
                            break;
                        }
                    }
                }
                RANKDATA[4] = true;
                straightValue = gameFittedHand.getCards().get(0).getValue();
                return true;
            }
            else if (flushHand.getSize() >= gameHandCapacity && flushHand.getCards().get(0).getValue().equals("Ace")) {
                Boolean isStraightFlush = recursiveStraightCheck(flushHand, gameHandCapacity);

                if (isStraightFlush) {
                    for (int i = 0; i < gameHandCapacity; i++) {
                        gameFittedHand.addCard(flushHand.getCards().get(i));
                    }
                    RANKDATA[8] = true;
                    straightValue = gameFittedHand.getCards().get(gameFittedHand.getSize() - 1).getValue();
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean isARoyalFlush() {
        if (isAStraightFlush()) {
            if (gameFittedHand.containsCardValue("Ace") && !gameFittedHand.containsCardValue("2")) {
                RANKDATA[9] = true;
                return true;
            }
        }
        return false;
    }

    private Boolean recursiveStraightCheck(Hand hand, int i) {
        if (i == 1) {
            return hand.getCards().get(0).getValue().equals(Values.VALUES_INDEX[i - 1]);
        }
        if (hand.getCards().get(i - 2).getValue().equals(Values.VALUES_INDEX[i - 2])) {
            return recursiveStraightCheck(hand, i - 1);
        }
        return false;
    }

    private void moveCardFromInputHandToGameFittedHand(String value) {
        /*
            This method will search the input hand for a certain card value. For each playing card with that value, it
            will be added to the game-fitted hand and removed from the input hand.

            1.  For Loop:   Range (input hand size - 1) >= i >= 0.
                a.  if the card at index 'i' in inputHand is matches the queried value:
                    -   Add it to the 5-card hand.
                    -   Remove it from the input hand.
         */
        for (int i = inputHand.getSize() - 1; i  >= 0; i--) {
            if (inputHand.getCards().get(i).getValue().equals(value)) {
                gameFittedHand.addCard(inputHand.getCards().get(i));
                inputHand.removeCard(i);
            }
        }
    }
    private void moveKickersFromInputHandToGameFittedHand() {
        /*
            This method will fill the gameFittedHand with the highest value cards until it reaches capacity.

            1.  If the game-fitted hand has less than its capacity:
                a.  For Loop: Range (input hand size - 1) >= 'i' >= 0.
                b.  Add the card at 'inputHand' index i to gameFittedHand.
                c.  Remove the card at index 'i' from 'inputHand'.
                d.  If the game-fitted hand reaches capacity, break the loop.
         */
        if (gameFittedHand.getSize() < gameHandCapacity) {
            for (int i = inputHand.getSize() - 1; i >= 0; i--) {
                gameFittedHand.addCard(inputHand.getCards().get(i));
                inputHand.removeCard(i);
                if (gameFittedHand.getSize() == gameHandCapacity) {
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
                -   If the count at that index is 2, the card value will be added to the 'pairsList' list.
                -   If 3, the card value will be added to the 'tripsList' list.
                -   If 4, the 'quadsValue' variable will have the card value assigned to it.
                -   In any case, the 'RANKDATA' array will update for all applicable results.
            2.  If there is 2+ pairs in 'pairsList', 'RANKDATA' will set true for Two Pairs.
            3.  If both 'pairsList' and 'tripsList' contain at least one entry, the first items in each list will be
                added to 'fullHouseList', and 'RANKDATA' will set true for Full House.
         */

        for (int i = inputHand.getValueData().length - 1; i >= 0; i--) {
            switch (inputHand.getValueData()[i]) {
                case 2 -> {
                    if (pairsList.size() >= 1) {
                        RANKDATA[1] = true;
                    }
                    pairsList.add(Values.VALUES[i]);
                }
                case 3 -> {
                    tripsList.add(Values.VALUES[i]);
                    RANKDATA[3] = true;
                }
                case 4 -> {
                    quadsValue = Values.VALUES[i];
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
        /*
            This method executes each rank query in descending order.
         */

        if (isARoyalFlush()) {
            handRank = rankState.valueOf(STANDARDPOKERRANKS[9]);
        }
        else if (isAStraightFlush()){
            handRank = rankState.valueOf(STANDARDPOKERRANKS[8]);
        }
        else if (isAQuads()){
            moveCardFromInputHandToGameFittedHand(quadsValue);
            moveKickersFromInputHandToGameFittedHand();

            handRank = rankState.valueOf(STANDARDPOKERRANKS[7]);
        }
        else if (isAFullHouse()){
            moveCardFromInputHandToGameFittedHand(fullHouseList.get(0));
            moveCardFromInputHandToGameFittedHand(fullHouseList.get(1));

            handRank = rankState.valueOf(STANDARDPOKERRANKS[6]);
        }
        else if (isAFlush()){
            for (int i = Values.VALUES_INDEX.length - 1; i >= 0 ; i--) {
                for (int j = inputHand.getSize() - 1; j >= 0; j--) {
                    if (inputHand.getCards().get(j).getSuit().equals(flushValue)) {
                        gameFittedHand.addCard(inputHand.getCards().get(j));
                    }
                    if (gameFittedHand.getSize() == gameHandCapacity) {
                        break;
                    }
                }
                if (gameFittedHand.getSize() == gameHandCapacity) {
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
                moveCardFromInputHandToGameFittedHand(tripsList.get(0));
                while (gameFittedHand.getSize() < gameHandCapacity) {
                    moveKickersFromInputHandToGameFittedHand();
                }
            }



            handRank = rankState.valueOf(STANDARDPOKERRANKS[3]);
        }
        else if (isATwoPair()){
            if (pairsList.size() >= 2) {
                moveCardFromInputHandToGameFittedHand(pairsList.get(0));
                moveCardFromInputHandToGameFittedHand(pairsList.get(1));

                moveKickersFromInputHandToGameFittedHand();
            }

            handRank = rankState.valueOf(STANDARDPOKERRANKS[2]);
        }
        else if (isAPair()){
            moveCardFromInputHandToGameFittedHand(pairsList.get(0));
            while (gameFittedHand.getSize() < gameHandCapacity) {
                moveKickersFromInputHandToGameFittedHand();
            }
            handRank = rankState.valueOf(STANDARDPOKERRANKS[1]);
        }
        else {
            moveKickersFromInputHandToGameFittedHand();

            handRank = rankState.valueOf(STANDARDPOKERRANKS[0]);

        }
        deleteExcessCards();
    }
    public void deleteExcessCards() {
        if (gameFittedHand.getSize() > gameHandCapacity) {
            for (int i = gameHandCapacity; i < gameFittedHand.getSize(); i++) {
                gameFittedHand.removeCard(i);
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