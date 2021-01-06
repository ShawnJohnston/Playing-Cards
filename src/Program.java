/*
    Shawn Johnston
    Playing Card Project
 */

import javax.swing.*;
import java.awt.*;

public class Program {
    public static void main(String[] args) {

        GameFrame frame = new GameFrame();


        // The program will be kept running using a while loop, and will remain running as long as it's boolean remains true.
        boolean running = true;
        GameMode gameMode = new GameMode();
        while (running) {
            Player user = new Player();
            Player computer = new Player();
            user.setName(ConsoleMenu.getUserName());
            gameMode.initializeGame(user, computer);
            GameMode.setGameMode(ConsoleMenu.getGameToStart());

            gameMode.play5CardPoker(user, computer);
        }
    }
}