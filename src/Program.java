/*
    Shawn Johnston
    Playing Card Project
 */

import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        // The program will be kept running using a while loop, and will remain running as long as it's boolean remains true.
        boolean running = true;

        while (running) {

            DeckOfCards deck = new DeckOfCards(); // Initializes the deck of cards.
            Shuffler shuffler = new Shuffler();
            deck = shuffler.random(deck);
            deck = shuffler.handShuffle(deck);

            Player user = new Player();
            Dealer dealer = new Dealer();

            System.out.println(Player.getPlayerNumber());
            System.out.println(Player.getPlayerList());

            running = false;
        }
    }
}
