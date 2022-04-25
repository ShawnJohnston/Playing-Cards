package group.playingcardsdemo;

import java.util.Objects;

public class GameOutcome {
    private final HandEvaluator PLAYER1;
    private final HandEvaluator PLAYER2;
    private String winner;

    public GameOutcome(HandEvaluator player1, HandEvaluator player2) {
        PLAYER1 = player1;
        PLAYER2 = player2;
    }

    public String getWinner() {
        compareRanks();
        if (winner == null) {
            winner = "Tie";
        }
        return winner;
    }

    private void compareRanks() {
        /*
            This method will compare two hands using the hash map stored in each one's 'HandEvaluator'. The players'
            stored poker rank will be used as a key to a hash map that will return a value when queried. Those values
            will be compared and the higher one is the winning hand.

            1.  If player 1's poker rank returns a higher value than player 2's rank data, player 1 wins.
            2.  If player 2's poker rank returns a higher value than player 1's rank data, player 2 wins.
            3.  If the two hands tie by rank, a switch statement is used to handle each case.
         */

        if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) > HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
            winner = "Player 1";
        }
        else if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) < HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
            winner = "Player 2";
        }
        else if (Objects.equals(HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()), HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString()))) {
            switch (PLAYER1.getHandRank().toString()) {
                case "Flush", "HighCard" -> compareCards();
                case "StraightFlush", "Straight" -> compareStraight();
                case "Quads" -> compareQuads();
                case "FullHouse" -> compareFullHouse();
                case "Trips" -> compareTrips();
                case "TwoPair" -> compareTwoPair();
                case "Pair" -> comparePair();
            }
        }
    }
    private void compareCards() {
        /*
            This method will compare individual cards to determine the winner. This method uses the value data stored in
            the hand.

            1.  For Loop: Range (value data array length - 1) >= i >= 0. The loop will compare each value of the
                'valueData' array. The first hand to have a nonzero value will win, otherwise the hands will tie.
                -   If the value stored in player 1's value data index 'i' is greater than player 2's, player 1 wins.
                -   If the value stored in player 2's value data index 'i' is greater than player 1's, player 2 wins.
                -   Otherwise, each hand is equivalent by value. The players tie.
         */

        for (int i = PLAYER1.getFiveCardHand().getValueData().length - 1; i >= 0; i--) {
            if (PLAYER1.getFiveCardHand().getValueData()[i] > PLAYER2.getFiveCardHand().getValueData()[i]) {
                winner = "Player 1";
                break;
            }
            else if (PLAYER1.getFiveCardHand().getValueData()[i] < PLAYER2.getFiveCardHand().getValueData()[i]) {
                winner = "Player 2";
                break;
            }
            if (i == 0 && (PLAYER1.getFiveCardHand().getValueData()[i] == PLAYER2.getFiveCardHand().getValueData()[i])) {
                winner = "Tie";
            }
        }
    }
    private void compareKickerAt(int k) {
        int handPosition = k;
        for (int i = PlayingCard.VALUES.length - 1; i >= 0; i--) {
            for (int j = handPosition - 1; j >= PLAYER1.getFullHand().getSize() ; j++) {
                if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) > HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
                    winner = "Player 1";
                }
                else if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) < HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
                    winner = "Player 2";
                }
                else if (PLAYER1.getFiveCardHand().getCards().get(j).getValue().equals(PlayingCard.VALUES[i]) && PLAYER2.getFiveCardHand().getCards().get(j).getValue().equals(PlayingCard.VALUES[i])) {
                    handPosition++;
                    break;
                }
            }
        }
    }
    private void compareQuads() {
        /*
            This method is used to evaluate two quads hands to determine the winner. The stored quads value for each
            player is used as a key in a hash map. The returned values are compared.

            1.  If player 1's returned value is greater than player 2's, player 1 wins.
            2.  If player 2's returned value is greater than player 1's. player 2 wins.
            3.  Otherwise, use the 'compareCards()' method to evaluate kickers.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getQuadsValue()) > PlayingCard.valueMap.get(PLAYER2.getQuadsValue())) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getQuadsValue()) < PlayingCard.valueMap.get(PLAYER2.getQuadsValue())) {
            winner = "Player 2";
        }
        else {
            compareCards();
        }
    }
    private void compareFullHouse() {
        /*
            This method compares two Full House hands to determine a winner. The Trips portion of each hand are used as
            keys through a hash map, and the returned values are compared. If those are equal, the same thing will be
            attempted using the Pair portion of each hand. If they are also equivalent, the players tie.

            1.  Each hand's first index value in their FullHouseList is used as a key in a hash map. If player 1's
                returned value is greater than player 2's returned value, player 1 wins.
            2.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
            3.  If these values are equal:

                4.  Each hand's second index value in their FullHouseList is used as a key in a hash map. If player 1's
                    returned value is greater than player 2's returned value, player 1 wins.
                5.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
                6.  If these values are equal, the players tie.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getFullHouseList().get(0)) > PlayingCard.valueMap.get(PLAYER2.getFullHouseList().get(0))) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getFullHouseList().get(0)) < PlayingCard.valueMap.get(PLAYER2.getFullHouseList().get(0))) {
            winner = "Player 2";
        }
        else {
            if (PlayingCard.valueMap.get(PLAYER1.getFullHouseList().get(1)) > PlayingCard.valueMap.get(PLAYER2.getFullHouseList().get(1))) {
                winner = "Player 1";
            }
            else if (PlayingCard.valueMap.get(PLAYER1.getFullHouseList().get(1)) < PlayingCard.valueMap.get(PLAYER2.getFullHouseList().get(1))) {
                winner = "Player 2";
            }
            else {
                winner = "Tie";
            }
        }
    }
    private void compareStraight() {
        /*
            This method is used to determine which of two Straight hands wins. Each hand stores a value indicating the
            top value of the straight, so it will be compared to determine the winner.

            1.  The 'straightValue' for each hand is used as a key through a hash map. If player 1's returned value is
                greater than player 2's returned value, player 1 wins.
            2.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getStraightValue()) > PlayingCard.valueMap.get(PLAYER2.getStraightValue())) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getStraightValue()) < PlayingCard.valueMap.get(PLAYER2.getStraightValue())) {
            winner = "Player 2";
        }
        else {
            winner = "Tie";
        }
    }
    private void compareTrips() {
        /*
            This method is used to determine which of two Trips hands wins. Each hand's Trips value will be used as a
            key in a hash map, and the returned values will be compared.

            1.  If player 1's returned value is greater than player 2's returned value, player 1 wins.
            2.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
            3.  If the Trips values are equal, execute a method to compare kickers.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getTripsList().get(0)) > PlayingCard.valueMap.get(PLAYER2.getTripsList().get(0))) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getTripsList().get(0)) < PlayingCard.valueMap.get(PLAYER2.getTripsList().get(0))) {
            winner = "Player 2";
        }
        else {
            compareKickerAt(0);
        }
    }
    private void compareTwoPair() {
        /*
            This method compares two Two Pair hands to determine a winner. The first Pair of each hand are used as
            keys through a hash map, and the returned values are compared. If those are equal, the same thing will be
            attempted using the second Pair of each hand.

            1.  Each hand's first index value in their PairsList is used as a key in a hash map. If player 1's
                returned value is greater than player 2's returned value, player 1 wins.
            2.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
            3.  If these values are equal:

                4.  Each hand's second index value in their PairsList is used as a key in a hash map. If player 1's
                    returned value is greater than player 2's returned value, player 1 wins.
                5.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
                6.  If these values are equal, execute a method to compare the kicker card.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(0)) > PlayingCard.valueMap.get(PLAYER2.getPairsList().get(0))) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(0)) < PlayingCard.valueMap.get(PLAYER2.getPairsList().get(0))) {
            winner = "Player 2";
        }
        else {
            if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(1)) > PlayingCard.valueMap.get(PLAYER2.getPairsList().get(1))) {
                winner = "Player 1";
            }
            else if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(1)) < PlayingCard.valueMap.get(PLAYER2.getPairsList().get(1))) {
                winner = "Player 2";
            }
            else {
                compareCards();
            }
        }
    }
    private void comparePair() {
        /*
            This method compares two Pair hands to determine a winner. The Pair values of each hand are used as keys
            through a hash map, and the returned values are compared. If those are equal,

            1.  Each hand's first index value in their PairsList is used as a key in a hash map. If player 1's
                returned value is greater than player 2's returned value, player 1 wins.
            2.  If player 2's returned value is greater than player 1's returned value, player 2 wins.
            3.  If these values are equal, a method will execute to compare kickers.
         */

        winner = "";
        if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(0)) > PlayingCard.valueMap.get(PLAYER2.getPairsList().get(0))) {
            winner = "Player 1";
        }
        else if (PlayingCard.valueMap.get(PLAYER1.getPairsList().get(0)) < PlayingCard.valueMap.get(PLAYER2.getPairsList().get(0))) {
            winner = "Player 2";
        }
        else {
            compareKickerAt(0);
        }
    }
}
