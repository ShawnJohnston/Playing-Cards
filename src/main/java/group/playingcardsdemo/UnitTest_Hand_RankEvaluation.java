package group.playingcardsdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitTest_Hand_RankEvaluation {
    @Test
    public void canCompareValuesOfCards() {
        Global.initializeCardValueMap();

        for (int i = Global.VALUES.length - 1; i > 0; i--) {
            PlayingCard card1 = new PlayingCard(Global.VALUES[i], "Spades");
            PlayingCard card2 = new PlayingCard(Global.VALUES[i - 1], "Spades");

            System.out.printf(PlayingCard.valueMap.get(card1.getValue()) + " (%s)  > " + PlayingCard.valueMap.get(card2.getValue()) + " (%s)\n",
                    Global.VALUES[i], Global.VALUES[i - 1]);
            Assertions.assertTrue(PlayingCard.valueMap.get(card1.getValue()) > PlayingCard.valueMap.get(card2.getValue()));
            Assertions.assertFalse(PlayingCard.valueMap.get(card1.getValue()) < PlayingCard.valueMap.get(card2.getValue()));
        }
        for (int i = Global.VALUES.length - 1; i > 0; i--) {
            PlayingCard card1 = new PlayingCard(Global.VALUES[i - 1], "Spades");
            PlayingCard card2 = new PlayingCard(Global.VALUES[i], "Spades");

            System.out.printf(PlayingCard.valueMap.get(card2.getValue()) + " (%s) > " + PlayingCard.valueMap.get(card1.getValue()) + " (%s)\n",
                    Global.VALUES[i], Global.VALUES[i - 1]);
            Assertions.assertTrue(PlayingCard.valueMap.get(card2.getValue()) > PlayingCard.valueMap.get(card1.getValue()));
            Assertions.assertFalse(PlayingCard.valueMap.get(card2.getValue()) < PlayingCard.valueMap.get(card1.getValue()));
        }
    }
    @Test
    public void canCompareRanks_ByHashMap() {
        Global.initializePokerRanks();

        for (int i = Global.STANDARDPOKERRANKS.length - 1; i > 0; i--) {
            String rankTier1 = Global.STANDARDPOKERRANKS[i];
            String rankTier2 = Global.STANDARDPOKERRANKS[i - 1];

            System.out.printf(HandEvaluator.pokerRanks.get(rankTier1) + " (%s)  > " + HandEvaluator.pokerRanks.get(rankTier2) + " (%s)\n",
                    Global.STANDARDPOKERRANKS[i], Global.STANDARDPOKERRANKS[i - 1]);
            Assertions.assertTrue(HandEvaluator.pokerRanks.get(rankTier1) > HandEvaluator.pokerRanks.get(rankTier2));
            Assertions.assertFalse(HandEvaluator.pokerRanks.get(rankTier1) < HandEvaluator.pokerRanks.get(rankTier2));
        }
    }
    @Test
    public void canCompareRanks_ByHashMap_UsingHandEvaluator() {
        Global.initializePokerRanks();

        Hand hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "King", "Queen", "Jack", "10",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        Hand hand2 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("King", "Queen", "Jack", "10", "9",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "Ace", "Ace", "10",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "Ace", "King", "King",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "King", "Queen", "Jack", "10",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "Ace", "Jack", "10",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "King", "King", "10",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "King", "Jack", "10",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);

        hand1 = new Hand(new ArrayList<>(UnitTesting.handBuilder("Ace", "King", "Queen", "Jack", "9",
                "Spades", "Diamonds", "Diamonds", "Diamonds", "Diamonds")));
        canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(hand1, hand2);
    }
    public void canCompareRanks_ByHashMap_UsingHandEvaluator_Helper(Hand hand1, Hand hand2) {
        HandEvaluator evaluator1 = new HandEvaluator(new Player(), hand1);
        HandEvaluator evaluator2 = new HandEvaluator(new Player(), hand2);
        GameOutcome outcome = new GameOutcome(evaluator1, evaluator2);

        System.out.printf("Player 1: %s     Player 2: %s\n", evaluator1.getHandRank(), evaluator2.getHandRank());
        System.out.println("Winner: " + outcome.getWinner());
        Assertions.assertEquals("Player 1", outcome.getWinner());
        Assertions.assertNotEquals("Player 2", outcome.getWinner());
    }
    @Test
    public void canCountValuesInHand() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamonds", "Diamonds", "Diamonds", "Diamonds", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getValueData()));
        int[] comparisonData = {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0 };
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Assertions.assertEquals(hand.getValueData()[i], comparisonData[i]);
        }
    }
    @Test
    public void canCountSuitsInHand() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);
        System.out.println(Arrays.toString(hand.getSuitData()));
        int[] comparisonData = {1, 1, 2, 1, 0};
        for (int i = 0; i < Global.SUITS.length - 1; i++) {
            Assertions.assertEquals(hand.getSuitData()[i], comparisonData[i]);
        }
    }
    @Test
    public void canReadHandOfCards() {
        Hand hand = new Hand();
        ArrayList<PlayingCard> cards = UnitTesting.handBuilder("Ace", "Queen", "Jack", "10", "9",
                "Diamond", "Diamond", "Diamond", "Diamond", "Diamond");
        hand.setHand(cards);
        for (int i = 0; i < hand.getCards().size(); i++) {
            System.out.println(hand.getCards().get(i).getName());
        }
    }
