package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when attempting to use an invalid token
 */
public class TokenInvalidEx extends GeneralAppException {
    /**
     * Creates exception for attempting to use an invalid token
     */
    public TokenInvalidEx() {
        super(403, Const.Logs.Exceptions.TOKEN403);
    }
}
