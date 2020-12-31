import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

public class TestingSandbox {
    DeckOfCards deck = new DeckOfCards();
    Shuffler shuffler = new Shuffler();

    @Test
    public void viewUnshuffledDeck() {
        for (PlayingCard card: deck.getCards()) {
            System.out.println(card.getName());
        }
    }

    @Test
    public void viewRandomShuffledDeck() {
        deck = shuffler.random(deck);
        for (PlayingCard card: deck.getCards()) {
            System.out.println(card.getName());
        }
    }

    @Test
    public void viewHandShuffledDeck() {
        deck = shuffler.random(deck);
        for (PlayingCard card: deck.getCards()) {
            System.out.println(card.getName());
        }
    }

    @Test
    public void viewPlayers() {
        System.out.println("List of players: " + Player.getPlayerList());
        System.out.println("Number of players: " + Player.getPlayerNumber());

        System.out.println();

        Player player = new Player();
        Player dealer = new Player();
        System.out.println("List of players: " + Player.getPlayerList());
        System.out.println("Number of players: " + Player.getPlayerNumber());

        System.out.println();

        Player player1 = new Player();
        System.out.println("List of players: " + Player.getPlayerList());
        System.out.println("Number of players: " + Player.getPlayerNumber());

        System.out.println();

        Player player2 = new Player();
        System.out.println("List of players: " + Player.getPlayerList());
        System.out.println("Number of players: " + Player.getPlayerNumber());
    }
}
