package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when attempting to register a user with duplicate username
 */
public class UserAlreadyExistsEx extends GeneralAppException {
    /**
     * Creates exception for attempting to register a user with duplicate username
     * @param username User's username
     */
    public UserAlreadyExistsEx(String username) {
        super(422, Const.Logs.Exceptions.USER422.replaceFirst(Const.Logs.$, username));
    }
}
