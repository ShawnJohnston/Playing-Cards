// This class is used to represent a playing card object.
public class PlayingCard {
    // Fields
    private String value;
    private String suit;
    private String name;
    private String color;
    private String front;
    private String back;
    private static facing currentFacing = facing.faceDown;

    // Constructors
    public PlayingCard() {
    }

    // Getters
    public String getValue() {
        return this.value;
    }
    public String getSuit() {
        return this.suit;
    }
    public String getName() {
        return name;
    }
    public String getColor() {
        return this.color;
    }
    
    // Setters
    public void setValue(String value) {
        this.value = value;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public void setName() {
        this.name = this.value + " of " + this.suit;
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
enum facing {
    faceUp, faceDown
}
