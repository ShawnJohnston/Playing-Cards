package group.playingcardsdemo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
