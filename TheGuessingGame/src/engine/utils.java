package engine;

import exceptions.ExceptionCodes;
import exceptions.InvalidBoundsException;

import java.util.Random;

public class utils {

    /**
     * Generate an integer in the interval [lowerBound, upperBound] (both inclusive)
     * at random following a uniform distribution
     *
     * @param lowerBound The lower bound of the interval
     * @param upperBound The upper bound of the interval
     * @return a uniformly random drawn integer in the interval
     * @throws InvalidBoundsException if lowerBound &gt; upperBound
     */
    public static int generateRandomAnswerInInterval(int lowerBound, int upperBound) throws InvalidBoundsException {
        Random rd = new Random();
        try {
            return lowerBound + rd.nextInt(1 + upperBound - lowerBound);
        } catch (IllegalArgumentException e) {
            throw new InvalidBoundsException(
                    ExceptionCodes.LOWER_BOUND_GREATER_THAN_UPPER_BOUND,
                    String.format("Unable to generate random answer: [%d, %d] is not an interval",
                            lowerBound, upperBound)
            );
        }
    }

    /**
     * Upper bound for Level i (starting with 1): 10^(i+1)
     *
     * @param level The input level
     * @return The computed upper bound
     */
    public static int computeUpperBoundForLevel(int level) {
        return (int) Math.pow(10, level + 1);
    }

    private static String wordToCapitalizedCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public static String sentenceToUpperCase(String str) {
        return str.toUpperCase();
    }

    public static String sentenceToCapitalizedCase(String str) {
        String[] strAsArray = str.split(" ");
        StringBuilder res = new StringBuilder();
        for (String word : strAsArray) {
            res.append(utils.wordToCapitalizedCase(word)).append(" ");
        }
        // Compile String and remove trailing whitespace
        return res.toString().trim();
    }

    public static String sentenceToLowerCase(String str) {
        return str.toLowerCase();
    }
}
