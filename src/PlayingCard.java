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
        return this.name;
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
        setColor();
    }
    protected void setName() {
        this.name = this.value + " of " + this.suit;
    }
    protected void setColor() {
        if (this.suit.equals("Diamonds") || this.suit.equals("Hearts")) {
            this.color = "Red";
        }
        if (this.suit.equals("Spades") || this.suit.equals("Clubs")) {
            this.color = "Black";
        }
    }
}
// The JokerCard class inherits from PlayingCard. Is used to ensure that Jokers are distinct from other cards.
class JokerCard extends PlayingCard {
    // Constructors
    public JokerCard() {
        this.name = "Joker";
    }
    @Override
    public String getValue() {
        return "Joker";
    }
    @Override
    public String getSuit() {
        return "Joker";
    }

    @Override
    public void setName() {}
}
enum facing {
    faceUp, faceDown
}
