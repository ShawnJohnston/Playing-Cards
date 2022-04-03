package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter

public class Hand {
    private ArrayList<PlayingCard> cards = new ArrayList<>();
    private int capacity;
    private int size;
    private int[] valueData = new int[PlayingCard.VALUESHIERARCHY.length];
    private int[] suitData = new int[PlayingCard.SUITS.length];

    public Hand() {
    }
    public Hand(ArrayList<PlayingCard> cards) {
        this.cards = cards;
        size = cards.size();
        countValues();
        countSuits();
    }

    public int getSize() {
        return size;
    }
    public int getCapacity() {
        return capacity;
    }
    public ArrayList<PlayingCard> getCards() {
        return cards;
    }
    public int[] getValueData() {
        return valueData;
    }
    public int[] getSuitData() {
        return suitData;
    }

    public void setHand(ArrayList<PlayingCard> newCards) {
        cards = new ArrayList<>(newCards);
        countValues();
        countSuits();
    }
    public void setValueData(int[] data) {
        valueData = data;
    }
    public void setCapacity(int num) {
        capacity = num;
    }

    public void addCard(PlayingCard newCard) {
        cards.add(newCard);
        updateHand();
    }
    public void addCard(int index, PlayingCard newCard) {
        cards.add(index, newCard);
        updateHand();
    }
    public void removeCard(int index) {
        cards.remove(index);
        size = cards.size();
    }
    public void clear() {
        cards.clear();
        size = 0;
        valueData = new int[PlayingCard.VALUES.length];
        suitData = new int[PlayingCard.SUITS.length];
    }

    private void updateHand() {
        size = getCards().size();
        countValues();
        countSuits();
    }
    public void sortHand() {
        ArrayList<PlayingCard> sortedHand = new ArrayList<>(); // Buffer hand to overwrite playerHand.
        int cardsSorted = 0; // Counter used to break out of the following loop when the hand has been sorted.

        // This 2D loop will compare each card to each index in the values array. If they match, the card will be added
        // to the buffer array and the counter will increment.
        // The counter will break the loop when it increments to the value equal to the number of cards in the hand.
        for (int i = 0; i < PlayingCard.VALUES.length; i++) {
            for (PlayingCard card : cards) {
                // The outer loop sets the comparison value, and the inner loop cycles through each card in the hand.
                if (card.getValue().equals(PlayingCard.VALUES[i])) {
                    // The card's value matches the values array.
                    sortedHand.add(card); // The card is added to the buffer list.
                    cardsSorted++;
                }
            }
            if (cardsSorted == cards.size()) {
                break;
            }
        }
        cards = sortedHand;
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
        Arrays.fill(valueData, 0);

        ArrayList<PlayingCard> tempCards = cards;

        int counter = 0;
        for (int i = 0; i < PlayingCard.VALUES.length; i++) {
            for (PlayingCard card : tempCards) {
                if (card.getValue().equals(PlayingCard.VALUES[i])) {
                    valueData[i]++;
                    counter++;
                }
            }
            if (counter >= tempCards.size()) {
                break;
            }
        }
    }
    private void countSuits() {
        Arrays.fill(suitData, 0);
        for (PlayingCard card : cards) {
            switch (card.getSuit()) {
                case "Spades" -> suitData[0]++;
                case "Hearts" -> suitData[1]++;
                case "Clubs" -> suitData[2]++;
                case "Diamonds" -> suitData[3]++;
                case "Joker" -> suitData[4]++;
            }
        }
    }
}