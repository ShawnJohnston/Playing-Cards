public class GameOutcome {
    private final HandEvaluator PLAYER1;
    private final HandEvaluator PLAYER2;
    private Player winner;

    public GameOutcome(HandEvaluator player1, HandEvaluator player2) {
        this.PLAYER1 = player1;
        this.PLAYER2 = player2;
    }

    public Player getWinner() {
        compareRanks();
        return winner;
    }

    private void compareRanks() {
        for (int i = 0; i < Global.STANDARDPOKERRANKS.length - 1; i++) {
            if (PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i]) && PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i])) {
                switch (Global.STANDARDPOKERRANKS[i]) {
                    case "Quads" -> compareQuads();
                    case "FullHouse" -> compareFullHouse();
                    case "Trips" -> compareTrips();
                    case "TwoPair" -> compareTwoPair();
                    case "Pair" -> comparePair();
                    default -> compareCards();
                }
            }
            else if (PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i]) && !PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i])) {
                this.winner = this.PLAYER1.getPlayer();
            }
            else if (!PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i]) && PLAYER1.getHandRank().toString().equals(Global.STANDARDPOKERRANKS[i])) {
                this.winner = this.PLAYER2.getPlayer();
            }
        }
    }
    private void compareCards() {

    }
    private void compareQuads() {
    }
    private void compareFullHouse() {

    }
    private void compareTrips() {

    }
    private void compareTwoPair() {

    }
    private void comparePair() {

    }

}
