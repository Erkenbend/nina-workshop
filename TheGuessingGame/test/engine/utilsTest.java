package engine;

import exceptions.ExceptionCodes;
import exceptions.InvalidBoundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class utilsTest {

    @Test
    void generateRandomAnswerInIntervalShouldSucceed() {
        // Init
        int generatedAnswer;

        // Normal case
        try {
            int lowerBound = 0;
            int upperBound = 100;
            generatedAnswer = utils.generateRandomAnswerInInterval(lowerBound, upperBound);
            assertTrue(generatedAnswer >= lowerBound);
            assertTrue(generatedAnswer <= upperBound);
        } catch (InvalidBoundsException e) {
            Assertions.fail("Did not expect Exception");
        }

        // Edge case
        try {
            int lowerAndUpperBound = 8;
            generatedAnswer = utils.generateRandomAnswerInInterval(lowerAndUpperBound, lowerAndUpperBound);
            assertEquals(lowerAndUpperBound, generatedAnswer);
        } catch (InvalidBoundsException e) {
            Assertions.fail("Did not expect Exception");
        }
    }

    @Test
    void generateRandomAnswerInIntervalShouldThrowException() {
        InvalidBoundsException e = assertThrows(
                InvalidBoundsException.class,
                () -> utils.generateRandomAnswerInInterval(60, 50)
        );
        assertEquals(ExceptionCodes.LOWER_BOUND_GREATER_THAN_UPPER_BOUND, e.getCode());
    }

    @Test
    void computeUpperBoundForLevel() {
        assertEquals(100, utils.computeUpperBoundForLevel(1));
        assertEquals(1000000, utils.computeUpperBoundForLevel(5));
    }

    @Test
    void sentenceToUpperCase() {
        assertEquals(
                "TEST SENTENCE: UPPER CASE",
                utils.sentenceToUpperCase("Test sentence: UppER cAse")
        );
    }

    @Test
    void sentenceToCapitalizedCase() {
        assertEquals(
                "Test Sentence: Capitalized Case",
                utils.sentenceToCapitalizedCase("Test sentence: cAPITaliZed cAse")
        );
    }

    @Test
    void sentenceToLowerCase() {
        assertEquals(
                "test sentence: lower case",
                utils.sentenceToLowerCase("Test sentence: LowER cAse")
        );
    }
}