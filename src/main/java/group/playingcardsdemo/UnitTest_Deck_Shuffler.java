package group.playingcardsdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class UnitTest_Deck_Shuffler {
    @Test
    public void canInstantiateDeck_WithOrWithoutJokers() throws FileNotFoundException {
        int deckSize = 52;

        for (int i = 0; i < 3; i++) {
            DeckOfCards deck;
            switch (i) {
                case 0:
                    deck = new DeckOfCards();
                    break;
                default:
                    deckSize++;
                    deck = new DeckOfCards(i);
                    break;
            }
            Assertions.assertEquals(deckSize, deck.getMaxSize());
            Assertions.assertEquals(deckSize, deck.getCurrentSize());

        }
    }

    @Test
    public void thereAreFourOfEachCardValueInTheDeck() throws FileNotFoundException {
        DeckOfCards deck = new DeckOfCards();
        // This test checks that each face value appears in the deck exactly 4 times.
        int[] valueCounter = new int[PlayingCard.VALUES.length]; // This array is used to run parallel to 'values' array to count each value.
        int expectedValueCount = 4;

        // 2D for loop.
        // Outer loop runs through each index of the 'values' array.
        // Inner loop runs through each index of 'cards' array that composes the deck object.
        for (int i = 0; i < PlayingCard.VALUES.length; i++) {
            for (int j = 0; j < deck.getMaxSize(); j++) {
                if (PlayingCard.VALUES[i].equals(deck.getCards().get(j).getValue())) {
                    // If the current 'i' index of 'values' matches the current 'j' index of cards in the deck.
                    valueCounter[i]++; // Increments the count for that value.
                }
            }
            // At this point, the value at the current index  of 'values' has run through each card in the deck.
            // The next index will run through the entire deck for matching PlayingCard.VALUES.
        }
        for (int i = 0; i < PlayingCard.VALUES.length - 1; i++) {
            System.out.println("Card value: " + deck.getCards().get(i).getValue() + " x " + valueCounter[i]);
            Assertions.assertEquals(expectedValueCount, valueCounter[i]);
        }
    }
    @Test
    public void thereAreThirteenOfEachCardSuitInTheDeck() throws FileNotFoundException {
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
                System.out.println(PlayingCard.SUITS[i] + ": " + counters[i]);
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

    @Test
    public void theDeckCanBeShuffledUsingEachMethodInTheShufflerClass() throws FileNotFoundException {
        DeckOfCards deck = new DeckOfCards();
        DeckOfCards shuffledDeck = new DeckOfCards();
        int step = 0;
        System.out.println("Pre-Shuffle:");
        System.out.println("Not Shuffled          Shuffled");
        for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
            // This loop will run through the entire deck. The suit of the card at the current index will increment
            // it's corresponding suit counter.
            System.out.print(i + ". " + deck.getCards().get(i).getName() + "      ");
            System.out.print(shuffledDeck.getCards().get(i).getName() + "    ");
            System.out.println();
            Assertions.assertEquals(deck.getCards().get(i).getName(), shuffledDeck.getCards().get(i).getName());
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

            String[] standardCards = new String[deck.getMaxSize()];
            String[] shuffledCards = new String[shuffledDeck.getMaxSize()];

            for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
                standardCards[i] = deck.getCards().get(i).getName();
                shuffledCards[i] = shuffledDeck.getCards().get(i).getName();
            }
            System.out.println("Not Shuffled          Shuffled");
            for (int i = 0; i < shuffledDeck.getMaxSize(); i++) {
                System.out.print(i + ". " + deck.getCards().get(i).getName() + "      ");
                System.out.print(shuffledDeck.getCards().get(i).getName() + "    ");
                System.out.println();
            }
            Assertions.assertNotEquals(standardCards, shuffledCards);

            step++;
        }
    }
    @Test
    public void canDrawDeckDeckToPlayerHandThenDiscard_forEachDeckVariant() throws FileNotFoundException {
        canDrawFromDeckToPlayerHandAndDiscardFromHand(new DeckOfCards(), 0);
        canDrawFromDeckToPlayerHandAndDiscardFromHand(new DeckOfCards(1), 1);
        canDrawFromDeckToPlayerHandAndDiscardFromHand(new DeckOfCards(2), 2);

        canDrawAllCardsFromDeck(new DeckOfCards(), 0);
        canDrawAllCardsFromDeck(new DeckOfCards(1), 1);
        canDrawAllCardsFromDeck(new DeckOfCards(2), 2);
    }
    public void canDrawFromDeckToPlayerHandAndDiscardFromHand(DeckOfCards deck, int jokerCount) throws FileNotFoundException {
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
            hand.addCard(deck.drawTopCard());

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
            hand.addCard(deck.drawTopCard());
        }

        Assertions.assertEquals(0, deck.getCurrentSize());
        Assertions.assertEquals(52 + jokers, hand.getSize());
    }
    @Test
    public void noDuplicateCardsAfterHandShuffleMultiplier() throws FileNotFoundException {
        int counter = 0;
        while (counter < 100) {
            noDuplicateCardsAfterShuffle(handShuffleDeck());
            counter++;
        }
    }
    @Test
    public void noDuplicateCardsAfterRandomShuffleMultiplier() throws FileNotFoundException {
        int counter = 0;
        while (counter < 100) {
            noDuplicateCardsAfterShuffle(randomShuffleDeck());
            counter++;
        }
    }
    public DeckOfCards handShuffleDeck() throws FileNotFoundException {
        DeckOfCards deck = new DeckOfCards();
        Shuffler shuffler = new Shuffler();
        shuffler.handShuffle(deck);
        return deck;
    }
    public DeckOfCards randomShuffleDeck() throws FileNotFoundException {
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
