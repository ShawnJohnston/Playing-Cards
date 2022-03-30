package group.playingcardsdemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Global {
    public static String playerName;
    public static String gameMode;
    public static int startingChips = 0;
    public static int chipCount = 0;
    public static String[] cardFileNames = new String[54];
    public static Image[] cardImages = new Image[54];
    public static ImageView[] cardImageViews = new ImageView[54];

    public static final String[] STANDARDPOKERRANKS = {"HighCard", "Pair", "TwoPair", "Trips", "Straight", "Flush",
            "FullHouse", "Quads", "StraightFlush", "RoyalFlush"};
    public static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] VALUESHIERARCHY = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] SUITS = {"Spades", "Hearts", "Clubs", "Diamonds", "Joker"};
    public static int straightFlushSize = 5;

    public static final String[] GAMES = {"", "5 Card Stud", "UTH", "wasd", "qwerty"};
    public static String[][] payoutSheetUTH = new String[8][3];

    //public static void initializeChipMaps() {
    //    Chip.chipColors.put("White", 1);
    //    Chip.chipColors.put("Yellow", 2);
    //    Chip.chipColors.put("Red", 5);
    //    Chip.chipColors.put("Green", 25);
    //    Chip.chipColors.put("Black", 100);
//
    //    Chip.chipValues.put(1, "White");
    //    Chip.chipValues.put(2, "Yellow");
    //    Chip.chipValues.put(5, "Red");
    //    Chip.chipValues.put(25, "Green");
    //    Chip.chipValues.put(100, "Black");
    //}
    public static void initializeCardValueMap() {
        PlayingCard.valueMap.put("2", 0);
        PlayingCard.valueMap.put("3", 1);
        PlayingCard.valueMap.put("4", 2);
        PlayingCard.valueMap.put("5", 3);
        PlayingCard.valueMap.put("6", 4);
        PlayingCard.valueMap.put("7", 5);
        PlayingCard.valueMap.put("8", 6);
        PlayingCard.valueMap.put("9", 7);
        PlayingCard.valueMap.put("10", 8);
        PlayingCard.valueMap.put("Jack", 9);
        PlayingCard.valueMap.put("Queen", 10);
        PlayingCard.valueMap.put("King", 11);
        PlayingCard.valueMap.put("Ace", 12);
        PlayingCard.valueMap.put("Joker", 13);
    }
    public static void initializePokerRanks() {
        HandEvaluator.pokerRanks.put("HighCard", 0);
        HandEvaluator.pokerRanks.put("Pair", 1);
        HandEvaluator.pokerRanks.put("TwoPair", 2);
        HandEvaluator.pokerRanks.put("Trips", 3);
        HandEvaluator.pokerRanks.put("Straight", 4);
        HandEvaluator.pokerRanks.put("Flush", 5);
        HandEvaluator.pokerRanks.put("FullHouse", 6);
        HandEvaluator.pokerRanks.put("Quads", 8);
        HandEvaluator.pokerRanks.put("StraightFlush", 9);
        HandEvaluator.pokerRanks.put("RoyalFlush", 10);
    }
    public static void initializeCardImages() throws FileNotFoundException {
        String[] modifiedValues = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for (int i = 0; i < SUITS.length - 1; i++) {
            for (int j = 0; j < modifiedValues.length; j++) {
                String fileName = SUITS[i].toLowerCase() + "_" + modifiedValues[j].toLowerCase() + ".png";
                cardFileNames[(13*i) + j] = fileName;

                cardImages[(13*i) + j] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[(13*i) + j]));
            }
        }
        cardFileNames[52] = "joker_black.png";
        cardFileNames[53] = "joker_red.png";

        cardImages[52] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[52]));
        cardImages[53] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[53]));

    }
    public static void initializeCardImages(DeckOfCards deck) throws FileNotFoundException {
        cardFileNames = new String[54];
        cardImages = new Image[54];

        String fileName;
        for (int i = 0; i < 54; i++) {
            if (i >= deck.getMaxSize()) {
                fileName = "none.png";
            }
            else {
                fileName = String.valueOf(deck.getCards().get(i).getFront());
            }
            cardFileNames[i] = fileName;

            cardImages[i] = new Image(new FileInputStream("src/main/resources/group/playingcardsdemo/Card_Fronts/" + cardFileNames[i]));
        }
        initializeCardImageViews(deck.getMaxSize());
    }
    public static void initializeCardImageViews(int deckSize) {
        for (int i = 0; i < deckSize; i++) {
            cardImageViews[i].setImage(cardImages[i]);
        }
    }
}
