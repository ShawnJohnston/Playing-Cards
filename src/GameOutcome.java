import java.util.ArrayList;

public class GameOutcome {
    private static String[] standardPokerRanks = {"HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    private HandEvaluator userHand;
    private HandEvaluator dealerHand;
    private Player winner;

    public GameOutcome(HandEvaluator userHand, HandEvaluator dealerHand) {
        this.userHand = userHand;
        this.dealerHand = dealerHand;
    }

    public void compareRanks() {
        int userRankNum = 0;
        int dealerRankNum = 0;
        for (int i = standardPokerRanks.length; i >= 0; i--) {
            /*
            if (this.userHand.getRank().equals(standardPokerRanks[i])) {
                userRankNum = i;
            }
            if (this.dealerHand.getRank().equals(standardPokerRanks[i])) {
                dealerRankNum = i;
            }
            */
        }
        if (userRankNum > dealerRankNum) {
            this.winner = userHand.getPlayer();
        }
        else if (userRankNum < dealerRankNum) {
            this.winner = dealerHand.getPlayer();
        }
        else if (userRankNum == dealerRankNum) {

        }
    }
}
