/*
    Shawn Johnston
    Playing Card Project
 */

public class Program {
    public static void main(String[] args) {
        // The program will be kept running using a while loop, and will remain running as long as it's boolean remains true.
        boolean running = true;
        GameMode gameMode = new GameMode();
        while (running) {
            ConsoleMenu menu = new ConsoleMenu();
            running = ConsoleMenu.keepRunning;

            Player user = new Player();
            Dealer dealer = new Dealer();
            gameMode.InitializeGame(user, dealer);
            GameMode.setGameMode(ConsoleMenu.getGameToStart());
            System.out.println(GameMode.getGameMode());

            DeckOfCards deck = new DeckOfCards(); // Initializes the deck of cards.
            Shuffler shuffler = new Shuffler();
            deck = shuffler.random(deck);
            deck = shuffler.handShuffle(deck);


        }
    }
}