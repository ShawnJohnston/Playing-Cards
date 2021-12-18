import java.util.ArrayList;
import java.util.Arrays;

public class Hand {
    private ArrayList<PlayingCard> cards = new ArrayList<>();
    private int capacity;
    private int size;
    private final int[] VALUEDATA = new int[Global.VALUES.length];
    private final int[] SUITDATA = new int[Global.SUITS.length];

    public Hand() {
    }
    public Hand(ArrayList<PlayingCard> cards) {
        this.cards = cards;
        this.size = cards.size();
        countValues();
        countSuits();
    }

    public int getSize() {
        return this.size;
    }
    public ArrayList<PlayingCard> getCards() {
        return this.cards;
    }
    public int[] getValueData() {
        return this.VALUEDATA;
    }
    public int[] getSuitData() {
        return this.SUITDATA;
    }

    public void setHand(ArrayList<PlayingCard> newCards) {
        this.cards = new ArrayList<>(newCards);
        countValues();
        countSuits();
    }

    public void addCard(PlayingCard newCard) {
        this.cards.add(newCard);
        updateHand();
    }
    public void addCard(int index, PlayingCard newCard) {
        this.cards.add(index, newCard);
        updateHand();
    }
    public void removeCard(int index) {
        this.cards.remove(index);
    }

    private void updateHand() {
        this.size = this.getCards().size();
        countValues();
        countSuits();
    }
    public void sortHand() {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>(); // Buffer hand to overwrite playerHand.
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Value order, excluding 'low Ace'.
        int cardsSorted = 0; // Counter used to break out of the following loop when the hand has been sorted.

        // This 2D loop will compare each card to each index in the values array. If they match, the card will be added
        // to the buffer array and the counter will increment.
        // The counter will break the loop when it increments to the value equal to the number of cards in the hand.
        for (String value : values) {
            for (PlayingCard card : this.cards) {
                // The outer loop sets the comparison value, and the inner loop cycles through each card in the hand.
                if (card.getValue().equals(value)) {
                    // The card's value matches the values array.
                    sortedHand.add(card); // The card is added to the buffer list.
                    cardsSorted++;
                }
            }
            if (cardsSorted == cards.size()) {
                break;
            }
        }
        this.cards = sortedHand;
    }
    public void readSortedHand(ArrayList<PlayingCard> hand) {
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
            if (cardsSorted == cards.size()) {
                break;
            }
        }
        for (PlayingCard card: sortedHand) {
            System.out.print(card.getName() + " ");
        }
        System.out.println();
    }
    private void countValues() {
        Arrays.fill(VALUEDATA, 0);

        ArrayList<PlayingCard> tempCards = this.cards;

        int counter = 0;
        for (int i = 0; i < Global.VALUES.length; i++) {
            for (PlayingCard card : tempCards) {
                if (card.getValue().equals(Global.VALUES[i])) {
                    this.VALUEDATA[i]++;
                    counter++;
                }
            }
            if (counter >= tempCards.size()) {
                break;
            }
        }
    }
    private void countSuits() {
        Arrays.fill(SUITDATA, 0);
        for (PlayingCard card : this.cards) {
            switch (card.getSuit()) {
                case "Spades" -> SUITDATA[0]++;
                case "Hearts" -> SUITDATA[1]++;
                case "Clubs" -> SUITDATA[2]++;
                case "Diamonds" -> SUITDATA[3]++;
                case "Joker" -> SUITDATA[4]++;
            }
        }
    }
}