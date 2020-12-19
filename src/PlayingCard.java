// This class is used to represent a playing card object.
public class PlayingCard {
    // Fields
    private String value;
    private String suit;
    private String color;

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
    public String getColor() {
        return color;
    }
    
    // Setters
    public void setValue(String value) {
        this.value = value;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
// The JokerCard class inherits from PlayingCard. Is used to ensure that Jokers are distinct from other cards.
class JokerCard extends PlayingCard {
    private final String VALUE = "Joker";
    private final String SUIT = "Joker";
    private final String COLOR = "Joker";

    // Constructors
    public JokerCard() {
    }

    // Getters
    public String getValue() {
        return VALUE;
    }

    public String getSuit() {
        return SUIT;
    }

    public String getColor() {
        return COLOR;
    }
}
