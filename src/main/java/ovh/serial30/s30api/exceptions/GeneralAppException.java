package ovh.serial30.s30api.exceptions;

/**
 * General s30-api application exception
 */
public class GeneralAppException extends Exception {
    public final int code;

    /**
     * Creates a general application exception
     * @param code HTTP status code
     * @param message Exception message
     */
    public GeneralAppException(int code, String message) {
        super(message);
        this.code = code;
    }
}
