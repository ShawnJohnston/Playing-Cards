package group.playingcardsdemo;

import java.util.ArrayList;
import java.util.Random;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Shuffler {

    public DeckOfCards random(DeckOfCards deck) {
        /*
            This method will shuffle the deck using Java's Random class to randomize the array that contains each card.

            1.  Initialize randomizer.
            2.  For Loop: Range 0 <= i < current deck size. This loop will iterate through each index of the deck.
                -   A random index value is initialized using the randomizer to get a pseudorandom integer from 0 to the
                    numerical size of the deck.
                -   A temporary 'PlayingCard' variable is initialized to store the 'PlayingCard' at current index 'i'.
                -   The 'PlayingCard' at the current index is overwritten by the 'PlayingCard' at the random index.
                -   The 'PlayingCard' at the random index is overwritten by the temporary 'PlayingCard'.
            3.  After the loop iterates through each index, the deck has completed shuffling and returns.
         */

        Random randomizer = new Random();
        for (int i = 0; i < deck.getCurrentSize(); i++) {
            int randomPosition = randomizer.nextInt(deck.currentSize);
            PlayingCard temp = deck.getCards().get(i);
            deck.getCards().set(i, deck.getCards().get(randomPosition));
            deck.getCards().set(randomPosition, temp);
        }
        return deck;
    }
    public DeckOfCards handShuffle(DeckOfCards deck) {
        /*
            This method shuffles the deck using the Casino Poker Shuffle. Note: This algorithm is substantially less
            computationally efficient than the other card randomizing method and provides no noticeable benefit other
            than to provide a sense of "procedural correctness" expected in a live casino setting. This method was
            written mainly to demonstrate what a proper hand shuffle would look like as an algorithm.

            There are 5 main steps to the hand shuffle procedure: riffle, riffle, box, riffle, cut.

            1.  Initialize a procedure counter.
            2.  While Loop: Range 0 <= x < 5. Uses a switch statement to control which steps to use in which order.
                Increment the counter at the end of each loop.
            3.  After the shuffle is complete, the deck returns.
         */


        int proceduralCount = 0;
        while (proceduralCount < 5) {
            int stackNumber = 4; // Represents number of stacks used for the 'box' procedure.

            switch (proceduralCount) {
                case 0, 1, 3 -> deck.setCards(riffle(deck)); // Riffle.
                case 2 -> deck.setCards(box(deck, stackNumber)); // Box.
                case 4 -> deck.setCards(cutTheDeck(deck)); // Cut.
            }
            proceduralCount++;
        }
        return deck;
    }
    private int setSplitPoint(DeckOfCards deck, int bounds) {
        /*
         This method defines the location in a deck of cards in which a top stack will separate from the rest of the
         deck. Variability is implemented here to create an element of human imprecision to the procedure.

         1. Initialize the midpoint of the deck. for a 52-card deck, that would be 26.
         2. Initialize variability using a method that simulates a crude bell curve distribution.
         3. Variability is added to the midpoint and returned.
         */

        int deckMidPoint = deck.getCurrentSize() / 2;
        int variability = randomValueFromNormalDistribution(bounds);
        return deckMidPoint + variability;
    }
    private int setSplitPointForBox(int cardsSize, int boxCount) {
        // This variant of setSplitPoint is intended for the "Box" step in the hand shuffle. Every time the recursive box
        // uses this method, cardsSize (the size of the deck) will be smaller due to previous calls removing from it.
        Random randomizer = new Random();
        int deckSegment = cardsSize / boxCount; // boxCount is decremented each level into the recursion. The deck gets
        // smaller every level, but the segments become relatively larger.
        int variability = randomValueFromNormalDistribution(5); // Variability of +/- 5 cards.
        return deckSegment + variability;
    }
    private int randomValueFromNormalDistribution(int bounds) {
        // This method is used to create a normal distribution of values (resembling a bell curve). Mainly used in setSplitPoint methods.
        // The rationale behind this method is that for many situations of random values within a range of possibilities,
        // some values are more likely than others. For example, when cutting a deck of cards, it is possible for the cut to
        // happen 10 steps from the midpoint, but it is more likely that the cut will be closer. (Refer to "standard deviation" in Statistics texts.)
        // To select any random card from a deck of cards, however, all possibilities are equally likely. This results in a flat distribution.
        // This method is NOT to be used for any scenario of a flat distribution.
        Random randomizer = new Random();
        ArrayList<Integer> numberSpread = new ArrayList<>(); // The absoluteBounds range of numbers are added here.
        // Numbers are included here (absoluteBounds) times - their distance from 0.
        // This 2D for-loop will add to numberSpread as to create a normal distribution of values, with 0 as the median.
        // Outer loop: (-absoluteBounds <= i <= absoluteBounds).
        // Inner loop: The current i value is added to numberSpread i times.
        for (int i = -bounds; i <= bounds; i++) {
            int constantMultiplier = bounds - Math.abs(i); // This is used to control the number of times i is added to numberSpread.
            for (int j = 0; j < constantMultiplier + 1; j++) {
                numberSpread.add(i);
            }
        }
        int randomIndex = randomizer.nextInt(numberSpread.size());
        return numberSpread.get(randomIndex); // A random value from the numberSpread returns.
    }
    private ArrayList<PlayingCard> riffle(DeckOfCards deck) {
        // This method will split the deck into two stacks, then be riffled back together to make a new full stack.
        ArrayList<PlayingCard> topStack = new ArrayList<>(); // Top half of the deck.
        ArrayList<PlayingCard> bottomStack = new ArrayList<>(); // Bottom half of the deck.

        // For every card in the deck up to and including the split point (the deck's midpoint with +/- 5 variance),
        // the current card index will be added to the top stack. The rest will be added to the bottom stack.
        int splitPoint = setSplitPoint(deck, 5);
        for (int i = 0; i < deck.getCurrentSize(); i++) {
            if (i < splitPoint) {
                topStack.add(deck.getCards().get(i));
            }
            else {
                bottomStack.add(deck.getCards().get(i));
            }
        }

        ArrayList<PlayingCard> riffledDeck = new ArrayList<>(); // New arrangement of cards post-riffle.

        // This while-loop controls the riffling of the two stacks together.
        while (riffledDeck.size() < deck.getCurrentSize()) {
            // It is unlikely that cards will riffle exactly one at a time from each stack when shuffling by hand.
            // Variability is used here to create bunching together of the cards randomly.
            rifflingAction(riffledDeck, topStack, bottomStack);
        }
        // This loop incrementally assigns each card in the riffledDeck list to the deck's corresponding card index.
        for (int i = 0; i < deck.getCurrentSize(); i++) {
            deck.getCards().set(i, riffledDeck.get(i));
        }
        return deck.getCards(); // The riffle is complete. The result returns to main to overwrite the deck's card array.
    }
    private void rifflingAction(ArrayList<PlayingCard> riffledDeck, ArrayList<PlayingCard> topStack, ArrayList<PlayingCard> bottomStack) {
        // The actual riffling occurs here. Java's Random class is used to make the procedures of the shuffle inexact.
        Random randomizer = new Random();

        boolean stackSelector = randomizer.nextBoolean();
        while (!topStack.isEmpty() || !bottomStack.isEmpty()) {
            if (topStack.isEmpty()) {
                stackSelector = false;
            }
            else if (bottomStack.isEmpty()) {
                stackSelector = true;
            }
            int variability = 1 + randomizer.nextInt(2); // Bunching of cards only happens with up to 3 cards at a time.
            if (stackSelector) {
                int counter = 0;
                while (!topStack.isEmpty()) {
                    if (counter == variability) {
                        break;
                    }
                    riffledDeck.add(topStack.get(topStack.size() - 1));
                    topStack.remove(topStack.size() - 1);
                    counter++;
                }
            }
            else {
                int counter = 0;
                while (!bottomStack.isEmpty()) {
                    if (counter == variability) {
                        break;
                    }
                    riffledDeck.add(bottomStack.get(bottomStack.size() - 1));
                    bottomStack.remove(bottomStack.size() - 1);
                    counter++;
                }
            }
            stackSelector = !stackSelector;
        }
    }
    private ArrayList<PlayingCard> box(DeckOfCards deck, int boxCount) {
        // The box method will use several lists, and use recursion to perform the entire procedure.

        ArrayList<PlayingCard> cards = deck.getCards(); // The deck's cards are stored as a list.
        ArrayList<PlayingCard> deckBox; // This list will have stacks added to it recursively.
        ArrayList<PlayingCard> stack = new ArrayList<>(); // Individual stacks will contain cards, and the stack will be added to deckBox.
        deckBox = boxTail(cards, stack, boxCount); // Recursive method to perform the box procedure.

        deck.setCards(deckBox);
        return deck.getCards(); // To deck in handShuffle method.
    }
    private ArrayList<PlayingCard> boxTail(ArrayList<PlayingCard> cards, ArrayList<PlayingCard> stack, int boxCount) {
        stack = new ArrayList<>();

        // Recursion base case.
        if (boxCount == 1) {
            stack.addAll(cards); // All remaining cards from the deck array are added to the current stack list.
            cards.clear(); // The cards list is emptied.
            return stack; // The stack returns recursively to box method.
        }

        // Instead of overloading the setSplitPoint method, I will just code the same functionality here since this approach
        // is tailored exclusively for this specific implementation.
        int splitPoint = setSplitPointForBox(cards.size(), boxCount);
        ArrayList<PlayingCard> tempStack = new ArrayList<>(); // Buffer list to store card information.

        if (splitPoint > cards.size()) {
            // If more cards can be added than there are cards in the deck, all cards will be added to the stack.
            stack.addAll(cards);
            cards.clear(); // Removes all cards added to the stack.
        }
        else {
            // The tempStack is filled with each card until the split point is reached.
            for (int i = splitPoint - 1; i >= 0; i--) {
                tempStack.add(cards.get(cards.size() - 1)); // Add to tempStack.
                cards.remove(cards.size() - 1); // Remove from card list.
            }
            stack.addAll(tempStack); // The cards from the tempStack are added to stack.
        }

        stack.addAll(boxTail(cards, stack, boxCount - 1)); // Tail recursion to continue the procedure.
        return stack; // Returns the stack to the next highest recursion level.
    }
    private ArrayList<PlayingCard> cutTheDeck(DeckOfCards deck) {
        // This method will split the deck into two stacks, then swap the position of the top and bottom stacks.

        int topStackSize = setSplitPoint(deck, 20); // Top stack will split at the deck midpoint (+/-) 10.
        int bottomStackSize = deck.getCurrentSize() - topStackSize; // The remaining cards make up the bottom stack.


        ArrayList<PlayingCard> topStack = new ArrayList<>();
        ArrayList<PlayingCard> bottomStack = new ArrayList<>();

        int j = 0; // Secondary incrementer.
        // This for loop will assign each index of cards in the deck to the corresponding topStack index.
        for (int i = 0; i < deck.getCurrentSize(); i++) {
            if (i < topStackSize) {
                topStack.add(deck.getCards().get(i));
            }
            else {
                // The split point has been passed, the card is assigned to bottomStack at index j.
                bottomStack.add(deck.getCards().get(i));
                j++;
            }
        }

        ArrayList<PlayingCard> cutDeck = new ArrayList<>(); // For containing the resulting card array.
        // This for loop will assign each card in bottomStack to the new deck array, then cards from topStack when it
        // increments beyond the length of bottomStack.
        for (int i = 0; i < deck.getCurrentSize(); i++) {
            if (i < bottomStackSize) {
                cutDeck.add(bottomStack.get(i));
            }
            else {
                cutDeck.add(topStack.get(i - bottomStackSize));
            }
        }
        return cutDeck; // To handShuffle method. Overwrites the deck's card array.
    }
}
