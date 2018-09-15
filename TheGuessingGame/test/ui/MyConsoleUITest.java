package ui;

import org.junit.jupiter.api.Test;
import org.omg.CORBA.IntHolder;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MyConsoleUITest {

    @Test
    void displayBoundChoiceOneTry() {
        // Init
        InputStream mockInput = new MockInput("2");
        MyConsoleUI ui = new MyConsoleUI(mockInput);
        int expectedLowerBound = 0;
        int expectedUpperBound = 1000;
        IntHolder lowerBoundHolder = new IntHolder();
        IntHolder upperBoundHolder = new IntHolder();

        // Act
        ui.displayBoundChoice(lowerBoundHolder, upperBoundHolder);

        // Assert
        assertEquals(expectedLowerBound, lowerBoundHolder.value);
        assertEquals(expectedUpperBound, upperBoundHolder.value);
    }

    @Test
    void displayBoundChoiceMultipleTries() {
        // Init
        InputStream mockInput = new MockInput(new String[] {
                "asdf",  // Not an integer
                "s4f5vdsv1",  // Not an integer
                "0",  // Too small
                "12",  // Too big
                "4"  // OK
        });
        MyConsoleUI ui = new MyConsoleUI(mockInput);
        int expectedLowerBound = 0;
        int expectedUpperBound = 100000;
        IntHolder lowerBoundHolder = new IntHolder();
        IntHolder upperBoundHolder = new IntHolder();

        // Act
        ui.displayBoundChoice(lowerBoundHolder, upperBoundHolder);

        // Assert
        assertEquals(expectedLowerBound, lowerBoundHolder.value);
        assertEquals(expectedUpperBound, upperBoundHolder.value);
    }

    @Test
    void displayNextGuessPromptOneTry() {
        // Init
        InputStream mockInput = new MockInput("23");
        MyConsoleUI ui = new MyConsoleUI(mockInput);
        int expectedGuess = 23;
        IntHolder guessHolder = new IntHolder();

        // Act
        ui.displayNextGuessPrompt(guessHolder);

        // Assert
        assertEquals(expectedGuess, guessHolder.value);
    }

    @Test
    void displayNextGuessPromptMultipleTries() {
        // Init
        InputStream mockInput = new MockInput(new String[] {
                "asdf",  // Not an integer
                "s4f5vdsv1",  // Not an integer
                "50"  // Acceptable
        });
        MyConsoleUI ui = new MyConsoleUI(mockInput);
        int expectedGuess = 50;
        IntHolder guessHolder = new IntHolder();

        // Act
        ui.displayNextGuessPrompt(guessHolder);

        // Assert
        assertEquals(expectedGuess, guessHolder.value);
    }
}