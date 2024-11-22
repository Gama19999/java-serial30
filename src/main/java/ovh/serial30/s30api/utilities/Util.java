package ovh.serial30.s30api.utilities;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Stores commonly used methods
 */
public class Util {
    private Util() {}

    /**
     * Validates a String
     * @param str String to evaluate
     * @return {@code true} if it's an invalid String. {@code false} otherwise.
     */
    public static boolean invalidStr(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    /**
     * Converts String to UUID
     * @param uuid_str String to convert
     * @return UUID representation
     */
    public static UUID touuid(String uuid_str) {
        return UUID.fromString(uuid_str);
    }

    /**
     * Converts UUID to String
     * @param uuid UUID to convert
     * @return String representation
     */
    public static String tostr(UUID uuid) {
        return uuid.toString();
    }

    /**
     * Sets Timestamp passed reference to the current
     */
    public static Timestamp setCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Checks if the passed role is invalid
     * @param role Role to evaluate
     * @return {@code true} if it's an invalid Role. {@code false} otherwise.
     */
    public static boolean invalidRole(String role) {
        return !Const.ROLES.contains(role);
    }
}