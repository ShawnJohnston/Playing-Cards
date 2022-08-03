package group.playingcardsdemo.PlayingCards;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor

// This class is used to represent a playing card object.
public class PlayingCard {
    protected String value;
    protected String suit;
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

