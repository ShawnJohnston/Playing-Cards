/*
    Shawn Johnston
    Playing Card Project
 */

public class Program {
    public static void main(String[] args) {
        // The program will be kept running using a while loop, and will remain running as long as it's boolean remains true.
        boolean running = true;

        while (running) {
            DeckOfCards deck = new DeckOfCards(); // Initializes the deck of cards.
            Shuffler shuffler = new Shuffler();

            deck.setCards(shuffler.random(deck.getCards()));
            deck = shuffler.handShuffle(deck);
            running = false;
        }
    }
}
