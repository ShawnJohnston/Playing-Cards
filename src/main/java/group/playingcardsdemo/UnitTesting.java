package group.playingcardsdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class UnitTesting {
    // Initialization of test variables.

    // Integers of deck sizes.
    int expectedDeckSize = 52;
    int expectedDeckSizeOneJoker = 53;
    int expectedDeckSizeTwoJokers = 54;

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
                            "Actual: " + standardDeck.getMaxSize());
        Assertions.assertEquals(expectedDeckSize, standardDeck.getMaxSize());
    }
    @Test
    public void variantDeckSizeIsCorrectGivenJokerCount() {
        // This test tests the expected sizes for variant decks (53 or 54) to the resulting value from class methods.
        System.out.println("Standard deck size w/ one Joker: \n" +
                "Expected: " + expectedDeckSizeOneJoker + "\n" +
                "Actual: " + deckWithOneJokers.getMaxSize());
        System.out.println("Standard deck size w/ two Jokers: \n" +
                "Expected: " + expectedDeckSizeTwoJokers + "\n" +
                "Actual: " + deckWithTwoJokers.getMaxSize());
        Assertions.assertEquals(expectedDeckSizeOneJoker, deckWithOneJokers.getMaxSize());
        Assertions.assertEquals(expectedDeckSizeTwoJokers, deckWithTwoJokers.getMaxSize());
    }
    @Test
    public void thereAreFourOfEachCardValueInTheDeck() {
        // This test checks that each face value appears in the deck exactly 4 times.
        int[] valueCounter = new int[Global.VALUES.length]; // This array is used to run parallel to 'values' array to count each value.
        int expectedValueCount = 4;

        // 2D for loop.
        // Outer loop runs through each index of the 'values' array.
        // Inner loop runs through each index of 'cards' array that composes the deck object.
        for (int i = 0; i < Global.VALUES.length; i++) {
            for (int j = 0; j < standardDeck.getMaxSize(); j++) {
                if (Global.VALUES[i].equals(standardDeck.getCards().get(j).getValue())) {
                    // If the current 'i' index of 'values' matches the current 'j' index of cards in the deck.
                    valueCounter[i]++; // Increments the count for that value.
                }
            }
            // At this point, the value at the current index  of 'values' has run through each card in the deck.
            // The next index will run through the entire deck for matching Global.VALUES.
        }
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            System.out.println("Card value: " + standardDeck.getCards().get(i).getValue() + " x " + valueCounter[i]);
            Assertions.assertEquals(expectedValueCount, valueCounter[i]);
        }
    }
    @Test
    public void thereAreThirteenOfEachCardSuitInTheDeck() {
        // This test checks that each suit appears in the deck exactly 13 times.

        // A while loop will be used to control the logic of this test.
        int step = 0; // Counter used for the while loop. Will work for 3 steps, then the loop will end.
        int expectedSuitCount = 13; // Each suit should appear 13 times.

        while (step <=2) {
            int expectedJokerCount = step;
            DeckOfCards deckBeingTested = new DeckOfCards(step); // New deck object for testing.
            // Variables and counters.
            int clubCounter = 0;
            int heartCounter = 0;
            int spadeCounter = 0;
            int diamondCounter = 0;
            int jokerCounter = 0;

            if (step >= 1) {
                for (int i = 0; i < step; i++) {
                    deckBeingTested.getCards().add(new JokerCard());
                }
            }

            for (int i = 0; i < deckBeingTested.getMaxSize(); i++) {
                // This loop will run through the entire deck. The suit of the card at the current index will increment
                // it's corresponding suit counter.

                switch (deckBeingTested.getCards().get(i).getSuit()) {
                    case "Spades" -> spadeCounter++;
                    case "Hearts" -> heartCounter++;
                    case "Clubs" -> clubCounter++;
                    case "Diamonds" -> diamondCounter++;
                    default -> jokerCounter++;
                }
            }

            int[] counters = { spadeCounter, heartCounter, clubCounter, diamondCounter, jokerCounter};
            System.out.println("\nSuit count with deck containing " + step + " Jokers" + "\n");
            for (int i = 0; i < counters.length; i++) {
                    System.out.println(Global.SUITS[i] + ": " + counters[i]);
                    // Comparing expected counts to actual counts.
                    if (i < counters.length - 1) {
                        Assertions.assertEquals(expectedSuitCount, counters[i]);
                    }
                    else {
                        Assertions.assertEquals(expectedJokerCount, counters[i]);
                    }
            }

            step++; // Increments the step, which controls the loop and the deck variation.
        }
    }
    // Shuffler
    @Test
    public void theDeckCanBeShuffledUsingEachMethodInTheShufflerClass() {
        DeckOfCards shuffledDeck = new DeckOfCards();
        int step = 0;
        System.out.println("Pre-Shuffle:");
        System.out.println("Not Shuffled          Shuffled");
        for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
            // This loop will run through the entire deck. The suit of the card at the current index will increment
            // it's corresponding suit counter.
            System.out.print(i + ". " + standardDeck.getCards().get(i).getName() + "      ");
            System.out.print(shuffledDeck.getCards().get(i).getName() + "    ");
            System.out.println();
            Assertions.assertEquals(standardDeck.getCards().get(i).getName(), shuffledDeck.getCards().get(i).getName());
        }
        Shuffler shuffler = new Shuffler();

        while (step < 2) {
            if (step == 0) {
                System.out.println("\nComputerized Random Shuffle");
                shuffledDeck = shuffler.random(shuffledDeck);
            }
            else if (step == 1) {
                System.out.println("\nHand Shuffle");
                shuffledDeck = shuffler.handShuffle(shuffledDeck);
            }

            String[] standardCards = new String[standardDeck.getMaxSize()];
            String[] shuffledCards = new String[shuffledDeck.getMaxSize()];

            for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
                standardCards[i] = standardDeck.getCards().get(i).getName();
                shuffledCards[i] = shuffledDeck.getCards().get(i).getName();
            }
            System.out.println("Not Shuffled          Shuffled");
            for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
                System.out.print(i + ". " + standardDeck.getCards().get(i).getName() + "      ");
                System.out.print(shuffledDeck.getCards().get(i).getName() + "    ");
                System.out.println();
            }
            Assertions.assertNotEquals(standardCards, shuffledCards);

            step++;
        }
    }

    // Hand Evaluator
    // Straights and Flushes
    @Test
    public void canEvaluateFlushes() {
        // Explicit cases.
        int iterationNumber = 1;
        Player player = new Player();

        // This loop will test each suit possibility for a 5-card flush. Card values are irrelevant.
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
            fiveCardHand_fiveCardFlush_True(player, iterationNumber);
            iterationNumber++;
        }

        // 6-Card hand.
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            player.setHand(handBuilder("King", "7", "5", "2", "Ace",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
            fiveCardHand_fiveCardFlush_True(player, iterationNumber);
            iterationNumber++;
        }

        // Iterative value cases.
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            player.setHand(handBuilder(Global.VALUES[0],
                    Global.VALUES[1],
                    Global.VALUES[2],
                    Global.VALUES[3],
                    Global.VALUES[5],
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
            fiveCardHand_fiveCardFlush_True(player, i);
        }


        for (int i = 0; i < Global.VALUES.length - Global.straightFlushSize - 1; i++) {
            for (int j = 0; j < Global.SUITS.length - 1; j++) {
                player.setHand(handBuilder(
                        Global.VALUES[i],
                        Global.VALUES[i + 1],
                        Global.VALUES[i + 2],
                        Global.VALUES[i + 3],
                        Global.VALUES[i + 5],
                        Global.SUITS[j], Global.SUITS[j], Global.SUITS[j], Global.SUITS[j], Global.SUITS[j]));
                fiveCardHand_fiveCardFlush_True(player, i + 2);
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

        //player.setHand(handBuilder("Ace", "2", "3", "4", "5",
        //        "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        //HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        //Assertions.assertTrue(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("2", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));

        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("5", "6", "7", "8", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));

        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("9", "10", "Jack", "Queen", "King",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("10", "Jack", "Queen", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());
    }
    @Test
    public void canEvaluateStraightsByValue() {
        Player player = new Player();

        player.setHand(handBuilder("2", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("4", "5", "6", "7", "8",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("5", "6", "7", "8", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("9", "10", "Jack", "Queen", "King",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("10", "Jack", "Queen", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_True(player, player.getHand());
    }
    @Test
    public void canEvaluate7CardStraightsByValue() {
        Player player = new Player();

        player.setHand(handBuilder("2", "3", "4", "5", "6", "7", "8",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("4", "5", "6", "7", "8", "9","10",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("5", "6", "7", "8", "9", "10", "Jack",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("8", "9", "10", "Jack", "Queen", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
        straight_ByValue_True(player, player.getHand());

        player.setHand(handBuilder("7","8", "9", "10", "Jack", "Queen", "King",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
        straight_ByValue_True(player, player.getHand());
    }
    @Test
    public void canEvaluateAllStraights() {
        // This test will procedurally check ALL value combinations that are supposed to indicate the Straight rank.
        // An assertion will be made for each Iteration of a Straight hand.
        Player player = new Player();
        Hand hand = new Hand();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        PlayingCard[] cardsInHand;

        for (int i = 1; i < 11; i++) {
            hand = new Hand();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(Global.VALUESHIERARCHY[j + i], Global.SUITS[j]);
                hand.addCard(cardsInHand[j]);
            }
            printHand(hand);
            System.out.println();
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            Assertions.assertTrue(evaluator.isAStraight_ByValueData());
            printHandRanking(evaluator);
            System.out.println();
        }
    }
    @Test
    public void canEvaluateAllStraightsByValueData() {
        Player player = new Player();
        Hand hand = new Hand();

        int straightSize = 7;
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;

        int counter = 1;
        for (int i = 0; i < hand.getValueData().length; i++) {
            hand.getCards().clear();
            cardsInHand = new PlayingCard[7];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
                hand.addCard(cardsInHand[j]);
            }
            printHand(hand);
            if (counter == 5) {
                break;
            }
            if (hand.getValueData()[i] > 0 && hand.getValueData()[i + 1] > 0) {
                counter++;
            }
            else {
                counter = 1;
            }
            System.out.println(Arrays.toString(hand.getValueData()));
        }
        Assertions.assertEquals(5, counter);
    }
    @Test
    public void canEvaluateNotStraights() {
        Player player = new Player();

        //player.setHand(handBuilder("Ace", "2", "3", "4", "6",
        //        "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        //HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        //Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("Ace", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("Ace", "2", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("Ace", "3", "5", "7", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("Ace", "Ace", "Ace", "Ace", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("2", "3", "4", "5", "5",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("2", "3", "Jack", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());

        player.setHand(handBuilder("9", "10", "Jack", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());
    }
    @Test
    public void canEvaluateNotStraightsByValue() {
        Player player = new Player();

        player.setHand(handBuilder("Ace", "2", "3", "4", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());
        player.setHand(handBuilder("Ace", "3", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("Ace", "2", "4", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("Ace", "3", "5", "7", "9",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("Ace", "Ace", "Ace", "Ace", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("2", "3", "4", "5", "5",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("2", "3", "Jack", "5", "6",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());

        player.setHand(handBuilder("9", "10", "Jack", "King", "Ace",
                "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
        straight_ByValue_False(player, player.getHand());
    }
    @Test
    public void canEvaluateStraights6CardHand() {
        // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        Hand hand = new Hand();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.addCard(sevenSpades);
        hand.addCard(jackHearts);
        hand.addCard(nineHearts);
        hand.addCard(eightClubs);
        hand.addCard(tenSpades);
        hand.addCard(queenHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateStraight6CardHand1() {
        // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        Hand hand = new Hand();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
        PlayingCard kingHearts = new PlayingCard("King", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.addCard(sevenSpades);
        hand.addCard(jackHearts);
        hand.addCard(nineHearts);
        hand.addCard(eightClubs);
        hand.addCard(tenSpades);
        hand.addCard(kingHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isAStraight_ByValueData());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateNotStraights6CardHand() {
        // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
        Player player = new Player();
        Hand hand = new Hand();
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightClubs = new PlayingCard("8", "Clubs");
        PlayingCard nineHearts = new PlayingCard("9", "Hearts");
        PlayingCard tenSpades = new PlayingCard("10", "Spades");
        PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
        PlayingCard kingHearts = new PlayingCard("King", "Hearts");

        // Each card is added to the player's hand. They are added out of order intentionally.
        // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
        hand.addCard(sevenSpades);
        hand.addCard(nineHearts);
        hand.addCard(eightClubs);
        hand.addCard(tenSpades);
        hand.addCard(queenHearts);
        hand.addCard(kingHearts);

        printHand(hand);

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraight_ByValueData());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateAllStraightFlushes() {
        // Note: This test is identical to the 'canEvaluateAllFlushes()' test. It is reused for the
        // distinct purpose to demonstrate the evaluation of Straight Flushes specifically.

        // This test will procedurally check ALL Straight possibilities (disregarding suits).
        // An assertion will be made for each Iteration of a Straight hand.
        Player player = new Player();
        Hand hand = new Hand();

        // These values compose every value in a standard deck of playing cards.
        // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
        PlayingCard[] cardsInHand;
        int iterationCount = 1;

        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            for (int j = 0; j < 9; j++) {
                hand.getCards().clear();
                cardsInHand = new PlayingCard[5];
                System.out.println("Iteration " + iterationCount + ": " + Global.SUITS[i]);
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
                    hand.addCard(cardsInHand[k]);
                }
                iterationCount++;
                printHand(hand);
                HandEvaluator evaluator = new HandEvaluator(player, hand);
                Assertions.assertTrue(evaluator.isAStraightFlush());
                printHandRanking(evaluator);
                if (hand.getCards().get(hand.getCards().size() - 1).getValue().equals("Ace")) {
                    Assertions.assertEquals("RoyalFlush", evaluator.getHandRank().toString());
                }
                else {
                    Assertions.assertEquals("StraightFlush", evaluator.getHandRank().toString());
                }
            }
        }

    }
    @Test
    public void canEvaluateNotStraightFlushes() {
        // This test determines the inverse of truth to the evaluation of a Straight Flush (ie, NOT Straight Flush).
        Player player = new Player();
        Hand hand = new Hand();
        PlayingCard fiveSpades = new PlayingCard("5", "Spades");
        PlayingCard sixSpades = new PlayingCard("6", "Spades");
        PlayingCard sevenSpades = new PlayingCard("7", "Spades");
        PlayingCard eightSpades = new PlayingCard("8", "Spades");
        PlayingCard nineClubs = new PlayingCard("9", "Clubs");

        hand.addCard(fiveSpades);
        hand.addCard(sixSpades);
        hand.addCard(nineClubs);
        hand.addCard(sevenSpades);
        hand.addCard(eightSpades);

        printHand(hand);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertFalse(evaluator.isAStraightFlush());
        Assertions.assertFalse(evaluator.isARoyalFlush());
        printHandRanking(evaluator);
    }
    @Test
    public void canEvaluateAllRoyalFlushes() {
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            fiveCardHand_fiveCardRoyalFlush_True(Global.SUITS[i], (i + 1));
        }
    }
    // Determine Winner
    @Test
    public void canReadHandOfCards() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamond", "Diamond", "Diamond", "Diamond", "Diamond");
        hand.setHand(cards);
        for (int i = 0; i < hand.getCards().size(); i++) {
            System.out.println(hand.getCards().get(i).getName());
        }
    }
    @Test
    public void canCountValuesInHand() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getValueData()));
        int[] comparisonData = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0 };
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Assertions.assertEquals(hand.getValueData()[i], comparisonData[i]);
        }
    }
    @Test
    public void canCountSuitsInHand() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getSuitData()));
        int[] comparisonData = {1, 1, 2, 1, 0};
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Assertions.assertEquals(hand.getSuitData()[i], comparisonData[i]);
        }
    }
    @Test
    public void theHandContainsOnePair() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getPairs());

            Assertions.assertEquals(1, evaluator.getPairs().size());
            Assertions.assertEquals(Global.VALUES[i], evaluator.getPairs().get(0));
        }
    }
    @Test
    public void theHandContainsTwoPair() {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getPairs());

            Assertions.assertEquals(2, evaluator.getPairs().size());
            Assertions.assertEquals(Global.VALUES[i], evaluator.getPairs().get(1));
            Assertions.assertEquals(Global.VALUES[i + 1], evaluator.getPairs().get(0));
            Assertions.assertEquals("TwoPair", evaluator.getHandRank().toString());
        }
    }
    @Test
    public void theHandContainsTrips() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getTrips());

            Assertions.assertEquals(Global.VALUES[i], evaluator.getTrips().get(0));
        }
    }
    @Test
    public void theHandContainsQuads() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getQuadsValue());

            Assertions.assertEquals(4, hand.getValueData()[i]);
            Assertions.assertEquals("Quads", evaluator.getHandRank().toString());
        }
    }
    @Test
    public void theHandContainsFullHouse() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getFullHouse());

            Assertions.assertEquals(Global.VALUES[i], evaluator.getFullHouse().get(0));
            Assertions.assertEquals(Global.VALUES[i + 1], evaluator.getFullHouse().get(1));
            Assertions.assertEquals("FullHouse", evaluator.getHandRank().toString());
        }
    }
    @Test
    public void theHandContainsNoPair() {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder("Ace", "King", "Jack", "10", "9",
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getValueData()));

        for (int i = 0; i < hand.getValueData().length; i++) {
            Assertions.assertNotEquals(2, hand.getValueData()[i]);
        }
    }
    @Test
    public void theHandContainsNoTrips() {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getTrips());

            Assertions.assertEquals(0, evaluator.getTrips().size());
        }

        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                "Ace", "Ace", "2",
                Global.VALUES[13],
                Global.VALUES[13],
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);

        System.out.println(Arrays.toString(hand.getValueData()));
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println(evaluator.getTrips());

        Assertions.assertEquals(0, evaluator.getTrips().size());
    }
    @Test
    public void theHandContainsNoQuads() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getQuadsValue());

            Assertions.assertNotEquals(4, hand.getValueData()[i]);
        }
    }
    @Test
    public void canCountPairsInHand() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder("Ace", "Ace", "Jack", "10", "9",
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getValueData()));


    }

    // Hand comparisons
    @Test
    public void royalFlushVersusOtherHands() {
        Player player1 = new Player();
        Hand hand1 = new Hand();
        hand1.setHand(handBuilder(
                "Ace", "King", "Queen", "Jack", "10",
                "Spades", "Spades", "Spades", "Spades", "Spades"));
        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
        System.out.println("Player 1: " + evaluator1.getHandRank());

        handVersusRoyalFlush(evaluator1, "Tie");
        handVersusStraightFlush(evaluator1, "Player 1");
        handVersusQuads(evaluator1, "Player 1");
        handVersusFullHouse(evaluator1, "Player 1");
        handVersusFlush(evaluator1, "Player 1");
        handVersusStraight(evaluator1, "Player 1");
        handVersusTrips(evaluator1, "Player 1");
        handVersusTwoPair(evaluator1, "Player 1");
        handVersusPair(evaluator1, "Player 1");
        handVersusHighCard(evaluator1, "Player 1");
    }
    @Test
    public void straightFlushVersusOtherHands() {
        Player player1 = new Player();
        Hand hand1 = new Hand();
        PlayingCard[] cardsInHand;
        int iterationCount = 1;
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            for (int j = 0; j < 8; j++) {
                hand1.getCards().clear();
                cardsInHand = new PlayingCard[5];
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
                    hand1.addCard(cardsInHand[k]);
                }
                iterationCount++;
                HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
                Assertions.assertTrue(evaluator1.isAStraightFlush());
                printHandRanking(evaluator1);
                System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));

                handVersusRoyalFlush(evaluator1, "Player 2");
                handVersusQuads(evaluator1, "Player 1");
                handVersusFullHouse(evaluator1, "Player 1");
                handVersusFlush(evaluator1, "Player 1");
                handVersusStraight(evaluator1, "Player 1");
                handVersusTrips(evaluator1, "Player 1");
                handVersusTwoPair(evaluator1, "Player 1");
                handVersusPair(evaluator1, "Player 1");
                handVersusHighCard(evaluator1, "Player 1");
            }
        }
    }
    @Test
    public void quadsVersusOtherHands() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            
            handVersusFullHouse(evaluator1, "Player 1");
            handVersusFlush(evaluator1, "Player 1");
            handVersusStraight(evaluator1, "Player 1");
            handVersusTrips(evaluator1, "Player 1");
            handVersusTwoPair(evaluator1, "Player 1");
            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void fullHouseVersusOtherHands() {
        for (int i = Global.VALUES.length - 1; i >=2; i--) {
            Hand hand1 = new Hand();
            Player player1 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i - 1],
                    Global.VALUES[i - 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getFullHouse());
            
            handVersusFlush(evaluator1, "Player 1");
            handVersusStraight(evaluator1, "Player 1");
            handVersusTrips(evaluator1, "Player 1");
            handVersusTwoPair(evaluator1, "Player 1");
            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void flushVersusOtherHands() {
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            hand1.setHand(handBuilder(
                    "King", "7", "Jack", "5", "2",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));

            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getHandRank());

            handVersusStraight(evaluator1, "Player 1");
            handVersusTrips(evaluator1, "Player 1");
            handVersusTwoPair(evaluator1, "Player 1");
            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void straightVersusOtherHands() {
        Player player1 = new Player();
        Hand hand1;

        PlayingCard[] cardsInHand;
        for (int i = 0; i < 10; i++) {
            hand1 = new Hand();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(Global.VALUESHIERARCHY[j + i], Global.SUITS[j]);
                hand1.addCard(cardsInHand[j]);
            }
            printHand(hand1);
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println(evaluator1.getHandRank());

            handVersusTrips(evaluator1, "Player 1");
            handVersusTwoPair(evaluator1, "Player 1");
            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void tripsVersusOtherHands() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getTrips());


            handVersusTwoPair(evaluator1, "Player 1");
            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void twoPairVersusOtherHands() {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 2: " + evaluator1.getPairs());

            handVersusPair(evaluator1, "Player 1");
            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void pairVersusOtherHands() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 2: " + evaluator1.getPairs());

            handVersusHighCard(evaluator1, "Player 1");
        }
    }
    @Test
    public void highCardVersusOtherHands() {
        Player player1 = new Player();
        Hand hand1 = new Hand();
        hand1.setHand(handBuilder(
                "Ace", "King", "Queen", "Jack", "9",
                "Spades", "Hearts", "Hearts", "Clubs", "Diamonds"));
        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
        System.out.println("Player 1: " + evaluator1.getHandRank());

        handVersusHighCard(evaluator1, "Player 1");
    }

    @Test
    public void straightFlushVersusStraightFlushTie() {
        PlayingCard[] cardsInHand;
        int iterationCount = 1;
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player1 = new Player();
            Player player2 = new Player();
            Hand hand1 = new Hand();
            Hand hand2 = new Hand();

            for (int j = 0; j < 9; j++) {
                hand1.getCards().clear();
                hand2.getCards().clear();
                cardsInHand = new PlayingCard[5];
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
                    hand1.addCard(cardsInHand[k]);
                    hand2.addCard(cardsInHand[k]);
                }
                iterationCount++;
                HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
                HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
                //System.out.println("Player 1: " + Arrays.toString(evaluator1.getHand().getValueData()));
                //System.out.println("Player 2: " + Arrays.toString(evaluator2.getHand().getValueData()));
                System.out.println(evaluator2.getHandRank());
                GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
                Assertions.assertEquals("Tie", outcome.getWinner());
            }
        }
    }
    @Test
    public void quadsVersusQuadsTie() {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player1 = new Player();
            player1.setHand(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Diamonds", "Joker"));

            System.out.print("Player 1: " + Arrays.toString(player1.getHand().getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, player1.getHand());

            Player player2 = new Player();
            player2.setHand(player1.getHand().getCards());

            System.out.print("Player 2: " + Arrays.toString(player2.getHand().getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, player2.getHand());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void fullHouseVersusFullHouseTie() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand1 = new Hand();
            Player player1 = new Player();

            Hand hand2 = new Hand();
            Player player2 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);
            hand1.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getFullHouse());


            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getFullHouse());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void flushVersusFlushTie() {
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();

            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards =  handBuilder(
                    "King", "7", "Jack", "5", "2",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]);
            hand1.setHand(cards);
            hand2.setHand(cards);

            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getHandRank());

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void straightVersusStraightTie() {
        Player player1 = new Player();
        Hand hand1 = new Hand();

        Player player2 = new Player();
        Hand hand2 = new Hand();

        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;
        for (int i = 0; i < 10; i++) {
            hand1.getCards().clear();
            hand2.getCards().clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
                hand1.addCard(cardsInHand[j]);
                hand2.addCard(cardsInHand[j]);
            }
            System.out.print("Player 1: ");
            printHand(hand1);
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);

            System.out.print("Player 2: ");
            printHand(hand2);
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);

            GameOutcome outcome = new GameOutcome(evaluator1,evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void tripsVersusTripsTie() {
        for (int i = 2; i < Global.VALUES.length - 1; i++) {
            Hand hand1 = new Hand();
            Player player1 = new Player();
            
            Hand hand2 = new Hand();
            Player player2 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);
            hand2.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getTrips());

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getTrips());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void twoPairVersusTwoPairTie() {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();

            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);
            hand2.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getPairs());
            
            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getPairs());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void pairVersusPairTie() {
        for (int i = 3; i < Global.VALUES.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();
            
            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[2],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand1.setHand(cards);
            hand2.setHand(cards);
            
            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getPairs());

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getPairs());


            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void highCardVersusHighCardTie() {
        Player player1 = new Player();
        Hand hand1 = new Hand();
        
        Player player2 = new Player();
        Hand hand2 = new Hand();
        ArrayList<PlayingCard> cards = handBuilder(
                "Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Hearts", "Clubs", "Diamonds");
        hand1.setHand(cards);
        hand2.setHand(cards);

        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
        System.out.println("Player 1: " + evaluator1.getHandRank());
        
        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 2: " + evaluator2.getHandRank());

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals("Tie", outcome.getWinner());
    }

    // Drawing cards from deck
    @Test
    public void forEachDeckVariant() {
        canDrawFromDeckToPlayerHandAndDiscardFromHand(standardDeck, 0);
        canDrawFromDeckToPlayerHandAndDiscardFromHand(deckWithOneJokers, 1);
        canDrawFromDeckToPlayerHandAndDiscardFromHand(deckWithTwoJokers, 2);

        standardDeck = new DeckOfCards();
        deckWithOneJokers = new DeckOfCards(1);
        deckWithTwoJokers = new DeckOfCards(2);

        canDrawAllCardsFromDeck(standardDeck, 0);
        canDrawAllCardsFromDeck(deckWithOneJokers, 1);
        canDrawAllCardsFromDeck(deckWithTwoJokers, 2);
    }
    public void canDrawFromDeckToPlayerHandAndDiscardFromHand(DeckOfCards deck, int jokerCount) {
        Hand hand = new Hand();

        // Draw cards from deck.

        Assertions.assertEquals((52 + jokerCount), deck.getCurrentSize());

        System.out.println("Drawing cards from deck to hand \n");
        System.out.println("Deck size: " + deck.getCurrentSize());
        System.out.println("Top 5 cards: ");
        for (int i = 0; i < 5; i++) {
            System.out.println(deck.getCards().get(i).getName());
        }
        System.out.println();

        int counter = 0;
        while (counter < 5) {
            System.out.println("~~~~~~~~~~~~~");
            System.out.println("Top card of the deck: " + deck.getCards().get(0).getName());

            System.out.println("Drawing top card" + "\n");
            hand.addCard(deck.draw());

            Assertions.assertEquals(counter + 1, hand.getSize());
            Assertions.assertEquals(deck.getMaxSize() - (counter + 1), deck.getCurrentSize());

            System.out.println("Deck size: " + deck.getCurrentSize() + "\n");

            System.out.println("New top card: " + deck.getCards().get(0).getName());
            System.out.println("Cards in hand: ");
            for (PlayingCard card: hand.getCards()) {
                System.out.println(card.getName());
            }
            System.out.println();
            counter++;
        }

        // Discard from hand to deck.
        Discard discard = new Discard();
        System.out.println("Discarding cards from hand \n");
        while (hand.getSize() > 0) {
            System.out.println("~~~~~~~~~~~~~");


            System.out.println("Discard size: " + discard.getCurrentSize());
            System.out.println("Discarding: " + hand.getCards().get(0).getName());
            discard.addCard(hand.getCards().get(0));

            hand.removeCard(0);
            System.out.println("Hand size: " + hand.getSize() + "\n");
        }
        System.out.println("Cards in discard pile: ");
        for (PlayingCard card: discard.getCards()) {
            System.out.println(card.getName());
        }
    }
    public void canDrawAllCardsFromDeck(DeckOfCards deck, int jokers) {
        Hand hand = new Hand();

        Assertions.assertEquals(52 + jokers, deck.getCurrentSize());
        Assertions.assertEquals(0,hand.getSize());

        while (deck.getCurrentSize() > 0) {
            hand.addCard(deck.draw());
        }

        Assertions.assertEquals(0, deck.getCurrentSize());
        Assertions.assertEquals(52 + jokers, hand.getSize());
    }

    // Methods
    public ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5,
                                              String suit1, String suit2, String suit3, String suit4, String suit5) {
        ArrayList<PlayingCard> cards = new ArrayList<>();
        PlayingCard card1 = new PlayingCard(value1, suit1);
        PlayingCard card2 = new PlayingCard(value2, suit2);
        PlayingCard card3 = new PlayingCard(value3, suit3);
        PlayingCard card4 = new PlayingCard(value4, suit4);
        PlayingCard card5 = new PlayingCard(value5, suit5);
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        return cards;
    }
    public ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5, String value6, String value7,
                                              String suit1, String suit2, String suit3, String suit4, String suit5, String suit6, String suit7) {
        ArrayList<PlayingCard> cards = new ArrayList<>();
        PlayingCard card1 = new PlayingCard(value1, suit1);
        PlayingCard card2 = new PlayingCard(value2, suit2);
        PlayingCard card3 = new PlayingCard(value3, suit3);
        PlayingCard card4 = new PlayingCard(value4, suit4);
        PlayingCard card5 = new PlayingCard(value5, suit5);
        PlayingCard card6 = new PlayingCard(value6, suit6);
        PlayingCard card7 = new PlayingCard(value7, suit7);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);

        return cards;
    }
    public void printHand(Hand hand) {
        Player player = new Player();
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println("Player hand:");
        //for (PlayingCard card : evaluator.getHand().getCards()) {
        //    System.out.printf("%s of %s \n", card.getValue(), card.getSuit());
        //}
    }
    public void printHandRanking(HandEvaluator evaluator) {
        System.out.println("Hand rank evaluates to: " + evaluator.getHandRank().toString() + "\n");
    }
    public void fiveCardHand_fiveCardFlush_True(Player player, int iterationNumber) {
        // This single test case will evaluate if a given Poker hand evaluates to be the Flush rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Every card is the same suit and the values do not matter for this scenario.

        System.out.println("Iteration " + iterationNumber);
        printHand(player.getHand());

        // The evaluator will check if the player's hand evaluates to be a flush.
        // Finally, it is tested for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
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
        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAFlush());
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCard_Straight_True(Player player, int iterationNumber) {
        // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
        // A player, 5 playing cards, and the hand evaluator are used for the test.
        // Each card will be sorted, then the hand will be checked to see if the values are in sequence.

        System.out.println("Iteration " + iterationNumber);
        printHand(player.getHand());

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If true, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertTrue(evaluator.isAStraight_ByValueData(), "Failure at iteration " + iterationNumber);
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCardStraight_False(Player player, int iterationNumber) {
        // This test check that a particular 5 card poker hand does NOT evaluate to be a straight.
        // The hand is consecutive to 4 cards (in other words a "4-card straight", which is not a straight).
        // When evaluated in the handEvaluator, the boolean should return false. If so, the test passes.

        printHand(player.getHand());

        // The evaluator will check if the hand values are consecutive.
        // The boolean value is checked for truth. If false, the test passes.
        HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
        Assertions.assertFalse(evaluator.isAStraight_ByValueData(), "Failure at iteration " + iterationNumber);
        printHandRanking(evaluator);
    }
    public void fiveCardHand_fiveCardRoyalFlush_True(String suit, int iterationNumber) {
        Player player = new Player();
        Hand hand = new Hand();
        PlayingCard tenSuit = new PlayingCard("10", suit);
        PlayingCard jackSuit = new PlayingCard("Jack", suit);
        PlayingCard queenSuit = new PlayingCard("Queen", suit);
        PlayingCard kingSuit = new PlayingCard("King", suit);
        PlayingCard aceSuit = new PlayingCard("Ace", suit);

        hand.addCard(aceSuit);
        hand.addCard(jackSuit);
        hand.addCard(kingSuit);
        hand.addCard(tenSuit);
        hand.addCard(queenSuit);

        System.out.println("Iteration " + iterationNumber);
        printHand(hand);

        HandEvaluator evaluator = new HandEvaluator(player, hand);
        Assertions.assertTrue(evaluator.isARoyalFlush());
        printHandRanking(evaluator);
        Assertions.assertEquals("RoyalFlush", evaluator.getHandRank().toString());
    }
    public void straight_ByValue_True(Player player, Hand hand) {
        int counter = 1;
        printHand(hand);
        for (int i = 0; i < Global.VALUES.length; i++) {
            if (counter == 5) {
                if (hand.getValueData()[i] > 0 && hand.getValueData()[i+1] > 0) {
                    counter++;
                }
                if (hand.getValueData()[i + 1] > 0 && hand.getValueData()[i + 2] > 0) {
                    counter++;
                }
                break;
            }
            if (hand.getValueData()[i] > 0 && hand.getValueData()[i+1] > 0) {
                counter++;
            }
            else {
                counter = 1;
            }
        }
        System.out.println(counter);
        Assertions.assertTrue(counter >= 5);
    }
    public void straight_ByValue_False(Player player, Hand hand) {
        int counter = 1;
        printHand(hand);
        System.out.println(Arrays.toString(hand.getValueData()));
        for (int i = 1; i < Global.VALUES.length; i++) {
            if (hand.getValueData()[i] > 1 && hand.getValueData()[i+1] > 0) {
                counter++;
            }
            else {
                counter = 1;
            }
        }
        System.out.println(counter);
        Assertions.assertTrue(counter < 5);
    }

    public void handVersusRoyalFlush(HandEvaluator evaluator1, String winner) {
        Player player2 = new Player();
        Hand hand2 = new Hand();
        hand2.setHand(handBuilder(
                "Ace", "King", "Queen", "Jack", "10",
                "Spades", "Spades", "Spades", "Spades", "Spades"));
        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 1: " + evaluator1.getHandRank());

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals(winner, outcome.getWinner());
    }
    public void handVersusStraightFlush(HandEvaluator evaluator1, String winner) {
        Player player2 = new Player();
        Hand hand2 = new Hand();

        PlayingCard[] cardsInHand;
        int iterationCount = 1;
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            for (int j = 0; j < 8; j++) {
                hand2.getCards().clear();
                cardsInHand = new PlayingCard[5];
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
                    hand2.addCard(cardsInHand[k]);
                }
                iterationCount++;
                HandEvaluator evaluator = new HandEvaluator(player2, hand2);
                Assertions.assertTrue(evaluator.isAStraightFlush());
                printHandRanking(evaluator);
                HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
                System.out.println(evaluator2.getHandRank());
                GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
                Assertions.assertEquals(winner, outcome.getWinner());
            }
        }
    }
    public void handVersusQuads(HandEvaluator evaluator1, String winner) {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getHandRank());
            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusFullHouse(HandEvaluator evaluator1, String winner) {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand2 = new Hand();
            Player player2 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getFullHouse());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusFlush(HandEvaluator evaluator1, String winner) {
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            hand2.setHand(handBuilder(
                    "King", "7", "Jack", "5", "2",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusStraight(HandEvaluator evaluator1, String winner) {
        Player player2 = new Player();
        Hand hand2 = new Hand();

        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;
        for (int i = 0; i < 10; i++) {
            hand2.getCards().clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
                hand2.addCard(cardsInHand[j]);
            }
            System.out.print("Player 2: ");
            printHand(hand2);
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);

            GameOutcome outcome = new GameOutcome(evaluator1,evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusTrips(HandEvaluator evaluator1, String winner) {
        for (int i = 2; i < Global.VALUES.length - 2; i++) {
            Player player2 = new Player();
            player2.setHand(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));

            System.out.println("Player 2: " + Arrays.toString(player2.getHand().getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, player2.getHand());
            System.out.println("Player 2: " + evaluator2.getTrips());

            System.out.println();
            System.out.println(Arrays.toString(evaluator1.getRankData()));
            System.out.println(evaluator1.getHandRank());

            System.out.println(Arrays.toString(evaluator2.getRankData()));
            System.out.println(evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusTwoPair(HandEvaluator evaluator1, String winner) {
        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getPairs());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusPair(HandEvaluator evaluator1, String winner) {
        for (int i = 3; i < Global.VALUES.length - 1; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[2],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getPairs());


            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusHighCard(HandEvaluator evaluator1, String winner) {
        Player player2 = new Player();
        Hand hand2 = new Hand();
        hand2.setHand(handBuilder(
                "Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Hearts", "Clubs", "Diamonds"));
        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 2: " + evaluator2.getHandRank());

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals(winner, outcome.getWinner());
    }
    @Test
    public void noDuplicateCardsAfterHandShuffleMultiplier() {
        int counter = 0;
        while (counter < 100) {
            noDuplicateCardsAfterShuffle(handShuffleDeck());
            counter++;
        }
    }
    @Test
    public void noDuplicateCardsAfterRandomShuffleMultiplier() {
        int counter = 0;
        while (counter < 100) {
            noDuplicateCardsAfterShuffle(randomShuffleDeck());
            counter++;
        }
    }
    public DeckOfCards handShuffleDeck() {
        DeckOfCards deck = new DeckOfCards();
        Shuffler shuffler = new Shuffler();
        shuffler.handShuffle(deck);
        return deck;
    }
    public DeckOfCards randomShuffleDeck() {
        DeckOfCards deck = new DeckOfCards();
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);
        return deck;
    }
    public void noDuplicateCardsAfterShuffle(DeckOfCards deck) {
        int[] valueCounter = new int[14];

        for (int i = 0; i < deck.getMaxSize(); i++) {
            switch (deck.getCards().get(i).getValue()) {
                case "2" -> valueCounter[0]++;
                case "3" -> valueCounter[1]++;
                case "4" -> valueCounter[2]++;
                case "5" -> valueCounter[3]++;
                case "6" -> valueCounter[4]++;
                case "7" -> valueCounter[5]++;
                case "8" -> valueCounter[6]++;
                case "9" -> valueCounter[7]++;
                case "10" -> valueCounter[8]++;
                case "Jack" -> valueCounter[9]++;
                case "Queen" -> valueCounter[10]++;
                case "King" -> valueCounter[11]++;
                case "Ace" -> valueCounter[12]++;
                case "Joker" -> valueCounter[13]++;
            }
        }
        for (int i = 0; i < valueCounter.length - 1; i++) {
            Assertions.assertEquals(4, valueCounter[i]);
        }
    }
}