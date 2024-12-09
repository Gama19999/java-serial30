package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.TokenInvalidEx;
import ovh.serial30.s30api.exceptions.TokenNotRenewableEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;

import java.util.UUID;

public interface JWTService {
    /**
     * Generates a new token for a specific user<br><b>Token has: 900000ms (15 min) of expiration time</b>
     * @param userId User's generated ID
     * @return Generated user's token
     * @throws UserNotFoundEx If user does not exist
     */
    String generateToken(UUID userId) throws UserNotFoundEx;

    /**
     * Renews token for specified user
     * @param token User's expired token
     * @return Renewed user's token
     * @throws UserNotFoundEx If user does not exist
     * @throws TokenInvalidEx If expired token is invalid
     * @throws TokenNotRenewableEx If expired token is +7 days older
     */
    String renewToken(String token) throws UserNotFoundEx, TokenInvalidEx, TokenNotRenewableEx;

    /**
     * Extracts user's ID from request token
     * @param token User's token
     * @return Token user's ID
     * @throws TokenInvalidEx If token is invalid
     */
    UUID getUserID(String token) throws TokenInvalidEx;
}
