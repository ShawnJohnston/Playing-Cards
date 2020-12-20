import java.util.Random;

public class Shuffler {
    //Fields

    // Constructor
    public Shuffler() {
    }

    public DeckOfCards random(DeckOfCards deck) {
        // This method will shuffle the deck using Java's Random class to randomize the array that contains each card.
        Random randomizer = new Random(); // 'Random' object.


        for (int i = 0; i < deck.getSize(); i++) {
            int randomPosition = randomizer.nextInt(deck.getSize());
            PlayingCard temp = deck.getCards()[i];
            deck.getCards()[i] = deck.getCards()[randomPosition];
            deck.getCards()[randomPosition] = temp;
        }
        for (int i = 0; i < deck.getSize(); i++) {
            System.out.println(deck.getCards()[i].getValue() + " " + deck.getCards()[i].getSuit());
        }
        return deck;
    }
}
