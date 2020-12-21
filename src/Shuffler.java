import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Shuffler {
    //Fields

    // Constructor
    public Shuffler() {
    }

    public PlayingCard[] random(PlayingCard[] cards) {
        // This method will shuffle the deck using Java's Random class to randomize the array that contains each card.
        Random randomizer = new Random(); // 'Random' object.


        for (int i = 0; i < cards.length; i++) {
            int randomPosition = randomizer.nextInt(cards.length); // Random integer from 0 to the value of the deck size.
            PlayingCard temp = cards[i]; // stores the current card in a temporary variable.
            cards[i] = cards[randomPosition]; // The random index's card is assigned to the current index.
            cards[randomPosition] = temp; // The temporary variable is assigned to the random index.
        }
        return cards; // To the deck object in main.
    }

    public DeckOfCards handShuffle(DeckOfCards deck) {
        int proceduralCount = 0;

        while (proceduralCount < 5) {
            ArrayList<PlayingCard> topStack = new ArrayList<>();
            ArrayList<PlayingCard> bottomStack = new ArrayList<>();
            Random randomizer = new Random();
            int deckMidPoint = deck.getSize() / 2;
            int variability = randomizer.nextInt(10) - 5;
            int splitPoint = deckMidPoint + variability;
            int stackNumber = 4;

            for (int i = 0; i < deck.getSize(); i++) {
                if (i < splitPoint) {
                    topStack.add(deck.getCards()[i]);
                }
                else {
                    bottomStack.add(deck.getCards()[i]);
                }
            }

            // Casino Poker Shuffle procedure is riffle, riffle, box, riffle, cut.
            switch (proceduralCount) {
                case 0, 1, 3 -> deck.setCards(riffle(deck, topStack, bottomStack)); // Riffle.
                case 2 -> deck.setCards(box(deck, stackNumber)); // Box.
                case 4 -> deck.setCards(cutTheDeck(deck.getCards())); // Cut.
            }
            proceduralCount++;
            }
        return deck;
    }
    private PlayingCard[] riffle(DeckOfCards deck, ArrayList<PlayingCard> topStack, ArrayList<PlayingCard> bottomStack) {
        Random randomizer = new Random();
        ArrayList<PlayingCard> riffledDeck = new ArrayList<>();
        Collections.reverse(topStack);
        Collections.reverse(bottomStack);

        while (riffledDeck.size() < deck.getSize()) {
            int variability = randomizer.nextInt(3);
            if (!topStack.isEmpty()) {
                if (variability > topStack.size()) {
                    riffledDeck.addAll(topStack);
                }
                else {
                    for (int i = 0; i < variability; i++) {
                        riffledDeck.add(topStack.get(topStack.size() - 1));
                        topStack.remove(topStack.size() - 1);
                        if (topStack.isEmpty()) {
                            break;
                        }
                    }
                }
                
            }
            if (!bottomStack.isEmpty()) {
                if (variability > bottomStack.size()) {
                    riffledDeck.addAll(bottomStack);
                }
                else {
                    for (int i = 0; i < variability; i++) {
                        riffledDeck.add(bottomStack.get(bottomStack.size() - 1));
                        bottomStack.remove(bottomStack.size() - 1);
                        if (bottomStack.isEmpty()) {
                            break;
                        }
                    }
                }

            }
        }
        for (int i = 0; i < deck.getSize(); i++) {
            deck.getCards()[i] = riffledDeck.get(i);
        }
        return deck.getCards();
    }
    private PlayingCard[] box(DeckOfCards deck, int boxCount) {
        ArrayList<PlayingCard> cards = new ArrayList<>(Arrays.asList(deck.getCards()));
        ArrayList<PlayingCard> deckBox;
        ArrayList<PlayingCard> stack = new ArrayList<>();
        deckBox = boxTail(cards, stack, boxCount);
        for (int i = 0; i < deck.getSize(); i++) {
            deck.getCards()[i] = deckBox.get(i);
        }
        return deck.getCards();
    }
    private ArrayList<PlayingCard> boxTail(ArrayList<PlayingCard> cards, ArrayList<PlayingCard> stack, int boxCount) {
        stack = new ArrayList<>();
        if (boxCount == 1) {
            stack.addAll(cards);
            cards.clear();
            return stack;
        }

        Random randomizer = new Random();

        int deckSegment = cards.size() / boxCount;
        int variability = randomizer.nextInt(8) - 4;
        int splitPoint = deckSegment + variability;
        ArrayList<PlayingCard> tempStack = new ArrayList<>();
        if (splitPoint > cards.size()) {
            stack.addAll(cards);
            cards.clear();
        }
        else {
            for (int i = 0; i < splitPoint; i++) {
                tempStack.add(cards.get(cards.size() - 1));
                cards.remove(cards.size() - 1);
            }
            Collections.reverse(tempStack);
            stack.addAll(tempStack);
        }

        stack.addAll(boxTail(cards, stack, boxCount - 1));
        return stack;
    }
    private PlayingCard[] cutTheDeck(PlayingCard[] cards) {
        Random randomizer = new Random();
        int deckMidpoint = cards.length / 2;
        int variability = randomizer.nextInt(10) - 5;
        int splitPoint = deckMidpoint + variability;

        PlayingCard[] topStack = new PlayingCard[splitPoint];
        PlayingCard[] bottomStack = new PlayingCard[cards.length - splitPoint];

        for (int i = 0; i < cards.length; i++) {
            if (i < splitPoint) {
                topStack[i] = cards[i];
            }
            else {
                bottomStack[i - splitPoint] = cards[i];
            }
        }

        PlayingCard[] cutDeck = new PlayingCard[cards.length];
        for (int i = 0; i < cutDeck.length; i++) {
            if (i < bottomStack.length) {
                cutDeck[i] = bottomStack[i];
            }
            else {
                cutDeck[i] = topStack[i - bottomStack.length];
            }
        }
        return cutDeck;
    }
}
