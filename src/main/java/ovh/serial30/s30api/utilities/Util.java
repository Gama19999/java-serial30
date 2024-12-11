package ovh.serial30.s30api.utilities;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Stores commonly used methods
 */
public final class Util {
    private Util() {}
    /**
     * Checks if passed route is public route
     * @param route Requested route
     * @return {@code true} if route is public - {@code false} otherwise
     */
    public static boolean isPublicRoute(String route) {
        var firstPath = route.split(Const.Routes.CONTEXT)[1];
        return firstPath.startsWith(Const.Routes.STATUS) ||
               firstPath.startsWith(Const.Routes.PROJECTS) ||
               firstPath.startsWith(Const.Routes.AUTH) ||
               firstPath.equals(Const.Routes.USERS + Const.Routes.REGISTER);
    }

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

    /**
     * Checks whether <b>d1</b> is a date before <b>d2</b>
     * @param d1 Date one
     * @param d2 Date two
     * @return {@code true} if d1 is before d2 - {@code false} otherwise
     */
    public static boolean beforeThan(Object d1, Object d2) {
        return ((Date) d1).before((Date) d2);
    }
}
