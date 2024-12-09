package ovh.serial30.s30api.services;

import ovh.serial30.s30api.exceptions.*;
import ovh.serial30.s30api.pojos.request.UserSignupRequest;
import ovh.serial30.s30api.pojos.request.UserUpdateRequest;
import ovh.serial30.s30api.pojos.response.UserResponse;

import java.util.UUID;

public interface UsersService {
    /**
     * Handles user registration
     * @param request UserSignupRequest object
     * @return User's generated ID
     * @throws UserAlreadyExistsEx If the user to register is a duplicate server stored username
     * @throws RoleNotFoundEx If role does not exist
     * @throws ProjectNotFoundEx If project does not exist on server database
     */
    UUID registerUser(UserSignupRequest request) throws UserAlreadyExistsEx, RoleNotFoundEx, ProjectNotFoundEx;

    /**
     * Updates user's data. <b>[Token secured method]</b>
     * @param update User update data representation
     * @return User's generated ID
     * @throws UserNotFoundEx If user does not exist
     * @throws UserWrongCredentialsEx If user credentials do not match the ones in server database
     * @throws RoleNotFoundEx If role name does not exist
     * @throws ProjectNotFoundEx If project does not exist
     */
    UserResponse updateUserData(UUID userId, UserUpdateRequest update) throws UserNotFoundEx, UserWrongCredentialsEx, RoleNotFoundEx, ProjectNotFoundEx;
}
