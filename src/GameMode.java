import java.util.ArrayList;

public class GameMode {
    private static String gameMode;
    private static int maximumHandSizeToSet;

    public GameMode() {
        setGameMode("");
    }

    public static String getGameMode() {
        return gameMode;
    }
    public static void setGameMode(String gameToStart) {
        gameMode = gameToStart;
        if (gameMode.equals("5 Card Poker")) {
            maximumHandSizeToSet = 5;
        }
    }

    public void initializeGame(Player user, Player dealer) {
        user.setMaxNumCardsInHand(maximumHandSizeToSet);
        user.setStartingChips(1000);
        dealer.setMaxNumCardsInHand(maximumHandSizeToSet);
    }

    public void play5CardPoker(Player user, Player computer) {
        boolean playing = true;

        while (playing) {
            DeckOfCards deck = new DeckOfCards(); // Initializes the deck of cards.
            Shuffler shuffler = new Shuffler();
            deck = shuffler.random(deck);
            deck = shuffler.handShuffle(deck);

            ArrayList<PlayingCard> drawnCards = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                System.out.println(deck.getCards().get(i).getValue() + " " + deck.getCards().get(i).getSuit());
            }
            for (int i = 0; i < 10; i++) {
                drawnCards.add(deck.getCards().get(i));
            }
            for (int i = 0; i < drawnCards.size();) {
                user.addToHand(drawnCards.get(i));
                computer.addToHand(drawnCards.get(i + 1));
                i += 2;
            }
            for (PlayingCard card: user.getHand()) {
                System.out.println(card.getValue() + " " + card.getSuit());
            }

            playing = false;
        }
    }
}
