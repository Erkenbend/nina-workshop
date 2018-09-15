package things;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeirdClassTest {

    @Test
    void sumUnderConditionsWithoutFirstMagicWord() {
        int[] integersToSum = new int[] {2, 5, -4, 10};
        String secondMagicWord = "Thanks";
        String expectedMessage = "YOU FORGOT THE MAGIC WORDS!";

        Exception e1 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions("Hey", integersToSum, secondMagicWord)
        );
        assertEquals(expectedMessage, e1.getMessage());

        Exception e2 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions("", integersToSum, secondMagicWord)
        );
        assertEquals(expectedMessage, e2.getMessage());

        Exception e3 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions(null, integersToSum, secondMagicWord)
        );
        assertEquals(expectedMessage, e3.getMessage());
    }

    @Test
    void sumUnderConditionsWithoutSecondMagicWord() {
        int[] integersToSum = new int[] {2, 5, -4, 10};
        String firstMagicWord = "Please";
        String expectedMessage = "YOU FORGOT THE MAGIC WORDS!";

        Exception e1 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions(firstMagicWord, integersToSum, "Quick")
        );
        assertEquals(expectedMessage, e1.getMessage());

        Exception e2 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions(firstMagicWord, integersToSum, "")
        );
        assertEquals(expectedMessage, e2.getMessage());

        Exception e3 = assertThrows(
                Exception.class,
                () -> WeirdClass.sumUnderConditions(firstMagicWord, integersToSum, null)
        );
        assertEquals(expectedMessage, e3.getMessage());
    }

    @Test
    void sumUnderConditionsWithEmptyArray() {
        int[] integersToSum = new int[0];

        try {
            int result = WeirdClass.sumUnderConditions("Please", integersToSum, "Thanks");
            assertEquals(0, result);
        } catch (Exception e) {
            fail("Did not expect Exception here");
        }
    }

    @Test
    void sumUnderConditionsWithDifferentCases() {
        int[] integersToSum = new int[0];

        try {
            WeirdClass.sumUnderConditions("Please", integersToSum, "ThanKs");
            WeirdClass.sumUnderConditions("PleasE", integersToSum, "Thanks");
            WeirdClass.sumUnderConditions("PLEASE", integersToSum, "Thanks");
            WeirdClass.sumUnderConditions("plEaSE", integersToSum, "THANKS");
            WeirdClass.sumUnderConditions("please", integersToSum, "Thanks");
        } catch (Exception e) {
            fail("Did not expect Exception here");
        }
    }

    @Test
    void sumUnderConditionsWithOnlyPositiveIntegers() {
        int[] integersToSum = new int[] {2, 6, 10, 4};

        try {
            int result = WeirdClass.sumUnderConditions("Please", integersToSum, "Thanks");
            assertEquals(22, result);
        } catch (Exception e) {
            fail("Did not expect Exception here");
        }
    }

    @Test
    void sumUnderConditionsWithPositiveAndNegativeIntegers() {
        int[] integersToSum = new int[] {2, -6, 10, 4, -1};

        try {
            int result = WeirdClass.sumUnderConditions("Please", integersToSum, "Thanks");
            assertEquals(16, result);
        } catch (Exception e) {
            fail("Did not expect Exception here");
        }
    }

    @Test
    void sumUnderConditionsWithOnlyNegativeIntegers() {
        int[] integersToSum = new int[] {-2, -6, -10, -4};

        try {
            int result = WeirdClass.sumUnderConditions("Please", integersToSum, "Thanks");
            assertEquals(0, result);
        } catch (Exception e) {
            fail("Did not expect Exception here");
        }
    }
}