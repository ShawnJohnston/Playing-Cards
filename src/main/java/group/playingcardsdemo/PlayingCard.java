package group.playingcardsdemo;

import javafx.scene.Node;

import java.util.HashMap;

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
    protected facing currentFacing = facing.faceDown;

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

    // Getters
    public String getValue() {
        return value;
    }
    public String getSuit() {
        return suit;
    }
    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public String getFront() {
        return front;
    }
    public String getBack() {
        return back;
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
        name = value + " of " + suit;
    }
    protected void setColor() {
        if (suit.equals("Diamonds") || suit.equals("Hearts")) {
            color = "Red";
        }
        if (suit.equals("Spades") || suit.equals("Clubs")) {
            color = "Black";
        }
    }
    protected void setFront(String fileName) {
        front = fileName;
    }
    protected void setBack(String fileName) {
        back = fileName;
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

        this.color = color;
        setFront("joker_black.png");
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
    public void setColor(String color) {
        this.color = color;
    }
}
enum facing {
    faceUp, faceDown
}