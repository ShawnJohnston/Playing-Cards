import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ProgramTest {
    // Initialization of test variables;

    // Integers of deck sizes.
    int expectedDeckSize = 52;
    int expectedDeckSizeOneJoker = 53;
    int expectedDeckSizeTwoJokers = 54;

    // Arrays representing card attributes.
    String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    String[] suits = {"Club", "Spade", "Heart", "Diamond"};

    // Objects of Deck variations.
    DeckOfCards standardDeck = new DeckOfCards();
    DeckOfCards deckWithOneJokers = new DeckOfCards(1);
    DeckOfCards deckWithTwoJokers = new DeckOfCards(2);

    // Deck of Cards
    @Test
    public void testStandardDeckSize() {
        // This test compares the expected size of a standard deck (52) to the size resulting from class methods.
        Assertions.assertEquals(expectedDeckSize, standardDeck.getSize());
    }
    @Test
    public void testVariantDeckSize() {
        // This test tests the expected sizes for variant decks (53 or 54) to the resulting value from class methods.
        Assertions.assertEquals(expectedDeckSizeOneJoker, deckWithOneJokers.getSize());
        Assertions.assertEquals(expectedDeckSizeTwoJokers, deckWithTwoJokers.getSize());
    }
    @Test
    public void testCardObjectValueCount() {
        // This test checks that each face value appears in the deck exactly 4 times.
        int[] valueCounter = new int[values.length]; // This array is used to run parallel to 'values' array to count each value.
        int expectedValueCount = 4;

        // 2D for loop.
        // Outer loop runs through each index of the 'values' array.
        // Inner loop runs through each index of 'cards' array that composes the deck object.
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < standardDeck.getSize(); j++) {
                if (values[i].equals(standardDeck.getCards()[j].getValue())) {
                    // If the current 'i' index of 'values' matches the current 'j' index of cards in the deck.
                    valueCounter[i]++; // Increments the count for that value.
                }
            }
            // At this point, the value at the current index  of 'values' has run through each card in the deck.
            // The next index will run through the entire deck for matching values.
        }
        for (int i = 0; i < values.length; i++) {
            Assertions.assertEquals(expectedValueCount, valueCounter[i]);
        }
    }
    @Test
    public void testCardObjectSuits() {
        // This test checks that each suit appears in the deck exactly 13 times.

        // A while loop will be used to control the logic of this test.
        int step = 0; // Counter used for the while loop. Will work for 3 steps, then the loop will end.
        DeckOfCards deckBeingTested = new DeckOfCards(); // New deck object for testing.
        deckBeingTested.buildDeck();
        while (step <=2) {
            // Variables and counters.
            int expectedSuitCount = 13; // Each suit should appear 13 times.
            int expectedJokerCount = 0; // Depends on the deck variant. Is 0 normally.
            int clubCounter = 0;
            int heartCounter = 0;
            int spadeCounter = 0;
            int diamondCounter = 0;
            int jokerCounter = 0;

            if (step == 1) {
                // Re-instantiation of the deck to include 1 Joker.
                deckBeingTested = new DeckOfCards(1);
                expectedJokerCount = 1;
                deckBeingTested.getCards()[52] = new JokerCard();
            }
            else if (step == 2) {
                // // Re-instantiation of the deck to include 2 Jokers.
                deckBeingTested = new DeckOfCards(2);
                expectedJokerCount = 2;
                deckBeingTested.getCards()[52] = new JokerCard();
                deckBeingTested.getCards()[53] = new JokerCard();
            }

            for (int i = 0; i < deckBeingTested.getSize(); i++) {
                // This loop will run through the entire deck. The suit of the card at the current index will increment
                // it's corresponding suit counter.
                if (deckBeingTested.getCards()[i].getSuit().equals("Club")) {
                    clubCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Heart")) {
                    heartCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Spade")) {
                    spadeCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Diamond")) {
                    diamondCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Joker")) {
                    jokerCounter++;
                }
            }

            // Series of assertion, comparing expected counts to actual counts.
            Assertions.assertEquals(expectedSuitCount, clubCounter);
            Assertions.assertEquals(expectedSuitCount, heartCounter);
            Assertions.assertEquals(expectedSuitCount, spadeCounter);
            Assertions.assertEquals(expectedSuitCount, diamondCounter);
            Assertions.assertEquals(expectedJokerCount, jokerCounter);

            step++; // Increments the step, which controls the loop and the deck variation.
        }
    }

    // Shuffler

    // Player

    // Dealer

    // Chips

}