@Test
public void theHandContainsOnePair() {
    for (int i = 0; i < Global.VALUES.length - 1; i++) {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[13],
                Global.VALUES[13],
                Global.VALUES[13],
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);

        System.out.println(Arrays.toString(hand.getValueData()));
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println(evaluator.getPairs());

        Assertions.assertEquals(1, evaluator.getPairs().size());
        Assertions.assertEquals(Global.VALUES[i], evaluator.getPairs().get(0));
    }
}
@Test
public void theHandContainsTwoPair() {
    for (int i = 0; i < Global.VALUES.length - 2; i++) {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[i + 1],
                Global.VALUES[i + 1],
                Global.VALUES[13],
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);

        System.out.println(Arrays.toString(hand.getValueData()));
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println(evaluator.getPairs());

        Assertions.assertEquals(2, evaluator.getPairs().size());
        Assertions.assertEquals(Global.VALUES[i], evaluator.getPairs().get(1));
        Assertions.assertEquals(Global.VALUES[i + 1], evaluator.getPairs().get(0));
        Assertions.assertEquals("TwoPair", evaluator.getHandRank().toString());
    }
}
@Test
public void theHandContainsTrips() {
    for (int i = 0; i < Global.VALUES.length - 1; i++) {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[13],
                Global.VALUES[13],
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);

        System.out.println(Arrays.toString(hand.getValueData()));
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println(evaluator.getTrips());

        Assertions.assertEquals(Global.VALUES[i], evaluator.getTrips().get(0));
    }
}
@Test
public void theHandContainsQuads() {
    for (int i = 0; i < Global.VALUES.length - 1; i++) {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[i],
                Global.VALUES[13],
                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
        hand.setHand(cards);

        System.out.println(Arrays.toString(hand.getValueData()));
        HandEvaluator evaluator = new HandEvaluator(player, hand);
        System.out.println(evaluator.getQuadsValue());

        Assertions.assertEquals(4, hand.getValueData()[i]);
        Assertions.assertEquals("Quads", evaluator.getHandRank().toString());
    }
}
    @Test
    public void theHandContainsFullHouse() {
        for (int i = 0; i < Global.VALUES.length - 1; i++) {
            Hand hand = new Hand();
            Player player = new Player();
            ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i],
                    Global.VALUES[i + 1],
                    Global.VALUES[i + 1],
                    "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
            hand.setHand(cards);

            System.out.println(Arrays.toString(hand.getValueData()));
            HandEvaluator evaluator = new HandEvaluator(player, hand);
            System.out.println(evaluator.getFullHouse());

            Assertions.assertEquals(Global.VALUES[i], evaluator.getFullHouse().get(0));
            Assertions.assertEquals(Global.VALUES[i + 1], evaluator.getFullHouse().get(1));
            Assertions.assertEquals("FullHouse", evaluator.getHandRank().toString());
        }
    }
    //@Test
    //public void theHandContainsNoPair() {
    //    Hand hand = new Hand();
    //    Player player = new Player();
    //    ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder("Ace", "King", "Jack", "10", "9",
    //            "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //    hand.setHand(cards);
    //    System.out.println(Arrays.toString(hand.getValueData()));
