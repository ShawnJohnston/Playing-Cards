public class GameMode {
    private static String gameMode;
    private static int maximumHandSizeToSet;

    public GameMode() {
        setGameMode("");
    }

    public static String getGameMode() {
        return gameMode;
    }
    public static void setGameMode(String gameToStart) {
        gameMode = gameToStart;
        if (gameMode.equals("5 Card Stud")) {
            maximumHandSizeToSet = 5;
        }
    }

    public void initializeGame(Player user, Player dealer) {
        user.setMaxNumCardsInHand(maximumHandSizeToSet);
        user.setStartingChips(1000);
        dealer.setMaxNumCardsInHand(maximumHandSizeToSet);
    }

    public void play5CardStud() {

    }
}
