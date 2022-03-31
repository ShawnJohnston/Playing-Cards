package group.playingcardsdemo;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter

// This class is used to represent a playing card object.
public class PlayingCard extends Node {
    // Fields
    protected String value;
    protected String suit;
    protected String name;
    protected String color;
    protected String front;
    protected String back;
    protected static HashMap<String, Integer> valueMap = new HashMap<>();
    protected Facing currentFacing = Facing.faceDown;

    // Constructors
    public PlayingCard() {
    }
    public PlayingCard(String value, String suit) {
        setValue(value);
        setSuit(suit);
        setName();

        String filename = suit.toLowerCase() + "_" + value.toLowerCase() + ".png";
        setFront(filename);
    }
    public void setSuit(String suit) {
        this.suit = suit;
        setColor();
    }
    protected void setName() {
        if (!value.equals("Joker")) {
            name = value + " of " + suit;
        }
    }
    protected void setColor() {
        if (suit.equals("Diamonds") || suit.equals("Hearts")) {
            color = "Red";
        }
        if (suit.equals("Spades") || suit.equals("Clubs")) {
            color = "Black";
        }
    }
}
// The JokerCard class inherits from PlayingCard. Is used to ensure that Jokers are distinct from other cards.
class JokerCard extends PlayingCard {
    // Constructors
    public JokerCard() {
        name = "Joker";
    }

    public JokerCard(String color) {
        name = "Joker";
        value = "Joker";
        suit = "Joker";

        this.color = color;
        setFront("joker_black.png");
    }
}
enum Facing {
    faceUp, faceDown
}