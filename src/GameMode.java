public class GameMode {
    private static playing gameMode;
    private static int maximumHandSizeToSet;

    public GameMode() {
        playing game = playing.None;
        setGameMode(game);
    }

    public static playing getGameMode() {
        return gameMode;
    }
    public static void setGameMode(playing game) {
        gameMode = game;
        if (gameMode.equals("5CardStud")) {
            maximumHandSizeToSet = 5;
        }
    }

    public void InitializeGame(Player user, Player dealer) {
        user.setMaxNumCardsInHand(maximumHandSizeToSet);
        user.setChipTotal(1000);

        dealer.setMaxNumCardsInHand(maximumHandSizeToSet);
    }
}
enum playing {
    None, FiveCardStud
}
