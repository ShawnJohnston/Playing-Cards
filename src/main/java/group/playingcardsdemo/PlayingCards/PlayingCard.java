package group.playingcardsdemo.PlayingCards;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor

// This class is used to represent a playing card object.
public class PlayingCard implements CardInterface {
    protected String value;
    protected Values _value;
    protected String suit;
    protected Suits _suit;
    protected String name;
    protected String color;
    protected String front;
    protected String back;
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
}

