package group.playingcardsdemo;

import java.util.ArrayList;

public class ChipBank {
    private int total;
    private ArrayList<ChipStack> chipStacks = new ArrayList<>();

    public static class ChipStack {
        private int size = 0;
        private final int maxSize = 20;
        private int stackValue;
        private String stackColor;
        private ArrayList<Chip> stack = new ArrayList<>();
    }

    ChipBank() {
    }
    public void setChipTotal(int total) {
        this.total = total;
    }

    public int getChipTotal() {
        return total;
    }

    public void addToTotal(int amount) {
        this.total += amount;
    }
    public void subtractFromTotal(int amount) {
        this.total -= amount;
    }
}
