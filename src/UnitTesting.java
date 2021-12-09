import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;

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
        System.out.println("Standard deck size: \n" +
                            "Expected: " + expectedDeckSize + "\n" +
                            "Actual: " + standardDeck.getSize());
        Assertions.assertEquals(expectedDeckSize, standardDeck.getSize());
    }
    @Test
    public void variantDeckSizeIsCorrectGivenJokerCount() {
        // This test tests the expected sizes for variant decks (53 or 54) to the resulting value from class methods.
        System.out.println("Standard deck size w/ one Joker: \n" +
                "Expected: " + expectedDeckSizeOneJoker + "\n" +
                "Actual: " + deckWithOneJokers.getSize());
        System.out.println("Standard deck size w/ two Jokers: \n" +
                "Expected: " + expectedDeckSizeTwoJokers + "\n" +
                "Actual: " + deckWithTwoJokers.getSize());
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
            System.out.println("Card value: " + standardDeck.getCards()[i].getValue() + " x " + valueCounter[i]);
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
            System.out.println("Suit count with " + step + " Jokers" + "\n" +
                    "Clubs count: " + clubCounter + "\n" +
                    "Hearts count: " + heartCounter + "\n" +
                    "Spades count: " + spadeCounter + "\n" +
                    "Diamond count: " + diamondCounter + "\n" +
                    "Joker count: " + jokerCounter + "\n");
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
        System.out.println("Unshuffled:");
        for (int i = 0; i < shuffledDeck.getSize(); i++) {
            // This loop will run through the entire deck. The suit of the card at the current index will increment
            // it's corresponding suit counter.
            System.out.printf("%d. Unshuffled: %s of %s", i, standardDeck.getCards()[i].getValue(), standardDeck.getCards()[i].getSuit());
            System.out.printf("    Shuffled: %s of %s", shuffledDeck.getCards()[i].getValue(), shuffledDeck.getCards()[i].getSuit());
            System.out.println();
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
            System.out.println("\nShuffled");
            for (int i = 0; i < shuffledDeck.getSize(); i++) {
                System.out.printf("%d. Unshuffled: %s of %s", i, standardDeck.getCards()[i].getValue(), standardDeck.getCards()[i].getSuit());
                System.out.printf("    Shuffled: %s of %s", shuffledDeck.getCards()[i].getValue(), shuffledDeck.getCards()[i].getSuit());
                System.out.println();
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
        // Explicit cases.
        int iterationNumber = 1;
        Player player = new Player("Thomas");

        // This loop will test each suit possibility for a 5-card flush. Card values are irrelevant.
        for (String suit: suits) {
            player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                    suit, suit, suit, suit, suit));
            fiveCardHand_fiveCardFlush_True(player, iterationNumber);
            iterationNumber++;
        }

        // 6-Card hand.
        for (String suit: suits) {
            player.setHand(handBuilder("King", "7", "5", "2", "Ace", "3",
                    suit, suit, suit, suit, suit, suit));
            fiveCardHand_fiveCardFlush_True(player, iterationNumber);
            iterationNumber++;
        }

        // Random value cases.
        System.out.println("Iteration: Random 1");
        Random randomizer = new Random();
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        for (int i = 0; i < 1000; i++) {
            for (String suit: suits) {
                player.setHand(handBuilder(values[randomizer.nextInt(12)],values[randomizer.nextInt(12)],values[randomizer.nextInt(12)],values[randomizer.nextInt(12)],values[randomizer.nextInt(12)],
                        suit, suit, suit, suit, suit));
                fiveCardHand_fiveCardFlush_True(player, 9999);
            }
        }
    }
    @Test
    public void canEvaluateNotFlushes() {
        Player player = new Player();

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Hearts", "Spades", "Spades", "Spades", "Spades"));
        fiveCardHand_fiveCard_Flush_False(player, 1);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Spades", "Hearts", "Spades", "Spades", "Spades"));
        fiveCardHand_fiveCard_Flush_False(player, 2);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Spades", "Spades", "Hearts", "Spades", "Spades"));
        fiveCardHand_fiveCard_Flush_False(player, 3);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Spades", "Spades", "Spades", "Hearts", "Spades"));
        fiveCardHand_fiveCard_Flush_False(player, 4);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Spades", "Spades", "Spades", "Spades", "Hearts"));
        fiveCardHand_fiveCard_Flush_False(player, 5);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "Spades", "Spades", "Spades", "Spades", "asdf"));
        fiveCardHand_fiveCard_Flush_False(player, 6);

        player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                "asdf", "asdf", "asdf", "asdf", "asdf"));
        fiveCardHand_fiveCard_Flush_False(player, 7);
    }
    @Test
    public void canEvaluateStraights() {
        Player player = new Player();

        player.setHand(handBuilder("Ace", "2", "3", "4", "5",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCard_Straight_True(player, 1);

        player.setHand(handBuilder("2", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCard_Straight_True(player, 2);

        player.setHand(handBuilder("5", "6", "7", "8", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCard_Straight_True(player, 3);

        player.setHand(handBuilder("9", "10", "Jack", "Queen", "King",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCard_Straight_True(player, 4);

        player.setHand(handBuilder("10", "Jack", "Queen", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCard_Straight_True(player, 5);
    }
    @Test
    public void canEvaluateAllStraights() {
        // This test will procedurally check ALL value combinations that are supposed to indicate the Straight rank.
        // An assertion will be made for each Iteration of a Straight hand.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;

        for (int i = 0; i < 10; i++) {
            hand.clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], "Spades");
                hand.add(cardsInHand[j]);
            }
            printHand(hand);
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            Assertions.assertTrue(evaluator.isAStraight());
            printHandRanking(evaluator);
        }
    }
    @Test
    public void canEvaluateNotStraights() {
        Player player = new Player();

        player.setHand(handBuilder("Ace", "2", "3", "4", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 1);

        player.setHand(handBuilder("Ace", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 2);

        player.setHand(handBuilder("Ace", "2", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 3);

        player.setHand(handBuilder("Ace", "3", "5", "7", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 4);

        player.setHand(handBuilder("Ace", "Ace", "Ace", "Ace", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 5);
        player.setHand(handBuilder("2", "3", "4", "5", "5",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 6);

        player.setHand(handBuilder("2", "3", "Jack", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 7);

        player.setHand(handBuilder("9", "10", "Jack", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        fiveCardHand_fiveCardStraight_False(player, 8);
    }
    @Test
    public void scanEvaluateStraights6CardHand() {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.add(sevenSpades);
        hand.add(jackHearts);
        hand.add(nineHearts);
        hand.add(eightClubs);
        hand.add(tenSpades);
        hand.add(queenHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        evaluator.setHandSize(6);
        Assertions.assertTrue(evaluator.isAStraight());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateStraight6CardHand1() {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard kingHearts = new PlayingCard("King", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.add(sevenSpades);
        hand.add(jackHearts);
        hand.add(nineHearts);
        hand.add(eightClubs);
        hand.add(tenSpades);
        hand.add(kingHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        evaluator.setHandSize(6);
        Assertions.assertTrue(evaluator.isAStraight());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateNotStraights6CardHand() {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
        PlayingCard kingHearts = new PlayingCard("King", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.add(sevenSpades);
        hand.add(nineHearts);
        hand.add(eightClubs);
        hand.add(tenSpades);
        hand.add(queenHearts);
        hand.add(kingHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        evaluator.setHandSize(6);
        Assertions.assertFalse(evaluator.isAStraight());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateAllStraightFlushes() {
        // Note: This test is identical to the 'canEvaluateAllFlushes()' test. It is reused for the
        // distinct purpose to demonstrate the evaluation of Straight Flushes specifically.

        // This test will procedurally check ALL Straight possibilities (with disregard to suits).
        // An assertion will be made for each Iteration of a Straight hand.
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        PlayingCard[] cardsInHand;
        int iterationCount = 1;

        for (String suit : suits) {
            for (int j = 0; j < 9; j++) {
                hand.clear();
                cardsInHand = new PlayingCard[5];
                System.out.println("Iteration " + iterationCount + ": " + suit);
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(values[k + j], suit);
                    hand.add(cardsInHand[k]);
                }
                iterationCount++;
                printHand(hand);
                HandEvaluator evaluator = new HandEvaluator(player, hand);
                Assertions.assertTrue(evaluator.isAStraightFlush());
                printHandRanking(evaluator);
            }
        }

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

        printHand(hand);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraightFlush());
        Assertions.assertFalse(evaluator.isARoyalFlush());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateAllRoyalFlushes() {
        for (int i = 0; i < suits.length; i++) {
            fiveCardHand_fiveCardRoyalFlush_True(suits[i], (i + 1));
        }
    }

    // Determine Winner
    @Test
    public void canDetermineWinner() {
        Player player = new Player("Player");
        Player dealer = new Player(("Dealer"));

        player.setHand(handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamond", "Diamond", "Diamond", "Diamond", "Diamond"));
        dealer.setHand(handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Hearts", "Hearts", "Hearts", "Hearts", "Hearts"));

        HandEvaluator playerEvaluator = new HandEvaluator(player);
        HandEvaluator dealerEvaluator = new HandEvaluator(dealer);

        GameOutcome gameOutcome = new GameOutcome(playerEvaluator, dealerEvaluator);
        gameOutcome.compareRanks();

        printHandRanking(playerEvaluator);
        printHandRanking(dealerEvaluator);
        Assertions.assertNull(gameOutcome.getWinner());
    }

    // Methods
    public ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5,
                                                String suit1, String suit2, String suit3, String suit4, String suit5) {
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard card1 = new PlayingCard(value1, suit1);
        PlayingCard card2 = new PlayingCard(value2, suit2);
        PlayingCard card3 = new PlayingCard(value3, suit3);
        PlayingCard card4 = new PlayingCard(value4, suit4);
        PlayingCard card5 = new PlayingCard(value5, suit5);
        hand.add(card1);
        hand.add(card2);
        hand.add(card3);
        hand.add(card4);
        hand.add(card5);

        return hand;
    }
    public ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5, String value6,
                                              String suit1, String suit2, String suit3, String suit4, String suit5, String suit6) {
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard card1 = new PlayingCard(value1, suit1);
        PlayingCard card2 = new PlayingCard(value2, suit2);
        PlayingCard card3 = new PlayingCard(value3, suit3);
        PlayingCard card4 = new PlayingCard(value4, suit4);
        PlayingCard card5 = new PlayingCard(value5, suit5);
        PlayingCard card6 = new PlayingCard(value6, suit6);

        hand.add(card1);
        hand.add(card2);
        hand.add(card3);
        hand.add(card4);
        hand.add(card5);
        hand.add(card6);

        return hand;
    }
    public void printHand(ArrayList<PlayingCard> hand) {
        Player player = new Player();
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println("Player hand:");
        for (PlayingCard card : evaluator.getPlayerHand()) {
            System.out.printf("%s of %s \n", card.getValue(), card.getSuit());
        }
    }
    public void printHandRanking(HandEvaluator evaluator) {
        System.out.println("Hand rank evaluates to: " + evaluator.getHandRank().toString() + "\n");
    }
    public void fiveCardHand_fiveCardFlush_True(Player player, int iterationNumber) {
        // This single test case will evaluate whether or not a given Poker hand evaluates to be the Flush rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Every card is the same suit and the values do not matter for this scenario.

        System.out.println("Iteration " + iterationNumber);
        printHand(player.getHand());

        // The evaluator will check if the player's hand evaluates to be a flush.
        // Finally, it is tested for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player);
        Assertions.assertTrue(evaluator.isAFlush(), "Failure at iteration #" + iterationNumber);
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCard_Flush_False(Player player, int iterationNumber) {
        // This test case will evaluate the inverse case of a flush evaluation for a hand.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // The hand must not contain all matching suits and the values do not matter for this scenario.
        System.out.println("Iteration " + iterationNumber);
        printHand(player.getHand());

        // The evaluator will check if the player's hand evaluates to be a flush.
        // Finally, it is tested for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player);
        Assertions.assertFalse(evaluator.isAFlush());
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCard_Straight_True(Player player, int iterationNumber) {
        // This single test case will evaluate whether or not the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.

        System.out.println("Iteration " + iterationNumber);
        printHand(player.getHand());

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player);
        Assertions.assertTrue(evaluator.isAStraight(), "Failure at iteration " + iterationNumber);
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCardStraight_False(Player player, int iterationNumber) {
        // This test check that a particular 5 card poker hand does NOT evaluate to be a straight.
        // The hand is consecutive to 4 cards (in other words a "4-card straight", which is not a straight).
        // When evaluated in the handEvaluator, the boolean should return false. If so, the test passes.

        printHand(player.getHand());

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If false, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player);
        Assertions.assertFalse(evaluator.isAStraight(), "Failure at iteration " + iterationNumber);
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCardRoyalFlush_True(String suit, int iterationNumber) {
        Player player = new Player();
        ArrayList<PlayingCard> hand = new ArrayList<>();
        PlayingCard tenSuit = new PlayingCard("10", suit);
        PlayingCard jackSuit = new PlayingCard("Jack", suit);
        PlayingCard queenSuit = new PlayingCard("Queen", suit);
        PlayingCard kingSuit = new PlayingCard("King", suit);
        PlayingCard aceSuit = new PlayingCard("Ace", suit);

        hand.add(aceSuit);
        hand.add(jackSuit);
        hand.add(kingSuit);
        hand.add(tenSuit);
        hand.add(queenSuit);

        System.out.println("Iteration " + iterationNumber);
        printHand(hand);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isARoyalFlush());
        printHandRanking(evaluator);
    }

}