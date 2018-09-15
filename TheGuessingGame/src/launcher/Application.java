package launcher;

import engine.Game;
import engine.utils;
import exceptions.InvalidBoundsException;
import org.omg.CORBA.IntHolder;
import ui.MyConsoleUI;

/**
 * Main class of the application
 * <p>
 * This is a slightly modified version of the classical game that consists in guessing some number
 * with the only indications that the number to guess (set at random in a given interval by the
 * computer) is lower or greater than your last guess.
 * <p>
 * In this variation, the game only tells in addition to the original information an indication
 * of how close the guess is to the answer. This is expressed with 'lower case' for bad guesses,
 * 'Capitalized Case' for average guesses, and 'CAPITAL CASE' for very close guesses.
 */
public class Application {
    public static void main(String[] args) {
        MyConsoleUI ui = new MyConsoleUI();

        // Init game
        IntHolder lowerBoundHolder = new IntHolder();
        IntHolder upperBoundHolder = new IntHolder();
        Game game = null;
        do {
            ui.displayBoundChoice(lowerBoundHolder, upperBoundHolder);
            try {
                int answer = utils.generateRandomAnswerInInterval(lowerBoundHolder.value, upperBoundHolder.value);
                game = new Game(lowerBoundHolder.value, upperBoundHolder.value, answer, ui);
            } catch (InvalidBoundsException e) {
                ui.displayGameStartError(e.getMessage());
            }
        } while (game == null);
        ui.displayGameStartSuccess(lowerBoundHolder.value, upperBoundHolder.value);

        // Play game
        IntHolder userGuess = new IntHolder();
        do {
            ui.displayNextGuessPrompt(userGuess);
            game.setLatestGuess(userGuess.value);
            ui.displaySimpleMessage(game.getHintForNextMove());
        } while (!game.isFinished());

        // Exit game
        ui.displayGoodbyeMessage();
    }
}
