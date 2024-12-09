package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when attempting to renew a +1 month older expired user token
 */
public class TokenNotRenewableEx extends GeneralAppException {
    /**
     * Creates exception for attempting to renew a +1 month older expired user token
     */
    public TokenNotRenewableEx() {
        super(401, Const.Logs.Exceptions.TOKEN401);
    }
}
