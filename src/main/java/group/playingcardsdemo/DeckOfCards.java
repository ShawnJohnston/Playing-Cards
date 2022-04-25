package group.playingcardsdemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

@Getter
@Setter

public class DeckOfCards {
    protected int maxSize = 52;
    protected int currentSize = 52;
    protected static int jokerCount = 0;
    protected ArrayList<PlayingCard> cards = new ArrayList<>();

    public static Image[] cardImages = new Image[54];
    public static ImageView[] cardImageViews = new ImageView[54];

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

    public static void initializeCardImages(DeckOfCards deck) throws FileNotFoundException {
        /*
            This method concatenates an image url for every card and stores them in the 'cardImages' static array.

            1.  Outer For Loop: Range 0 <= i <= (length of SUITS array - 1). 'i' times, execute the inner for-loop.
            2.  Inner For Loop: Range 0 <= j <= (length of VALUES_INDEX - 2). 'j' time, perform the following:
                -   Declare string 'fileName', containing the current suit (index i) lowercased, then '_', then the
                    current value (index j) lowercased, then '.png'.
                -   The fileName will be stored at index (13*i) + j. The beginning of any given suit is 13 indices from
                    the beginning of the previous or next, so multiplying 13 times 'i' will jump to the correct 1/4th of
                    the array that starts the correct suit group. Adding 'j' will locate the index for the card value
                    within the suit section.
         */

        for (int i = 0; i < PlayingCard.SUITS.length - 1; i++) {
            for (int j = 0; j < PlayingCard.VALUES_INDEX.length - 2; j++) {
                String fileName = PlayingCard.SUITS[i].toLowerCase() + "_" + PlayingCard.VALUES_INDEX[j].toLowerCase() + ".png";
                cardImages[(13*i) + j] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + fileName));
            }
        }
        cardImages[52] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + "joker_black.png"));
        cardImages[53] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" +"joker_red.png"));
    }
}
class Discard extends DeckOfCards {
    public Discard() {
        super();
        maxSize = 54;
        currentSize = 0;
        cards = new ArrayList<>();
    }
    public void addCard(PlayingCard card) {
        cards.add(card);
        currentSize++;
    }
}