package group.playingcardsdemo;

import java.util.HashMap;

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

    public String getColor() {
        return COLOR;
    }
    public int getValue() {
        return VALUE;
    }

}