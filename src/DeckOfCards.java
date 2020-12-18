
public class DeckOfCards {
    private int size = 52;
    private PlayingCard[] cards = new PlayingCard[size];
    private PlayingCard[] discard = new PlayingCard[size];

    public DeckOfCards() {
        buildDeck();
    }
    public DeckOfCards(int jokers) {
        this.size += jokers;
    }
    public void buildDeck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Club", "Spade", "Heart", "Diamond"};

        int rankCounter = 0;
        int suitCounter = 0;
        for (int i = 0; i < getSize(); i++) {
            if (rankCounter == ranks.length) {
                rankCounter = 0;
            }
            if (suitCounter == suits.length) {
                suitCounter = 0;
            }
            PlayingCard card = new PlayingCard(ranks[rankCounter], suits[suitCounter]);
            this.cards[i] = card;

            rankCounter++;
            suitCounter++;
        }

    }

    public PlayingCard[] getCards() {
        return cards;
    }
    public int getSize() {
        return size;
    }
}