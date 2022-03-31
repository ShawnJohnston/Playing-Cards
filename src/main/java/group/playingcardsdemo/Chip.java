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
}