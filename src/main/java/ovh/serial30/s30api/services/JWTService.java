package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.UserNotFoundEx;

import java.util.UUID;

public interface JWTService {
    /**
     * Generates a new token for a specific user
     * @param userId User's generated ID
     * @return Generated user's token
     * @throws UserNotFoundEx If user does not exist
     */
    String generateToken(UUID userId) throws UserNotFoundEx;

    /**
     * Validates token authenticity
     * @param token User token
     * @return {@code true} if token is valid. {@code false} otherwise.
     */
    boolean isValidToken(String token);
}
