package group.playingcardsdemo;

import lombok.Getter;

import java.util.HashMap;

@Getter

public class Chip {
    private final String COLOR;
    private final int VALUE;
    protected static HashMap<String, Integer> chipColors = new HashMap<>();
    protected static HashMap<Integer, String> chipValues = new HashMap<>();

    public Chip(String color) {
        this.COLOR = color;
        this.VALUE = chipColors.get(color);
    }
    public Chip(int value) {
        this.VALUE = value;
        this.COLOR = chipValues.get(value);
    }
    public static void initializeChipMaps() {
        Chip.chipColors.put("White", 1);
        Chip.chipColors.put("Yellow", 2);
        Chip.chipColors.put("Red", 5);
        Chip.chipColors.put("Green", 25);
        Chip.chipColors.put("Black", 100);

        Chip.chipValues.put(1, "White");
        Chip.chipValues.put(2, "Yellow");
        Chip.chipValues.put(5, "Red");
        Chip.chipValues.put(25, "Green");
        Chip.chipValues.put(100, "Black");
    }
}