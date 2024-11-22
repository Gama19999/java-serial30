package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when querying info for an unknown user
 */
public class UserNotFoundEx extends GeneralAppException {
    /**
     * Creates exception for querying info of an unknown user
     * @param username User's username
     */
    public UserNotFoundEx(String username) {
        super(404, Const.Logs.Exceptions.USER404.replaceFirst(Const.Logs.$, username));
    }
}
