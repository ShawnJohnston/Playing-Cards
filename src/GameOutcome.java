public class GameOutcome {
    private static String[] standardPokerRanks = {"HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    private HandEvaluator player;
    private HandEvaluator dealer;
    private Player winner;

    public GameOutcome(HandEvaluator player, HandEvaluator dealer) {
        this.player = player;
        this.dealer = dealer;
    }

    public Player getWinner() {
        return winner;
    }

    public void compareRanks() {
        int userRankNum = 0;
        int dealerRankNum = 0;

        this.player.isAStraight();
        this.player.isAFlush();
        this.player.isAStraightFlush();
        this.player.isARoyalFlush();

        this.dealer.isAStraight();
        this.dealer.isAFlush();
        this.dealer.isAStraightFlush();
        this.dealer.isARoyalFlush();

        for (int i = standardPokerRanks.length - 1; i >= 0; i--) {
            if (standardPokerRanks[i].equals(this.player.getHandRank().toString())) {
                userRankNum = i;
            }
            if (standardPokerRanks[i].equals(this.dealer.getHandRank().toString())) {
                dealerRankNum = i;
            }
        }
        if (userRankNum > dealerRankNum) {
            this.winner = player.getPlayer();
        }
        else if (userRankNum < dealerRankNum) {
            this.winner = dealer.getPlayer();
        }
        else if (userRankNum == dealerRankNum) {
            String[] values = {"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6", "5", "4", "3", "2"}; // Reference array.
            for (int i = 0; i < player.getPlayerHand().size(); i++) {
                if (!player.getPlayerHand().get(i).getValue().equals(dealer.getPlayerHand().get(i).getValue())) {
                    for (String value: values) {
                        if (player.getPlayerHand().get(i).getValue().equals(value) && !dealer.getPlayerHand().get(i).getValue().equals(value)) {
                            this.winner = player.getPlayer();
                            break;
                        }
                        else {
                            this.winner = dealer.getPlayer();
                            break;
                        }
                    }
                }
                else {
                    this.winner = null;
                }
            }
        }
    }
}
