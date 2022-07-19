package group.playingcardsdemo.PlayingCards;

import java.util.ArrayList;
import java.util.Collection;

public interface Hand_Interface {
    Collection<Object> cards = new ArrayList<>();
    int capacity = 0;
    int size = 0;
    int[] valueData = new int[Values.VALUES.length];
    int[] suitData = new int[Suits.SUITS.length];

    void setCards(ArrayList<PlayingCard> cards);
    void addCard(PlayingCard card);
    void addCard(int index, PlayingCard newCard);
    void removeCard(int index);
    void clear();
    boolean containsCardValue(String value);
    boolean containsCard(String value, String suit);
    void sortHandByValue();
    void sortHandByRank();
}
