package exceptions;

/**
 * Exception class for invalid bounds while creating game
 * <p>
 * Codes: 10xx
 */
public class InvalidBoundsException extends MyException {

    public InvalidBoundsException(int code, String message) {
        super(code, message);
    }
}
