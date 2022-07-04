package group.playingcardsdemo.PlayingCards;

// The JokerCard class inherits from PlayingCard. Is used to ensure that Jokers are distinct from other cards.
public class JokerCard extends PlayingCard {
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
            setFront("src/main/resources/group/playingcardsdemo/Card_Fronts/joker_black.png");
        } else if (color.equals("Red")) {
            setFront("src/main/resources/group/playingcardsdemo/Card_Fronts/joker_red.png");
        }
    }
}
