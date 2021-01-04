import java.util.ArrayList;
import java.util.Arrays;

public class Dealer extends Player {
    // Fields
    private static int playerID = getPlayerNumber() + 1;
    private int playerCount;

    // Constructors
    public Dealer() {
        setPlayerCount(2);
    }
    public Dealer(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    //Methods
}