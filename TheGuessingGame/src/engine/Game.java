package engine;

import exceptions.ExceptionCodes;
import exceptions.InvalidBoundsException;
import ui.UserInterface;

/**
 * Main class of the game engine
 * <p>
 * This class contains all info to run the game while saving its state and computing the results
 */
public class Game {

    public double VERY_SMALL_MARGIN = 0.02;
    public double RELATIVELY_SMALL_MARGIN = 0.15;

    private int lowerBound;
    private int upperBound;
    private int answer;
    private int latestGuess;
    private int nbGuesses;

    private UserInterface ui;

    public Game(int lowerBound, int upperBound, int answer, UserInterface ui) throws InvalidBoundsException {
        checkBoundsValidity(lowerBound, upperBound, answer);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.answer = answer;
        this.nbGuesses = 0;
        this.ui = ui;
    }

    /**
     * Check if the proposed bounds are valid in order to allow Game creation
     * Validity constraint: lowerBound &lt;= answer &lt;= upperBound
     *
     * @param lowerBound The lower bound of the interval containing the answer
     * @param upperBound The lower bound of the interval containing the answer
     * @param answer     The number that is to find
     * @throws InvalidBoundsException when the bounds are not valid
     */
    public static void checkBoundsValidity(int lowerBound, int upperBound, int answer) throws InvalidBoundsException {
        if (lowerBound > upperBound) {
            throw new InvalidBoundsException(
                    ExceptionCodes.LOWER_BOUND_GREATER_THAN_UPPER_BOUND,
                    String.format("Lower bound %d should be lower than upper bound %d", lowerBound, upperBound)
            );
        }
        if (answer < lowerBound) {
            throw new InvalidBoundsException(
                    ExceptionCodes.ANSWER_LOWER_THAN_LOWER_BOUND,
                    String.format("Answer %d should be greater than lower bound %d", answer, lowerBound)
            );
        }
        if (answer > upperBound) {
            throw new InvalidBoundsException(
                    ExceptionCodes.ANSWER_GREATER_THAN_UPPER_BOUND,
                    String.format("Answer %d should be lower than upper bound %d", answer, upperBound)
            );
        }

        // if we made it until here, all conditions are met: bounds are valid: return nothing, program goes on
    }

    /**
     * Set attribute and increment number of needed guesses
     *
     * @param latestGuess The user's latest guess
     */
    public void setLatestGuess(int latestGuess) {
        this.latestGuess = latestGuess;
        this.nbGuesses++;
    }

    /**
     * Compute hint for next move or success message.
     * <p>
     * Rules are:
     * - 'UPPER CASE' message if the guess lies within a very small margin next to the answer in the interval
     * - 'Capitalized Case' message if the guess lies within a relatively small margin
     * - 'lower case' message in other cases
     *
     * see 'VERY_SMALL_MARGIN' for the definition of the very small margin
     * see 'RELATIVELY_SMALL_MARGIN' for the definition of the relatively small margin
     *
     * @return A message containing a hint for the next move to play
     */
    public String getHintForNextMove() {
        int differenceToAnswer = this.answer - this.latestGuess;
        if (differenceToAnswer == 0) {
            // Success case
            return String.format("You win! Needed: %d guesses", this.nbGuesses);
        } else {
            // TODO: here possibility to add more sentences to make the game less boring
            String result;

            // First, compute unformatted result
            if (differenceToAnswer < 0) {
                // Too large
                result = this.ui.getTooLargeMessage();
            } else {
                // Too small
                result = this.ui.getTooLowMessage();
            }

            // Then, format to right case
            int intervalSize = this.upperBound - this.lowerBound;
            StringBuilder formattedResult = new StringBuilder(ui.getHintPrefix());
            if (Math.abs(differenceToAnswer) < VERY_SMALL_MARGIN * intervalSize) {
                // Very close
                formattedResult.append(utils.sentenceToUpperCase(result));
            } else if (Math.abs(differenceToAnswer) < RELATIVELY_SMALL_MARGIN * intervalSize) {
                // Relatively close
                formattedResult.append(utils.sentenceToCapitalizedCase(result));
            } else {
                // Nothing
                formattedResult.append(utils.sentenceToLowerCase(result));
            }

            // Finally, return formatted result
            return formattedResult.toString();
        }
    }

    public boolean isFinished() {
        return this.answer == this.latestGuess;
    }

    public int getLatestGuess() {
        return this.latestGuess;
    }

    public int getNbGuesses() {
        return this.nbGuesses;
    }
}