//
    //    for (int i = 0; i < hand.getValueData().length; i++) {
    //        Assertions.assertNotEquals(2, hand.getValueData()[i]);
    //    }
    //}
    //@Test
    //public void theHandContainsNoTrips() {
    //    for (int i = 0; i < Global.VALUES.length - 2; i++) {
    //        Hand hand = new Hand();
    //        Player player = new Player();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i + 1],
    //                Global.VALUES[13],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand.setHand(cards);
//
    //        System.out.println(Arrays.toString(hand.getValueData()));
    //        HandEvaluator evaluator = new HandEvaluator(player, hand);
    //        System.out.println(evaluator.getTrips());
//
    //        Assertions.assertEquals(0, evaluator.getTrips().size());
    //    }
//
    //    Hand hand = new Hand();
    //    Player player = new Player();
    //    ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //            "Ace", "Ace", "2",
    //            Global.VALUES[13],
    //            Global.VALUES[13],
    //            "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //    hand.setHand(cards);
//
    //    System.out.println(Arrays.toString(hand.getValueData()));
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    System.out.println(evaluator.getTrips());
//
    //    Assertions.assertEquals(0, evaluator.getTrips().size());
    //}
    //@Test
    //public void theHandContainsNoQuads() {
    //    for (int i = 0; i < Global.VALUES.length - 1; i++) {
    //        Hand hand = new Hand();
    //        Player player = new Player();
    //        ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder(
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[i],
    //                Global.VALUES[13],
    //                Global.VALUES[13],
    //                "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //        hand.setHand(cards);
//
    //        System.out.println(Arrays.toString(hand.getValueData()));
    //        HandEvaluator evaluator = new HandEvaluator(player, hand);
    //        System.out.println(evaluator.getQuadsValue());
//
    //        Assertions.assertNotEquals(4, hand.getValueData()[i]);
    //    }
    //}
    //@Test
    //public void canCountPairsInHand() {
    //    Hand hand = new Hand();
    //    ArrayList<PlayingCard> cards = new ArrayList<>(UnitTesting.handBuilder("Ace", "Ace", "Jack", "10", "9",
    //            "Spades", "Hearts", "Clubs", "Clubs", "Diamonds"));
    //    hand.setHand(cards);
    //    System.out.println(Arrays.toString(hand.getValueData()));
//
//
    //}
    //// Hand Evaluator
    //// Straights and Flushes
    //@Test
    //public void canEvaluateFlushes() {
    //    // Explicit cases.
    //    int iterationNumber = 1;
    //    Player player = new Player();
//
    //    // This loop will test each suit possibility for a 5-card flush. Card values are irrelevant.
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //                Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
    //        fiveCardHand_fiveCardFlush_True(player, iterationNumber);
    //        iterationNumber++;
    //    }
//
    //    // 6-Card hand.
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //                Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
    //        fiveCardHand_fiveCardFlush_True(player, iterationNumber);
    //        iterationNumber++;
    //    }
