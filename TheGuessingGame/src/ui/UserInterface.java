package ui;

import org.omg.CORBA.IntHolder;

/**
 * Interface for the user interface of the game
 */
public interface UserInterface {
    String getTooLargeMessage();
    String getTooLowMessage();
    String getHintPrefix();
    String getWinMessage();
    void displayBoundChoice(IntHolder lowerBoundHolder, IntHolder upperBoundHolder);
    void displayGameStartError(String message);
    void displayGameStartSuccess(int lowerBound, int upperBound);
    void displayNextGuessPrompt(IntHolder userGuess);
    void displaySimpleMessage(String hint);
    void displayGoodbyeMessage();
}
