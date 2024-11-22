package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when user submits wrong credentials
 */
public class UserWrongCredentialsEx extends GeneralAppException {
    /**
     * Creates exception for user's wrong credentials
     */
    public UserWrongCredentialsEx() {
        super(401, Const.Logs.Exceptions.USER401);
    }
}
