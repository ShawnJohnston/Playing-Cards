package group.playingcardsdemo.PlayingCards;

import java.util.Stack;

public class Discard extends DeckOfCards {
    public Discard() {
        super();
        capacity = 54;
        currentSize = 0;
        cards = new Stack<>();
    }

    public void addCard(PlayingCard card) {
        cards.add(card);
        currentSize++;
    }
}
