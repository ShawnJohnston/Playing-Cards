package group.playingcardsdemo;

import java.util.ArrayList;

public abstract class GameMode {
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
            this.size = size;
            if (size < 0) {
                size = 0;
            }
            usesCommunityCards = (size == 0);
            this.nextCardToRevealIndex = 0;
        }
        public void initialize() {
            for (int i = 0; i < this.size; i++) {
                board.add(deck.draw());
            }
        }
        public void flipNextCard() {
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
        switch (gameMode.toString()) {
            case "FiveCardStud" -> {
                this.communityCardsSize = 5;
                this.pocketSize = 5;
                this.handSize = 5;
                this.totalSize = 5;
                this.straightFlushSize = 5;
            }
            case "UTH" -> {
                this.communityCardsSize = 5;
                this.pocketSize = 2;
                this.handSize = 5;
                this.totalSize = 7;
                this.straightFlushSize = 5;
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
                player.addToPocket(deck.draw());
            }
            dealer.getPocket().addCard(deck.draw());
        }
    }
}
class UTH extends GameMode {
    UTH(String game, Player player) {
        super(game, player);
    }


    protected void configureHandScalingByGame() {
        super.configureHandScalingByGame();
    }
    @Override
    protected void session() {
        dealingPhase();
        preFlopPhase();
        postFlopPhase();
        turnPhase();
        riverPhase();
        showdownPhase();
    }
    @Override
    protected void initialBets() {
    }
    private void preFlopPhase() {
    }
    private void postFlopPhase() {
        for (int i = 0; i < 3; i++) {
            this.community.flipNextCard();
        }
    }
    private void turnPhase() {
        this.community.flipNextCard();
    }
    private void riverPhase() {
        this.community.flipNextCard();
    }
    private void showdownPhase() {

    }
}

enum Games {
    FiveCardStud, UTH
}