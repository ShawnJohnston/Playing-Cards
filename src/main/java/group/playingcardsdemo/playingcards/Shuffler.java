package group.playingcardsdemo.playingcards;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

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
    private int setSplitPoint(DeckOfCards deck, int standardDeviation) {
        /*
         This method defines the location in a deck of cards in which a top stack will separate from the rest of the
         deck. Variability is implemented here to create an element of human imprecision to the procedure.

         1. Initialize the midpoint of the deck. for a 52-card deck, that would be 26.
         2. Return the results of a method that gets a random value within some standard deviations of 'deckMidPoint'.
         */

        int deckMidPoint = deck.getCurrentSize() / 2;
        return randomValueFromNormalDistribution(deckMidPoint, standardDeviation);
    }
    private int setSplitPointForBox(int cardsSize, int boxCount) {
        // This variant of setSplitPoint is intended for the "Box" step in the hand shuffle. Every time the recursive box
        // uses this method, cardsSize (the size of the deck) will be smaller due to previous calls removing from it.
        int deckSegment = cardsSize / boxCount; // boxCount is decremented each level into the recursion. The deck gets
        // smaller every level, but the segments become relatively larger.
        return randomValueFromNormalDistribution(deckSegment, 1); // Variability of +/- 5 cards.
    }
    private int randomValueFromNormalDistribution(int mean, int standardDeviation) {
        /*
            This method uses the  Random class' nextGaussian() to get a normal distribution random value. Mainly used in
            setSplitPoint methods. In spirit of creating a human-like shuffle, the assumption is that the dealer would
            split the deck in half somewhere in the middle. It is unlikely that the dealer would split at the exact
            midpoint every time, but it's still more likely that the split point would be closer to the middle than
            farther, so the probability of splitting at a more outbound point would decrease the farther that point is.
            For example, when cutting a deck of cards, it is possible for the cut to happen 10 steps from the midpoint,
            but it is more likely that the cut will be closer. (Refer to "standard deviation" in Statistics texts.) In a
             flat distribution, on the other hand, all possibilities are equally likely.

             1. Initialize Random class.
             2. Return a random value within a certain number of standard deviations from the mean.
         */

        Random randomizer = new Random();
        return (int) randomizer.nextGaussian(mean, standardDeviation);
    }
    private Stack<PlayingCard> riffle(DeckOfCards deck) {
        /*
            This method will split the deck into two stacks, then be riffled back together to make a new full stack. To
            simulate human imperfection, variability is utilized again to avoid a perfect one-one alternating riffle.
            Every card in the deck up to and including the split point (the deck's midpoint with +/- 5 variability) will
            be added to the top stack. The rest will be added to the bottom stack.

            1.  Initialize a top stack and bottom stack.
            2.  initialize a splitPoint. Bounds are set to 5, meaning that the midpoint of the split could be 26 +/- 5.
            3.  For Loop: Range (deck size - 1) >= i >= 0. While 'i' is an integer >= the 'splitPoint', the card at that
                index will push to 'bottomStack'. If 'i' < 'splitPoint', the card at that index will push to 'topStack'.
            4.  A new stack is initialized to contain the cards as they are riffled.
            5.  While Loop: While the riffled stack has fewer cards than the original deck stack, the riffling method
                will execute.
            6.  After the riffling is complete, the original deck will have its cards stack set to the riffle stack.
            7.  The deck returns to main with a shuffled stack of cards.
         */

        Stack<PlayingCard> topStack = new Stack<>();
        Stack<PlayingCard> bottomStack = new Stack<>();
        int splitPoint = setSplitPoint(deck, 2);

        for (int i = deck.currentSize - 1; i >= 0 ; i--) {
            if (i >= splitPoint) {
                bottomStack.push(deck.getCards().get(i));
            }
            else {
                topStack.push(deck.getCards().get(i));
            }
        }
        Stack<PlayingCard> riffledStack = new Stack<>();
        while (riffledStack.size() < deck.getCurrentSize()) {
            rifflingAction(topStack, bottomStack, riffledStack);
        }
        return riffledStack;
    }
    private void rifflingAction(Stack<PlayingCard> topStack, Stack<PlayingCard> bottomStack, Stack<PlayingCard> riffledStack) {
        /*
            This method performs the actual riffling procedure. The parameters include two sub-stacks of cards from
            the original deck, and an empty stack for the riffled cards. This method uses the Random class to emulate
            human imperfection in riffling a real deck of cards. A perfect one-to-one alternating riffle is unlikely in
            real life so the Random class will create some bunching in the cards. The Random class will also be used to
            determine which of the two stacks will start in riffling.

            1.  Initialize randomizer.
            2.  Initialize 'stackSelector' and assign a random boolean value using the randomizer. The 'True' value
                represents the top stack, whereas the 'False' value represents the bottom stack.
            3.  While Loop: While either of the stacks contain cards:
                a.   If the top stack is empty, switch 'stackSelector' to false. If the bottom stack is empty, switch
                    'stackSelector' to true.
                b.   Initialize variability from 1-3. Used for "bunching" effect in the riffle.
                c.   If/Else: Determines which stack to perform riffling from. Executes helper method.
                d.   The 'stackSelector' value flips, so that the stack to riffle from alternates with each cycle.
         */

        Random randomizer = new Random();
        boolean stackSelector = randomizer.nextBoolean();

        while (!topStack.isEmpty() || !bottomStack.isEmpty()) {
            //  If/Else pair used for when one stack is empty and the other is not.
            if (topStack.isEmpty()) {
                stackSelector = false;
            }
            else if (bottomStack.isEmpty()) {
                stackSelector = true;
            }

            int variability = 1 + randomizer.nextInt(2); // Bunching of cards. Minimum 1, up to 3 cards at a time.
            if (stackSelector) {
                rifflingHelper(topStack, riffledStack, variability);
            }
            else {
                rifflingHelper(bottomStack, riffledStack, variability);
            }
            stackSelector = !stackSelector;
        }
    }
    private void rifflingHelper(Stack<PlayingCard> stack, Stack<PlayingCard> riffledStack, int variability) {
        /*
            This method is used within the rifflingAction method to move a card or cards from a donor stack to the
            receiver stack. Variability is a random value, with a minimum value of 1 to ensure that at least one card is
            riffled from the donor stack.

            1.  Initialize a counter for the number of cards riffled from the stack.
            2.  While Loop: While the stack is not empty:
                a.   If the counter equals the variability value, break the loop.
                b.   Pop the top card from the donor stack, which is pushed into the riffled stack.
                c.   The counter increments at the end of the inner while loop.
         */

        int counter = 0;
        while (!stack.isEmpty()) {
            if (counter == variability) {
                break;
            }
            riffledStack.push(stack.pop());
            counter++;
        }
    }
    private Stack<PlayingCard> box(DeckOfCards deck, int boxCount) {
        // The box method will use several lists, and use recursion to perform the entire procedure.

        Stack<PlayingCard> cards = deck.getCards(); // The deck's cards are stored as a list.
        Stack<PlayingCard> deckBox; // This list will have stacks added to it recursively.
        Stack<PlayingCard> stack = new Stack<>(); // Individual stacks will contain cards, and the stack will be added to deckBox.
        deckBox = boxTail(cards, stack, boxCount); // Recursive method to perform the box procedure.

        return deckBox; // To deck in handShuffle method.
    }
    private Stack<PlayingCard> boxTail(Stack<PlayingCard> cards, Stack<PlayingCard> stack, int boxCount) {
        stack = new Stack<>();

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
    private Stack<PlayingCard> cutTheDeck(DeckOfCards deck) {
        /*
            This method will split the deck into two halves. The top and bottom halves will swap positions.

            1.  Initialize top stack and bottom stack.
            2.  Initialize split point within 4 standard deviations from the midpoint of the deck.
            3.  For Loop: Range (deck size - 1) >= i >= 0. While 'i' is an integer >= the 'splitPoint', the card at that
                index will push to 'bottomStack'. If 'i' < 'splitPoint', the card at that index will push to 'topStack'.
            4.  Add all cards from the 'topStack' to the end of 'bottomStack'.
            5.  Return the resulting stack.
         */

        Stack<PlayingCard> topStack = new Stack<>();
        Stack<PlayingCard> bottomStack = new Stack<>();
        int splitPoint = setSplitPoint(deck, 4);

        for (int i = deck.currentSize - 1; i >= 0 ; i--) {
            if (i >= splitPoint) {
                bottomStack.push(deck.getCards().get(i));
            }
            else {
                topStack.push(deck.getCards().get(i));
            }
        }
        bottomStack.addAll(topStack);

        return bottomStack;
    }
}
