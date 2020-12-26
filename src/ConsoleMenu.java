import java.util.InputMismatchException;
import java.util.Scanner;

// This class contains all of the main functionality of the console menu interface.
public class ConsoleMenu {
    // Fields
    private static String selector = ""; // Will be used to control menu navigation through menuController.
    private static String gameToStart; // Determines which game mode will start.
    private static String chipsToStart;
    public static boolean keepRunning = true; // Flips 'running' in Main to false, allowing the application to end.

    // Constructor
    public ConsoleMenu() {
        Scanner input = new Scanner(System.in); // User input scanner.
        mainMenu(input); // Main menu execute upon calling the class.
    }

    // Getters
    public static String getGameToStart() {
        return gameToStart; // gameToStart is used elsewhere to start the selected game mode.
    }
    public static String getChipsToStart() {
        return chipsToStart; // chipsToStart is used elsewhere to set the starting amount for the player.
    }

    // Methods
    public void mainMenu(Scanner input) {
        // The starting menu
        String prompt = "Playing Card Demo" + "\n";
        String[] options = { "Play", "Quit"};

        selectionEvaluator(input, prompt, options);
    }
    private void menuBranch(Scanner input) {
        // This method controls menu navigation, using the selector field as a condition.
        switch (selector) {
            case "Play":
                gameSelectionMenu(input); // Proceeds the user the the menu for selecting game mode.
                break;
            case "Quit":
                keepRunning = false; // The boolean in main that keeps the application running is flipped to false.
                break;
            case "5 Card Stud":
                gameToStart = selector; // The selector is assigned to gameToStart, which will trigger a game instance elsewhere.
                break;
            case "100", "$200", "$500", "$1000", "$2000", "$2500":
                chipsToStart = selector; // The selector is assigned to chipsToStart, which will set the starting amount elsewhere.
                break;
            case "Return to Main Menu":
                break; // All methods end. The loop in main iterates, resulting in a restart of the menu.
        }
    }
    private void gameSelectionMenu(Scanner input) {
        String prompt = "Game Modes" + "\n\n" + "Select a Card game:";
        String[] options = {"5 Card Stud", "Return to Main Menu"};

        selectionEvaluator(input, prompt, options);
        startingChipsMenu(input);
    }
    private void startingChipsMenu(Scanner input) {
        String prompt = "How much would you like to start with?" + "\n\n";
        String[] options = {"$100", "$200", "$500", "$1000", "$2000", "$2500", "Return to Main Menu"};

        selectionEvaluator(input, prompt, options);
    }
    private void selectionEvaluator(Scanner input, String prompt, String[] options) {
        // This method uses a menu's prompt and options and evaluates the user selection for validity.
        int selection = 0; // Default selection. '0' is defined to be erroneous in the following try/catch.
        while (selection == 0) {
            // This code iterated every time the loop reads 'selection' to be 0.
            try{
                menuBuilder(prompt, options); // Sends menu data to a method that builds the menu.
                selection = input.nextInt(); // User enters their selection.
                if (selection > options.length || selection < 0) {
                    // If the selection is greater, less, it is reset to 0.
                    selection = 0;
                }
            }
            catch (InputMismatchException e) {
                // The user entered a character that is not an integer.
                input.next(); // Clears the scanner so that it can be reused.
            }
        }
        selector = options[selection - 1]; // The selector field is assigned the String option at the corresponding index.
        menuBranch(input); // Executes menuBranch for navigation.
    }
    private void menuBuilder(String prompt, String[] options) {
        // This method will procedurally build the current menu.
        System.out.println(prompt); // Prints the prompt.
        for (int i = 0; i < options.length; i++) {
            // Each menu option is printed as a numbered list.
            System.out.printf("%d. %s\n", i + 1, options[i]);
        }
    }
}
