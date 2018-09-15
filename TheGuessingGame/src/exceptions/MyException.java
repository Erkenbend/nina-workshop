package exceptions;

public abstract class MyException extends Exception {
    private int code = 1;

    public MyException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
