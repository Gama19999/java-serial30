package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when querying info for an unknown role
 */
public class RoleNotFoundEx extends GeneralAppException {
    /**
     * Creates exception for querying info of an unknown role
     * @param roleName Role's name
     */
    public RoleNotFoundEx(String roleName) {
        super(404, Const.Logs.Exceptions.ROLE404.replaceFirst(Const.Logs.$, roleName));
    }
}
