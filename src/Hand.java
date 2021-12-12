import java.util.ArrayList;
import java.util.Arrays;

public class Hand {
    private ArrayList<PlayingCard> cards;
    private int size = 0;
    private int capacity;
    private final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    private int[] VALUECOUNTER = new int[VALUES.length];

    public Hand() {
    }
    public Hand(ArrayList<PlayingCard> cards) {
        this.cards = cards;
        this.size = cards.size();
        countValues();
    }

    public ArrayList<PlayingCard> getCards() {
        return this.cards;
    }
    public int[] getValueData() {
        return VALUECOUNTER;
    }

    public void setHand(ArrayList<PlayingCard> newCards) {
        this.cards = new ArrayList<>(newCards);
        this.size++;
        countValues();
    }

    public void addCard(PlayingCard newCard) {
        this.cards.add(newCard);
        this.size++;
    }
    public void sortHand(ArrayList<PlayingCard> hand) {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>(); // Buffer hand to overwrite playerHand.
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Value order, excluding 'low Ace'.
        int cardsSorted = 0; // Counter used to break out of the following loop when the hand has been sorted.

        // This 2D loop will compare each card to each index in the values array. If they match, the card will be added
        // to the buffer array and the counter will increment.
        // The counter will break the loop when it increments to the value equal to the number of cards in the hand.
        for (String value : values) {
            for (PlayingCard card : hand) {
                // The outer loop sets the comparison value, and the inner loop cycles through each card in the hand.
                if (card.getValue().equals(value)) {
                    // The card's value matches the values array.
                    sortedHand.add(card); // The card is added to the buffer list.
                    cardsSorted++;
                }
            }
            if (cardsSorted == size) {
                break;
            }
        }
        this.cards = sortedHand;
    }
    private void countValues() {
        Arrays.fill(VALUECOUNTER, 0);

        ArrayList<PlayingCard> tempCards = this.cards;
        sortHand(tempCards);

        int counter = 0;
        for (int i = 0; i < VALUES.length; i++) {
            for (int j = 0; j < tempCards.size(); j++) {
                if (tempCards.get(j).getValue().equals(VALUES[i])) {
                    VALUECOUNTER[i]++;
                    counter++;
                }
            }
            if (counter == tempCards.size()) {
                break;
            }
        }

        //for (int i = values.length - 1; i >= 0; i--) {
        //    System.out.println(duplicates[i]);
        //    switch (duplicates[i]) {
        //        case 2 -> pairs.add(values[i]);
        //        case 3 -> trips.add(values[i]);
        //        case 4 -> quads.add(values[i]);
        //    }
        //}
    }
}