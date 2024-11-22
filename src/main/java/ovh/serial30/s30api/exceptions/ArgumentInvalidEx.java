package ovh.serial30.s30api.exceptions;

import ovh.serial30.s30api.utilities.Const;

/**
 * Thrown when an argument initialization value is invalid
 */
public class ArgumentInvalidEx extends GeneralAppException {
    /**
     * Creates exception for an invalid value argument initialization
     * @param argumentName Argument name
     * @param klazz Exception triggering class
     * @param method Method which triggered exception
     */
    public ArgumentInvalidEx(String argumentName, String klazz, String method) {
        super(422, Const.Logs.Exceptions.ARGUMENT422
                .replaceFirst(Const.Logs.$, argumentName)
                .replaceFirst(Const.Logs.$, klazz + "." + method));
    }
}