//
    //    // Iterative value cases.
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        player.setHand(UnitTesting.handBuilder(Global.VALUES[0],
    //                Global.VALUES[1],
    //                Global.VALUES[2],
    //                Global.VALUES[3],
    //                Global.VALUES[5],
    //                Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i], Global.SUITS[i]));
    //        fiveCardHand_fiveCardFlush_True(player, i);
    //    }
//
//
    //    for (int i = 0; i < Global.VALUES.length - Global.straightFlushSize - 1; i++) {
    //        for (int j = 0; j < Global.SUITS.length - 1; j++) {
    //            player.setHand(UnitTesting.handBuilder(
    //                    Global.VALUES[i],
    //                    Global.VALUES[i + 1],
    //                    Global.VALUES[i + 2],
    //                    Global.VALUES[i + 3],
    //                    Global.VALUES[i + 5],
    //                    Global.SUITS[j], Global.SUITS[j], Global.SUITS[j], Global.SUITS[j], Global.SUITS[j]));
    //            fiveCardHand_fiveCardFlush_True(player, i + 2);
    //        }
    //    }
    //}
    //@Test
    //public void canEvaluateNotFlushes() {
    //    Player player = new Player();
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Hearts", "Spades", "Spades", "Spades", "Spades"));
    //    fiveCardHand_fiveCard_Flush_False(player, 1);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Spades", "Hearts", "Spades", "Spades", "Spades"));
    //    fiveCardHand_fiveCard_Flush_False(player, 2);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Spades", "Spades", "Hearts", "Spades", "Spades"));
    //    fiveCardHand_fiveCard_Flush_False(player, 3);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Spades", "Spades", "Spades", "Hearts", "Spades"));
    //    fiveCardHand_fiveCard_Flush_False(player, 4);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Spades", "Spades", "Spades", "Spades", "Hearts"));
    //    fiveCardHand_fiveCard_Flush_False(player, 5);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "Spades", "Spades", "Spades", "Spades", "asdf"));
    //    fiveCardHand_fiveCard_Flush_False(player, 6);
//
    //    player.setHand(UnitTesting.handBuilder("King", "7", "5", "2", "Ace",
    //            "asdf", "asdf", "asdf", "asdf", "asdf"));
    //    fiveCardHand_fiveCard_Flush_False(player, 7);
    //}
    //@Test
    //public void canEvaluateStraights() {
    //    Player player = new Player();
//
    //    //player.setHand(UnitTesting.handBuilder("Ace", "2", "3", "4", "5",
    //    //        "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    //HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    //Assertions.assertTrue(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
//
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("5", "6", "7", "8", "9",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
//
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("9", "10", "Jack", "Queen", "King",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("10", "Jack", "Queen", "King", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
    //}
    //@Test
    //public void canEvaluateStraightsByValue() {
    //    Player player = new Player();
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("4", "5", "6", "7", "8",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("5", "6", "7", "8", "9",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("9", "10", "Jack", "Queen", "King",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("10", "Jack", "Queen", "King", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
    //}
    //@Test
    //public void canEvaluate7CardStraightsByValue() {
    //    Player player = new Player();
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "4", "5", "6", "7", "8",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("4", "5", "6", "7", "8", "9","10",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("5", "6", "7", "8", "9", "10", "Jack",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("8", "9", "10", "Jack", "Queen", "King", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("7","8", "9", "10", "Jack", "Queen", "King",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds","Hearts", "Diamonds"));
    //    straight_ByValue_True(player, player.getHand());
    //}
    //@Test
    //public void canEvaluateAllStraights() {
    //    // This test will procedurally check ALL value combinations that are supposed to indicate the Straight rank.
    //    // An assertion will be made for each Iteration of a Straight hand.
    //    Player player = new Player();
    //    Hand hand = new Hand();
//
    //    // These values compose every value in a standard deck of playing cards.
    //    // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
    //    PlayingCard[] cardsInHand;
//
    //    for (int i = 1; i < 11; i++) {
    //        hand = new Hand();
    //        cardsInHand = new PlayingCard[5];
    //        for (int j = 0; j < 5; j++) {
    //            cardsInHand[j] = new PlayingCard(Global.VALUESHIERARCHY[j + i], Global.SUITS[j]);
    //            hand.addCard(cardsInHand[j]);
    //        }
    //        UnitTesting.printHand(hand);
    //        System.out.println();
    //        HandEvaluator evaluator = new HandEvaluator(player, hand);
    //        Assertions.assertTrue(evaluator.isAStraight_ByValueData());
    //        UnitTesting.printHandRanking(evaluator);
    //        System.out.println();
    //    }
    //}
    //@Test
    //public void canEvaluateAllStraightsByValueData() {
    //    Player player = new Player();
    //    Hand hand = new Hand();
//
    //    int straightSize = 7;
    //    String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    //    PlayingCard[] cardsInHand;
//
    //    int counter = 1;
    //    for (int i = 0; i < hand.getValueData().length; i++) {
    //        hand.getCards().clear();
    //        cardsInHand = new PlayingCard[7];
    //        for (int j = 0; j < 5; j++) {
    //            cardsInHand[j] = new PlayingCard(values[j + i], Global.SUITS[j]);
    //            hand.addCard(cardsInHand[j]);
    //        }
    //        UnitTesting.printHand(hand);
    //        if (counter == 5) {
    //            break;
    //        }
    //        if (hand.getValueData()[i] > 0 && hand.getValueData()[i + 1] > 0) {
    //            counter++;
    //        }
    //        else {
    //            counter = 1;
    //        }
    //        System.out.println(Arrays.toString(hand.getValueData()));
    //    }
    //    Assertions.assertEquals(5, counter);
    //}
    //@Test
    //public void canEvaluateNotStraights() {
    //    Player player = new Player();
//
    //    //player.setHand(UnitTesting.handBuilder("Ace", "2", "3", "4", "6",
    //    //        "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    //HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    //Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "3", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "2", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "3", "5", "7", "9",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "Ace", "Ace", "Ace", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "4", "5", "5",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "Jack", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
//
    //    player.setHand(UnitTesting.handBuilder("9", "10", "Jack", "King", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
    //}
    //@Test
    //public void canEvaluateNotStraightsByValue() {
    //    Player player = new Player();
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "2", "3", "4", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
    //    player.setHand(UnitTesting.handBuilder("Ace", "3", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "2", "4", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "3", "5", "7", "9",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("Ace", "Ace", "Ace", "Ace", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "4", "5", "5",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("2", "3", "Jack", "5", "6",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
//
    //    player.setHand(UnitTesting.handBuilder("9", "10", "Jack", "King", "Ace",
    //            "Spades", "Spades", "Hearts", "Clubs", "Diamonds"));
    //    straight_ByValue_False(player, player.getHand());
    //}
    //@Test
    //public void canEvaluateStraights6CardHand() {
    //    // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
    //    Player player = new Player();
    //    Hand hand = new Hand();
    //    PlayingCard sevenSpades = new PlayingCard("7", "Spades");
    //    PlayingCard eightClubs = new PlayingCard("8", "Clubs");
    //    PlayingCard nineHearts = new PlayingCard("9", "Hearts");
    //    PlayingCard tenSpades = new PlayingCard("10", "Spades");
    //    PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
    //    PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
//
    //    // Each card is added to the player's hand. They are added out of order intentionally.
    //    // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
    //    hand.addCard(sevenSpades);
    //    hand.addCard(jackHearts);
    //    hand.addCard(nineHearts);
    //    hand.addCard(eightClubs);
    //    hand.addCard(tenSpades);
    //    hand.addCard(queenHearts);
//
    //    UnitTesting.printHand(hand);
//
    //    // The evaluator will check if the hand values are consecutive.
    //    // The boolean value is checked for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //@Test
    //public void canEvaluateStraight6CardHand1() {
    //    // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
    //    Player player = new Player();
    //    Hand hand = new Hand();
    //    PlayingCard sevenSpades = new PlayingCard("7", "Spades");
    //    PlayingCard eightClubs = new PlayingCard("8", "Clubs");
    //    PlayingCard nineHearts = new PlayingCard("9", "Hearts");
    //    PlayingCard tenSpades = new PlayingCard("10", "Spades");
    //    PlayingCard jackHearts = new PlayingCard("Jack", "Hearts");
    //    PlayingCard kingHearts = new PlayingCard("King", "Hearts");
//
    //    // Each card is added to the player's hand. They are added out of order intentionally.
    //    // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
    //    hand.addCard(sevenSpades);
    //    hand.addCard(jackHearts);
    //    hand.addCard(nineHearts);
    //    hand.addCard(eightClubs);
    //    hand.addCard(tenSpades);
    //    hand.addCard(kingHearts);
//
    //    UnitTesting.printHand(hand);
//
    //    // The evaluator will check if the hand values are consecutive.
    //    // The boolean value is checked for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData());
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //@Test
    //public void canEvaluateNotStraights6CardHand() {
    //    // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
    //    Player player = new Player();
    //    Hand hand = new Hand();
    //    PlayingCard sevenSpades = new PlayingCard("7", "Spades");
    //    PlayingCard eightClubs = new PlayingCard("8", "Clubs");
    //    PlayingCard nineHearts = new PlayingCard("9", "Hearts");
    //    PlayingCard tenSpades = new PlayingCard("10", "Spades");
    //    PlayingCard queenHearts = new PlayingCard("Queen", "Hearts");
    //    PlayingCard kingHearts = new PlayingCard("King", "Hearts");
    //
    //    // Each card is added to the player's hand. They are added out of order intentionally.
    //    // The hand evaluator must sort the hand correctly in order for its internal methods to identify a Straight.
    //    hand.addCard(sevenSpades);
    //    hand.addCard(nineHearts);
    //    hand.addCard(eightClubs);
    //    hand.addCard(tenSpades);
    //    hand.addCard(queenHearts);
    //    hand.addCard(kingHearts);
    //
    //    UnitTesting.printHand(hand);
    //
    //    // The evaluator will check if the hand values are consecutive.
    //    // The boolean value is checked for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData());
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //@Test
    //public void canEvaluateAllStraightFlushes() {
    //    // Note: This test is identical to the 'canEvaluateAllFlushes()' test. It is reused for the
    //    // distinct purpose to demonstrate the evaluation of Straight Flushes specifically.
//
    //    // This test will procedurally check ALL Straight possibilities (disregarding suits).
    //    // An assertion will be made for each Iteration of a Straight hand.
    //    Player player = new Player();
    //    Hand hand = new Hand();
//
    //    // These values compose every value in a standard deck of playing cards.
    //    // A "low-Ace" and "high-Ace" are necessary for determining the existence of a 5-high Straight (aka "wheel").
    //    PlayingCard[] cardsInHand;
    //    int iterationCount = 1;
//
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        for (int j = 0; j < 9; j++) {
    //            hand.getCards().clear();
    //            cardsInHand = new PlayingCard[5];
    //            System.out.println("Iteration " + iterationCount + ": " + Global.SUITS[i]);
    //            for (int k = 0; k < 5; k++) {
    //                cardsInHand[k] = new PlayingCard(Global.VALUES[k + j], Global.SUITS[i]);
    //                hand.addCard(cardsInHand[k]);
    //            }
    //            iterationCount++;
    //            UnitTesting.printHand(hand);
    //            HandEvaluator evaluator = new HandEvaluator(player, hand);
    //            Assertions.assertTrue(evaluator.isAStraightFlush());
    //            UnitTesting.printHandRanking(evaluator);
    //            if (hand.getCards().get(hand.getCards().size() - 1).getValue().equals("Ace")) {
    //                Assertions.assertEquals("RoyalFlush", evaluator.getHandRank().toString());
    //            }
    //            else {
    //                Assertions.assertEquals("StraightFlush", evaluator.getHandRank().toString());
    //            }
    //        }
    //    }
//
    //}
    //@Test
    //public void canEvaluateNotStraightFlushes() {
    //    // This test determines the inverse of truth to the evaluation of a Straight Flush (ie, NOT Straight Flush).
    //    Player player = new Player();
    //    Hand hand = new Hand();
    //    PlayingCard fiveSpades = new PlayingCard("5", "Spades");
    //    PlayingCard sixSpades = new PlayingCard("6", "Spades");
    //    PlayingCard sevenSpades = new PlayingCard("7", "Spades");
    //    PlayingCard eightSpades = new PlayingCard("8", "Spades");
    //    PlayingCard nineClubs = new PlayingCard("9", "Clubs");
//
    //    hand.addCard(fiveSpades);
    //    hand.addCard(sixSpades);
    //    hand.addCard(nineClubs);
    //    hand.addCard(sevenSpades);
    //    hand.addCard(eightSpades);
//
    //    UnitTesting.printHand(hand);
//
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    Assertions.assertFalse(evaluator.isAStraightFlush());
    //    Assertions.assertFalse(evaluator.isARoyalFlush());
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //@Test
    //public void canEvaluateAllRoyalFlushes() {
    //    for (int i = 0; i < Global.SUITS.length - 1; i++) {
    //        fiveCardHand_fiveCardRoyalFlush_True(Global.SUITS[i], (i + 1));
    //    }
    //}
//
//
//
    //public void fiveCardHand_fiveCardFlush_True(Player player, int iterationNumber) {
    //    // This single test case will evaluate if a given Poker hand evaluates to be the Flush rank.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // Every card is the same suit and the values do not matter for this scenario.
//
    //    System.out.println("Iteration " + iterationNumber);
    //    UnitTesting.printHand(player.getHand());
//
    //    // The evaluator will check if the player's hand evaluates to be a flush.
    //    // Finally, it is tested for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAFlush(), "Failure at iteration #" + iterationNumber);
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //public void fiveCardHand_fiveCard_Flush_False(Player player, int iterationNumber) {
    //    // This test case will evaluate the inverse case of a flush evaluation for a hand.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // The hand must not contain all matching suits and the values do not matter for this scenario.
    //    System.out.println("Iteration " + iterationNumber);
    //    UnitTesting.printHand(player.getHand());
//
    //    // The evaluator will check if the player's hand evaluates to be a flush.
    //    // Finally, it is tested for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAFlush());
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //public void fiveCardHand_fiveCard_Straight_True(Player player, int iterationNumber) {
    //    // This single test case will evaluate if the given Poker hand evaluates to be the Straight rank.
    //    // A player, 5 playing cards, and the hand evaluator are used for the test.
    //    // Each card will be sorted, then the hand will be checked to see if the values are in sequence.
//
    //    System.out.println("Iteration " + iterationNumber);
    //    UnitTesting.printHand(player.getHand());
//
    //    // The evaluator will check if the hand values are consecutive.
    //    // The boolean value is checked for truth. If true, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertTrue(evaluator.isAStraight_ByValueData(), "Failure at iteration " + iterationNumber);
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //public void fiveCardHand_fiveCardStraight_False(Player player, int iterationNumber) {
    //    // This test check that a particular 5 card poker hand does NOT evaluate to be a straight.
    //    // The hand is consecutive to 4 cards (in other words a "4-card straight", which is not a straight).
    //    // When evaluated in the handEvaluator, the boolean should return false. If so, the test passes.
//
    //    UnitTesting.printHand(player.getHand());
//
    //    // The evaluator will check if the hand values are consecutive.
    //    // The boolean value is checked for truth. If false, the test passes.
    //    HandEvaluator evaluator = new HandEvaluator(player, player.getHand());
    //    Assertions.assertFalse(evaluator.isAStraight_ByValueData(), "Failure at iteration " + iterationNumber);
    //    UnitTesting.printHandRanking(evaluator);
    //}
    //public void fiveCardHand_fiveCardRoyalFlush_True(String suit, int iterationNumber) {
    //    Player player = new Player();
    //    Hand hand = new Hand();
    //    PlayingCard tenSuit = new PlayingCard("10", suit);
    //    PlayingCard jackSuit = new PlayingCard("Jack", suit);
    //    PlayingCard queenSuit = new PlayingCard("Queen", suit);
    //    PlayingCard kingSuit = new PlayingCard("King", suit);
    //    PlayingCard aceSuit = new PlayingCard("Ace", suit);
//
    //    hand.addCard(aceSuit);
    //    hand.addCard(jackSuit);
    //    hand.addCard(kingSuit);
    //    hand.addCard(tenSuit);
    //    hand.addCard(queenSuit);
//
    //    System.out.println("Iteration " + iterationNumber);
    //    UnitTesting.printHand(hand);
//
    //    HandEvaluator evaluator = new HandEvaluator(player, hand);
    //    Assertions.assertTrue(evaluator.isARoyalFlush());
    //    UnitTesting.printHandRanking(evaluator);
    //    Assertions.assertEquals("RoyalFlush", evaluator.getHandRank().toString());
    //}
    //public void straight_ByValue_True(Player player, Hand hand) {
    //    int counter = 1;
    //    UnitTesting.printHand(hand);
    //    for (int i = 0; i < Global.VALUES.length; i++) {
    //        if (counter == 5) {
    //            if (hand.getValueData()[i] > 0 && hand.getValueData()[i+1] > 0) {
    //                counter++;
    //            }
    //            if (hand.getValueData()[i + 1] > 0 && hand.getValueData()[i + 2] > 0) {
    //                counter++;
    //            }
    //            break;
    //        }
    //        if (hand.getValueData()[i] > 0 && hand.getValueData()[i+1] > 0) {
    //            counter++;
    //        }
    //        else {
    //            counter = 1;
    //        }
    //    }
    //    System.out.println(counter);
    //    Assertions.assertTrue(counter >= 5);
    //}
    //public void straight_ByValue_False(Player player, Hand hand) {
    //    int counter = 1;
    //    UnitTesting.printHand(hand);
    //    System.out.println(Arrays.toString(hand.getValueData()));
    //    for (int i = 1; i < Global.VALUES.length; i++) {
    //        if (hand.getValueData()[i] > 1 && hand.getValueData()[i+1] > 0) {
    //            counter++;
    //        }
    //        else {
    //            counter = 1;
    //        }
    //    }
    //    System.out.println(counter);
    //    Assertions.assertTrue(counter < 5);
    //}
}
