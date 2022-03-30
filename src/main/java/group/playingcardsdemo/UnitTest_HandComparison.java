package group.playingcardsdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitTest_HandComparison {

    // Hand comparisons
    @Test
    public void royalFlushVersusOtherHands() {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        Hand hand1 = new Hand(UnitTesting.handBuilder(
                "Ace", "King", "Queen", "Jack", "10",
                "Clubs", "Clubs", "Clubs", "Clubs", "Clubs"));
        HandEvaluator evaluator1 = new HandEvaluator(new Player(), hand1);

        System.out.println("Royal Flush vs Royal Flush: \n");
        handVersusRoyalFlush(evaluator1, "Tie");
        System.out.println("Royal Flush vs Straight Flush: \n");
        handVersusStraightFlush(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Quads: \n");
        handVersusQuads(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Full House: \n");
        handVersusFullHouse(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Flush: \n");
        handVersusFlush(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Straight: \n");
        handVersusStraight(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Trips: \n");
        handVersusTrips(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Two Pair: \n");
        handVersusTwoPair(evaluator1, "Player 1");
        System.out.println("Royal Flush vs Pair: \n");
        handVersusPair(evaluator1, "Player 1");
        System.out.println("Royal Flush vs High Card: \n");
        handVersusHighCard(evaluator1, "Player 1");
    }
    //@Test
    //public void straightFlushVersusOtherHands() {
    //    Global.initializePokerRanks();
    //    Global.initializeCardValueMap();
//
    //    Player player1 = new Player();
    //    Hand hand1 = new Hand();
    //    PlayingCard[] cardsInHand;
    //    int iterationCount = 1;
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        for (int j = 0; j < 8; j++) {
    //            hand1.getCards().clear();
    //            cardsInHand = new PlayingCard[5];
    //            for (int k = 0; k < 5; k++) {
    //                cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
    //                hand1.addCard(cardsInHand[k]);
    //            }
    //            iterationCount++;
    //            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //            Assertions.assertTrue(evaluator1.isAStraightFlush());
    //            UnitTesting.printHandRanking(evaluator1);
    //            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
//
    //            handVersusRoyalFlush(evaluator1, "Player 2");
    //            handVersusQuads(evaluator1, "Player 1");
    //            handVersusFullHouse(evaluator1, "Player 1");
    //            handVersusFlush(evaluator1, "Player 1");
    //            handVersusStraight(evaluator1, "Player 1");
    //            handVersusTrips(evaluator1, "Player 1");
    //            handVersusTwoPair(evaluator1, "Player 1");
    //            handVersusPair(evaluator1, "Player 1");
    //            handVersusHighCard(evaluator1, "Player 1");
    //        }
    //    }
    //}
    //@Test
    //public void quadsVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = 0; i < Global.VALUES.length - 1; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);
//
    //        System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
//
    //        handVersusFullHouse(evaluator1, "Player 1");
    //        handVersusFlush(evaluator1, "Player 1");
    //        handVersusStraight(evaluator1, "Player 1");
    //        handVersusTrips(evaluator1, "Player 1");
    //        handVersusTwoPair(evaluator1, "Player 1");
    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void fullHouseVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = Global.VALUES.length - 1; i >=2; i--) {
    //        Hand hand1 = new Hand();
    //        Player player1 = new Player();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i - 1],
    //                Global.VALUES[i - 1],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);
//
    //        System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 1: " + evaluator1.getFullHouse());
//
    //        handVersusFlush(evaluator1, "Player 1");
    //        handVersusStraight(evaluator1, "Player 1");
    //        handVersusTrips(evaluator1, "Player 1");
    //        handVersusTwoPair(evaluator1, "Player 1");
    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void flushVersusOtherHands() {
    //Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
    //        hand1.setHand(UnitTesting.handBuilder(
    //                "King", "7", "Jack", "5", "2",
    //                Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
//
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 1: " + evaluator1.getHandRank());
//
    //        handVersusStraight(evaluator1, "Player 1");
    //        handVersusTrips(evaluator1, "Player 1");
    //        handVersusTwoPair(evaluator1, "Player 1");
    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void straightVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    Player player1 = new Player();
    //    Hand hand1;
//
    //    PlayingCard[] cardsInHand;
    //    for (int i = 0; i < 10; i++) {
    //        hand1 = new Hand();
    //        cardsInHand = new PlayingCard[5];
    //        for (int j = 0; j < 5; j++) {
    //            cardsInHand[j] = new PlayingCard(Global.VALUESHIERARCHY[j + i], Global.SUITS[j]);
    //            hand1.addCard(cardsInHand[j]);
    //        }
    //        UnitTesting.printHand(hand1);
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println(evaluator1.getHandRank());
//
    //        handVersusTrips(evaluator1, "Player 1");
    //        handVersusTwoPair(evaluator1, "Player 1");
    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void tripsVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = 0; i < Global.VALUES.length - 1; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[13],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);
//
    //        System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 1: " + evaluator1.getTrips());
//
//
    //        handVersusTwoPair(evaluator1, "Player 1");
    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void twoPairVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();

    //    for (int i = 0; i < Global.VALUES.length - 2; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i + 1],
    //                Global.VALUES[i + 1],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);

    //        System.out.println("Player 2: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 2: " + evaluator1.getPairs());

    //        handVersusPair(evaluator1, "Player 1");
    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void pairVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();

    //    for (int i = 0; i < Global.VALUES.length - 1; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[13],
    //                Global.VALUES[13],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);

    //        System.out.println("Player 2: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 2: " + evaluator1.getPairs());

    //        handVersusHighCard(evaluator1, "Player 1");
    //    }
    //}
    //@Test
    //public void highCardVersusOtherHands() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();

    //    Player player1 = new Player();
    //    Hand hand1 = new Hand();
    //    hand1.setHand(UnitTesting.handBuilder(
    //            "Ace", "King", "Queen", "Jack", "9",
    //            "Spades", "Hearts", "Hearts", "Clubs", "Diamonds"));
    //    HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //    System.out.println("Player 1: " + evaluator1.getHandRank());

    //    handVersusHighCard(evaluator1, "Player 1");
    //}

    @Test
    public void straightFlushVersusStraightFlushTie() {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        PlayingCard[] cardsInHand;
        int iterationCount = 1;
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player1 = new Player();
            Player player2 = new Player();
            Hand hand1 = new Hand();
            Hand hand2 = new Hand();

            for (int j = 0; j < 9; j++) {
                hand1.getCards().clear();
                hand2.getCards().clear();
                cardsInHand = new PlayingCard[5];
                for (int k = 0; k < 5; k++) {
                    cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
                    hand1.addCard(cardsInHand[k]);
                    hand2.addCard(cardsInHand[k]);
                }
                iterationCount++;
                HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
                HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
                //System.out.println("Player 1: " + Arrays.toString(evaluator1.getHand().getValueData()));
                //System.out.println("Player 2: " + Arrays.toString(evaluator2.getHand().getValueData()));
                System.out.println(evaluator2.getHandRank());
                GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
                Assertions.assertEquals("Tie", outcome.getWinner());
            }
        }
    }
    @Test
    public void quadsVersusQuadsTie() {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player1 = new Player();
            player1.setHand(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[Global.VALUES.length - 1],
                    "Spades", "Hearts", "Clubs", "Diamonds", "Diamonds"));

            System.out.print("Player 1: " + Arrays.toString(player1.getHand().getValueData()));
            System.out.println();
            HandEvaluator evaluator1 = new HandEvaluator(player1, player1.getHand());

            Player player2 = new Player();
            player2.setHand(player1.getHand().getCards());

            System.out.print("Player 2: " + Arrays.toString(player2.getHand().getValueData()));
            System.out.println();
            HandEvaluator evaluator2 = new HandEvaluator(player2, player2.getHand());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void fullHouseVersusFullHouseTie() {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand1 = new Hand();
            Player player1 = new Player();

            Hand hand2 = new Hand();
            Player player2 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);
            hand1.setHand(cards);

            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getFullHouse());


            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getFullHouse());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void flushVersusFlushTie() {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player1 = new Player();
            Hand hand1 = new Hand();

            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards =  UnitTesting.handBuilder(
                    "King", "7", "Jack", "5", "2",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]);
            hand1.setHand(cards);
            hand2.setHand(cards);

            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
            System.out.println("Player 1: " + evaluator1.getHandRank());

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    @Test
    public void straightVersusStraightTie() {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        Player player1 = new Player();
        Hand hand1 = new Hand();

        Player player2 = new Player();
        Hand hand2 = new Hand();

        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;
        for (int i = 0; i < 10; i++) {
            hand1.getCards().clear();
            hand2.getCards().clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
                hand1.addCard(cardsInHand[j]);
                hand2.addCard(cardsInHand[j]);
            }
            System.out.print("Player 1: ");
            UnitTesting.printHand(hand1);
            HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);

            System.out.print("Player 2: ");
            UnitTesting.printHand(hand2);
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);

            GameOutcome outcome = new GameOutcome(evaluator1,evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    //@Test
    //public void tripsVersusTripsTie() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = 2; i < Global.VALUES.length - 1; i++) {
    //        Hand hand1 = new Hand();
    //        Player player1 = new Player();
//
    //        Hand hand2 = new Hand();
    //        Player player2 = new Player();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[1],
    //                Global.VALUES[0],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);
    //        hand2.setHand(cards);
//
    //        System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 1: " + evaluator1.getTrips());
//
    //        System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
    //        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
    //        System.out.println("Player 2: " + evaluator2.getTrips());
//
    //        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
    //        Assertions.assertEquals("Tie", outcome.getWinner());
    //    }
    //}
    @Test
    public void twoPairVersusTwoPairTie() {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Hand hand1 = new Hand(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            Hand hand2 = new Hand(hand1.getCards());


            HandEvaluator evaluator1 = new HandEvaluator(new Player(), hand1);
            HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);

            System.out.println("Player 1: " + evaluator1.getPairs());
            System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
            System.out.println("Player 2: " + evaluator2.getPairs());
            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals("Tie", outcome.getWinner());
        }
    }
    //@Test
    //public void pairVersusPairTie() {
    //    Global.initializePokerRanks(); Global.initializeCardValueMap();
//
    //    for (int i = 3; i < Global.VALUES.length - 1; i++) {
    //        Player player1 = new Player();
    //        Hand hand1 = new Hand();
//
    //        Player player2 = new Player();
    //        Hand hand2 = new Hand();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[2],
    //                Global.VALUES[1],
    //                Global.VALUES[0],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand1.setHand(cards);
    //        hand2.setHand(cards);
//
    //        System.out.println("Player 1: " + Arrays.toString(hand1.getValueData()));
    //        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
    //        System.out.println("Player 1: " + evaluator1.getPairs());
//
    //        System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
    //        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
    //        System.out.println("Player 2: " + evaluator2.getPairs());
//
//
    //        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
    //        Assertions.assertEquals("Tie", outcome.getWinner());
    //    }
    //}
    @Test
    public void highCardVersusHighCardTie() {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        Player player1 = new Player();
        Hand hand1 = new Hand();

        Player player2 = new Player();
        Hand hand2 = new Hand();
        ArrayList<PlayingCard> cards = UnitTesting.handBuilder(
                "Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Hearts", "Clubs", "Diamonds");
        hand1.setHand(cards);
        hand2.setHand(cards);

        HandEvaluator evaluator1 = new HandEvaluator(player1, hand1);
        System.out.println("Player 1: " + evaluator1.getHandRank());

        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 2: " + evaluator2.getHandRank());

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals("Tie", outcome.getWinner());
    }

    // Drawing cards from deck

    // Methods


    public void handVersusRoyalFlush(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        Player player2 = new Player();
        Hand hand2 = new Hand(UnitTesting.handBuilder(
                "Ace", "King", "Queen", "Jack", "10",
                "Spades", "Spades", "Spades", "Spades", "Spades"));
        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 1: " + evaluator1.getHandRank());
        System.out.println("Player 1: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));
        System.out.println("Player 2: " + evaluator2.getHandRank());
        System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
        System.out.println();

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals(winner, outcome.getWinner());
    }
    public void handVersusStraightFlush(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();


        for (int i = 0; i < Global.VALUES.length - 6; i++) {
            Hand hand2 = new Hand(UnitTesting.handBuilder(Global.VALUES[i], Global.VALUES[i + 1], Global.VALUES[i + 2], Global.VALUES[i + 3], Global.VALUES[i + 4],
                    "Clubs","Clubs","Clubs","Clubs","Clubs"));
            HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);


            System.out.println("Player 1: " + evaluator1.getHandRank());
            System.out.println("Player 1: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));
            System.out.println("Player 2: " + evaluator2.getHandRank());
            System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
        System.out.println();
    }
    public void handVersusQuads(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);


            System.out.println("Player 1: " + evaluator1.getHandRank());
            System.out.println("Player 1: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));
            System.out.println("Player 2: " + evaluator2.getHandRank());
            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusFullHouse(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand2 = new Hand();
            Player player2 = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));
            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getFullHouse());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusFlush(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            hand2.setHand(UnitTesting.handBuilder(
                    "King", "7", "Jack", "5", "2",
                    Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusStraight(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        Hand hand2 = new Hand();

        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        PlayingCard[] cardsInHand;
        for (int i = 0; i < 10; i++) {
            hand2.getCards().clear();
            cardsInHand = new PlayingCard[5];
            for (int j = 0; j < 5; j++) {
                cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
                hand2.addCard(cardsInHand[j]);
            }
            HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);

            System.out.println("Player 1: " + evaluator1.getHandRank());
            System.out.println("Player 1: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));
            System.out.println("Player 2: " + evaluator2.getHandRank());
            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));

            GameOutcome outcome = new GameOutcome(evaluator1,evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusTrips(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 2; i < Global.VALUES.length - 2; i++) {
            Hand hand2 = new Hand(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));

            HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);
            System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
            System.out.println("Player 2: " + evaluator2.getTrips());
            System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
            System.out.println(evaluator2.getHandRank());
            System.out.println();
            System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
            System.out.println("Player 2: " + evaluator2.getTrips());
            System.out.println("Player 2: " + Arrays.toString(evaluator2.getFiveCardHand().getValueData()));
            System.out.println(evaluator2.getHandRank());

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusTwoPair(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        for (int i = 0; i < Global.VALUES.length - 2; i++) {
            Player player2 = new Player();
            Hand hand2 = new Hand();
            ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    Global.VALUES[13],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand2.setHand(cards);

            HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
            System.out.println("Player 2: " + evaluator2.getPairs());
            System.out.println("Player 2: " + Arrays.toString(hand2.getValueData()));

            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusPair(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks();
        Global.initializeCardValueMap();

        for (int i = 3; i < Global.VALUES.length - 1; i++) {
            Hand hand2 = new Hand(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[2],
                    Global.VALUES[1],
                    Global.VALUES[0],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));

            HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);

            System.out.println("Player 1: " + evaluator1.getPairs());
            System.out.println("Player 1: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));

            System.out.println("Player 2: " + evaluator2.getPairs());
            System.out.println("Player 2: " + Arrays.toString(evaluator1.getFiveCardHand().getValueData()));


            GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
            Assertions.assertEquals(winner, outcome.getWinner());
        }
    }
    public void handVersusHighCard(HandEvaluator evaluator1, String winner) {
        Global.initializePokerRanks(); Global.initializeCardValueMap();

        Player player2 = new Player();
        Hand hand2 = new Hand();
        hand2.setHand(UnitTesting.handBuilder(
                "Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Hearts", "Clubs", "Diamonds"));
        HandEvaluator evaluator2 = new HandEvaluator(player2, hand2);
        System.out.println("Player 2: " + evaluator2.getHandRank());

        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);
        Assertions.assertEquals(winner, outcome.getWinner());
    }
}
