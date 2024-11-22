package ovh.serial30.s30api.pojos.response;

/**
 * User login representation
 * @param token User's token
 * @param expiration User's token expiration in milliseconds
 */
public record LoginResponse(String token, long expiration) {}
