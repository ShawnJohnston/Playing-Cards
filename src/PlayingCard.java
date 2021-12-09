// This class is used to represent a playing card object.
public class PlayingCard {
    // Fields
    protected String value;
    protected String suit;
    protected String name;
    protected String color;
    protected String front;
    protected String back;
    protected static facing currentFacing = facing.faceDown;

    // Constructors
    public PlayingCard() {
    }
    public PlayingCard(String value, String suit) {
        this.setValue(value);
        this.setSuit(suit);
        this.setName();
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
    private void setName() {
        this.name = this.value + " of " + this.suit;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
// The JokerCard class inherits from PlayingCard. Is used to ensure that Jokers are distinct from other cards.
class JokerCard extends PlayingCard {

    // Constructors
    public JokerCard() {
        value = "Joker";
        suit = "Joker";
        name = "Joker";
        color = "Joker";
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
    public String getName() {
        return name;
    }
}
enum facing {
    faceUp, faceDown
}
