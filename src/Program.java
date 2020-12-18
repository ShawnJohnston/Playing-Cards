import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            DeckOfCards deck = new DeckOfCards();

            for (PlayingCard card: deck.getCards()) {
                System.out.println(card.getRank() + card.getSuit());
            }
            running = false;
        }
    }
}
