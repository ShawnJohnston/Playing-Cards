package group.playingcardsdemo;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Getter
@Setter

// This class is used to represent a playing card object.
public class PlayingCard extends Node {
    // Fields
    public static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] VALUESHIERARCHY = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] SUITS = {"Spades", "Hearts", "Clubs", "Diamonds", "Joker"};
    public static String[] cardFileNames = new String[54];
    public static Image[] cardImages = new Image[54];
    public static ImageView[] cardImageViews = new ImageView[54];
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
    public static void initializeCardImages() throws FileNotFoundException {
        String[] modifiedValues = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for (int i = 0; i < SUITS.length - 1; i++) {
            for (int j = 0; j < modifiedValues.length; j++) {
                String fileName = SUITS[i].toLowerCase() + "_" + modifiedValues[j].toLowerCase() + ".png";
                cardFileNames[(13*i) + j] = fileName;

                cardImages[(13*i) + j] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[(13*i) + j]));
            }
        }
        cardFileNames[52] = "joker_black.png";
        cardFileNames[53] = "joker_red.png";

        cardImages[52] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[52]));
        cardImages[53] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[53]));

    }
    public static void initializeCardImages(DeckOfCards deck) throws FileNotFoundException {
        cardFileNames = new String[54];
        cardImages = new Image[54];

        String fileName;
        for (int i = 0; i < 54; i++) {
            if (i >= deck.getMaxSize()) {
                fileName = "none.png";
            }
            else {
                fileName = String.valueOf(deck.getCards().get(i).getFront());
            }
            cardFileNames[i] = fileName;

            cardImages[i] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[i]));
        }
        initializeCardImageViews(deck.getMaxSize());
    }
    public static void initializeCardImageViews(int deckSize) {
        for (int i = 0; i < deckSize; i++) {
            cardImageViews[i].setImage(cardImages[i]);
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
        setFront("joker_black.png");
    }
}
enum Facing {
    faceUp, faceDown
}