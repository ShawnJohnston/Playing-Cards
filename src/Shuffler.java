import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            PlayingCard temp = deck.getCards()[i]; // stores the current card in a temporary variable.
            deck.getCards()[i] = deck.getCards()[randomPosition]; // The random index's card is assigned to the current index.
            deck.getCards()[randomPosition] = temp; // The temporary variable is assigned to the random index.
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
        Random randomizer = new Random();
        int deckMidPoint = deck.getSize() / 2;
        int variability = randomizer.nextInt(10) - 5;
        return deckMidPoint + variability;
    }
    private int setSplitPoint(DeckOfCards deck, int bounds) {
        Random randomizer = new Random();
        int deckMidPoint = deck.getSize() / 2;
        int variability = randomizer.nextInt(bounds) - (bounds / 2);
        return deckMidPoint + variability;
    }
    private PlayingCard[] riffle(DeckOfCards deck) {
        // This method will split the deck into two stacks, then be riffled back together to make a new full stack.
        ArrayList<PlayingCard> topStack = new ArrayList<>(); // Top half of the deck.
        ArrayList<PlayingCard> bottomStack = new ArrayList<>(); // Bottom half of the deck.

        // For every card in the deck up to and including the split point (the deck's midpoint with +/- 5 variance),
        // the current card index will be added to the top stack. The rest will be added to the bottom stack.
        for (int i = 0; i < deck.getSize(); i++) {
            if (i < setSplitPoint(deck)) {
                topStack.add(deck.getCards()[i]);
            }
            else {
                bottomStack.add(deck.getCards()[i]);
            }
        }
        // Riffling starts at the bottom of each stack and proceeds upwards. Each stack is simply reversed.
        Collections.reverse(topStack);
        Collections.reverse(bottomStack);

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
            deck.getCards()[i] = riffledDeck.get(i);
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
    private PlayingCard[] box(DeckOfCards deck, int boxCount) {
        // The box method will use several lists, and use recursion to perform the entire procedure.

        ArrayList<PlayingCard> cards = new ArrayList<>(Arrays.asList(deck.getCards())); // The deck's cards are stored as a list.
        ArrayList<PlayingCard> deckBox; // This list will have stacks added to it recursively.
        ArrayList<PlayingCard> stack = new ArrayList<>(); // Individual stacks will contain cards, and the stack will be added to deckBox.
        deckBox = boxTail(cards, stack, boxCount); // Recursive method to perform the box procedure.
        for (int i = 0; i < deck.getSize(); i++) {
            deck.getCards()[i] = deckBox.get(i); // Each card index in the deck is reassigned a corresponding card from deckBox.
        }
        return deck.getCards(); // To deck in handShuffle method.
    }
    private ArrayList<PlayingCard> boxTail(ArrayList<PlayingCard> cards, ArrayList<PlayingCard> stack, int boxCount) {
        stack = new ArrayList<>();

        // Recursion base case.
        if (boxCount == 1) {
            stack.addAll(cards); // All of the remaining cards from the deck array are added to the current stack list.
            cards.clear(); // The cards list is emptied.
            return stack; // The stack returns recursively to box method.
        }

        // Instead of overloading the setSplitPoint method, I will just code the same functionality here since it is
        // unlikely that this variation of the method will be needed again.
        Random randomizer = new Random();
        int deckSegment = cards.size() / boxCount; // boxCount is decremented each level into the recursion. The deck gets
                                                    // smaller every level, but the segments become relatively larger.
        int variability = randomizer.nextInt(10) - 5; // Variability of +/- 5 cards.
        int splitPoint = deckSegment + variability; // The box stack splits from the deck at the deckSegment with variability.
        ArrayList<PlayingCard> tempStack = new ArrayList<>(); // Buffer list to store card information.

        if (splitPoint > cards.size()) {
            // If more cards can be added than there are cards in the deck, all cards will be added to the stack.
            stack.addAll(cards);
            cards.clear(); // Removes all cards added to the stack.
        }
        else {
            // The tempStack is filled with each card until the split point is reached.
            for (int i = 0; i < splitPoint; i++) {
                tempStack.add(cards.get(cards.size() - 1)); // Add to tempStack.
                cards.remove(cards.size() - 1); // Remove from card list.
            }
            Collections.reverse(tempStack); // The tempStack reverses because the procedure used here adds cards in reverse order.
            stack.addAll(tempStack); // The cards from the tempStack are added to stack.
        }

        stack.addAll(boxTail(cards, stack, boxCount - 1)); // Tail recursion to continue the procedure.
        return stack; // Returns the stack to the next highest recursion level.
    }
    private PlayingCard[] cutTheDeck(DeckOfCards deck) {
        // This method will split the deck into two stacks swap the position of the top and bottom stacks.
        PlayingCard[] topStack = new PlayingCard[setSplitPoint(deck, 20)]; // Top stack will split at the deck midpoint (+/-) 10.
        PlayingCard[] bottomStack = new PlayingCard[deck.getSize() - topStack.length]; // The remaining cards make up the bottom stack.
        int j = 0; // Secondary incrementer.
        // This for loop will assign each index of cards in the deck to the corresponding topStack index.
        for (int i = 0; i < deck.getSize(); i++) {
            if (i < topStack.length) {
                topStack[i] = deck.getCards()[i];
            }
            else {
                // The split point has been passed, the card is assigned to bottomStack at index j.
                bottomStack[j] = deck.getCards()[i];
                j++;
            }
        }

        PlayingCard[] cutDeck = new PlayingCard[deck.getSize()]; // For containing the resulting card array.
        // This for loop will assign each card in bottomStack to the new deck array, then cards from topStack when it
        // increments beyond the length of bottomStack.
        for (int i = 0; i < cutDeck.length; i++) {
            if (i < bottomStack.length) {
                cutDeck[i] = bottomStack[i];
            }
            else {
                cutDeck[i] = topStack[i - bottomStack.length];
            }
        }
        return cutDeck; // To handShuffle method. Overwrites the deck's card array.
    }
}
