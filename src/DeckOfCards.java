import java.util.ArrayList;

// This class is used to represent a playing card deck object.
// It uses the PlayingCard class.
public class DeckOfCards {
    private int size = 52; // A standard deck of playing cards contains 52 cards.
    private ArrayList<PlayingCard> cards = new ArrayList<>(); // A list of each individual card object.
    public static final String[] VALUES = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    public static final String[] SUITS = {"Spades", "Hearts", "Clubs", "Diamonds", "Jokers"};


    // Constructors
    public DeckOfCards() {
        buildDeck();
    }
    public DeckOfCards(int jokers) {
        this.size += jokers;
        buildDeck();
    }

    // Getters
    public ArrayList<PlayingCard> getCards() {
        return this.cards;
    }
    public int getSize() {
        return this.size;
    }

    // Setters
    public void setCards(ArrayList<PlayingCard> cards) {
        this.cards = cards; }

    // This method will create the card objects and assign them into the 'cards' array.
    public void buildDeck() {
        // Initialization of variables
        // These two arrays will contain the features of each card: value and suit. They will be used to build each card object.

        // These ints will be used in the following for loop.
        int valueCounter = 0; // Counts through each index of 'values'.
        int suitGroup = 0; // increments the index of 'suits' whenever 'valueCounter' reaches it's maximum.

        // This loop will procedurally initialize new card objects, set their value and suit, and assign them to the
        // 'cards' array that makes up the deck object.
        // Cards will be added to the deck by matching 'i' to the 'values' array along with a suit. When 'i' reaches the
        // last index of 'values', it will reset, but the next suit will be assigned.
        for (int i = 0; i < getSize(); i++) {
            if (i > 52) {
                // The loop has iterated past the cards in a standard 52-card deck of playing cards.
                // The remaining cards are expected to be Jokers.
                for (int j = 0; j < (getSize() - 52); j++) {
                    cards.add(new JokerCard());
                }
                // The for loop should break when 'i' exceeds 52.
                // A standard deck of playing cards contains 52 cards, all regular cards should be assigned at this point.
                // As such, hard-coding '52' here is appropriate due to the
                // Any further cards are Jokers. They will be added elsewhere.
                break;
            }
            if (suitGroup >= 4) {
                // A standard deck of playing cards has 4 different suits. If 'suitGroup' exceeds 3, then break the loop.
                break;
            }
            PlayingCard card = new PlayingCard(); // Card object.
            card.setValue(VALUES[valueCounter]); // value is set.
            card.setSuit(SUITS[suitGroup]); // Suit is set.
            card.setName();
            if (card.getSuit().equals("Hearts") || card.getSuit().equals("Diamonds")) {
                card.setColor("Red");
            } else if (card.getSuit().equals("Clubs") || card.getSuit().equals("Spades")) {
                card.setColor("Black");
            }
            this.cards.add(card); // Card assigned to 'cards' array at index 'i'.
            valueCounter++;

            if (valueCounter == VALUES.length) {
                // The counter has reached the end of the 'values' array. It must reset to the start.
                // The next set of cards will use the next suit in the array.
                valueCounter = 0;
                suitGroup++;
            }
        }
    }
    // This method will return true if there are no more cards in the deck object.
    public boolean isEmpty() {
        return this.getSize() == 0;
    }
}
