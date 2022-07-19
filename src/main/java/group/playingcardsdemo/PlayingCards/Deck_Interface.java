package group.playingcardsdemo.PlayingCards;

import java.util.ArrayList;
import java.util.Collection;

public interface Deck_Interface {
    int capacity = 52;
    int currentSize = 52;
    int jokerCount = 0;
    Collection<Object> cards = null;
    Collection<Object> outOfPlay = null;

    boolean isEmpty();
    void clearCards();
    void combine(DeckOfCards deck);
}
