/*
    Shawn Johnston
    Playing Card Project
    
    Objective
    
        This mini-project is designed to be a demonstration of basic Object-Oriented Programming.
    
    Setup
    
    1.  The first stage of this demonstration is to build a working deck of playing cards. The deck will contain 52
        cards, as is standard, along with the ability to include Jokers into the deck. The deck of cards is divided into
        4 suits, then subdivided into 13 values.
    2.  The second stage is to include a couple of methods of shuffling the deck. The first method will be a simple
        computerized randomization of the deck. The second method will be designed to simulate a hand-shuffle, using the
        riffle-riffle-box-riffle procedure. Small amounts of computerized randomization will be used within each step as necessary.
    3.  The third stage is to deal cards to a player and a dealer. To keep this demo simple, a basic 5-card stud game will
        be implemented for now (The player's 5 cards vs. the dealer's 5-cards). Standard poker ranks will be used and both
        hands will be evaluated for them. From weakest to strongest, they are:
        High Card -> Pair -> Two Pair -> Trips -> Straight -> Flush -> Full House -> Quads -> Straight Flush -> Royal Flush
    4.  The fourth stage will be implement a GUI using Swing.
 */

public class Program {
    public static void main(String[] args) {

        // The program will be kept running using a while loop, and will remain running as long as it's boolean remains true.
        boolean running = true;
        while (running) {

            DeckOfCards deck = new DeckOfCards(); // Initializes the deck of cards.
            running = false;
        }
    }
}
