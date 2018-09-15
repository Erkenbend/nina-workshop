package ui;

import engine.utils;
import org.omg.CORBA.IntHolder;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Implementation of a Console User Interface for the game
 */
public class MyConsoleUI implements UserInterface {

    private Scanner sc;

    private static final String[] LEVELS = new String[]{
            "Easy",
            "Normal",
            "Quite Hard",
            "Hard",
            "Very Hard",
            "Ultra Hard",
            "Champion",
            "Impossible"
    };

    private static final String TOO_LARGE_MESSAGE = "Too large, try again";
    private static final String TOO_LOW_MESSAGE = "Too small, try again";
    private static final String HINT_PREFIX = "Computer says: ";
    private static final String WIN_MESSAGE = "You win! Needed: %d guesses";

    public MyConsoleUI() {
        this.sc = new Scanner(System.in);
    }

    public MyConsoleUI(InputStream inputStream) {
        this.sc = new Scanner(inputStream);
    }

    public String getTooLargeMessage() {
        return TOO_LARGE_MESSAGE;
    }

    public String getTooLowMessage() {
        return TOO_LOW_MESSAGE;
    }

    public String getHintPrefix() {
        return HINT_PREFIX;
    }

    /**
     * Warning: The win message has to contain a "%d" for the number of guesses
     * as it will be formatted in the game
     *
     * @return The win message
     */
    public String getWinMessage() {
        return WIN_MESSAGE;
    }

    /**
     * Display a message inviting the user to choose a interval via a level system
     *
     * @param lowerBoundHolder holder for the lower bound value
     * @param upperBoundHolder holder for the upper bound value
     */
    public void displayBoundChoice(IntHolder lowerBoundHolder, IntHolder upperBoundHolder) {
        int userChoice = -1;
        boolean isChoiceInvalid;
        do {
            isChoiceInvalid = false;
            System.out.println("Please choose a level in the following: ");
            for (int levelIndex = 0; levelIndex < LEVELS.length; levelIndex++) {
                System.out.println((levelIndex + 1) + ") " + LEVELS[levelIndex]);
            }
            System.out.print("Your choice: ");
            try {
                userChoice = this.nextInt(sc);
            } catch (NumberFormatException e) {
                isChoiceInvalid = true;
            }
            if (isChoiceInvalid = isChoiceInvalid || (userChoice <= 0 || userChoice > LEVELS.length)) {
                System.out.println("Invalid choice");
            }
        } while (isChoiceInvalid);

        lowerBoundHolder.value = 0;
        upperBoundHolder.value = utils.computeUpperBoundForLevel(userChoice);
    }

    /**
     * Display an error message for the user if the game cannot start
     *
     * @param message The error message
     */
    public void displayGameStartError(String message) {
        System.out.println("Error while starting the game, please try again. Error message: " + message);
    }

    /**
     * Display a success message for the user if the game successfully started
     *
     * @param lowerBound The lower bound of the game interval
     * @param upperBound The upper bound of the game interval
     */
    public void displayGameStartSuccess(int lowerBound, int upperBound) {
        System.out.println(String.format(
                "Starting game with interval [%d, %d]",
                lowerBound,
                upperBound
        ));
    }

    /**
     * Ask the user for their next guess in the game
     *
     * @param userGuess The user's guess
     */
    public void displayNextGuessPrompt(IntHolder userGuess) {
        int userChoice;
        do {
            System.out.print("Your guess: ");
            try {
                userChoice = this.nextInt(sc);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
            }
        } while (true);
        userGuess.value = userChoice;
    }

    /**
     * Display a simple message: hint for the user's next move or success message for instance
     *
     * @param hint The message to display
     */
    public void displaySimpleMessage(String hint) {
        System.out.println(hint);
    }

    /**
     * Display a last message before exiting the game
     */
    public void displayGoodbyeMessage() {
        System.out.println("Goodbye");
    }

    /**
     * Mimic Scanner.nextInt: read string from scanner, cast it to integer, throw exception
     * if string is not an integer. Wait for newline if input is empty or consists in
     * whitespace only
     *
     * @param sc Scanner that will receive the user input
     * @return parsed int
     * @throws NumberFormatException when conversion fails
     */
    private int nextInt(Scanner sc) throws NumberFormatException {
        String userInput;
        do {
            userInput = sc.nextLine();
        } while ("".equals(userInput.trim()));

        return Integer.parseInt(userInput);
    }
}
