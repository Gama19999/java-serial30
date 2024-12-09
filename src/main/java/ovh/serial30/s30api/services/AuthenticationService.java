package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;

import java.util.UUID;

public interface AuthenticationService {
    /**
     * Handles user login
     * @param login User's login data
     * @return Logged user ID
     * @throws UserNotFoundEx If user does not exist
     */
    UUID login(UserLoginRequest login) throws UserNotFoundEx;
}
