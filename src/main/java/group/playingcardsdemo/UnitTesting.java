package group.playingcardsdemo;

import group.playingcardsdemo.cards.Hand;
import group.playingcardsdemo.cards.HandEvaluator;
import group.playingcardsdemo.cards.PlayingCard;

import java.util.ArrayList;

public class UnitTesting {
    public static ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5,
                                                     String suit1, String suit2, String suit3, String suit4, String suit5) {
    ArrayList<PlayingCard> cards = new ArrayList<>();
    PlayingCard card1 = new PlayingCard(value1, suit1);
    PlayingCard card2 = new PlayingCard(value2, suit2);
    PlayingCard card3 = new PlayingCard(value3, suit3);
    PlayingCard card4 = new PlayingCard(value4, suit4);
    PlayingCard card5 = new PlayingCard(value5, suit5);
    cards.add(card1);
    cards.add(card2);
    cards.add(card3);
    cards.add(card4);
    cards.add(card5);

    return cards;
}
    public static ArrayList<PlayingCard> handBuilder(String value1, String value2, String value3, String value4, String value5, String value6, String value7,
                                              String suit1, String suit2, String suit3, String suit4, String suit5, String suit6, String suit7) {
        ArrayList<PlayingCard> cards = new ArrayList<>();
        PlayingCard card1 = new PlayingCard(value1, suit1);
        PlayingCard card2 = new PlayingCard(value2, suit2);
        PlayingCard card3 = new PlayingCard(value3, suit3);
        PlayingCard card4 = new PlayingCard(value4, suit4);
        PlayingCard card5 = new PlayingCard(value5, suit5);
        PlayingCard card6 = new PlayingCard(value6, suit6);
        PlayingCard card7 = new PlayingCard(value7, suit7);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);

        return cards;
    }

    public static void printHand(Hand hand) {
        Player player = new Player();
        HandEvaluator evaluator = new HandEvaluator(hand);
        System.out.println("Player hand:");
        //for (PlayingCard card : evaluator.getHand().getCards()) {
        //    System.out.printf("%s of %s \n", card.getValue(), card.getSuit());
        //}
    }
    public static void printHandRanking(HandEvaluator evaluator) {
        System.out.println("Hand rank evaluates to: " + evaluator.getHandRank() + "\n");
    }
}