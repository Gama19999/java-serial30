package ovh.serial30.s30api.services;

import ovh.serial30.s30api.entities.UserEntity;
import ovh.serial30.s30api.exceptions.*;
import ovh.serial30.s30api.pojos.request.UserUpdateRequest;

import java.util.UUID;

public interface UsersService {
    /**
     * Saves user's data to server database
     * @param userEnt UserEntity object
     * @return User's generated ID
     * @throws UserAlreadyExistsEx If the user to register is a duplicate server stored username
     */
    UUID registerUser(UserEntity userEnt) throws UserAlreadyExistsEx;

    /**
     * Updates user's data. <b>[Secured method]</b>
     * @param update User update data representation
     * @return User's generated ID
     * @throws UserNotFoundEx If user does not exist
     * @throws UserWrongCredentialsEx If user credentials do not match the ones in server database
     * @throws RoleNotFoundEx If role name does not exist
     * @throws ProjectNotFoundEx If project does not exist
     */
    String updateUserData(UserUpdateRequest update) throws UserNotFoundEx, UserWrongCredentialsEx, RoleNotFoundEx, ProjectNotFoundEx;

    /**
     * Gets User's ID from given username
     * @param username User's username
     * @return User's ID
     * @throws UserNotFoundEx If user does not exist
     */
    UUID getIdByUsername(String username) throws UserNotFoundEx;
}
