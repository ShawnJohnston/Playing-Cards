import java.util.ArrayList;
import java.util.Random;

public class Shuffler {
    // Constructor
    public Shuffler() {
    }

    public DeckOfCards random(DeckOfCards deck) {
        // This method will shuffle the deck using Java's Random class to randomize the array that contains each card.
        Random randomizer = new Random(); // 'Random' object.


        for (int i = 0; i < deck.getSize(); i++) {
            int randomPosition = randomizer.nextInt(deck.getSize()); // Random integer from 0 to the value of the deck size.
            PlayingCard temp = deck.getCards().get(i); // stores the current card in a temporary variable.
            deck.getCards().set(i, deck.getCards().get(randomPosition)); // The random index's card is assigned to the current index.
            deck.getCards().set(randomPosition, temp); // The temporary variable is assigned to the random index.
        }
        return deck; // To the deck object in main.
    }
    public DeckOfCards handShuffle(DeckOfCards deck) {
        // This method shuffles the deck using the Casino Poker Shuffle.
        int proceduralCount = 0; // There are 5 main steps to the hand shuffle procedure.

        while (proceduralCount < 5) {
            int stackNumber = 4; // Represents number of stacks used for the 'box' procedure.

            // Casino Poker Shuffle procedure is riffle, riffle, box, riffle, cut.
            switch (proceduralCount) {
                case 0, 1, 3 -> deck.setCards(riffle(deck)); // Riffle.
                case 2 -> deck.setCards(box(deck, stackNumber)); // Box.
                case 4 -> deck.setCards(cutTheDeck(deck)); // Cut.
            }
            proceduralCount++;
            }
        return deck; // To deck object in main.
    }
    private int setSplitPoint(DeckOfCards deck) {
        // This method defines the location in a deck of cards in which a top stack will separate from the rest of the deck.
        // This default version of the method is intended to separate a full deck of cards into two half stacks.
        Random randomizer = new Random();
        int deckMidPoint = deck.getSize() / 2;
        int variability = randomValueFromNormalDistribution(5);
        return deckMidPoint + variability;
    }
    private int setSplitPoint(DeckOfCards deck, int bounds) {
        // This overloaded method is used in the "Cut" step of the hand shuffle.
        int deckMidPoint = deck.getSize() / 2; // The index position representing the halfway point into the deck.
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
        int absoluteBounds = (bounds / 2); // Absolute value for creating a +/- effect.
        ArrayList<Integer> numberSpread = new ArrayList<>(); // The absoluteBounds range of numbers are added here.
                                                             // Numbers are included here (absoluteBounds) times - their distance from 0.
        // This 2D for-loop will add to numberSpread as to create a normal distribution of values, with 0 as the median.
        // Outer loop: (-absoluteBounds <= i <= absoluteBounds).
        // Inner loop: The current i value is added to numberSpread i times.
        for (int i = -absoluteBounds; i <= absoluteBounds; i++) {
            int constantMultiplier = absoluteBounds - Math.abs(i); // This is used to control the number of times i is added to numberSpread.
            for (int j = 0; j < constantMultiplier + 1; j++) {
                numberSpread.add(i);
            }
        }
        return numberSpread.get(randomizer.nextInt(numberSpread.size())); // A random value from the numberSpread returns.
    }
    private ArrayList<PlayingCard> riffle(DeckOfCards deck) {
        // This method will split the deck into two stacks, then be riffled back together to make a new full stack.
        ArrayList<PlayingCard> topStack = new ArrayList<>(); // Top half of the deck.
        ArrayList<PlayingCard> bottomStack = new ArrayList<PlayingCard>(); // Bottom half of the deck.

        // For every card in the deck up to and including the split point (the deck's midpoint with +/- 5 variance),
        // the current card index will be added to the top stack. The rest will be added to the bottom stack.
        for (int i = deck.getSize() - 1; i >= 0; i--) {
            if (i < setSplitPoint(deck)) {
                topStack.add(deck.getCards().get(i));
            }
            else {
                bottomStack.add(deck.getCards().get(i));
            }
        }

        ArrayList<PlayingCard> riffledDeck = new ArrayList<>(); // New arrangement of cards post-riffle.

        // This while-loop controls the riffling of the two stacks together.
        while (riffledDeck.size() < deck.getSize()) {
            // It is unlikely that cards will riffle exactly one at a time from each stack when shuffling by hand.
            // Variability is used here to create bunching together of the cards randomly.
            rifflingAction(riffledDeck, topStack);
            rifflingAction(riffledDeck, bottomStack);
        }
        // This loop incrementally assigns each card in the riffledDeck list to the deck's corresponding card index.
        for (int i = 0; i < deck.getSize(); i++) {
            deck.getCards().set(i, riffledDeck.get(i));
        }
        return deck.getCards(); // The riffle is complete. The result returns to main to overwrite the deck's card array.
    }
    private void rifflingAction(ArrayList<PlayingCard> riffledDeck, ArrayList<PlayingCard> stack) {
        // The actual riffling occurs here. Java's Random class is used to make the procedures of the shuffle inexact.
        Random randomizer = new Random();
        int variability = 1 + randomizer.nextInt(2); // Bunching of cards only happens with up to 3 cards at a time.
        if (!stack.isEmpty()) {
            // Nested if-else only executes as long as the stack is not empty.
            if (variability > stack.size()) {
                // If variability is greater than the remaining size of the stack, it could result in an index bounds error.
                // To prevent this, the entire stack is added to the deck,
                riffledDeck.addAll(stack);
            }
            else {
                // A random number of cards from this stack (from 1 - 3), will riffle into the riffledDeck list.
                for (int i = 0; i < variability; i++) {
                    riffledDeck.add(stack.get(stack.size() - 1)); // The current card is added to the riffleDeck list.
                    stack.remove(stack.size() - 1); // The added card is removed from the stack list.
                    if (stack.isEmpty()) {
                        // The stack no longer contains any cards. THe loop will break.
                        break;
                    }
                }
            }
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
        int bottomStackSize = deck.getSize() - topStackSize; // The remaining cards make up the bottom stack.


        ArrayList<PlayingCard> topStack = new ArrayList<>();
        ArrayList<PlayingCard> bottomStack = new ArrayList<>();

        int j = 0; // Secondary incrementer.
        // This for loop will assign each index of cards in the deck to the corresponding topStack index.
        for (int i = 0; i < deck.getSize(); i++) {
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
        for (int i = 0; i < deck.getSize(); i++) {
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
