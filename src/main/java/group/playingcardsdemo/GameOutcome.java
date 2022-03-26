package group.playingcardsdemo;

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
        for (int i = Global.STANDARDPOKERRANKS.length - 1; i >= 0; i--) {
            if (PLAYER1.getRankData()[i] && !PLAYER2.getRankData()[i]) {
                winner = "Player 1";
                break;
            }
            else if (!PLAYER1.getRankData()[i] && PLAYER2.getRankData()[i]) {
                winner = "Player 2";
                break;
            }
            else if (PLAYER1.getRankData()[i] && PLAYER2.getRankData()[i]) {
                switch (Global.STANDARDPOKERRANKS[i]) {
                    case "Quads" -> compareQuads();
                    case "FullHouse" -> compareFullHouse();
                    case "Trips" -> compareTrips();
                    case "TwoPair" -> compareTwoPair();
                    case "Pair" -> comparePair();
                    case "HighCard" -> compareCards();
                    //default -> compareCards();
                }
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
        }
    }
    private void compareKickerAt(int k) {
        int handPosition = k;
        for (int i = Global.VALUES.length - 1; i >= 0; i--) {
            for (int j = handPosition; j >= PLAYER1.getRawHand().getSize() ; j++) {
                if (PLAYER1.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i]) && !PLAYER2.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i])) {
                    winner = "Player 1";
                }
                else if (!PLAYER1.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i]) && PLAYER2.getFiveCardHand().getCards().get(j).getValue().equals(Global.VALUES[i])) {
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
        for (int i = PLAYER1.getRawHand().getValueData().length - 1; i >= 0; i--) {
            if (PLAYER1.getQuadsValue().equals(Global.VALUES[i]) && !PLAYER2.getQuadsValue().equals(Global.VALUES[i])) {
                winner = "Player 1";
            }
            else if (!PLAYER1.getQuadsValue().equals(Global.VALUES[i]) && PLAYER2.getQuadsValue().equals(Global.VALUES[i])) {
                winner = "Player 2";
            }
            else if (PLAYER1.getQuadsValue().equals(Global.VALUES[i]) && PLAYER2.getQuadsValue().equals(Global.VALUES[i])) {
                compareKickerAt(4);
            }
        }
    }
    private void compareFullHouse() {
        for (int i = Global.VALUES.length - 1; i >= 0 ; i--) {
            if (PLAYER1.getFullHouse().get(0).equals(Global.VALUES[i]) && !PLAYER2.getFullHouse().get(0).equals(Global.VALUES[i])) {
                winner = "Player 1";
            }
            else if (!PLAYER1.getFullHouse().get(0).equals(Global.VALUES[i]) && PLAYER2.getFullHouse().get(0).equals(Global.VALUES[i])) {
                winner = "Player 2";
            }
            else if (PLAYER1.getFullHouse().get(0).equals(Global.VALUES[i]) && PLAYER2.getFullHouse().get(0).equals(Global.VALUES[i])) {
                if (PLAYER1.getFullHouse().get(1).equals(Global.VALUES[i]) && !PLAYER2.getFullHouse().get(1).equals(Global.VALUES[i])) {
                    winner = "Player 1";
                }
                else if (!PLAYER1.getFullHouse().get(1).equals(Global.VALUES[i]) && PLAYER2.getFullHouse().get(1).equals(Global.VALUES[i])) {
                    winner = "Player 2";
                }
                else if (PLAYER1.getFullHouse().get(1).equals(Global.VALUES[i]) && PLAYER2.getFullHouse().get(1).equals(Global.VALUES[i])) {
                    break;
                }
            }
        }
    }
    private void compareTrips() {
        for (int i = Global.VALUES.length - 1; i >= 0 ; i--) {
            if (PLAYER1.getTrips().get(0).equals(Global.VALUES[i]) && !PLAYER2.getTrips().get(0).equals(Global.VALUES[i])) {
                winner = "Player 1";
            } else if (!PLAYER1.getTrips().get(0).equals(Global.VALUES[i]) && PLAYER2.getTrips().get(0).equals(Global.VALUES[i])) {
                winner = "Player 2";
            } else if (PLAYER1.getTrips().get(0).equals(Global.VALUES[i]) && PLAYER2.getTrips().get(0).equals(Global.VALUES[i])) {
                compareKickerAt(3);
            }
        }
    }
    private void compareTwoPair() {
        for (int i = Global.VALUES.length - 1; i >= 0 ; i--) {
            if (PLAYER1.getPairs().get(0).equals(Global.VALUES[i]) && !PLAYER2.getPairs().get(0).equals(Global.VALUES[i])) {
                winner = "Player 1";
            } else if (!PLAYER1.getPairs().get(0).equals(Global.VALUES[i]) && PLAYER2.getPairs().get(0).equals(Global.VALUES[i])) {
                winner = "Player 2";
            } else if (PLAYER1.getPairs().get(0).equals(Global.VALUES[i]) && PLAYER2.getPairs().get(0).equals(Global.VALUES[i])) {
                if (PLAYER1.getPairs().get(1).equals(Global.VALUES[i]) && !PLAYER2.getPairs().get(1).equals(Global.VALUES[i])) {
                    winner = "Player 1";
                } else if (!PLAYER1.getPairs().get(1).equals(Global.VALUES[i]) && PLAYER2.getPairs().get(1).equals(Global.VALUES[i])) {
                    winner = "Player 2";
                } else if (PLAYER1.getPairs().get(1).equals(Global.VALUES[i]) && PLAYER2.getPairs().get(1).equals(Global.VALUES[i])) {
                    compareKickerAt(4);
                }
            }
        }
    }
    private void comparePair() {
        winner = "";
        if (PLAYER1.getPairs().get(0).equals(PLAYER2.getPairs().get(0))) {
            compareKickerAt(0);
        }
        else {
            for (int i = Global.VALUES.length - 1; i >= 0 ; i--) {
                if (PLAYER1.getPairs().get(0).equals(Global.VALUES[i]) && !PLAYER2.getPairs().get(0).equals(Global.VALUES[i])) {
                    winner = "Player 1";
                }
                else if (PLAYER2.getPairs().get(0).equals(Global.VALUES[i]) && !PLAYER1.getPairs().get(0).equals(Global.VALUES[i])) {
                    winner = "Player 2";
                }
            }
        }
    }
}
