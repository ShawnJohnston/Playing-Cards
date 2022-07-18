package group.playingcardsdemo.PlayingCards;

import java.util.HashMap;

public enum Values {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE, JOKER;

    public static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};
    public static final String[] VALUES_INDEX = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "Joker"};

    public static final HashMap<String, Integer> VALUEMAP = new HashMap<>();
    public static final HashMap<Integer, String> INDEXMAP = new HashMap<>();

    public static void initializeValueMap() {
        Values.VALUEMAP.put("2", 0);
        Values.VALUEMAP.put("3", 1);
        Values.VALUEMAP.put("4", 2);
        Values.VALUEMAP.put("5", 3);
        Values.VALUEMAP.put("6", 4);
        Values.VALUEMAP.put("7", 5);
        Values.VALUEMAP.put("8", 6);
        Values.VALUEMAP.put("9", 7);
        Values.VALUEMAP.put("10", 8);
        Values.VALUEMAP.put("Jack", 9);
        Values.VALUEMAP.put("Queen", 10);
        Values.VALUEMAP.put("King", 11);
        Values.VALUEMAP.put("Ace", 12);
        Values.VALUEMAP.put("Joker", 13);
    }
    public static void initializeIndexMap() {
        Values.INDEXMAP.put(0, "2");
        Values.INDEXMAP.put(1, "3");
        Values.INDEXMAP.put(2, "4");
        Values.INDEXMAP.put(3, "5");
        Values.INDEXMAP.put(4, "6");
        Values.INDEXMAP.put(5, "7");
        Values.INDEXMAP.put(6, "8");
        Values.INDEXMAP.put(7, "9");
        Values.INDEXMAP.put(8, "10");
        Values.INDEXMAP.put(9, "Jack");
        Values.INDEXMAP.put(10, "Queen");
        Values.INDEXMAP.put(11, "King");
        Values.INDEXMAP.put(12, "Ace");
        Values.INDEXMAP.put(13, "Joker");
    }
}
