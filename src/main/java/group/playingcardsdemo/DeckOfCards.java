package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class DeckOfCards {
    protected int maxSize = 52;
    protected int currentSize = 52;
    protected static int jokerCount = 0;
    protected ArrayList<PlayingCard> cards = new ArrayList<>();

    // Constructors
    public DeckOfCards() {
        buildDeck();
    }
    public DeckOfCards(int jokers) {
        jokerCount = jokers;
        maxSize = 52 + jokers;
        currentSize = 52 + jokers;
        buildDeck();
    }

    private void buildDeck() {
        /*
            This method will instantiate the cards that make up the deck of cards, using a 2D for loop, followed by a
            while loop to instantiate any joker cards. The cards are added in descending order to conform to a
            stack-like data structure.

            1. While loop: The joker count is used to instantiate jokers and determine what each joker's color will be.
            2. Outer loop: For each suit listed (Spades, Hearts, Clubs, Diamonds), execute the inner loop.
            3. Inner Loop: For each card value (from Ace to King), instantiate a playing card of value i and the
               current suit, and add it to the cards list.
         */
        int counter = 0;
        while (counter < jokerCount) {
            if (counter == 0) {
                cards.add(new JokerCard("Black"));
            }
            if (counter == 1) {
                cards.add(new JokerCard("Red"));
            }
            counter++;
        }
        for (int i = PlayingCard.SUITS.length - 2; i >= 0; i--) {
            for (int j = PlayingCard.VALUES_INDEX.length - 3; j >= 0; j--) {
                cards.add(new PlayingCard(PlayingCard.VALUES_INDEX[j], PlayingCard.SUITS[i]));
            }
        }
    }
    public boolean isEmpty() {
        return getCurrentSize() == 0;
    }
    public PlayingCard drawTopCard() {
        /*
            This method draws a card from the back of the cards list, modeling a stack structure. If index 0 represents
            the bottom of the deck of cards, then the last index would be the top card.

            1. Storage variable declared for the card at the last index.
            2. The card at the last index is deleted from the list.
            3. The card list's size is adjusted for the change.
            4. The storage variable is returned.
         */

        PlayingCard drawnCard = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        currentSize = cards.size();
        return drawnCard;
    }
    public void compileFromDiscard(Discard discard) {
        /*
            This method copies all cards from the incoming discard object to the deck's cards list.

            1. For Loop: A copy of each card in the discard is added to the cards list.
            2. The deck's size is updated.
         */

        for (int i = 0; i < discard.getCurrentSize(); i++) {
            this.cards.add(discard.cards.get(i));
        }
        currentSize = cards.size();
    }
}
class Discard extends DeckOfCards {
    public Discard() {
        maxSize = 54;
        currentSize = 0;
        cards = new ArrayList<>();
    }
    public void addCard(PlayingCard card) {
        cards.add(card);
        currentSize++;
    }
}