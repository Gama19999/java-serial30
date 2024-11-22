package ovh.serial30.s30api.services;

import ovh.serial30.s30api.entities.UserEntity;
import ovh.serial30.s30api.exceptions.ProjectNotFoundEx;
import ovh.serial30.s30api.exceptions.RoleNotFoundEx;
import ovh.serial30.s30api.exceptions.UserNotFoundEx;
import ovh.serial30.s30api.exceptions.UserWrongCredentialsEx;
import ovh.serial30.s30api.pojos.request.UserUpdate;

import java.util.UUID;

public interface UsersService {
    /**
     * Saves user's data to server database
     * @param entity UserEntity object
     * @return User's generated ID
     */
    UUID registerUser(UserEntity entity);

    /**
     * Updates user's data. <b>[Secured method]</b>
     * @param request User update data representation
     * @return User's generated ID
     * @throws UserNotFoundEx If user does not exist
     * @throws UserWrongCredentialsEx If user credentials do not match the ones in server database
     * @throws RoleNotFoundEx If role name does not exist
     * @throws ProjectNotFoundEx If project does not exist
     */
    String updateUserData(UserUpdate request) throws UserNotFoundEx, UserWrongCredentialsEx, RoleNotFoundEx, ProjectNotFoundEx;

    /**
     * Gets User's ID from given username
     * @param username User's username
     * @return User's ID
     * @throws UserNotFoundEx If user does not exist
     */
    UUID getIdByUsername(String username) throws UserNotFoundEx;
}
