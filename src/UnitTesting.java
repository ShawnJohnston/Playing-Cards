import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class UnitTesting {
    // Initialization of test variables;

    // Integers of deck sizes.
    int expectedDeckSize = 52;
    int expectedDeckSizeOneJoker = 53;
    int expectedDeckSizeTwoJokers = 54;

    // Arrays representing card attributes.
    String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};

    // Objects of Deck variations.
    DeckOfCards standardDeck = new DeckOfCards();
    DeckOfCards deckWithOneJokers = new DeckOfCards(1);
    DeckOfCards deckWithTwoJokers = new DeckOfCards(2);

    // Deck of Cards
    @Test
    public void standardDeckSize() {
        // This test compares the expected size of a standard deck (52) to the size resulting from class methods.
        Assertions.assertEquals(expectedDeckSize, standardDeck.getSize());
    }
    @Test
    public void variantDeckSize() {
        // This test tests the expected sizes for variant decks (53 or 54) to the resulting value from class methods.
        Assertions.assertEquals(expectedDeckSizeOneJoker, deckWithOneJokers.getSize());
        Assertions.assertEquals(expectedDeckSizeTwoJokers, deckWithTwoJokers.getSize());
    }
    @Test
    public void cardObjectValueCount() {
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
    public void cardObjectSuits() {
        // This test checks that each suit appears in the deck exactly 13 times.

        // A while loop will be used to control the logic of this test.
        int step = 0; // Counter used for the while loop. Will work for 3 steps, then the loop will end.
        DeckOfCards deckBeingTested = new DeckOfCards(); // New deck object for testing.
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
                if (deckBeingTested.getCards()[i].getSuit().equals("Clubs")) {
                    clubCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Hearts")) {
                    heartCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Spades")) {
                    spadeCounter++;
                }
                if (deckBeingTested.getCards()[i].getSuit().equals("Diamonds")) {
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
    @Test
    public void shuffle() {
        DeckOfCards shuffledDeck = new DeckOfCards();
        int step = 0;

        for (int i = 0; i < shuffledDeck.getSize(); i++) {
            // This loop will run through the entire deck. The suit of the card at the current index will increment
            // it's corresponding suit counter.

            Assertions.assertEquals(standardDeck.getCards()[i].getSuit(), shuffledDeck.getCards()[i].getSuit());
            Assertions.assertEquals(standardDeck.getCards()[i].getValue(), shuffledDeck.getCards()[i].getValue());
        }
        Shuffler shuffler = new Shuffler();

        while (step < 2) {
            if (step == 0) {
                shuffledDeck = shuffler.random(shuffledDeck);
            }
            else if (step == 1) {
                shuffledDeck = shuffler.handShuffle(shuffledDeck);
            }

            String[] standardValues = new String[standardDeck.getSize()];
            String[] standardSuits = new String[standardDeck.getSize()];
            String[] shuffledValues = new String[shuffledDeck.getSize()];
            String[] shuffledSuits = new String[shuffledDeck.getSize()];

            for (int i = 0; i < shuffledDeck.getSize(); i++) {
                standardValues[i] = standardDeck.getCards()[i].getValue();
                shuffledValues[i] = shuffledDeck.getCards()[i].getValue();
            }
            for (int i = 0; i < shuffledDeck.getSize(); i++) {
                standardSuits[i] = standardDeck.getCards()[i].getSuit();
                shuffledSuits[i] = shuffledDeck.getCards()[i].getSuit();
            }

            Assertions.assertNotEquals(standardValues, shuffledValues);
            Assertions.assertNotEquals(standardSuits, shuffledSuits);

            step++;
        }
    }

    // Chips

    // Hand Evaluator
    // Straights and Flushes
    @Test
    public void canEvaluateFlushes() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard();
        PlayingCard kingSpades = new PlayingCard();
        PlayingCard sixSpades = new PlayingCard();
        PlayingCard twoSpades = new PlayingCard();
        PlayingCard fourSpades = new PlayingCard();

        sevenSpades.setValue("7");
        sevenSpades.setSuit("Spades");
        kingSpades.setValue("King");
        kingSpades.setSuit("Spades");
        sixSpades.setValue("6");
        sixSpades.setSuit("Spades");
        twoSpades.setValue("2");
        twoSpades.setSuit("Spades");
        fourSpades.setValue("4");
        fourSpades.setSuit("Spades");

        hand.add(sevenSpades);
        hand.add(kingSpades);
        hand.add(sixSpades);
        hand.add(twoSpades);
        hand.add(fourSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAFlush());
    }
    @Test
    public void canEvaluateFlushes1() {
        Player player = new Player("Bob");
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenHearts = new PlayingCard("7", "Hearts");
        PlayingCard kingSpades = new PlayingCard("King", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard twoSpades = new PlayingCard("2","Spades");
        PlayingCard fourSpades = new PlayingCard("4", "Spades");

        hand.add(sevenHearts);
        hand.add(kingSpades);
        hand.add(sixSpades);
        hand.add(twoSpades);
        hand.add(fourSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAFlush());
    }
    @Test
    public void canEvaluateStraights() {
        Player player = new Player("Bill");
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");

        hand.add(sevenSpades);
        hand.add(jackHearts);
        hand.add(nineHearts);
        hand.add(eightClubs);
        hand.add(tenSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraight());
    }
    @Test
    public void canEvaluateStraights1() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard aceSpades = new PlayingCard("Ace", "Spades");
        PlayingCard twoHearts = new PlayingCard("2", "Hearts");
        PlayingCard threeHearts = new PlayingCard("3", "Hearts");
        PlayingCard fourClubs = new PlayingCard("4", "Clubs");
        PlayingCard fiveSpades = new PlayingCard("5", "Spades");

        hand.add(aceSpades);
        hand.add(threeHearts);
        hand.add(fiveSpades);
        hand.add(fourClubs);
        hand.add(twoHearts);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraight());
    }
    @Test
    public void canEvaluateStraights2() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
        PlayingCard kingClubs = new PlayingCard("King", "Clubs");
        PlayingCard aceSpades = new PlayingCard("Ace", "Spades");

        hand.add(aceSpades);
        hand.add(jackHearts);
        hand.add(kingClubs);
        hand.add(tenSpades);
        hand.add(queenHearts);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraight());
    }
    @Test
    public void canEvaluateStraights3() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
        PlayingCard kingClubs = new PlayingCard("King", "Clubs");
        PlayingCard fourSpades = new PlayingCard("4", "Spades");

        hand.add(fourSpades);
        hand.add(jackHearts);
        hand.add(kingClubs);
        hand.add(tenSpades);
        hand.add(queenHearts);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraight());
    }
    @Test
    public void canEvaluateStraights4() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard aceSpades = new PlayingCard("Ace", "Spades");
        PlayingCard twoHearts = new PlayingCard("2", "Hearts");
        PlayingCard threeHearts = new PlayingCard("3", "Hearts");
        PlayingCard fourClubs = new PlayingCard("4", "Clubs");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");

        hand.add(aceSpades);
        hand.add(threeHearts);
        hand.add(sixSpades);
        hand.add(fourClubs);
        hand.add(twoHearts);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraight());
    }
    @Test
    public void canEvaluateStraightFlush() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard aceSpades = new PlayingCard("Ace", "Spades");
        PlayingCard twoSpades = new PlayingCard("2", "Spades");
        PlayingCard threeSpades = new PlayingCard("3", "Spades");
        PlayingCard fourSpades = new PlayingCard("4", "Spades");
        PlayingCard fiveSpades = new PlayingCard("5", "Spades");

        hand.add(aceSpades);
        hand.add(twoSpades);
        hand.add(fiveSpades);
        hand.add(fourSpades);
        hand.add(threeSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraightFlush());
        Assertions.assertFalse(evaluator.isARoyalFlush());
    }
    @Test
    public void canEvaluateRoyalFlush() {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackSpades = new PlayingCard("Jack", "Spades");
        PlayingCard queenSpades = new PlayingCard("Queen", "Spades");
        PlayingCard kingSpades = new PlayingCard("King", "Spades");
        PlayingCard aceSpades = new PlayingCard("Ace", "Spades");

        hand.add(aceSpades);
        hand.add(jackSpades);
        hand.add(kingSpades);
        hand.add(tenSpades);
        hand.add(queenSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isARoyalFlush());
    }
}