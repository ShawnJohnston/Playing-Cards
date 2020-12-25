import java.nio.channels.Selector;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {
    private static String selector = ""; // Will be used to control menu navigation through menuController.
    private static String gameToStart;
    public static boolean keepRunning = true; // Flips 'running' in Main to false, allowing the application to end.

    public static String getGameToStart() {
        return gameToStart;
    }

    public ConsoleMenu() {
        Scanner input = new Scanner(System.in); // User input scanner.
        mainMenu(input); // Main menu execute upon calling the class.
    }
    public void mainMenu(Scanner input) {
        String prompt = "Playing Card Demo" + "\n";
        String[] options = { "Play", "Quit"};

        selectionEvaluator(input, prompt, options);
    }
    private void menuBranch(Scanner input) {
        // This method controls menu navigation, using the selector field as a condition.
        switch (selector) {
            case "Play":
                gameSelectionMenu(input);
                break;
            case "Quit":
                keepRunning = false;
                break;
            case "5 Card Stud":
                gameToStart = selector;
                break;
            case "Return to Main Menu":
                break;
        }
    }
    private void gameSelectionMenu(Scanner input) {
        String prompt = "Game Modes" + "\n\n" + "Select a Card game:";
        String[] options = {"5 Card Stud", "Return to Main Menu"};

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
