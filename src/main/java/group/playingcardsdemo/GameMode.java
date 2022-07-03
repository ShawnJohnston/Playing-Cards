package group.playingcardsdemo;

import group.playingcardsdemo.playingcards.*;

import java.util.ArrayList;

public abstract class GameMode {
    public static final String[] GAMES = {"", "5 Card Stud", "UTH"};
    protected Games gameMode;
    protected Player dealer;
    protected ArrayList<Player> playersList = new ArrayList<>();
    protected DeckOfCards deck;
    protected Discard discard = new Discard();
    protected boolean usesCommunityCards;
    protected CommunityCards community;
    protected int communityCardsSize;
    protected int pocketSize;
    protected int handSize;
    protected int totalSize;
    protected int straightFlushSize;
    protected int jokersInDeck = 0;

    public class CommunityCards {
        protected int size;
        protected int nextCardToRevealIndex;
        protected ArrayList<PlayingCard> board = new ArrayList<>();

        CommunityCards(int size) {
            /*
                1.  Set board size.
                2.  If size is negative, set it to zero.
                3.  Determine if community cards are used in the game. If size is nonzero, community cards are used.
                4.  The index of the next card to reveal on the board is set to zero.
             */

            this.size = size;
            if (size < 0) {
                size = 0;
            }
            usesCommunityCards = (size == 0);
            this.nextCardToRevealIndex = 0;
        }
        public void initialize() {
            /*
                This method will fill the board with community card, drawn from the top of the deck and set face down.
             */
            for (int i = 0; i < this.size; i++) {
                board.add(deck.drawTopCard());
                board.get(i).setCurrentFacing(Facing.faceDown);
            }
        }
        public void flipNextCard() {
            /*
                This method will flip a card on the board from face-up to face-down.

                1.  If the index of next card to reveal is less than the size of the board:
                    a.  Set the facing of the next card on the board to reveal to face-up.
                    b.  For each player, add the newly revealed card to their hand.
                    c.  Add the newly revealed card to the dealer's hand.
                    d.  Increment the next card index.
             */

            if (!(this.nextCardToRevealIndex >= this.size - 1)) {
                board.get(nextCardToRevealIndex).setCurrentFacing(Facing.faceUp);
                for (Player player: playersList) {
                    player.getHand().addCard(board.get(nextCardToRevealIndex));
                }
                dealer.getHand().addCard(board.get(nextCardToRevealIndex));
                this.nextCardToRevealIndex++;
            }
        }
    }

    GameMode(String game, Player player) {
        gameMode = Games.valueOf(game);
        playersList.add(player);
        configureHandScalingByGame();
        sessionStart();
    }
    protected void configureHandScalingByGame() {
        /*
            This method sets the game settings that determine the constraints of hands for any given game.
         */
        switch (gameMode.toString()) {
            case "FiveCardStud" -> {
                this.communityCardsSize = 0;
                this.pocketSize = 5;
                this.handSize = 5;
                this.totalSize = pocketSize;
                this.straightFlushSize = handSize;
            }
            case "UTH" -> {
                this.communityCardsSize = 5;
                this.pocketSize = 2;
                this.handSize = 5;
                this.totalSize = communityCardsSize + pocketSize;
                this.straightFlushSize = handSize;
            }
        }
    }
    protected void sessionStart() {
        deck = new DeckOfCards(jokersInDeck);
        Shuffler shuffler = new Shuffler();
        shuffler.random(deck);

        initialBets();
        session();
    }
    protected void session() {}
    protected void initialBets() {

    }
    protected void dealingPhase() {
        if (this.community.size > 0) {
            community.initialize();
        }

        for (int i = 0; i < pocketSize; i++) {
            for (Player player : playersList) {
                player.addCardToPocket(deck.drawTopCard());
            }
            dealer.getPocket().addCard(deck.drawTopCard());
        }
    }
}
class UTH extends GameMode implements UTHFormat {
    UTH(String game, Player player) {
        super(game, player);
    }


    protected void configureHandScalingByGame() {
        super.configureHandScalingByGame();
    }
    @Override
    protected void session() {
        initialBets();
        dealingPhase();
        preFlopPhase();
        postFlopPhase();
        turnPhase();
        riverPhase();
        showdownPhase();
    }
    @Override
    public void initialBets() {
    }

    @Override
    public void dealingPhase() {

    }

    public void preFlopPhase() {
    }
    public void postFlopPhase() {
        for (int i = 0; i < 3; i++) {
            this.community.flipNextCard();
        }
    }
    public void turnPhase() {
        this.community.flipNextCard();
    }
    public void riverPhase() {
        this.community.flipNextCard();
    }
    public void showdownPhase() {

    }
}

enum Games {
    FiveCardStud, UTH
}