package ovh.serial30.s30api.services;

import java.util.UUID;

public interface UtilityService {
    /**
     * Gets user ID out of token from current request
     * @return User's ID
     */
    String getRequestUserId();

    /**
     * Checks whether user ID exists on server database
     * @param userId User's ID
     * @return {@code true} if user is registered. {@code false} otherwise.
     */
    boolean userExists(UUID userId);

    /**
     * Checks whether username exists on server database
     * @param username User's username
     * @return {@code true} if user exists. {@code false} otherwise.
     */
    boolean userExists(String username);
}
