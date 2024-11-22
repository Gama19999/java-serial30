package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLogin;
import ovh.serial30.s30api.pojos.request.UserRegistration;

import java.util.UUID;

public interface AuthService {
    /**
     * Handles user login
     * @param login User's login data
     * @return Logged user ID
     * @throws UserNotFoundEx If user does not exist
     */
    UUID login(UserLogin login) throws UserNotFoundEx;

    /**
     * Handles user registration
     * @param registration User's registration data
     * @return User's generated ID
     * @throws ProjectNotFoundEx If project does not exist on server database
     * @throws RoleNotFoundEx If role does not exist
     */
    UUID register(UserRegistration registration) throws ProjectNotFoundEx, RoleNotFoundEx;
}
