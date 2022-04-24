package group.playingcardsdemo;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor

// This class is used to represent a playing card object.
public class PlayingCard extends Node {
    // Fields
    public static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] VALUES_INDEX = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] SUITS = {"Spades", "Hearts", "Clubs", "Diamonds", "Joker"};
    protected String value;
    protected String suit;
    protected String name;
    protected String color;
    protected String front;
    protected String back;
    protected static HashMap<String, Integer> valueMap = new HashMap<>();
    protected Facing currentFacing = Facing.faceDown;

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
    public static void initializeCardValueMap() {
        PlayingCard.valueMap.put("2", 0);
        PlayingCard.valueMap.put("3", 1);
        PlayingCard.valueMap.put("4", 2);
        PlayingCard.valueMap.put("5", 3);
        PlayingCard.valueMap.put("6", 4);
        PlayingCard.valueMap.put("7", 5);
        PlayingCard.valueMap.put("8", 6);
        PlayingCard.valueMap.put("9", 7);
        PlayingCard.valueMap.put("10", 8);
        PlayingCard.valueMap.put("Jack", 9);
        PlayingCard.valueMap.put("Queen", 10);
        PlayingCard.valueMap.put("King", 11);
        PlayingCard.valueMap.put("Ace", 12);
        PlayingCard.valueMap.put("Joker", 13);
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

        if (color.equals("Black")) {
            setFront("joker_black.png");
        }
        else if (color.equals("Red")) {
            setFront("joker_red.png");
        }
    }
}
enum Facing {
    faceUp, faceDown
}