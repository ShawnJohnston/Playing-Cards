import java.util.ArrayList;

public class Rank {
    private static String[] standardPokerRanks = {"High Card", "Pair", "Two Pair", "Trips", "Straight", "Flush",
        "Full House", "Quads", "Straight Flush", "Royal Flush"};
    private Player player;
    private rank rank;
    private ArrayList<String[]> pairsList = new ArrayList<>();
    private ArrayList<String[]> tripsList = new ArrayList<>();
    private String highCard;
    private String[] kickers;

    public Rank() {
    }
    public Rank(Player player, ArrayList<String> hand) {
        this.player = player;
    }
}
enum rank {
    HighCard, Pair, TwoPair, Trips, Straight, Flush,
            FullHouse, Quads, StraightFlush, RoyalFlush
}