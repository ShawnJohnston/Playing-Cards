import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    public void standardDeckSizeIsCorrect() {
        // This test compares the expected size of a standard deck (52) to the size resulting from class methods.
        Assertions.assertEquals(expectedDeckSize, standardDeck.getSize());
    }
    @Test
    public void variantDeckSizeIsCorrectGivenJokerCount() {
        // This test tests the expected sizes for variant decks (53 or 54) to the resulting value from class methods.
        Assertions.assertEquals(expectedDeckSizeOneJoker, deckWithOneJokers.getSize());
        Assertions.assertEquals(expectedDeckSizeTwoJokers, deckWithTwoJokers.getSize());
    }
    @Test
    public void thereAreFourOfEachCardValueInTheDeck() {
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
    public void thereAreThirteenOfEachCardSuitInTheDeck() {
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
    public void theDeckCanBeShuffledUsingEachMethodInTheShufflerClass() {
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
    // Hand Evaluator
    // Straights and Flushes
    @Test
    public void canEvaluateFlushes() {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Flush rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Every card is the same suit and the values do not matter for this scenario.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard kingSpades = new PlayingCard("King", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard twoSpades = new PlayingCard("2", "Spades");
        PlayingCard fourSpades = new PlayingCard("4", "Spades");

        // All 5 cards are added to the player's hand.
        hand.add(sevenSpades);
        hand.add(kingSpades);
        hand.add(sixSpades);
        hand.add(twoSpades);
        hand.add(fourSpades);

        // The evaluator will check if the player's hand evaluates to be a flush.
        // Finally, it is tested for truth. If true, the test passes.
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
    public void canEvaluateNotFlushes() {
        // This single test case will evaluate the inverse case of a flush evaluation for a hand.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // The hand must not contain all matching suits and the values do not matter for this scenario.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard kingSpades = new PlayingCard("King", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard twoSpades = new PlayingCard("2", "Spades");
        PlayingCard fourHearts = new PlayingCard("4", "Hearts");

        // All 5 cards are added to the player's hand.
        hand.add(sevenSpades);
        hand.add(kingSpades);
        hand.add(sixSpades);
        hand.add(twoSpades);
        hand.add(fourHearts);

        // The evaluator will check if the player's hand evaluates to be a flush.
        // Finally, it is tested for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAFlush());
    }
    @Test
    public void canEvaluateStraights() {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player("Bill");
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.add(sevenSpades);
        hand.add(jackHearts);
        hand.add(nineHearts);
        hand.add(eightClubs);
        hand.add(tenSpades);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
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
    public void canEvaluateAllStraights() {
        // This test will procedurally check ALL value combinations that are supposed to indicate the Straight rank.
        // An assertion will be made for each instance of a Straight hand.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;

        for (int i = 0; i < 9; i++) {
            hand.clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], "Spades");
                hand.add(cardsInHand[j]);
            }
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            Assertions.assertTrue(evaluator.isAStraight());
        }
    }
    @Test
    public void canEvaluateNotStraights() {
        // This test check that a particular 5 card poker hand does NOT evaluate to be a straight.
        // The hand is consecutive to 4 cards (in other words a "4-card straight", which is not a straight).
        // When evaluated in the handEvaluator, the boolean should return false. If so, the test passes.
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
    public void canEvaluateNotStraights1() {
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
    public void canEvaluateStraightFlushes() {
        // This test determines if the HandEvaluator can identify Straight Flushes.
        // 5 cards of consecutive values and matching suits are added to a player's hand.
        // Truth will be asserted upon the evaluation of a Straight Flush. If true, the test passes.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard fiveSpades = new PlayingCard("5", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightSpades = new PlayingCard("8", "Spades");
        PlayingCard nineSpades = new PlayingCard("9", "Spades");

        // Cards added to the hand out of order in order to validate HandEvaluator's ability to sort properly.
        // HandEvaluator cannot read Straights if the hand is out of order.
        hand.add(fiveSpades);
        hand.add(sixSpades);
        hand.add(nineSpades);
        hand.add(sevenSpades);
        hand.add(eightSpades);

        // The Hand is to be asserted true for Straight Flush, but false for Royal Flush.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraightFlush());
        Assertions.assertFalse(evaluator.isARoyalFlush());
    }
    @Test
    public void canEvaluateNotStraightFlushes() {
        // This test determines the inverse of truth to the evaluation of a Straight Flush (ie, NOT Straight Flush).
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard fiveSpades = new PlayingCard("5", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightSpades = new PlayingCard("8", "Spades");
        PlayingCard nineClubs = new PlayingCard("9", "Clubs");

        hand.add(fiveSpades);
        hand.add(sixSpades);
        hand.add(nineClubs);
        hand.add(sevenSpades);
        hand.add(eightSpades);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraightFlush());
        Assertions.assertFalse(evaluator.isARoyalFlush());
    }
    @Test
    public void canEvaluateStraightFlushWheel() {
        // This test evaluates the "wheel" special straight case as a Straight Flush.
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
    public void canEvaluateAllStraightFlushes() {
        // Note: This test is identical to the 'canEvaluateAllFlushes()' test. It is reused for the
        // distinct purpose to demonstrate the evaluation of Straight Flushes specifically.

        // This test will procedurally check ALL Straight possibilities (with disregard to suits).
        // An assertion will be made for each instance of a Straight hand.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;

        for (int i = 0; i < 9; i++) {
            hand.clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], "Spades");
                hand.add(cardsInHand[j]);
            }
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            Assertions.assertTrue(evaluator.isAStraightFlush());
        }
    }
    @Test
    public void canEvaluateAllRoyalFlushes() {
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

        for (PlayingCard card: hand) {
            card.setSuit("Hearts");
        }
        Assertions.assertTrue(evaluator.isARoyalFlush());

        for (PlayingCard card: hand) {
            card.setSuit("Clubs");
        }
        Assertions.assertTrue(evaluator.isARoyalFlush());

        for (PlayingCard card: hand) {
            card.setSuit("Diamonds");
        }
        Assertions.assertTrue(evaluator.isARoyalFlush());
    }
}