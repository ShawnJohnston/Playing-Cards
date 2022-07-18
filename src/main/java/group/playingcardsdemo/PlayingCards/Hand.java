package group.playingcardsdemo.PlayingCards;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
public class Hand {
    private ArrayList<PlayingCard> cards = new ArrayList<>();
    private int capacity;
    private int size;
    private int[] valueData = new int[Values.VALUES.length];
    private int[] suitData = new int[Suits.SUITS.length];

    public Hand(ArrayList<PlayingCard> cards) {
        this.cards = cards;
        size = cards.size();
        countValues();
        countSuits();
    }

    public void setCards(ArrayList<PlayingCard> newCards) {
        cards = new ArrayList<>(newCards);
        countValues();
        countSuits();
    }

    public void addCard(PlayingCard newCard) {
        // Add the new card to the hand and update hand data.

        cards.add(newCard);
        updateHand();
    }
    public void addCard(int index, PlayingCard newCard) {
        // Add the new card to the hand at a specific index. Update hand data.

        cards.add(index, newCard);
        updateHand();
    }
    public void removeCard(int index) {
        // Remove a card at a specific index and update the hand data.

        cards.remove(index);
        updateHand();
    }
    public boolean containsCardValue(String value) {
        /*
            This method exists to determine is a specified card value exists in a given hand. The card value is input
            into a hash map as a key. If the returned value is greater than 0, then there is at least one of that card.
            The result of the search is returned as true or false.
         */
        return valueData[Values.VALUEMAP.get(value)] > 0;
    }
    public void clear() {
        /*
            Resets the hand object.

            1.  Remove all cards from the list.
            2.  The hand size is set to 0.
            3.  The hand data arrays are reinitialized.
         */

        cards.clear();
        size = 0;
        Arrays.fill(valueData, 0);
        Arrays.fill(suitData, 0);
    }

    private void updateHand() {
        // Hand size is recalibrated, and the hand data is recounted.

        size = cards.size();
        countValues();
        countSuits();
    }
    public void sortHandByValue() {


        ArrayList<PlayingCard> sortedHand = new ArrayList<>();
        int cardsSorted = 0;

        for (int i = 0; i < Values.VALUES.length; i++) {
            for (PlayingCard card : cards) {
                if (card.getValue().equals(Values.VALUES[i])) {
                    sortedHand.add(card);
                    cardsSorted++;
                }
            }
            if (cardsSorted == cards.size()) {
                break;
            }
        }
        cards = sortedHand;
    }
    private void countValues() {
        // This method will reset valueData, and then count the numbers of each value that appears in the hand.

        Arrays.fill(valueData, 0);
        for (PlayingCard card : cards) {
            valueData[Values.VALUEMAP.get(card.getValue())]++;
        }
    }
    private void countSuits() {
        // This method will reset suitData, and then count the numbers of each value that appears in the hand.

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
