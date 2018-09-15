package engine;


import exceptions.ExceptionCodes;
import exceptions.InvalidBoundsException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ui.MyConsoleUI;
import ui.UserInterface;

class GameTest {

    @Test
    void checkBoundsValidityShouldReturnTrue() {
        // Nothing to assert here: the void method will return nothing if the check passed
        // and throw an exception if it failed.

        try {
            // Normal cases
            Game.checkBoundsValidity(1, 10, 3);
            Game.checkBoundsValidity(-100, 100, 98);
            Game.checkBoundsValidity(0, 10, 1);
            Game.checkBoundsValidity(1, 3, 2);

            // Edge cases
            Game.checkBoundsValidity(1, 10, 10);
            Game.checkBoundsValidity(1, 10, 1);
            Game.checkBoundsValidity(8, 8, 8);
        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void checkBoundsValidityShouldThrowException() {
        // Lower > Upper with answer in between
        InvalidBoundsException e1 = assertThrows(
                InvalidBoundsException.class,
                () -> Game.checkBoundsValidity(100, 0, 50)
        );
        assertEquals(ExceptionCodes.LOWER_BOUND_GREATER_THAN_UPPER_BOUND, e1.getCode());

        // Lower > Upper with answer outside
        InvalidBoundsException e2 = assertThrows(
                InvalidBoundsException.class,
                () -> Game.checkBoundsValidity(100, 0, 150)
        );
        assertEquals(ExceptionCodes.LOWER_BOUND_GREATER_THAN_UPPER_BOUND, e2.getCode());

        // Answer > Greater
        InvalidBoundsException e3 = assertThrows(
                InvalidBoundsException.class,
                () -> Game.checkBoundsValidity(0, 100, 101)
        );
        assertEquals(ExceptionCodes.ANSWER_GREATER_THAN_UPPER_BOUND, e3.getCode());

        // Answer < Lower
        InvalidBoundsException e4 = assertThrows(
                InvalidBoundsException.class,
                () -> Game.checkBoundsValidity(-50, 50, -100)
        );
        assertEquals(ExceptionCodes.ANSWER_LOWER_THAN_LOWER_BOUND, e4.getCode());
    }

    @Test
    void setLatestGuess() {
        try {
            // Initialize
            Game g = new Game(0, 10, 8, new MyConsoleUI());
            assertEquals(0, g.getNbGuesses());

            // Check that latestGuess is set and nbGuesses is incremented at each turn
            int expectedNbGuesses = 0;
            for (int guess : new int[]{5, 7, 9, 8}) {
                g.setLatestGuess(guess);
                expectedNbGuesses++;
                assertEquals(guess, g.getLatestGuess());
                assertEquals(expectedNbGuesses, g.getNbGuesses());
            }
        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void getHintForNextMoveWithVerySmallMargin() {
        try {
            // Initialize
            UserInterface ui = new MyConsoleUI();
            Game g = new Game(0, 100, 80, ui);
            g.VERY_SMALL_MARGIN = 0.1;
            g.RELATIVELY_SMALL_MARGIN = 0.2;
            String hint;

            // Check that the hint is uppercase for very close answers (here +/- 10%)
            g.setLatestGuess(81);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToUpperCase(ui.getTooLargeMessage()), hint);

            g.setLatestGuess(79);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToUpperCase(ui.getTooLowMessage()), hint);

            g.setLatestGuess(89);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToUpperCase(ui.getTooLargeMessage()), hint);

            g.setLatestGuess(71);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToUpperCase(ui.getTooLowMessage()), hint);

        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void getHintForNextMoveWithRelativelySmallMargin() {
        try {
            // Initialize
            UserInterface ui = new MyConsoleUI();
            Game g = new Game(0, 100, 80, ui);
            g.VERY_SMALL_MARGIN = 0.1;
            g.RELATIVELY_SMALL_MARGIN = 0.2;
            String hint;

            // Check that the hint is capitalized for relatively close answers (here +/- 20% but not less than 10%)
            g.setLatestGuess(90);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToCapitalizedCase(ui.getTooLargeMessage()), hint);

            g.setLatestGuess(70);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToCapitalizedCase(ui.getTooLowMessage()), hint);

            g.setLatestGuess(99);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToCapitalizedCase(ui.getTooLargeMessage()), hint);

            g.setLatestGuess(61);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToCapitalizedCase(ui.getTooLowMessage()), hint);

        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void getHintForNextMoveWithBigMargin() {
        try {
            // Initialize
            UserInterface ui = new MyConsoleUI();
            Game g = new Game(0, 100, 80, ui);
            g.VERY_SMALL_MARGIN = 0.1;
            g.RELATIVELY_SMALL_MARGIN = 0.2;
            String hint;

            // Check that the hint is lowercase for not so close answers (here more than +/- 20%)
            g.setLatestGuess(100);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToLowerCase(ui.getTooLargeMessage()), hint);

            g.setLatestGuess(60);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToLowerCase(ui.getTooLowMessage()), hint);

            g.setLatestGuess(12);
            hint = g.getHintForNextMove();
            assertEquals(ui.getHintPrefix() + utils.sentenceToLowerCase(ui.getTooLowMessage()), hint);

        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void getHintForNextMoveWithRightGuess() {
        try {
            // Initialize
            UserInterface ui = new MyConsoleUI();
            int answer = 42;
            Game g = new Game(0, 100, answer, ui);
            String hint;

            g.setLatestGuess(answer);
            hint = g.getHintForNextMove();
            assertEquals(String.format(ui.getWinMessage(), 1), hint);

        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }

    @Test
    void isFinished() {
        try {
            // Initialize
            UserInterface ui = new MyConsoleUI();
            int answer = 42;
            Game g = new Game(0, 100, answer, ui);

            g.setLatestGuess(answer + 15);
            g.getHintForNextMove();
            assertFalse(g.isFinished());

            g.setLatestGuess(answer - 5);
            g.getHintForNextMove();
            assertFalse(g.isFinished());

            g.setLatestGuess(answer);
            g.getHintForNextMove();
            assertTrue(g.isFinished());

        } catch (InvalidBoundsException e) {
            fail("Did not expect Exception");
        }
    }
}