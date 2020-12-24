public class Chips {
    private String color;
    private int value;
    private final int FULL_STACK = 20;

    public Chips(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    public int getValue() {
        return value;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void setValue(int value) {
        this.value = value;
    }
}