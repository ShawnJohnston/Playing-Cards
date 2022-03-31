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
        if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) > HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
            winner = "Player 1";
        }
        else if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) < HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
            winner = "Player 2";
        }
        else if (Objects.equals(HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()), HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString()))) {
            switch (PLAYER1.getHandRank().toString()) {
                case "RoyalFlush", "Flush", "HighCard" -> compareCards();
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
        for (int i = PLAYER1.getRawHand().getValueData().length - 1; i >= 0; i--) {
            if (PLAYER1.getRawHand().getValueData()[i] > PLAYER2.getRawHand().getValueData()[i]) {
                winner = "Player 1";
                break;
            }
            else if (PLAYER1.getRawHand().getValueData()[i] < PLAYER2.getRawHand().getValueData()[i]) {
                winner = "Player 2";
                break;
            }
            if (i == 0 && (PLAYER1.getRawHand().getValueData()[i] == PLAYER2.getRawHand().getValueData()[i])) {
                winner = "Tie";
            }
        }
    }
    private void compareKickerAt(int k) {
        int handPosition = k;
        for (int i = Global.VALUES.length - 1; i >= 0; i--) {
            for (int j = handPosition - 1; j >= PLAYER1.getRawHand().getSize() ; j++) {
                if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) > HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
                    winner = "Player 1";
                }
                else if (HandEvaluator.pokerRanks.get(PLAYER1.getHandRank().toString()) < HandEvaluator.pokerRanks.get(PLAYER2.getHandRank().toString())) {
                    winner = "Player 2";
                }
                else if (PLAYER1.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i]) && PLAYER2.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i])) {
                    handPosition++;
                    break;
                }
            }
        }
    }
    private void compareQuads() {
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
