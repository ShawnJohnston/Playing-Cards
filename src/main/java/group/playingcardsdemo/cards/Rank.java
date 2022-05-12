package group.playingcardsdemo.cards;

import java.util.ArrayList;

public abstract class Rank {
    protected int straightFlushSize = HandEvaluator.straightFlushSize;
    protected Hand hand;
    protected String rank;
    protected ArrayList<String> kickers = new ArrayList<>();

    Rank() {
        hand = new Hand();
        rank = rankState.HighCard.toString();
    }
    Rank(HandEvaluator evaluator) {
        this.hand = evaluator.getFiveCardHand();
        this.rank = String.valueOf(evaluator.getHandRank());
    }
}
class Pairs extends Rank {
    protected ArrayList<String> pairs;
}class Trips extends Rank {
    protected ArrayList<String> trips;
}
class Quads extends Rank {
    protected ArrayList<String> quads;
    protected String quadsValue;
}
class Straight extends Rank {
    protected String topCard;
}
class Flush extends Rank {
    protected String suit;
}
class StraightFlush extends Rank {
    protected String topCard;
    protected String suit;
}