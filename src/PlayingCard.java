public class PlayingCard {
    private String rank;
    private String suit;

    public PlayingCard(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
}
