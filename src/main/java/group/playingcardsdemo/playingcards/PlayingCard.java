package group.playingcardsdemo.playingcards;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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
    public static HashMap<String, Integer> valueMap = new HashMap<>();
    public static HashMap<Integer, String> indexMap = new HashMap<>();
    protected Facing currentFacing = Facing.faceDown;

    public PlayingCard(String value, String suit) {
        setValue(value);
        setSuit(suit);
        setName();

        String filename = "src/main/resources/group/playingcardsdemo/Card_Fronts/" + suit.toLowerCase() + "_" + value.toLowerCase() + ".png";
        setFront(filename);
    }
    public void setSuit(String suit) {
        this.suit = suit;
        setColor();
    }
    public void setName() {
        if (!value.equals("Joker")) {
            name = value + " of " + suit;
        }
    }
    public void setColor() {
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
    public static void initializeCardIndexMap() {
        PlayingCard.indexMap.put(0, "2");
        PlayingCard.indexMap.put(1, "3");
        PlayingCard.indexMap.put(2, "4");
        PlayingCard.indexMap.put(3, "5");
        PlayingCard.indexMap.put(4, "6");
        PlayingCard.indexMap.put(5, "7");
        PlayingCard.indexMap.put(6, "8");
        PlayingCard.indexMap.put(7, "9");
        PlayingCard.indexMap.put(8, "10");
        PlayingCard.indexMap.put(9, "Jack");
        PlayingCard.indexMap.put(10, "Queen");
        PlayingCard.indexMap.put(11, "King");
        PlayingCard.indexMap.put(12, "Ace");
        PlayingCard.indexMap.put(13, "Joker");
    }
}

