package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserAlreadyExistsEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.pojos.request.UserLoginRequest;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;

import java.util.UUID;

public interface AuthenticationService {
    /**
     * Handles user login
     * @param login User's login data
     * @return Logged user ID
     * @throws UserNotFoundEx If user does not exist
     */
    UUID login(UserLoginRequest login) throws UserNotFoundEx;

    /**
     * Handles user registration
     * @param signup User's registration data
     * @return User's generated ID
     * @throws ProjectNotFoundEx If project does not exist on server database
     * @throws RoleNotFoundEx If role does not exist
     * @throws UserAlreadyExistsEx If the user to register is a duplicate server stored username
     */
    UUID register(UserSignupRequest signup) throws ProjectNotFoundEx, RoleNotFoundEx, UserAlreadyExistsEx;
}
