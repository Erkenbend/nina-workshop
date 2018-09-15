package things;

import java.util.Arrays;

public class WeirdClass {
    /**
     * This function outputs the sum of all integers in the middle argument under several conditions.
     * The user has to use the magic words "Please" and "Thanks" (case insensitive) at the beginning
     * and end of their request. If they don't, an exception is thrown with the message "YOU FORGOT
     * THE MAGIC WORDS!". In addition, only positive integers should be considered. Negative integers
     * in the input array must be ignored.
     *
     * @param magicWordNumberOne The magic word to use when asking something
     * @param integersToSum The array of integers to be summed
     * @param magicWordNumberTwo The magic word to use when receiving something
     * @return The sum of the positive integers in the input array
     * @throws Exception if the magic words are not correct
     */
    public static int sumUnderConditions(String magicWordNumberOne, int[] integersToSum, String magicWordNumberTwo) throws Exception {
        if (!("Please".equalsIgnoreCase(magicWordNumberOne) && "Thanks".equalsIgnoreCase(magicWordNumberTwo))) {
            throw new Exception("YOU FORGOT THE MAGIC WORDS!");
        }
        return Arrays.stream(integersToSum).reduce(0, (a, b) -> a + Math.max(0, b));
    }
}
