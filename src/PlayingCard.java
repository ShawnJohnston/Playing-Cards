// This class is used to represent a playing card object.
public class PlayingCard {
    // Fields
    private String value;
    private String suit;

    // Constructors
    public PlayingCard() {
    }

    // Getters
    public String getValue() {
        return value;
    }
    public String getSuit() {
        return suit;
    }
    
    // Setters
    public void setValue(String value) {
        this.value = value;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
}